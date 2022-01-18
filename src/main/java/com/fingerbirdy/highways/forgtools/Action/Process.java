package com.fingerbirdy.highways.forgtools.Action;

import com.fingerbirdy.highways.forgtools.Blueprint;
import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.Enum;
import com.fingerbirdy.highways.forgtools.Event.ClientTick;
import com.fingerbirdy.highways.forgtools.ForgTools;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Process {

    // Pickaxe, Ender Chest, Obsidian...

    public static Enum.process_status status = Enum.process_status.GETOBSIDIAN;

    public static void tick() {

        // Checks delay ticks
        if (ClientTick.ticks % Integer.parseInt(Config.config.get("delay_ticks")) != 0) {
            return;
        }

        // Checks if status needs to change
        int obsidian_in_inventory = 0;
        for (ItemStack slot : mc.player.inventory.mainInventory) {
            if (slot.getItem() == Item.getItemFromBlock(Block.getBlockFromName(Config.config.get("material")))) {
                obsidian_in_inventory += slot.getCount();
            }
        }

        if (obsidian_in_inventory > Integer.parseInt(Config.config.get("target_obsidian_refill_stacks")) * 64) {

            status = Enum.process_status.BUILD;

        } else if (obsidian_in_inventory < Integer.parseInt(Config.config.get("obsidian_refill_threshold"))) {

            status = Enum.process_status.GETOBSIDIAN;
            Blueprint.blueprint.clear();
            Blueprint.retry_blueprint.clear();
            Blueprint.blueprint_digging.clear();

        } else if (status == Enum.process_status.BUILD && Blueprint.blueprint.isEmpty() && Blueprint.retry_blueprint.isEmpty() && Blueprint.blueprint_digging.isEmpty()) {

            status = Enum.process_status.CONTINUE;

        }

        // Generate Blueprints
        if (status == Enum.process_status.BUILD && Math.floor(mc.player.lastTickPosX) != Math.floor(mc.player.posX) || Math.floor(mc.player.lastTickPosY) != Math.floor(mc.player.posY) || Math.floor(mc.player.lastTickPosZ) != Math.floor(mc.player.posZ)) {
            Blueprint.generate_build();
        } else if (status == Enum.process_status.GETOBSIDIAN) {
            Blueprint.generate_get_obsidian();
        }

        // Do the action
        if (status == Enum.process_status.GETOBSIDIAN || status == Enum.process_status.BUILD) {

            if (!Blueprint.blueprint_digging.isEmpty()) {
                Dig.tick();
            } else if (!Blueprint.blueprint.isEmpty() || !Blueprint.retry_blueprint.isEmpty()) {
                Place.tick();
            }

        }

        else if (status == Enum.process_status.CONTINUE) {

            if (Continue.going) {
                Continue.tick();
            } else {
                status = Enum.process_status.BUILD;
            }

        }

    }

}