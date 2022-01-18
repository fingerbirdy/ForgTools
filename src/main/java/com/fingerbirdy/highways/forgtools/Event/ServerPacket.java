package com.fingerbirdy.highways.forgtools.Event;

import com.fingerbirdy.highways.forgtools.Util.ServerTps;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketTimeUpdate;

public class ServerPacket {

    // Called upon receiving a vanilla packet
    public static void onServerPacket (ChannelHandlerContext ctx, Packet msg) {

        if (msg instanceof SPacketTimeUpdate) {

            ServerTps.received_time_update_packet();

        }

    }

}
