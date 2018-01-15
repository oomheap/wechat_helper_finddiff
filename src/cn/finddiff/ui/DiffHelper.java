package cn.finddiff.ui;

import cn.finddiff.device.DeviceConfig;
import cn.finddiff.device.PropertiesLoader;
import cn.finddiff.ui.FindDiffUI;
import cn.finddiff.util.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Intellij IDEA.
 * Date  : 2018/1/3 00:27
 *
 * @author Eric Cui
 * Desc  :
 */
public class DiffHelper {

    public static int TOP;
    public static int LEFT;
    public static int WIDTH;
    public static int HEIGHT;
    public static int GAP;
    public static int IMG_WIDTH;
    public static int IMG_HEIGHT;
    private static final PropertiesLoader PROPERTIES_LOADER
            = PropertiesLoader.getInstance();

    static {
        final DeviceConfig deviceConfig = PROPERTIES_LOADER.getDeviceConfig("MX2");
        TOP = deviceConfig.getTOP();
        LEFT = deviceConfig.getLEFT();
        WIDTH = deviceConfig.getWIDTH();
        HEIGHT = deviceConfig.getHEIGHT();
        GAP = deviceConfig.getGAP();
        IMG_WIDTH = deviceConfig.getIMG_WIDTH() / 2;
        IMG_HEIGHT = deviceConfig.getIMG_HEIGHT() / 2;
    }

    private static final String path = Utils.getCurrFilePath();

    public static void main(String[] args) throws IOException {
        String fileName = "finddiff.png";
        // fileName = "finddiff_room.png";
        File file = new File(path + fileName);
        if (!file.exists()) {
            System.out.println("file not exist");
        }
        File oridDiffFile = new File(path + "_ori_diff.png");
        if (!oridDiffFile.exists()) {
            oridDiffFile.createNewFile();
        }
        File diffFile = new File(path + "_diff.png");
        if (!diffFile.exists()) {
            diffFile.createNewFile();
        }
        /*File topImg = new File(path + "_top.png");
        if (!topImg.exists()) {
            topImg.createNewFile();
        }
        File bottomImg = new File(path + "_bottom.png");
        if (!bottomImg.exists()) {
            bottomImg.createNewFile();
        }*/

        BufferedImage bufImg = ImageIO.read(file);
        BufferedImage diffBufImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // BufferedImage topBufImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        // BufferedImage bottomBufImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        int topRgb, bottomRgb;
        for (int x = LEFT; x < LEFT + WIDTH; x++) {
            for (int y = TOP; y < TOP + HEIGHT; y++) {
                topRgb = bufImg.getRGB(x, y);
                bottomRgb = bufImg.getRGB(x, y + HEIGHT + GAP);

                // topBufImg.setRGB(x - LEFT, y - TOP, topRgb);
                // bottomBufImg.setRGB(x - LEFT, y - TOP, bottomRgb);

                try {
                    if (topRgb != bottomRgb
                            && !colorSimilar(topRgb, bottomRgb)) {
                        bufImg.setRGB(x, y + HEIGHT + GAP, Color.GREEN.getRGB());
                        diffBufImg.setRGB(x - LEFT, y - TOP, Color.GREEN.getRGB());
                    } else {
                        // bufImg.setRGB(x, y + HEIGHT + GAP, Color.WHITE.getRGB());
                        // diffBufImg.setRGB(x - LEFT, y - TOP, Color.WHITE.getRGB());
                        diffBufImg.setRGB(x - LEFT, y - TOP, topRgb);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("X: " + (x - LEFT) + "\tY: " + (y - TOP));
                }
            }
        }

        ImageIO.write(bufImg, "png", oridDiffFile);
        ImageIO.write(diffBufImg, "png", diffFile);
        // ImageIO.write(topBufImg, "png", topImg);
        // ImageIO.write(bottomBufImg, "png", bottomImg);

        bufImg.flush();
    }

    public static void findDiff() {
        String diffFileName = fileName();
        FindDiffUI.fileName = diffFileName;
        try {
            String fileName = "finddiff.png";
            File file = new File(path + fileName);
            if (!file.exists()) {
                System.out.println("file not exist");
            }
            File diffFile = new File(path + diffFileName);
            if (!diffFile.exists()) {
                diffFile.createNewFile();
            }
            File oriDiffFile = new File(path + diffFileName);
            if (!oriDiffFile.exists()) {
                oriDiffFile.createNewFile();
            }

            BufferedImage bufImg = ImageIO.read(file);
            BufferedImage diffBufImg = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

            int topRgb, bottomRgb;
            for (int x = LEFT; x < LEFT + WIDTH; x++) {
                for (int y = TOP; y < TOP + HEIGHT; y++) {
                    topRgb = bufImg.getRGB(x, y);
                    bottomRgb = bufImg.getRGB(x, y + HEIGHT + GAP);

                    if (topRgb != bottomRgb
                            && !colorSimilar(topRgb, bottomRgb)) {
                        bufImg.setRGB(x, y + HEIGHT + GAP, Color.GREEN.getRGB());
                        diffBufImg.setRGB(x - LEFT, y - TOP, Color.GREEN.getRGB());
                    } else {
                        bufImg.setRGB(x, y + HEIGHT + GAP, Color.WHITE.getRGB());
                        // diffBufImg.setRGB(x - LEFT, y - TOP, Color.WHITE.getRGB());
                        diffBufImg.setRGB(x - LEFT, y - TOP, topRgb);
                    }
                }
            }

            ImageIO.write(diffBufImg, "png", diffFile);
            ImageIO.write(bufImg, "png", oriDiffFile);

            diffBufImg.flush();
            bufImg.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 颜色是否相近
     * @param oriRgb  color RGB值
     * @param diffRgb color RGB值
     * @return 相近:true,否则false
     */
    public static boolean colorSimilar(int oriRgb, int diffRgb) {
        Color oriColor = Color.getColor("", oriRgb);
        Color diffColor = Color.getColor("", diffRgb);

        return Math.abs(oriColor.getBlue() - diffColor.getBlue()) < 35
                && Math.abs(oriColor.getGreen() - diffColor.getGreen()) < 35
                && Math.abs(oriColor.getRed() - diffColor.getRed()) < 35;

    }

    public static void delOldImage(final String fileName) {
        File file = new File(path + fileName);
        file.deleteOnExit();
    }

    public static String fileName() {
        return String.valueOf(System.currentTimeMillis()) + ".png";
    }
}
