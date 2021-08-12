package com.fingerbirdy.highways.forgtools;

import com.fingerbirdy.highways.forgtools.module.Highways.Highways;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Config {

    private static HashMap<String, Object> defaults;
    private static final String[] CONFIGKEYS = "highways/build/width highways/build/height highways/build/railings highways/build/mode".split(" ");
    public static HashMap<String, Object> config;

    public static void init() {
        defaults = setDefaults();
        config = defaults;
        config = setConfig();
    }

    private static HashMap<String, Object> setDefaults() {

        HashMap<String, Object> values = new HashMap<>();
        values.put("autolog/enabled", false);
        values.put("autolog/health", 10);
        values.put("highways/build/width", 7);
        values.put("highways/build/height", 4);
        values.put("highways/build/railings", true);
        values.put("highways/build/mode", Highways.Mode.pave);
        return values;

    }

    private static final HashMap<String, Object> setConfig() {

        try {
            File configFile = new File(".\\ForgTools\\config.txt");
            if (!configFile.exists()) {
                configFile.createNewFile();
            }

            HashMap<String, Object> values = config;
            String[] configContents = FileUtils.readFileToString(configFile).split("\n");

            for (int i = 0; i < configContents.length; i++) {

                try {

                    String[] configLine = configContents[i].split("=");
                    config.replace(configLine[0], parseConfigValue(configLine));

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

            save();
            return values;

        } catch (Exception e) {
            ForgTools.logger.warn("Failed to read config file; enabling default config.");
            return defaults;
        }

    }

    public static Object parseConfigValue(String[] values) {

        if (values[1].equals("true")) {
            return true;
        }
        if (values[1].equals("false")) {
            return false;
        }
        try {
            return Integer.parseInt(values[1]);
        } catch (Exception e) {

            if (values[0].equals("highways/build/mode")) {
                if (values[1].equals("pave")) {
                    return Highways.Mode.pave;
                } else if (values[1].equals("tunnel")) {
                    return Highways.Mode.tunnel;
                }
            }

            return values[1];

        }

    }

    public static void parseCommands(String[] args) {

        if (args.length == 2 && args[1].equals("keys")) {

            ForgTools.sendClientChat("List of config keys: ", true);
            for (String element : CONFIGKEYS) {
                ForgTools.sendClientChat(element, true);
            }
            return;

        }

        if (args.length == 3 && args[1].equals("get")) {

            if (config.containsKey(args[2])) {

                ForgTools.sendClientChat("Key " + args[2] + ": " + config.get(args[2]), true);
                return;

            } else {

                ForgTools.sendClientChat("Key " + args[2] + " does not exist!", true);
                return;

            }

        }

        if (args.length <= 2) {

            ForgTools.sendClientChat("Invalid usage! Use \n" + ForgTools.CommandPrefix + "c [key] [value] / c keys / c get [key]", true);
            return;

        }

        if (config.containsKey(args[1])) {

            config.replace(args[1], parseConfigValue(new String[] {args[1], args[2]}));
            save();
            ForgTools.sendClientChat("Key " + args[1] + " set to " + args[2], true);

            return;

        } else {

            ForgTools.sendClientChat(args[1] + " is not a config key! Use \"" + ForgTools.CommandPrefix + "c keys\" for a list of config keys", true);
            return;

        }

    }

    public static void save() {

        ForgTools.logger.warn("---------------------------- " + config.get("highways/build/width"));

        StringBuilder configOutput = new StringBuilder();

        for (String configkey : CONFIGKEYS) {

            if (config.containsKey(configkey)) {

                configOutput.append(configkey).append("=").append(config.get(configkey)).append("\n");

            }

        }

        try {
            FileWriter configFile = new FileWriter(".\\ForgTools\\config.txt");
            configFile.write(configOutput.toString());
            configFile.close();
        } catch (Exception e) {
            ForgTools.logger.warn("Failed to save config file; config will not save on reboot.");
        }

        ForgTools.logger.warn("---------------------------- " + config.get("highways/build/width"));

    }

}