package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.util.Blueprint;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.util.ServerTps;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

    public static void tick(Blueprint.blueprints blueprint) {

        if (!Blueprint.blueprint_digging.isEmpty()) {

            BlockPos key = Blueprint.blueprint_digging.keySet().stream().findFirst().get();
            NetHandlerPlayClient connection = mc.player.connection;

            // Removes unbreakable blocks
            if (mc.world.getBlockState(key).getBlock() == Block.getBlockById(7)) {
                Blueprint.blueprint_digging.remove(key);
                return;
            }

            // Start digging a new block
            if (!digging) {

                if (Inventory.set_hot_bar_0(278)) {

                    digging = true;
                    int ticksNeededRaw = calculate_ticks(key);
                    ticksNeeded = ticksNeededRaw + ClientTick.ticks;

                    if (ticksNeededRaw == -1) {

                        Blueprint.blueprint_digging.remove(key);
                        return;

                    } else if (ticksNeededRaw == -2) {

                        return;

                    } else if (ticksNeededRaw == -3) {

                        Blueprint.priority_blueprint.put(key, Block.getBlockById(87));
                        return;

                    } else {

                        connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, key, EnumFacing.UP));

                    }

                } else {

                    ForgTools.sendClientChat("Could not find pickaxe in inventory", true);

                }

            } else {

                // stop digging the current block if needed
                if (ClientTick.ticks >= ticksNeeded) {

                    digging = false;
                    Session.blocks_mined++;
                    ticksNeeded = Integer.MAX_VALUE;
                    Blueprint.blueprint_digging.remove(key);
                    connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, key, EnumFacing.UP));

                }

            }

            connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
            mc.player.swingArm(EnumHand.MAIN_HAND);

        }

    }

    private static int calculate_ticks(BlockPos block) {

        /*

           Returns:
            0 - Instant break
            1-inf - Regular break
            -1 - Air, continue
            -2 - Liquid, ignore
            -3 - Source liquid, clog

         */

        Material material = ForgTools.mc.world.getBlockState(block).getMaterial();

        if (material == Material.AIR) {
            return -1;
        }
        if (material.isLiquid()) {

            return -3;

        }

        return (int) Math.ceil((1 / mc.world.getBlockState(block).getPlayerRelativeBlockHardness(mc.player, mc.player.world, block)) * ServerTps.dynamic_delay_multiplier);

    }

}