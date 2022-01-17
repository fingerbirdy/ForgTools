package com.fingerbirdy.highways.forgtools.Command;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

import com.fingerbirdy.highways.forgtools.Action.Continue;
import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.Enum;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.Util.BaritoneHelper;
import net.minecraft.inventory.ClickType;

public class Debug {

    public static boolean continu = false;

    public static boolean execute(String[] args) {

        if (args.length == 1) { ForgTools.sendClientChat("Invalid syntax! Use " + Config.config.get("prefix").toString() + "debug [type]", true); return false; }

        if (args[1].equals("inv")) {

            if (args.length == 4) {

                try {
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, Integer.parseInt(args[2]), 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, Integer.parseInt(args[3]), 0, ClickType.SWAP, mc.player);
                    mc.playerController.windowClick(mc.player.inventoryContainer.windowId, Integer.parseInt(args[2]), 0, ClickType.PICKUP, mc.player);
                } catch (Exception e) {
                    ForgTools.sendClientChat("didnt work", true);
                }

            } else if (args.length == 3) {

                ForgTools.mc.player.inventory.changeCurrentItem(Integer.parseInt(args[2]));

            }

        }

        else if (args[1].equals("continue")) {

            continu = !continu;

        }

        else if (args[1].equals("goto")) {

            if (args.length >= 5) {

                BaritoneHelper.go_to(Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));

            }

        }

        else { ForgTools.sendClientChat("Debug: Incorrect debug value", true); return false; }

        return true;

    }

    public static void tick() {

        if (continu) {
            Continue.tick();
        }

    }

}
