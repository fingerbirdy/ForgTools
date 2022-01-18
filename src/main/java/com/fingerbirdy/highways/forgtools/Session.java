package com.fingerbirdy.highways.forgtools;

import com.fingerbirdy.highways.forgtools.action.Dig;

import java.util.ArrayList;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Session {

    public static boolean start() {

        // Reset session errors

        direction = Enum.direction.valueOf(Config.config.get("direction"));
        Blueprint.blueprint.clear();
        Blueprint.blueprint_digging.clear();
        Blueprint.retry_blueprint.clear();
        Dig.digging = false;

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

            if (Enum.build_mode.valueOf(Config.config.get("build_mode")) == Enum.build_mode.PAVE && (int) mc.player.posY != 120) {
                ForgTools.sendClientChat(incorrect_y_pos_message, true);
                return false;
            } else if (Enum.build_mode.valueOf(Config.config.get("build_mode")) == Enum.build_mode.TUNNEL && (int) mc.player.posY != 119) {
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

    // On a diagonal, axis_progress is x and axis_offset is either z-x or z+x
    public static int axis_onset = 0;
    public static int axis_offset = 0;
    public static int y_position = -1;
    public static Enum.direction direction = Enum.direction.PX;
    public static final ArrayList<String> exceptions = new ArrayList<>();

}
