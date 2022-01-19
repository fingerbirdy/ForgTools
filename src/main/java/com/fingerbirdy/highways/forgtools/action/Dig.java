package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.Blueprint;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.util.ServerTps;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Dig {

    public static boolean digging = false;
    public static int ticksNeeded = Integer.MAX_VALUE;

    public static void tick() {

        if (!Blueprint.blueprint_digging.isEmpty()) {

            BlockPos key = Blueprint.blueprint_digging.keySet().stream().findFirst().get();
            NetHandlerPlayClient connection = mc.player.connection;

            // Start digging a new block
            if (!digging) {

                if (Inventory.set_hot_bar_0(278)) {

                    digging = true;
                    ticksNeeded = calculate_ticks(key) + ClientTick.ticks;
                    connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, key, EnumFacing.UP));

                } else {

                    ForgTools.sendClientChat("Could not find pickaxe in inventory", true);

                }

            } else {

                // stop digging the current block if needed
                if (ClientTick.ticks >= ticksNeeded) {

                    digging = false;
                    ticksNeeded = Integer.MAX_VALUE;
                    Blueprint.blueprint_digging.remove(key);
                    connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, key, EnumFacing.UP));

                }

            }

            connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
            mc.player.swingArm(EnumHand.MAIN_HAND);

        }

    }

    // todo: suffer
    private static int calculate_ticks(BlockPos block) {

        return (int) Math.ceil(32 / Math.ceil(mc.player.world.getBlockState(block).getBlockHardness(mc.player.world, block)) * 20 * ServerTps.dynamic_delay_multiplier);

    }

}