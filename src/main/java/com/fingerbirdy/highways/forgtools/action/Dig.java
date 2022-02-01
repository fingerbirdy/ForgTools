package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.blueprinting.Blueprint;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.util.Config;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Dig {

    public static ArrayList<BlockPos> mining = new ArrayList<>();
    public static int toTicksNeeded = Integer.MAX_VALUE;
    public static int nukerTimeoutTicks = 0;

    public static void tick(Blueprint.blueprints blueprint) {

        boolean nukerActivated = false;
        int nukerLimit = Integer.parseInt(Config.config.get("nuker_limit"));

        if (nukerTimeoutTicks > 0) {
            nukerTimeoutTicks--;
        }

        // Starts mining blocks
        if (mining.size() == 0) {

            if (!Blueprint.blueprint_digging.isEmpty()) {

                Object[] diggingPoss = Blueprint.blueprint_digging.keySet().toArray();
                Inventory.set_hot_bar_0(278);

                for (Object posO : diggingPoss) {

                    BlockPos pos = (BlockPos) posO;
                    int ticksNeeded = calculate_ticks(pos);

                    // AIR/LIQUID FLOWING
                    if (ticksNeeded == -1 || ticksNeeded == -2) {
                        Blueprint.blueprint_digging.remove(pos);
                        continue;
                    }

                    // LIQUID SOURCE
                    if (ticksNeeded == -3) {
                        Blueprint.priority_blueprint.put(pos, Block.getBlockById(87));
                    }

                    // INSTANT MINE
                    if (ticksNeeded == 1 && nukerTimeoutTicks <= 0) {

                        toTicksNeeded = ClientTick.ticks + 1;
                        mining.add(pos);
                        nukerActivated = true;
                        sendDigPacket(pos, true);

                    } else {

                        if (!nukerActivated) {

                            toTicksNeeded = ClientTick.ticks + ticksNeeded;
                            mining.add(pos);
                            sendDigPacket(pos, true);

                        }

                        break;

                    }

                }

                mc.player.swingArm(EnumHand.MAIN_HAND);

            }

        }

        // Stops mining blocks
        else {

            if (ClientTick.ticks >= toTicksNeeded) {

                if (mining.size() > 1) {
                    nukerTimeoutTicks = Integer.parseInt(Config.config.get("nuker_timeout"));
                }

                for (BlockPos pos : mining) {
                    sendDigPacket(pos, false);
                }

                mining.clear();

            }

        }

    }

    private static void sendDigPacket(BlockPos pos, boolean start) {

        NetHandlerPlayClient connection = mc.getConnection();

        if (connection == null) {
            return;
        }

        if (start) {
            connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
        } else {
            connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
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

        return (int) Math.ceil((1 / mc.world.getBlockState(block).getPlayerRelativeBlockHardness(mc.player, mc.player.world, block)));

    }

}