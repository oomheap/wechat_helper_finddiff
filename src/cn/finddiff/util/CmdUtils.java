package cn.finddiff.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric
 * @date 2018/1/3 16:57
 * Description
 */
public class CmdUtils {
    private static final String[] WIN_RUNTIME = {"cmd.exe", "/C"};
    private static final String[] OS_LINUX_RUNTIME = {"/bin/bash", "-l", "-c"};

    private CmdUtils() {
    }

    private static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static String runProcess(boolean isWin, String... command) {
        System.out.print("   COMMAND TO RUN: [");
        for (String s : command) {
            System.out.print(s);
        }
        System.out.print("]\n");
        String[] allCommand;
        try {
            if (isWin) {
                allCommand = concat(WIN_RUNTIME, command);
            } else {
                allCommand = concat(OS_LINUX_RUNTIME, command);
            }
            ProcessBuilder pb = new ProcessBuilder(allCommand);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream(), Charset.forName("GBK")));
            String _temp;
            StringBuilder line = new StringBuilder();
            while ((_temp = in.readLine()) != null) {
                line.append(_temp);
            }
            System.out.println("RESULT OF COMMAND: [" + line + "]");
            return line.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
