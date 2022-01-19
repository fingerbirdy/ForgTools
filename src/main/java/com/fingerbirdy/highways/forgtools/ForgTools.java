/*

    Made by fingerbirdy#8056
    Since 8/7/2021

*/

/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

/* TODO
*   Github tutorial (1/17/22)
*   In Dig.java::calculate_ticks, do not assume efficiency 5 diamond 1/15/22 (haha good luck)
*   Improve grind obsidian placements
*/

package com.fingerbirdy.highways.forgtools;

import com.fingerbirdy.highways.forgtools.event.ClientConnectedToServer;
import com.fingerbirdy.highways.forgtools.gui.Settings;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;

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
    public static Minecraft mc;

    public static boolean enabled = false;

    @Mod.Instance(MOD_ID)
    public static ForgTools INSTANCE;

    public static void sendClientChat(String message, Boolean usePrefix) {

        try {

            if (usePrefix) {
                mc.player.sendMessage(new TextComponentString(MESSAGE_PREFIX + message));
            } else {
                mc.player.sendMessage(new TextComponentString(message));
            }

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Failed to send message " + message);

        }

    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

        mc = Minecraft.getMinecraft();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        FileSystem.init();
        Config.init();
        Settings.load();

        // Register non-static events
        MinecraftForge.EVENT_BUS.register(new ClientConnectedToServer());

    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

        System.out.println("ForgTools initialized!");

    }

}
