package com.fingerbirdy.highways.forgtools.action;

import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Inventory {

    public static void swap (int slot1, int slot2) {

        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot1, 0, ClickType.SWAP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot2, 0, ClickType.SWAP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.PICKUP, mc.player);

    }

    // returns true if item successfully moved
    public static boolean set_hot_bar_0(int item) {



        int slot = -1;
        // Cycles through every slot and checks if
        for (int i = 0; i < mc.player.inventory.getSizeInventory(); i++) {

            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (Item.getIdFromItem(stack.getItem()) == item) {
                slot = i;
                break;
            }

        }

        if (slot == -1) {
            return false;
        }

        // switching hotbar slot
        if (slot < 9) {

            while (slot != mc.player.inventory.currentItem) {
                mc.player.inventory.changeCurrentItem(-1);
            }
            return true;

        }

        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.SWAP, mc.player);
        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 0, 0, ClickType.PICKUP, mc.player);
        return true;

    }

}
