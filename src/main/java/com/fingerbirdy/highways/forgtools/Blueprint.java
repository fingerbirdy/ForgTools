/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
*/

package com.fingerbirdy.highways.forgtools;

import com.fingerbirdy.highways.forgtools.Event.ClientTick;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Blueprint {

    public static LinkedHashMap<BlockPos, Block> blueprint = new LinkedHashMap<>(); // placing
    public static LinkedHashMap<BlockPos, Block> blueprint_digging = new LinkedHashMap<>(); // breaking retry
    public static LinkedHashMap<BlockPos, Object[]> retry_blueprint = new LinkedHashMap<>(); // placing, Object[] should consist of Block to_place, int attempted_tick

    // Generate blueprint for tunneling and paving
    public static void generate_build () {

        blueprint.clear();

        // Paving algorithm
        if (Enum.build_mode.valueOf(Config.config.get("build_mode")) == Enum.build_mode.PAVE) {

            Block material = Block.getBlockFromName(Config.config.get("material"));
            int width = Integer.parseInt(Config.config.get("width"));

            // STRAIGHTS
            if (Session.direction == Enum.direction.PX || Session.direction == Enum.direction.NX || Session.direction == Enum.direction.PZ || Session.direction == Enum.direction.NZ) {

                ArrayList<Integer> onsets = new ArrayList<>();
                ArrayList<Integer> y_coord = new ArrayList<>();
                ArrayList<Integer> offsets = new ArrayList<>();

                int axis_progress;
                if (Session.direction == Enum.direction.PX || Session.direction == Enum.direction.NX) {
                    axis_progress = (int) mc.player.posX;
                } else {
                    axis_progress = (int) mc.player.posZ;
                }

                for (int i = axis_progress; i < axis_progress + 3; i++) {

                    for (int j = 0; j < width; j++) {

                        if (Session.direction == Enum.direction.PX || Session.direction == Enum.direction.PZ) {
                            onsets.add(i);
                        } else { onsets.add(i - 3); }
                        offsets.add(j - (width / 2) + Session.axis_offset);
                        if (j == 0 || j == width - 1) {
                            y_coord.add(Session.y_position);
                        } else {
                            y_coord.add(Session.y_position - 1);
                        }

                    }

                }

                if (Session.direction == Enum.direction.NX || Session.direction == Enum.direction.NZ) {
                    Collections.reverse(onsets);
                    Collections.reverse(y_coord);
                    Collections.reverse(offsets);
                }

                if (Session.direction == Enum.direction.PX || Session.direction == Enum.direction.NX) {
                    for (int i = 0; i < onsets.size(); i++) {
                        BlockPos position = new BlockPos(onsets.get(i), y_coord.get(i), offsets.get(i));
                        if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                    }
                } else {
                    for (int i = 0; i < onsets.size(); i++) {
                        BlockPos position = new BlockPos(offsets.get(i), y_coord.get(i), onsets.get(i));
                        if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                    }
                }

            }

            // DIAGONALS

            else {

                int axis_progress = (int) Math.floor(mc.player.posX);

                for (int i = 0; i < 3; i++) {

                    for (int j = (int) Math.ceil(width / -2); j <= width / 2; j++) {

                        int y_coord;
                        if (j == (int) Math.ceil(width / -2) || j > width / 2 - 1) {
                            y_coord = Session.y_position;
                        } else {
                            y_coord = Session.y_position - 1;
                        }

                        if (j < 0) {

                            if (Session.direction == Enum.direction.PP) {
                                BlockPos position = new BlockPos(axis_progress + j + i, y_coord, axis_progress + Session.axis_offset + i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            } else if (Session.direction == Enum.direction.MP) {
                                BlockPos position = new BlockPos(axis_progress - i, y_coord, axis_progress*-1 + j + Session.axis_offset + i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            } else if (Session.direction == Enum.direction.MM) {
                                BlockPos position = new BlockPos(axis_progress - j - i, y_coord, axis_progress + Session.axis_offset - i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            } else if (Session.direction == Enum.direction.PM) {
                                BlockPos position = new BlockPos(axis_progress + j + i, y_coord, axis_progress*-1 + Session.axis_offset - i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            }

                        } else {

                            if (Session.direction == Enum.direction.PP) {
                                BlockPos position = new BlockPos(axis_progress + i, y_coord, axis_progress + Session.axis_offset - j + i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            } else if (Session.direction == Enum.direction.MP) {
                                BlockPos position = new BlockPos(axis_progress + j - i, y_coord, axis_progress*-1 + Session.axis_offset + i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            } else if (Session.direction == Enum.direction.MM) {
                                BlockPos position = new BlockPos(axis_progress - i, y_coord, axis_progress + j + Session.axis_offset - i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            } else if (Session.direction == Enum.direction.PM) {
                                BlockPos position = new BlockPos(axis_progress + i, y_coord, axis_progress*-1 + j + Session.axis_offset - i);
                                if (!mc.player.world.getBlockState(position).getBlock().equals(material)) { blueprint.put(position, material); }
                            }

                        }

                    }

                }

            }

        }

    }

    // Generate blueprint for getting obsidian
    public static void generate_get_obsidian() {

        // Check if there is nearby end chest
        BlockPos player_pos = new BlockPos(Math.round(mc.player.posX - 0.5), (int) mc.player.posY, Math.round(mc.player.posZ - 0.5));
        BlockPos[] check_poss = new BlockPos[] {player_pos.east(), player_pos.west(), player_pos.north(), player_pos.south()};
        for (BlockPos pos : check_poss) {

            if (mc.world.getBlockState(pos).getBlock() == Block.getBlockById(130)) {
                blueprint_digging.put(pos, Block.getBlockById(0));
                return;
            }

        }

        // Place an ender chest
        for (BlockPos pos : check_poss) {

            if (mc.world.getBlockState(pos.down()).isFullCube() && Math.abs(mc.player.posX - pos.getX() + 0.5) > 1 && Math.abs(mc.player.posZ - pos.getZ() + 0.5) > 1) {
                blueprint.put(pos, Block.getBlockById(130));
                return;
            }

        }

        ForgTools.sendClientChat("Could not find empty adjacent block", true);

    }

}
