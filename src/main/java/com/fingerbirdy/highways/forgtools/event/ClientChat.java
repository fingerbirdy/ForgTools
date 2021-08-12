package com.fingerbirdy.highways.forgtools.event;

import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.module.Highways.Highways;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ForgTools.MOD_ID)
public class ClientChat {

    @SubscribeEvent
    public static void clientChatEvent(ClientChatEvent event) {

        String message = event.getMessage();

        // Parse ForgTools commands
        if (message.charAt(0) == ForgTools.CommandPrefix) {

            event.setCanceled(true);
            ForgTools.sendClientChat(message, true);
            String[] args = message.split(" ");

            if (args[0].equals(ForgTools.CommandPrefix + "hw") || args[0].equals(ForgTools.CommandPrefix + "highways")) {
                Highways.parseCommand(args);
            } else if (args[0].equals(ForgTools.CommandPrefix + "c") || args[0].equals(ForgTools.CommandPrefix + "config")) {
                Config.parseCommands(args);
            }

        }

    }

}
