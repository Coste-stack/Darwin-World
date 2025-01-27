package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// Singleton class
public final class ConfigHandler {
    private static ConfigHandler instance;
    private final Map<String, ConfigValue> configMap;

    public static class ConfigValue {
        private int value;
        private final boolean modifiable;

        public ConfigValue(int value, boolean modifiable) {
            this.value = value;
            this.modifiable = modifiable;
        }

        private void setValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }

        private boolean isModifiable() {
            return this.modifiable;
        }
    }

    private ConfigHandler() {
        configMap = new HashMap<>();
        loadConfig("static-config.txt", false);
        loadConfig("dynamic-config.txt", true);
    }

    public static ConfigHandler getInstance() {
        if (instance == null) {
            instance = new ConfigHandler();
        }
        return instance;
    }

    public Map<String, Integer> getConfig(boolean modifiable) {
        Map<String, Integer> resultMap = new HashMap<>();
        ConfigValue[] configArray = new ConfigValue[configMap.size()];
        configMap.forEach((key, value) -> {
            if (value.isModifiable() == modifiable) {
                resultMap.put(key, value.getValue());
            }
        });
        return resultMap;
    }

    public int getConfigValue(String key) {
        return configMap.get(key).getValue();
    }

    public void changeConfig(String key, int value) {
        ConfigValue configValue = configMap.get(key);
        if (!configValue.modifiable) {
            throw new IllegalStateException("Config value " + key + " is not modifiable.");
        }
        configValue.setValue(value);
    }

    private void loadConfig(String filename, Boolean modifiable) {
        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            if (resourceStream == null) {
                throw new IllegalStateException("File not found in resources: " + filename);
            }
            Properties properties = new Properties();
            properties.load(resourceStream);
            properties.forEach((key, value) -> {
                ConfigValue configValue = new ConfigValue(Integer.parseInt((String) value), modifiable);
                configMap.put((String) key, configValue);
            });
        } catch (IOException e) {
            System.err.println("Error reading " + filename + " file: " + e.getMessage());
        }
    }
}
