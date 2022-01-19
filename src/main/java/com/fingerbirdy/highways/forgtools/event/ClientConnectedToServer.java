package com.fingerbirdy.highways.forgtools.event;

import com.fingerbirdy.highways.forgtools.util.ServerTps;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

@ChannelHandler.Sharable
public class ClientConnectedToServer extends SimpleChannelInboundHandler<Packet> {

    // On connect to server
    @SubscribeEvent
    public void clientConnectedToServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {

        // Sets up server packet interception
        ChannelPipeline pipeline = event.getManager().channel().pipeline();
        pipeline.addBefore("packet_handler", this.getClass().getName(), this);

        // Clears server tps data
        ServerTps.init();

    }

    public ClientConnectedToServer() {

        super(false);

    }

    // Intercepts vanilla packets
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) {

        ServerPacket.onServerPacket(ctx, msg);

    }

}
