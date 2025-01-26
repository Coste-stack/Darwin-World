package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// Singleton class
public final class ConfigHandler {
    private static ConfigHandler instance;
    private final Map<String, Integer> configMap;

    private ConfigHandler() {
        configMap = new HashMap<>();
        loadConfig();
    }

    public static ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    public Map<String, Integer> getConfig() {
        return configMap;
    }

    public int getConfig(String key) {
        return configMap.get(key);
    }

    public void changeConfig(String key, int value) {
        configMap.replace(key, value);
    }

    private void loadConfig() {
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream("config.txt")) {
            if (resourceStream == null) {
                throw new IllegalStateException("Config file not found in resources");
            }
            Properties properties = new Properties();
            properties.load(resourceStream);
            properties.forEach((key, value) -> configMap.put((String) key, Integer.parseInt((String) value)));
        } catch (IOException e) {
            System.err.println("Error reading config file: " + e.getMessage());
        }
    }
}
