package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

// Singleton class
public final class ConfigHandler {
    private static ConfigHandler instance;
    private final Map<String, ConfigValue> configMap;
    private final List<String> orderedConfigList;

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
        orderedConfigList = new ArrayList<>();
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
        configMap.forEach((key, value) -> {
            if (value.isModifiable() == modifiable) {
                resultMap.put(key, value.getValue());
            }
        });
        return resultMap;
    }

    public List<String> getOrderedConfigList(boolean modifiable) {
        List<String> keyList = new ArrayList<>();
        for (String configKey : orderedConfigList) {
            if (configMap.get(configKey).isModifiable() == modifiable) {
                keyList.add(configKey);
            }
        }
        return keyList;
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
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (resourceStream == null) {
            throw new IllegalStateException("File not found in resources: " + filename);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceStream))) {
            int index = 0;
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip empty lines or comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Split key and value by '='
                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid format in config file: " + line);
                }

                String key = parts[0].trim();
                String value = parts[1].trim();

                // Parse the value as an integer
                try {
                    int intValue = Integer.parseInt(value);
                    ConfigValue configValue = new ConfigValue(intValue, modifiable);
                    configMap.put(key, configValue);
                    orderedConfigList.add(key);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid integer value for key " + key + ": " + value);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + filename + " file: " + e.getMessage());
        }
    }
}
