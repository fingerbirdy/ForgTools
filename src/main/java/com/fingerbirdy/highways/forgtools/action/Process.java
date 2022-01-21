package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.Blueprint;
import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.Enum;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Process {

    // Pickaxe, Ender Chest, Obsidian...

    public static Enum.process_status status = Enum.process_status.GET_OBSIDIAN;
    public static int status_ticks = 0;

    public static void tick() {

        status_ticks++;

        // Checks delay ticks
        if (ClientTick.ticks % Integer.parseInt(Config.config.get("delay_ticks")) != 0) {
            return;
        }

        // Checks if status needs to change
        int material_in_inventory = 0;
        for (ItemStack slot : mc.player.inventory.mainInventory) {
            if (slot.getItem() == Item.getItemFromBlock(Block.getBlockFromName(Config.config.get("material")))) {
                material_in_inventory += slot.getCount();
            }
        }

        if (status == Enum.process_status.GET_OBSIDIAN) {

            if (material_in_inventory > Integer.parseInt(Config.config.get("target_obsidian_refill_stacks")) * 64) {

                status = Enum.process_status.FINISH_GET_OBSIDIAN;
                status_ticks = 0;

            }

        } else if (status == Enum.process_status.BUILD) {

            if (material_in_inventory < Integer.parseInt(Config.config.get("obsidian_refill_threshold"))) {

                status = Enum.process_status.GET_OBSIDIAN;
                status_ticks = 0;
                Blueprint.blueprint.clear();
                Blueprint.retry_blueprint.clear();
                Blueprint.blueprint_digging.clear();

            } else if (Blueprint.blueprint.isEmpty() && Blueprint.retry_blueprint.isEmpty() && Blueprint.blueprint_digging.isEmpty()) {

                //status = Enum.process_status.CONTINUE;
                //status_ticks = 0;

            }

        }

        // Generate Blueprints
        if (status == Enum.process_status.BUILD) {
            Blueprint.generate_build();
        } else if (status == Enum.process_status.GET_OBSIDIAN || status == Enum.process_status.FINISH_GET_OBSIDIAN) {
            Blueprint.generate_get_obsidian();
        }

        // Do the action
        if (status == Enum.process_status.GET_OBSIDIAN || status == Enum.process_status.BUILD) {

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