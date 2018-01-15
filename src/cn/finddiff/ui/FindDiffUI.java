package cn.finddiff.ui;

import cn.finddiff.util.AdbHelper;
import cn.finddiff.util.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by IntelliJ IDEA.
 * GridLayoutDemo.java
 *
 * @author Eric
 * @date 2018/1/3 14:15
 * Description
 */
public class FindDiffUI extends JFrame {

    JButton refreshButton = new JButton("Refresh");
    // GridLayout experimentLayout = new GridLayout(0, 2);
    GridLayout experimentLayout = new GridLayout(1, 1);

    private static final String PATH = Utils.getCurrFilePath();

    public FindDiffUI(String name) {
        super(name);
        setResizable(false);
    }

    public void initGaps() {
    }

    public void addComponentsToPane(final Container pane) {
        initGaps();
        final JPanel compsToExperiment = new JPanel();
        compsToExperiment.setLayout(experimentLayout);
        JPanel controls = new JPanel();
        // controls.setLayout(new GridLayout(2, 3));
        controls.setLayout(new GridLayout(1, 1));

        //Set up components preferred size
        // JButton b = new JButton("Just fake button");
        // Dimension buttonSize = b.getPreferredSize();
        // compsToExperiment.setPreferredSize(new Dimension((int) (buttonSize.getWidth() * 2.5) + maxGap,
        //         (int) (buttonSize.getHeight() * 3.5) + maxGap * 2));
        compsToExperiment.setPreferredSize(new Dimension(DiffHelper.IMG_WIDTH, DiffHelper.IMG_HEIGHT));


        // Add buttons to experiment with Grid Layout
        ImagePanel imgPanel = new ImagePanel();
        compsToExperiment.add(imgPanel);

        // Add controls to set up horizontal and vertical gaps
        controls.add(refreshButton);

        // Process the refresh button press
        refreshButton.addActionListener(e -> {
            // 删除上一张图片
            DiffHelper.delOldImage(fileName);

            AdbHelper.screen();
            AdbHelper.pull();
            // 查找图片中的不同之处,并保存到一个新的文件中,文件名:fileName
            DiffHelper.findDiff();

            // String fileName = "_ori_diff.png";
            imgPanel.setFileName(fileName);
            imgPanel.repaint();
            System.out.println("refresh finish.");
        });
        pane.add(compsToExperiment, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(controls, BorderLayout.SOUTH);
    }

    public static String fileName = "";

    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        FindDiffUI frame = new FindDiffUI("找茬");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        // Display the window.
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent e) {
                DiffHelper.delOldImage(fileName);
            }

            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(FindDiffUI::createAndShowGUI);
    }

    class ImagePanel extends JPanel {
        private String fileName = null;
        public void paint(Graphics g) {
            super.paint(g);
            ImageIcon icon = new ImageIcon(getFilePath());
            g.drawImage(icon.getImage(), 0, 0, DiffHelper.IMG_WIDTH, DiffHelper.IMG_HEIGHT, this);
        }

        public void addMouseClickListener() {
            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // double x = e.getPoint().getX() + DiffHelper.LEFT;
                    // double y = e.getPoint().getY() + DiffHelper.TOP + DiffHelper.HEIGHT;
                    double x = e.getPoint().getX();
                    double y = e.getPoint().getY();

                    System.out.println(String.format("click x: %f, y: %f", x, y));
                    AdbHelper.tap((int) x * 2, (int) y * 2);
                }

                @Override
                public void mousePressed(MouseEvent e) {}
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
            });
        }

        ImagePanel() {
            this.addMouseClickListener();
        }
        public ImagePanel(String fileName) {
            this.fileName = fileName;
            this.addMouseClickListener();
        }

        private String getFilePath() {
            return PATH + (fileName == null ? "finddiff.png" : fileName);
        }

        void setFileName(String fileName) {
            this.fileName = fileName;
        }
    }
}

