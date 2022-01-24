/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
*/

package com.fingerbirdy.highways.forgtools.util;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.action.Process;

import com.fingerbirdy.highways.forgtools.action.Session;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Blueprint {

    public static final LinkedHashMap<BlockPos, Block> blueprint = new LinkedHashMap<>(); // placing
    public static final LinkedHashMap<BlockPos, Block> blueprint_digging = new LinkedHashMap<>(); // breaking
    public static final LinkedHashMap<BlockPos, Block> priority_blueprint = new LinkedHashMap<>(); // placing before breaking
    public static final LinkedHashMap<BlockPos, Object[]> retry_blueprint = new LinkedHashMap<>(); // placing, Object[] should consist of Block to_place, int attempted_tick

    public enum blueprints {

        // In order of priority
        priority_blueprint,
        blueprint_digging,
        retry_blueprint,
        blueprint

    }

    // Generate blueprint for tunneling and paving
    public static void generate_build () {

        blueprint.clear();

        // Clear space algorithm
        Block material = Block.getBlockFromName(Config.config.get("material"));
        int width = Integer.parseInt(Config.config.get("width"));
        int height = Integer.parseInt(Config.config.get("height"));

        // STRAIGHTS CLEARING
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

                    boolean isCorner = (j == 0 || j == width - 1);
                    int yCoord = Session.y_position;
                    int yLimit = Session.y_position + height;
                    if (isCorner) { yCoord++; }
                    if (Session.buildMode == Enum.build_mode.PAVE) { yCoord--; yLimit--; }

                    while (yCoord < yLimit) {

                        if (Session.direction == Enum.direction.PX || Session.direction == Enum.direction.PZ) {
                            onsets.add(i);
                        } else {
                            onsets.add(i - 3);
                        }
                        offsets.add(j - (width / 2) + Session.axis_offset);
                        y_coord.add(yCoord);

                        yCoord++;

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
                    if (!mc.player.world.getBlockState(position).getBlock().equals(material) && mc.player.world.getBlockState(position).getBlock() != Block.getBlockById(0)) { blueprint_digging.put(position, Block.getBlockById(0)); }
                }
            } else {
                for (int i = 0; i < onsets.size(); i++) {
                    BlockPos position = new BlockPos(offsets.get(i), y_coord.get(i), onsets.get(i));
                    if (!mc.player.world.getBlockState(position).getBlock().equals(material) && mc.player.world.getBlockState(position).getBlock() != Block.getBlockById(0)) { blueprint_digging.put(position, Block.getBlockById(0)); }
                }
            }

        }

        // DIAGONALS CLEARING

        else {

            int axis_progress = (int) Math.floor(mc.player.posX);

            ArrayList<BlockPos> positions = new ArrayList<>();

            for (int i = 0; i < 3; i++) {

                for (int j = (int) Math.ceil(width / -2F); j <= width / 2; j++) {

                    boolean isCorner = (j == (int) Math.ceil(width / -2F) || j > width / 2 - 1);

                    int y_coord = Session.y_position;
                    int yLimit = Session.y_position;
                    if (isCorner) { y_coord++; }
                    if (Session.buildMode == Enum.build_mode.PAVE) { y_coord--; yLimit--; }

                    while (y_coord < yLimit) {

                        if (j < 0) {

                            if (Session.direction == Enum.direction.PP) {
                                BlockPos position = new BlockPos(axis_progress + j + i, y_coord, axis_progress + Session.axis_offset + i);
                                positions.add(position);
                            } else if (Session.direction == Enum.direction.MP) {
                                BlockPos position = new BlockPos(axis_progress - i, y_coord, axis_progress * -1 + j + Session.axis_offset + i);
                                positions.add(position);
                            } else if (Session.direction == Enum.direction.MM) {
                                BlockPos position = new BlockPos(axis_progress - j - i, y_coord, axis_progress + Session.axis_offset - i);
                                positions.add(position);
                            } else if (Session.direction == Enum.direction.PM) {
                                BlockPos position = new BlockPos(axis_progress + j + i, y_coord, axis_progress * -1 + Session.axis_offset - i);
                                positions.add(position);
                            }

                        } else {

                            if (Session.direction == Enum.direction.PP) {
                                BlockPos position = new BlockPos(axis_progress + i, y_coord, axis_progress + Session.axis_offset - j + i);
                                positions.add(position);
                            } else if (Session.direction == Enum.direction.MP) {
                                BlockPos position = new BlockPos(axis_progress + j - i, y_coord, axis_progress * -1 + Session.axis_offset + i);
                                positions.add(position);
                            } else if (Session.direction == Enum.direction.MM) {
                                BlockPos position = new BlockPos(axis_progress - i, y_coord, axis_progress + j + Session.axis_offset - i);
                                positions.add(position);
                            } else if (Session.direction == Enum.direction.PM) {
                                BlockPos position = new BlockPos(axis_progress + i, y_coord, axis_progress * -1 + j + Session.axis_offset - i);
                                positions.add(position);
                            }

                        }

                        y_coord++;

                    }

                }

            }

            for (BlockPos position : positions) {
                if (!mc.player.world.getBlockState(position).getBlock().equals(material) && mc.player.world.getBlockState(position).getBlock() != Block.getBlockById(0)) { blueprint_digging.put(position, Block.getBlockById(0)); }
            }

        }

        // Paving algorithm
        if (Session.buildMode == Enum.build_mode.PAVE) {

            // STRAIGHTS PAVING
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

                        boolean isCorner = (j == 0 || j == width - 1);
                        int y_position = Session.y_position;
                        if (isCorner) { y_position++; }
                        if (Session.buildMode == Enum.build_mode.PAVE) { y_position--; }

                        if (Session.direction == Enum.direction.PX || Session.direction == Enum.direction.PZ) {
                            onsets.add(i);
                        } else { onsets.add(i - 3); }
                        offsets.add(j - (width / 2) + Session.axis_offset);

                        y_coord.add(y_position);

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

            // DIAGONALS PAVING

            else {

                int axis_progress = (int) Math.floor(mc.player.posX);

                for (int i = 0; i < 3; i++) {

                    for (int j = (int) Math.ceil(width / -2F); j <= width / 2; j++) {

                        boolean isCorner = (j == (int) Math.ceil(width / -2F) || j > width / 2 - 1);

                        int y_coord;
                        if (isCorner) {
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

            if (Process.status == Enum.process_status.FINISH_GET_OBSIDIAN && Process.status_ticks >= 20) {

                Process.status = Enum.process_status.BUILD;
                Process.status_ticks = 0;
                blueprint.clear();

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