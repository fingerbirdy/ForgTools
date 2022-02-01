package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.blueprinting.Blueprint;
import com.fingerbirdy.highways.forgtools.util.Config;
import com.fingerbirdy.highways.forgtools.util.Enum;
import com.fingerbirdy.highways.forgtools.ForgTools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Session {

    public static boolean start() {

        // Reset session

        direction = Enum.direction.valueOf(Config.config.get("direction"));
        buildMode = Enum.buildMode.valueOf(Config.config.get("build_mode"));
        Process.status = Enum.processStatus.BUILD;
        Process.status_ticks = 0;
        Blueprint.full_blueprint.clear();
        Blueprint.blueprint.clear();
        Blueprint.blueprint_digging.clear();
        Blueprint.retry_blueprint.clear();
        Blueprint.priority_blueprint.clear();
        Dig.mining.clear();
        obsidian_placed = 0;
        blocks_mined = 0;
        start_timestamp = System.nanoTime();
        exceptions.clear();

        // Check for alignment errors
        String axis_offset_message = "You are off axis! If you would like to start anyways, set allow_axis_offset to false.";
        String incorrect_y_pos_message = "You're y position is incorrect! If you would like to start anyways, set allow_incorrect_y_pos to false.";

        if (!Boolean.parseBoolean(Config.config.get("allow_axis_offset"))) {

            if (direction == Enum.direction.PX || direction == Enum.direction.NX) {
                if (Math.floor(mc.player.posZ) != 0.0) {
                    ForgTools.sendClientChat(axis_offset_message, true);
                    return false;
                }
            } else if (direction == Enum.direction.PZ || direction == Enum.direction.NZ) {
                if (Math.floor(mc.player.posX) != 0.0) {
                    ForgTools.sendClientChat(axis_offset_message, true);
                    return false;
                }
            } else if (direction == Enum.direction.PP || direction == Enum.direction.MM) {
                if (Math.floor(mc.player.posX) != Math.floor(mc.player.posZ)) {
                    ForgTools.sendClientChat(axis_offset_message, true);
                    return false;
                }
            } else if (direction == Enum.direction.PM || direction == Enum.direction.MP) {
                if (Math.floor(mc.player.posX) * -1 != Math.floor(mc.player.posZ)) {
                    ForgTools.sendClientChat(axis_offset_message, true);
                    return false;
                }
            }

        }

        // Check for y position errors
        if (!Boolean.parseBoolean(Config.config.get("allow_incorrect_y_pos"))) {

            if (Enum.buildMode.valueOf(Config.config.get("build_mode")) == Enum.buildMode.PAVE && (int) mc.player.posY != 120) {
                ForgTools.sendClientChat(incorrect_y_pos_message, true);
                return false;
            } else if (Enum.buildMode.valueOf(Config.config.get("build_mode")) == Enum.buildMode.TUNNEL && (int) mc.player.posY != 119) {
                ForgTools.sendClientChat(incorrect_y_pos_message, true);
                return false;
            }

        }

        // Set session variables
        y_position = (int) mc.player.posY;
        if (direction == Enum.direction.PX || direction == Enum.direction.NX) {
            axis_onset = (int) Math.floor(mc.player.posX);
            axis_offset = (int) Math.floor(mc.player.posZ);
        } else if (direction == Enum.direction.PZ || direction == Enum.direction.NZ) {
            axis_onset = (int) Math.floor(mc.player.posZ);
            axis_offset = (int) Math.floor(mc.player.posX);
        } else if (direction == Enum.direction.PP || direction == Enum.direction.MM) {
            axis_onset = (int) Math.floor(mc.player.posX);
            axis_offset = (int) (Math.floor(mc.player.posZ) - Math.floor(mc.player.posX));
        } else if (direction == Enum.direction.PM || direction == Enum.direction.MP) {
            axis_onset = (int) Math.floor(mc.player.posX);
            axis_offset = (int) (Math.floor(mc.player.posZ) + Math.floor(mc.player.posX));
        }

        Blueprint.generate_build();
        return true;

    }

    public static void stop() {

        ForgTools.enabled = false;

        // Saves file containing exceptions for bug reporting
        if (Session.exceptions.size() != 0) {

            String path = mc.gameDir.getAbsolutePath() + "\\ForgTools\\logs\\" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd HH_mm_ss_SSS")) + ".txt";
            ForgTools.sendClientChat("During your session, an error occurred. Please send " + path + " to fingerbirdy#8056", true);
            try {
                File exceptions_file = new File(path);
                exceptions_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                FileWriter exceptions_file = new FileWriter(path);
                exceptions_file.append("The below contents contain unintended results. Please send this file to fingerbirdy#8056.");
                for (int i = 0; i < Session.exceptions.size(); i++) {
                    exceptions_file.append("\n").append(Session.exceptions.get(i));
                }
                exceptions_file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    // On a diagonal, axis_progress is x and axis_offset is either z-x or z+x
    public static int axis_onset = 0;
    public static int axis_offset = 0;
    public static int y_position = -1;
    public static int obsidian_placed = 0;
    public static int blocks_mined = 0;
    public static long start_timestamp = 0;
    public static String current_action = "None";
    public static Enum.direction direction = Enum.direction.PX;
    public static Enum.buildMode buildMode = Enum.buildMode.PAVE;
    public static final ArrayList<String> exceptions = new ArrayList<>();

}
