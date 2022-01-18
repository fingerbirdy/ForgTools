/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools;

import com.fingerbirdy.highways.forgtools.Command.Start;
import net.minecraft.block.Block;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    public static HashMap<String, String> config = new HashMap<>();

    public static boolean init() {

        // MISC
        config.put("prefix", "?"); // Char
        config.put("allowed_logging_severity", "0"); // int
        // BUILD
        config.put("build_mode", "PAVE"); // Enum.build_mode
        config.put("width", "7"); // int
        config.put("height", "4"); // int
        config.put("railings", "true"); // boolean
        config.put("direction", "PP"); // Enum.direction
        config.put("material", "obsidian"); // Block.getBlockFromName(String)
        // BEHAVIOUR
        config.put("allow_axis_offset", "true"); // boolean
        config.put("allow_incorrect_y_pos", "true"); // boolean
        config.put("max_reach", "5"); // float
        config.put("target_obsidian_refill_stacks", "3"); // int
        config.put("obsidian_refill_threshold", "16"); // int
        config.put("delay_ticks", "2"); // int

        try {

            List<String> lines = Files.readAllLines(Paths.get(ForgTools.mc.gameDir + "\\ForgTools\\config\\config.txt"), StandardCharsets.UTF_8);

            for (String line : lines) {

                try {

                    String line_key = line.split("=")[0];
                    String line_value = line.split("=")[1];

                    // integers
                    if (line_key.equals("allowed_logging_severity") || line_key.equals("width") || line_key.equals("height") || line_key.equals("target_obsidian_refill_stacks") || line_key.equals("obsidian_refill_threshold") || line_key.equals("delay_ticks")) {
                        Integer.parseInt(line_value);
                        config.put(line_key, line_value);
                        continue;
                    }
                    // floats
                    if (line_key.equals("max_reach")) {
                        Float.parseFloat(line_value);
                        config.put(line_key, line_value);
                        continue;
                    }
                    // chars
                    if (line_key.equals("prefix")) {
                        config.put(line_key, String.valueOf(line_value.charAt(0)));
                        continue;
                    }
                    // boolean
                    if (line_key.equals("railings") || line_key.equals("allow_axis_offset") || line_key.equals("allowed_incorrect_y_pos")) {
                        if (line_value.equals("true")) { config.put(line_key, "true"); }
                        else if (line_value.equals("false")) { config.put(line_key, "false"); }
                        else { throw new Exception(); }
                        continue;
                    }
                    // Enum.build_mode
                    if (line_key.equals("build_mode")) {
                        config.put(line_key, Enum.build_mode.valueOf(line_value).name());
                        continue;
                    }
                    // Enum.direction
                    if (line_key.equals("direction")) {
                        config.put(line_key, Enum.direction.valueOf(line_value).name());
                        continue;
                    }
                    // Block.getBlockFromName(String)
                    if (line_key.equals("material")) {
                        if (Block.getBlockFromName(line_value) != null) {
                            config.put(line_key, line_value);
                        } else {
                            throw new Exception();
                        }
                        continue;
                    }

                    throw new Exception();

                } catch (Exception e) {
                    System.out.println("Invalid key/value for line " + line);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            Start.start_warnings.add("Failed to load config, restoring defaults.");
        }

        save();

        return true;

    }

    public static boolean save() {

        PrintWriter config_file_out = null;
        boolean config_save_success = false;

        try {

            config_file_out = new PrintWriter(ForgTools.mc.gameDir + "\\ForgTools\\config\\config.txt");
            String config_file_out_value = "";

            for (Map.Entry<String, String> entry : config.entrySet()) {
                config_file_out_value += entry.getKey() + "=" + entry.getValue() + "\n";
            }

            config_file_out.write(config_file_out_value);

            config_save_success = true;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (config_file_out != null) {
                config_file_out.close();
            }
        }

        return config_save_success;

    }

}
