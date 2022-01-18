package com.fingerbirdy.highways.forgtools.Action;

import com.fingerbirdy.highways.forgtools.Enum;
import com.fingerbirdy.highways.forgtools.Session;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Continue {

    public static boolean going = false;
    public static int goal_x;
    public static int goal_y;
    public static int goal_z;

    public static void tick () {

        // Starts going
        if (!going) {

            int calculated_goal_x;
            int calculated_goal_z;

            // Calculates axis progress
            int axis_progress;
            if (Session.direction == Enum.direction.PZ || Session.direction == Enum.direction.NZ) {
                axis_progress = (int) mc.player.posZ;
            } else {
                axis_progress = (int) mc.player.posX;
            }

            switch (Session.direction) {

                case PX:
                    calculated_goal_x = axis_progress + 1;
                    calculated_goal_z = Session.axis_offset;
                    break;
                case NX:
                    calculated_goal_x = axis_progress - 1;
                    calculated_goal_z = Session.axis_offset;
                    break;
                case PZ:
                    calculated_goal_x = Session.axis_offset;
                    calculated_goal_z = axis_progress + 1;
                    break;
                case NZ:
                    calculated_goal_x = Session.axis_offset;
                    calculated_goal_z = axis_progress - 1;
                    break;
                case PP:
                    calculated_goal_x = axis_progress + 1;
                    calculated_goal_z = axis_progress + 1 + Session.axis_offset;
                    break;
                case PM:
                    calculated_goal_x = axis_progress + 1;
                    calculated_goal_z = axis_progress - 1 + Session.axis_offset;
                    break;
                case MP:
                    calculated_goal_x = axis_progress - 1;
                    calculated_goal_z = axis_progress + 1 + Session.axis_offset;
                    break;
                case MM:
                    calculated_goal_x = axis_progress - 1;
                    calculated_goal_z = axis_progress - 1 + Session.axis_offset;
                    break;
                default:
                    return;

            }

            goal_x = calculated_goal_x;
            goal_y = Session.y_position;
            goal_z = calculated_goal_z;
            going = true;

        }

        // Checks if done doing
        else {

            if (Math.floor(mc.player.posX) == goal_x && Math.floor(mc.player.posY) == goal_y && Math.floor(mc.player.posZ) == goal_z) {

                going = false;

            }

        }

    }

}
