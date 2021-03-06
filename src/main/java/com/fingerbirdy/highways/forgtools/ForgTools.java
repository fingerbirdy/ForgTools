/*

    Made by fingerbirdy#8056
    Since 8/7/2021

*/

/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

/* TODO fix
*   auto obsidian
*   bot won't clear obsidian
*  TODO for release
*   baritone implementation
*/

package com.fingerbirdy.highways.forgtools;

import com.fingerbirdy.highways.forgtools.event.ClientConnectedToServer;
import com.fingerbirdy.highways.forgtools.gui.Settings;
import com.fingerbirdy.highways.forgtools.util.Config;
import com.fingerbirdy.highways.forgtools.util.FileSystem;
import com.fingerbirdy.highways.forgtools.util.Https;
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
    public static final String MOD_NAME = "foRg tools";
    public static final String GITHUB = "https://github.com/fingerbirdy/ForgTools";
    public static final String VERSION = "0.1.0-SNAPSHOT-0";
    public static final String VERSION_URL = "https://raw.githubusercontent.com/fingerbirdy/ForgTools/version/version.json";
    public static String latestVersion = VERSION;
    public static final String MESSAGE_PREFIX = TextFormatting.AQUA + "[foRg tools] " + TextFormatting.RESET;
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

        String res = Https.basicGet(VERSION_URL);
        if (res != null) {
            latestVersion = Https.basicGet(VERSION_URL);
        }

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
