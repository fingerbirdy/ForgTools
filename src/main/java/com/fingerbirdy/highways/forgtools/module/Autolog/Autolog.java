package com.fingerbirdy.highways.forgtools.module.Autolog;

import com.fingerbirdy.highways.forgtools.ForgTools;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;

public class Autolog {

    public static void runAutoLog() {

        if (ForgTools.mc.player.getHealth() < 10) {

            ForgTools.mc.player.connection.handleDisconnect(new SPacketDisconnect(new TextComponentString("You were being attacked!")));

        }

    }

}
