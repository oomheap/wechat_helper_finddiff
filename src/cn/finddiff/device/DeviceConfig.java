package cn.finddiff.device;

/**
 * @author Eric Cui
 * <p>
 * Created by Intellij IDEA.
 * Date  : 2018/1/15 20:27
 * Desc  :
 */
public class DeviceConfig {
    private int TOP;
    private int LEFT;
    private int HEIGHT;
    private int WIDTH;
    private int GAP;
    private int IMG_WIDTH;
    private int IMG_HEIGHT;

    public int getTOP() {
        return TOP;
    }

    public void setTOP(int TOP) {
        this.TOP = TOP;
    }

    public int getLEFT() {
        return LEFT;
    }

    public void setLEFT(int LEFT) {
        this.LEFT = LEFT;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }

    public int getGAP() {
        return GAP;
    }

    public void setGAP(int GAP) {
        this.GAP = GAP;
    }

    public int getIMG_WIDTH() {
        return IMG_WIDTH;
    }

    public void setIMG_WIDTH(int IMG_WIDTH) {
        this.IMG_WIDTH = IMG_WIDTH;
    }

    public int getIMG_HEIGHT() {
        return IMG_HEIGHT;
    }

    public void setIMG_HEIGHT(int IMG_HEIGHT) {
        this.IMG_HEIGHT = IMG_HEIGHT;
    }

    public DeviceConfig(int TOP, int LEFT, int HEIGHT, int WIDTH, int GAP, int IMG_WIDTH, int IMG_HEIGHT) {
        this.TOP = TOP;
        this.LEFT = LEFT;
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.GAP = GAP;
        this.IMG_WIDTH = IMG_WIDTH;
        this.IMG_HEIGHT = IMG_HEIGHT;
    }

    @Override
    public String toString() {
        return "DeviceConfig{" +
                "TOP=" + TOP +
                ", LEFT=" + LEFT +
                ", HEIGHT=" + HEIGHT +
                ", WIDTH=" + WIDTH +
                ", GAP=" + GAP +
                ", IMG_WIDTH=" + IMG_WIDTH +
                ", IMG_HEIGHT=" + IMG_HEIGHT +
                '}';
    }
}
