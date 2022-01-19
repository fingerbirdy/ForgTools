/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools.event;

import static com.fingerbirdy.highways.forgtools.Config.config;

import com.fingerbirdy.highways.forgtools.command.*;
import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ForgTools.MOD_ID)
public class ClientChat {

    @SubscribeEvent
    public static void clientChatEvent(ClientChatEvent event) {

        String message = event.getMessage();

        // Parse ForgTools commands
        if (message.charAt(0) == Config.config.get("prefix").charAt(0)) {

            event.setCanceled(true);
            ForgTools.sendClientChat(message, true);
            String[] args = message.split(" ");

            if (args[0].equals(config.get("prefix") + "debug")) {
                Debug.execute(args);
            } else if (args[0].equals(config.get("prefix") + "start")) {
                Start.execute(args);
            } else if (args[0].equals(config.get("prefix") + "stop")) {
                Stop.execute(args);
            } else if (args[0].equals(config.get("prefix") + "refresh_config")) {
                RefreshConfig.execute(args);
            }

            else {

                ForgTools.sendClientChat("That is not a command. Use " + Config.config.get("prefix") + "help for a list of commands.", true);

            }

        }

    }

}