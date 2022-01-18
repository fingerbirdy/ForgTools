/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools.Util;

import com.fingerbirdy.highways.forgtools.Config;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Logger {

    public static boolean log (int severity, String message) {

        if (message == null) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.WHITE + "[LOGGER] " + TextFormatting.RESET + "message is null"));
            System.out.println("[LOGGER] message is null");
        }

        if (severity < Integer.parseInt(Config.config.get("allowed_logging_severity"))) {
            return false;
        }

        if (severity == 0) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.WHITE + "[INFO] " + TextFormatting.RESET + message));
            System.out.println("[INFO] " + message);
        } else if (severity == 1) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.WHITE + "[DEBUG] " + TextFormatting.RESET + message));
            System.out.println("[DEBUG] " + message);
        } else if (severity == 2) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "[WARN] " + TextFormatting.RESET + message));
            System.out.println("[WARN] " + message);
        } else if (severity == 3) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "[SEVERE] " + TextFormatting.RESET + message));
            System.out.println("[SEVERE] " + message);
        }

        return true;

    }

}