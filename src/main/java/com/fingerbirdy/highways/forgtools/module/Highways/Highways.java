package com.fingerbirdy.highways.forgtools.module.Highways;

import com.fingerbirdy.highways.forgtools.ForgTools;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.HashMap;

public class Highways {

    public static HashMap<String, String> commandReturns = setCommandReturns();
    public static boolean enabled;
    public static Direction direction;
    public static String directionStr;
    public static int startX, startY, startZ, axisOffset;
    private static final String PREFIX = TextFormatting.AQUA + "" + TextFormatting.ITALIC + "[Highways] " + TextFormatting.RESET;

    public static int pavingI;
    public static int clearingI;

    public enum Mode {
        pave,
        tunnel
    }

    private static HashMap<String, String> setCommandReturns() {

        HashMap<String, String> values = new HashMap<>();
        values.put("help", "Invalid usage! Use \n" + ForgTools.CommandPrefix + "hw [e/enable/d/disable/c/config].");
        values.put("enable", "Highways module enabled!");
        values.put("disable", "Highways module disabled!");
        return values;

    }

    public static void parseCommand(String[] args) {

        if (args.length <= 1) {
            ForgTools.sendClientChat(Highways.commandReturns.get("help"), true);
        } else {

            switch (args[1]) {
                case "e":
                case "enable":

                    ForgTools.sendClientChat(Highways.commandReturns.get("enable"), true);
                    Highways.enable();

                    break;

                case "d":
                case "disable":

                    Highways.disable();

                    break;

                case "c":
                case "config":

                    break;

                default:

                    ForgTools.sendClientChat(Highways.commandReturns.get("help"), true);

                    break;

            }

        }

    }

    public static void enable() {

        enabled = true;

        int yaw = MathHelper.floor((double)(ForgTools.mc.player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (yaw) {
            case 0:
                direction = Direction.Z;
                directionStr = "+z";
                break;
            case 1:
                direction = Direction.X_;
                directionStr = "-x";
                break;
            case 2:
                direction = Direction.Z_;
                directionStr = "-z";
                break;
            case 3:
                direction = Direction.X;
                directionStr = "+x";
                break;
        }

        startX = (int) ForgTools.mc.player.posX;
        startY = (int) ForgTools.mc.player.posY;
        startZ = (int) ForgTools.mc.player.posZ;

        if (direction == Direction.X || direction == Direction.X_) {
            axisOffset = startZ;
            pavingI = startX;
            clearingI = startX;
        } else {
            axisOffset = startX;
            pavingI = startZ;
            clearingI = startZ;
        }

        Actions.actionStarted = false;
        Actions.actionsX = new ArrayList<>();
        Actions.actionsY = new ArrayList<>();
        Actions.actionsZ = new ArrayList<>();
        Actions.actionsA = new ArrayList<>();
        Actions.actionIndex = 0;

        ForgTools.sendClientChat(PREFIX + "Direction: " + directionStr, true);
        ForgTools.sendClientChat(PREFIX + "Axis Offset: " + axisOffset, true);

    }

    public static void tick() {

        Actions.update();
        boolean isFinished = Actions.checkIfFinished();

        if (isFinished) {

            Actions.actionIndex++;
            Actions.actionStarted = false;

        }

        if (!Actions.actionStarted) {

            Actions.perform();
            Actions.actionStarted = true;

        }

        /* ACTION STUCK HANDLING */

        Actions.actionStuckTicks++;

        /*if (Actions.actionStuckTicks < 20) {}
        else if (Actions.actionStuckTicks == 20) {

            int currentActionX = Actions.actionsX.get(Actions.actionIndex);
            int currentActionY = Actions.actionsY.get(Actions.actionIndex) + 1;
            int currentActionZ = Actions.actionsZ.get(Actions.actionIndex);

            if (direction == Direction.X) {
                ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "goto " + (currentActionX - 1) + " " + currentActionY + " " + currentActionZ);
            } else if (direction == Direction.X_) {
                ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "goto " + (currentActionX + 1) + " " + currentActionY + " " + currentActionZ);
            } else if (direction == Direction.Z) {
                ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "goto " + currentActionX + " " + currentActionY + " " + (currentActionZ - 1));
            } else if (direction == Direction.Z_) {
                ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "goto " + currentActionX + " " + currentActionY + " " + (currentActionZ + 1));
            }

        } else */if (Actions.actionStuckTicks >= 100) {

            int currentActionX = Actions.actionsX.get(Actions.actionIndex);
            int currentActionY = Actions.actionsY.get(Actions.actionIndex);
            int currentActionZ = Actions.actionsZ.get(Actions.actionIndex);
            Action currentActionA = Actions.actionsA.get(Actions.actionIndex);

            if ((int) ForgTools.mc.player.posX - 1 == currentActionX && (int) ForgTools.mc.player.posY - 1 == currentActionY && (int) ForgTools.mc.player.posZ - 1 == currentActionZ) {
                ForgTools.sendClientDebug("Stuck for 100 ticks on action:{" + currentActionX + ", " + currentActionY + ", " + currentActionZ + ", " + currentActionA.toString() + "}, canceling", ForgTools.DebugSeverity.warn);
                /*Actions.actionsX.add(currentActionX);
                Actions.actionsY.add(currentActionY);
                Actions.actionsZ.add(currentActionZ);
                Actions.actionsA.add(currentActionA);*/
                Actions.actionIndex++;
                Actions.actionStarted = false;
            }

        }

    }

    public static void disable() {

        ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel c");
        ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "c");
        ForgTools.sendClientChat(Highways.commandReturns.get("disable"), true);
        enabled = false;

    }

}
