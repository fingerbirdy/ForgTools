/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.Blueprint;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Place {

    public static BlockPos current_place_pos = null;
    public static Block current_place_block = null;
    public static int placing_stuck_ticks = 0;

    public static void tick() {

        boolean process_blueprint = true;

        // Checks if needs to process the retry blueprint, does if it does
        if (!Blueprint.retry_blueprint.isEmpty()) {

            for (BlockPos pos : Blueprint.retry_blueprint.keySet()) {

                if (ClientTick.ticks - (int) Blueprint.retry_blueprint.get(pos)[1] >= 20) {

                    if (!mc.player.world.getBlockState(pos).getBlock().equals(Blueprint.retry_blueprint.get(pos)[0])) {

                        process_blueprint = false;
                        current_place_pos = Blueprint.retry_blueprint.keySet().stream().findFirst().get();
                        current_place_block = (Block) Blueprint.retry_blueprint.get(current_place_pos)[0];
                        Blueprint.retry_blueprint.remove(pos);

                        place(current_place_pos, current_place_block);
                        Blueprint.retry_blueprint.put(current_place_pos, new Object[]{current_place_block, ClientTick.ticks});

                    } else {

                        Blueprint.retry_blueprint.remove(pos);

                    }

                    break;

                }

            }

        }

        // Processes the normal blueprint if it hasn't processed the retry
        if (process_blueprint && !Blueprint.blueprint.isEmpty()) {

            current_place_pos = Blueprint.blueprint.keySet().stream().findFirst().get();
            current_place_block = Blueprint.blueprint.get(current_place_pos);

            // Places the block
            place(current_place_pos, current_place_block);
            Blueprint.retry_blueprint.put(current_place_pos, new Object[]{current_place_block, ClientTick.ticks});
            Blueprint.blueprint.remove(current_place_pos);

        }

    }

    public static void place(BlockPos pos, Block block) {

        // If block is occupied, digs
        Material pos_material = mc.world.getBlockState(current_place_pos).getMaterial();
        if ((pos_material != Material.AIR && pos_material != Material.WATER && pos_material != Material.LAVA)) {

            Blueprint.blueprint_digging.put(current_place_pos, Block.getBlockById(0));
            return;

        }

        // The placing

        if (mc.player.inventory.getCurrentItem().getItem() != Item.getItemFromBlock(block)) {

            Inventory.set_hot_bar_0(Item.getIdFromItem(Item.getItemFromBlock(block)));
            return;

        }

        placing_stuck_ticks = 0;
        Object[] block_and_face = get_face(pos);
        if (Objects.isNull(block_and_face)) {
            return;
        }

        NetHandlerPlayClient connection = mc.getConnection();
        connection.sendPacket(new CPacketPlayerTryUseItemOnBlock((BlockPos) block_and_face[0], (EnumFacing) block_and_face[1], EnumHand.MAIN_HAND, 0, 0, 0));
        mc.player.swingArm(EnumHand.MAIN_HAND);

    }

    // Returns the face for where to place
    public static Object[] get_face(BlockPos pos) {

        if (mc.player.world.getBlockState(pos.down(1)).isFullCube()) { // block down
            return new Object[] {pos.down(1), EnumFacing.UP};
        }
        if (mc.player.world.getBlockState(pos.east(1)).isFullCube()) { // block east
            return new Object[] {pos.east(1), EnumFacing.WEST};
        }
        if (mc.player.world.getBlockState(pos.west(1)).isFullCube()) { // block west
            return new Object[] {pos.west(1), EnumFacing.EAST};
        }
        if (mc.player.world.getBlockState(pos.north(1)).isFullCube()) { // block north
            return new Object[] {pos.north(1), EnumFacing.SOUTH};
        }
        if (mc.player.world.getBlockState(pos.south(1)).isFullCube()) { // block south
            return new Object[] {pos.south(1), EnumFacing.NORTH};
        }
        if (mc.player.world.getBlockState(pos.up(1)).isFullCube()) { // block up
            return new Object[] {pos.up(1), EnumFacing.DOWN};
        }
        return null;

    }

}
