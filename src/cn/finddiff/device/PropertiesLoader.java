package cn.finddiff.device;

import cn.finddiff.util.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Eric Cui
 * <p>
 * Created by Intellij IDEA.
 * Date  : 2018/1/15 20:09
 * Desc  :
 */
public class PropertiesLoader {

    private static final class PropertiesHolder {
        private static final PropertiesLoader PROPERTIES_LOADER = new PropertiesLoader();
    }

    private static final String KEY_PREFIX_FORMAT = "device.%s.";

    private static Properties properties;
    private PropertiesLoader() {
        final String CONFIG_FILE =
                Utils.PathHelper.PATH.replace("util", "device") + "/device.properties";
        System.out.println("*****: " + CONFIG_FILE);
        properties = new Properties();
        try (InputStream is = new FileInputStream(Paths.get(CONFIG_FILE).toFile())) {
            properties.load(is);
        } catch (IOException ignore) {
        }
    }

    public static PropertiesLoader getInstance() {
        return PropertiesHolder.PROPERTIES_LOADER;
    }

    public String getProperty(final String key) {
        return properties.getProperty(key);
    }
    public int getIntProperty(final String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public DeviceConfig getDeviceConfig(final String deviceName) {
        final String keyPrefix = String.format(KEY_PREFIX_FORMAT, deviceName);
        return createDeviceConfig(keyPrefix);
    }

    private DeviceConfig createDeviceConfig(final String keyPrefix) {
        return new DeviceConfig(
                getIntProperty(keyPrefix + "TOP"),
                getIntProperty(keyPrefix + "LEFT"),
                getIntProperty(keyPrefix + "HEIGHT"),
                getIntProperty(keyPrefix + "WIDTH"),
                getIntProperty(keyPrefix + "GAP"),
                getIntProperty(keyPrefix + "IMG_WIDTH"),
                getIntProperty(keyPrefix + "IMG_HEIGHT")
        );
    }


    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        System.out.println(propertiesLoader.getDeviceConfig("MX2"));
    }

}
