/*

    Made by fingerbirdy#8056
    Since 8/7/2021

 */

package com.fingerbirdy.highways.forgtools;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.Logger;

@Mod(
        modid = ForgTools.MOD_ID,
        name = ForgTools.MOD_NAME,
        version = ForgTools.VERSION
)
public class ForgTools {

    public static final String MOD_ID = "forgtools";
    public static final String MOD_NAME = "Forg Tools";
    public static final String VERSION = "0.1.0-SNAPSHOT-0";
    public static final String MESSAGE_PREFIX = TextFormatting.AQUA + "[Forg Tools] " + TextFormatting.RESET;
    public static char CommandPrefix = '\\';
    public static char baritonePrefix = '#';
    public static Minecraft mc;
    public static Logger logger;

    @Mod.Instance(MOD_ID)
    public static ForgTools INSTANCE;

    public static void sendClientChat(String message, Boolean usePrefix) {

        if (usePrefix) {
            mc.player.sendMessage(new TextComponentString(MESSAGE_PREFIX + message));
        } else {
            mc.player.sendMessage(new TextComponentString(message));
        }

    }

    public enum DebugSeverity {
        info,
        warn,
        severe
    }

    public static void sendClientDebug(String message, DebugSeverity severity) {

        if (severity == DebugSeverity.info) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.WHITE + "[DEBUG/INFO] " + TextFormatting.RESET + message));
        } else if (severity == DebugSeverity.warn) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.YELLOW + "[DEBUG/WARN] " + TextFormatting.RESET + message));
        } else if (severity == DebugSeverity.severe) {
            mc.player.sendMessage(new TextComponentString(TextFormatting.RED + "[DEBUG/SEVERE] " + TextFormatting.RESET + message));
        }

    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

        mc = Minecraft.getMinecraft();
        logger = event.getModLog();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        Config.init();

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

    }

}
