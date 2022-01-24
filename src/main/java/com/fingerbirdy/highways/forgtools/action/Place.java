/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.util.Blueprint;
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

    public static void tick(Blueprint.blueprints blueprint, Object[] args) {

        // Priority blueprint
        if (blueprint == Blueprint.blueprints.priority_blueprint) {

            if (!Blueprint.priority_blueprint.isEmpty()) {

                current_place_pos = Blueprint.priority_blueprint.keySet().stream().findFirst().get();
                current_place_block = Blueprint.priority_blueprint.get(current_place_pos);

                // Places the block
                if ( place(current_place_pos, current_place_block) ) {
                    Blueprint.priority_blueprint.remove(current_place_pos);
                }

            }

        }

        // Retry blueprint, expects {BlockPos key} in args
        if (blueprint == Blueprint.blueprints.retry_blueprint) {

            current_place_pos = (BlockPos) args[0];
            current_place_block = (Block) Blueprint.retry_blueprint.get(current_place_pos)[0];

            if (!mc.player.world.getBlockState(current_place_pos).getBlock().equals(Blueprint.retry_blueprint.get(current_place_pos)[0])) {

                Blueprint.retry_blueprint.remove(current_place_pos);

                place(current_place_pos, current_place_block);
                Blueprint.retry_blueprint.put(current_place_pos, new Object[]{current_place_block, ClientTick.ticks});

            } else {

                if (Blueprint.retry_blueprint.get(current_place_pos)[0] == Block.getBlockById(49)) {
                    Session.obsidian_placed++;
                }
                Blueprint.retry_blueprint.remove(current_place_pos);

            }

        }

        // Normal blueprint
        if (blueprint == Blueprint.blueprints.blueprint) {

            if (!Blueprint.blueprint.isEmpty()) {

                current_place_pos = Blueprint.blueprint.keySet().stream().findFirst().get();
                current_place_block = Blueprint.blueprint.get(current_place_pos);

                // Places the block
                place(current_place_pos, current_place_block);
                Blueprint.retry_blueprint.put(current_place_pos, new Object[]{current_place_block, ClientTick.ticks});
                Blueprint.blueprint.remove(current_place_pos);

            }

        }

    }

    public static boolean place(BlockPos pos, Block block) {

        // If block is occupied, digs
        Material pos_material = mc.world.getBlockState(current_place_pos).getMaterial();
        if (!pos_material.isLiquid() && pos_material != Material.AIR) {

            Blueprint.blueprint_digging.put(current_place_pos, Block.getBlockById(0));
            return false;

        }

        // The placing

        if (mc.player.inventory.getCurrentItem().getItem() != Item.getItemFromBlock(block)) {

            Inventory.set_hot_bar_0(Item.getIdFromItem(Item.getItemFromBlock(block)));
            return false;

        }

        placing_stuck_ticks = 0;
        Object[] block_and_face = get_face(pos);
        if (Objects.isNull(block_and_face)) {
            return false;
        }

        NetHandlerPlayClient connection = mc.getConnection();
        connection.sendPacket(new CPacketPlayerTryUseItemOnBlock((BlockPos) block_and_face[0], (EnumFacing) block_and_face[1], EnumHand.MAIN_HAND, 0, 0, 0));
        mc.player.swingArm(EnumHand.MAIN_HAND);

        return true;

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
