package com.fingerbirdy.highways.forgtools.module.Highways;

import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class Actions {

    public static ArrayList<Integer> actionsX = new ArrayList<>();
    public static ArrayList<Integer> actionsY = new ArrayList<>();
    public static ArrayList<Integer> actionsZ = new ArrayList<>();
    public static ArrayList<Action> actionsA = new ArrayList<>();
    public static int actionIndex = 0;
    public static boolean actionStarted = false;
    public static int actionStuckTicks = 0;

    public static void update() {

        ForgTools.logger.info("-------------\n" + Config.config.get("highways/build/width"));
        int width = (int) Config.config.get("highways/build/width");
        int height = (int) Config.config.get("highways/build/height");
        boolean railings = (boolean) Config.config.get("highways/build/railings");
        boolean xaxis = Highways.direction == Direction.X || Highways.direction == Direction.X_; // true = +x/-x, false = +z/-z
        boolean paxis = Highways.direction == Direction.X || Highways.direction == Direction.Z; // true = x/z, false = -x/-z

        while (actionsA.size() < actionIndex + 16) {

            Highways.Mode mode = (Highways.Mode) Config.config.get("highways/build/mode");
            boolean pavingBehind;
            if (Highways.direction == Direction.X || Highways.direction == Direction.Z) {
                pavingBehind = Highways.pavingI < Highways.clearingI;
            } else {
                pavingBehind = Highways.pavingI > Highways.clearingI;
            }

            // GOING TO BLOCK

            if (mode == Highways.Mode.pave) {

                actionsA.add(Action.walkto);
                if (xaxis) {

                    if (paxis) { actionsX.add(Highways.pavingI - 1); }
                    else { actionsX.add(Highways.pavingI + 1); }
                    actionsY.add(Highways.startY);
                    actionsZ.add(Highways.axisOffset);

                } else {

                    if (paxis) { actionsZ.add(Highways.pavingI - 1); }
                    else { actionsZ.add(Highways.pavingI + 1); }
                    actionsY.add(Highways.startY);
                    actionsX.add(Highways.axisOffset);

                }

            }

            if (mode == Highways.Mode.tunnel) {

                actionsA.add(Action.walkto);
                if (xaxis) {

                    if (paxis) { actionsX.add(Highways.clearingI - 1); }
                    else { actionsX.add(Highways.clearingI + 1); }
                    actionsY.add(Highways.startY - 1);
                    actionsZ.add(Highways.axisOffset);

                } else {

                    if (paxis) { actionsZ.add(Highways.clearingI - 1); }
                    else { actionsZ.add(Highways.clearingI + 1); }
                    actionsY.add(Highways.startY - 1);
                    actionsX.add(Highways.axisOffset);

                }

            }

            if (mode == Highways.Mode.pave && pavingBehind) {

                // PLACING OBSIDIAN

                if (Highways.direction == Direction.X || Highways.direction == Direction.Z) {Highways.pavingI++;}
                else {Highways.pavingI--;}

                for (int j = Highways.axisOffset - (int) (width / 2); j <= Highways.axisOffset + (int) (width / 2); j++) {

                    if (!Block.isEqualTo(ForgTools.mc.world.getBlockState(new BlockPos(Highways.pavingI, Highways.startY - 1, j)).getBlock(), Block.getBlockFromName("minecraft:obsidian"))) {

                        if (xaxis) {
                            actionsX.add(Highways.pavingI);
                            actionsZ.add(j);
                        } else {
                            actionsZ.add(Highways.pavingI);
                            actionsX.add(j);
                        }
                        if (railings && (j == Highways.axisOffset - (int) (width / 2) || j == Highways.axisOffset + (int) (width / 2))) {
                            actionsY.add(Highways.startY);
                        } else {
                            actionsY.add(Highways.startY - 1);
                        }
                        actionsA.add(Action.place);

                    }

                }

            } else if (mode == Highways.Mode.tunnel || mode == Highways.Mode.pave) {

                // CLEARING TUNNEL

                if (Highways.direction == Direction.X || Highways.direction == Direction.Z) {
                    Highways.clearingI++;
                } else {
                    Highways.clearingI--;
                }

                for (int j = Highways.axisOffset - (int) (width / 2); j <= Highways.axisOffset + (int) (width / 2); j++) {

                    for (int k = Highways.startY - 1; k < Highways.startY + height - 1; k++) {

                        int addToX, addToY, addToZ;
                        if (xaxis) {
                            addToX = Highways.clearingI;
                            addToY = k;
                            addToZ = j;
                        } else {
                            addToZ = Highways.clearingI;
                            addToY = k;
                            addToX = j;
                        }

                        Block blockAt = ForgTools.mc.world.getBlockState(new BlockPos(addToX, addToY, addToZ)).getBlock();

                        if (Block.isEqualTo(blockAt, Block.getBlockFromName("minecraft:obsidian"))) {

                            if (addToY == Highways.startY - 1) {

                                return;

                            }

                            if (addToY == Highways.startY && (addToX == Highways.axisOffset - (int) (width / 2) || addToX == Highways.axisOffset + (int) (width / 2))) {

                                return;

                            }

                        }

                        if (!Block.isEqualTo(blockAt, Block.getBlockFromName("minecraft:air"))) {

                            actionsX.add(addToX);
                            actionsZ.add(addToZ);
                            actionsY.add(addToY);
                            actionsA.add(Action.mine);
                            return;

                        }

                        if (Block.isEqualTo(blockAt, Block.getBlockFromName("minecraft:lava"))) {

                            actionsX.add(addToX);
                            actionsZ.add(addToZ);
                            actionsY.add(addToY);
                            actionsA.add(Action.clog);
                            return;

                        }

                    }

                }

            }

        }

    }

    public static void perform() {

        actionStuckTicks = 0;
        ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "c");
        ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel c");

        if (actionsA.get(actionIndex) == Action.place) {

            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel 1 " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));
            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel 2 " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));
            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel fill obsidian");
            return;

        }

        if (actionsA.get(actionIndex) == Action.mine) {

            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel 1 " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));
            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel 2 " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));
            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel fill air");
            return;

        }

        if (actionsA.get(actionIndex) == Action.clog) {

            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel 1 " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));
            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel 2 " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));
            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "sel fill netherrack");
            return;

        }

        if (actionsA.get(actionIndex) == Action.walkto) {

            ForgTools.mc.player.sendChatMessage(ForgTools.baritonePrefix + "goto " + actionsX.get(actionIndex) + " " + actionsY.get(actionIndex) + " " + actionsZ.get(actionIndex));

        }

    }

    public static boolean checkIfFinished() {

        try {

            if (actionsA.get(actionIndex) == Action.place) {

                return Block.isEqualTo(ForgTools.mc.world.getBlockState(new BlockPos(actionsX.get(actionIndex), actionsY.get(actionIndex), actionsZ.get(actionIndex))).getBlock(), Block.getBlockFromName("minecraft:obsidian"));

            } else if (actionsA.get(actionIndex) == Action.mine) {

                return Block.isEqualTo(ForgTools.mc.world.getBlockState(new BlockPos(actionsX.get(actionIndex), actionsY.get(actionIndex), actionsZ.get(actionIndex))).getBlock(), Block.getBlockFromName("minecraft:air"));

            } else if (actionsA.get(actionIndex) == Action.walkto) {

                return ((int) ForgTools.mc.player.posX == actionsX.get(actionIndex) && (int) ForgTools.mc.player.posY == actionsY.get(actionIndex) && (int) ForgTools.mc.player.posZ == actionsZ.get(actionIndex));

            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

}