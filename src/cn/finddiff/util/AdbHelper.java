package cn.finddiff.util;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @date 2018/1/3 15:57
 * Description
 */
public class AdbHelper {

    private static final String PATH = Utils.getCurrFilePath();
    private static final String ADB_PATH;

    static {
        ADB_PATH = Utils.getAdbPath();
    }

    public static void main(String[] args) {
        // CmdUtils.runProcess(true, String.format("%s shell input tap %d %d", ADB_PATH, 540, 1000));

        screen();
        pull();
    }

    public static void screen() {
        CmdUtils.runProcess(Utils.isWin(), String.format("%s shell screencap -p /sdcard/finddiff.png", ADB_PATH));
    }

    public static void pull() {
        CmdUtils.runProcess(Utils.isWin(), String.format("%s pull /sdcard/finddiff.png %s", ADB_PATH, PATH));
    }

    public static void tap(int x, int y) {
        CmdUtils.runProcess(Utils.isWin(), String.format("%s shell input tap %d %d", ADB_PATH, x, y));
    }

}
