package cn.finddiff.util;

import java.net.URL;

/**
 * Created by Intellij IDEA.
 * Date  : 2018/1/3 20:28
 *
 * @author Eric Cui
 */
public class Utils {

    private static final String ADB_PATH = "D:\\__sssarm\\platform-tools\\adb";


    /**
     * 获取当前类的绝对路径
     * @return the absolute path to the current file
     */
    public static String getCurrFilePath() {
        String path = PathHelper.PATH;
        String osName = System.getProperty("os.name", "");
        path = path.replace("target/classes/", "src/main/java/");
        if (osName.contains("Win")) {
            if ('/' == path.charAt(0)) {
                path = path.replaceFirst("/", "");
            }
        }
        System.out.println("Path: " + path);
        return path;
    }

    public static boolean isWin() {
        String osName = System.getProperty("os.name", "");
        return osName.contains("Win");
    }

    public static String getAdbPath() {
        if (isWin()) {
            return ADB_PATH;
        } else {
            return "adb";
        }
    }


    public static class PathHelper {
        public static URL url;
        public static String PATH;
        private static final PathHelper CURRENT_PATH = new PathHelper();
        private PathHelper() {
            url = getClass().getResource("");
            PATH = url.getPath();
        }
    }

    public static void main(String[] args) {
        System.out.println(PathHelper.PATH);
    }
}
