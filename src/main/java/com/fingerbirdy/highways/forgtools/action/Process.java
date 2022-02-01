package com.fingerbirdy.highways.forgtools.action;

import com.fingerbirdy.highways.forgtools.blueprinting.Blueprint;
import com.fingerbirdy.highways.forgtools.util.Config;
import com.fingerbirdy.highways.forgtools.util.Enum;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Process {

    // Pickaxe, Ender Chest, Obsidian...

    public static Enum.processStatus status = Enum.processStatus.BUILD;
    public static int status_ticks = 0;

    public static void tick() {

        // Checks if player is in a world
        if (mc.player.world == null) {
            return;
        }

        // Generate Blueprints
        if (status_ticks == 0 || (int) mc.player.posX != (int) mc.player.lastTickPosX || (int) mc.player.posY != (int) mc.player.lastTickPosY || (int) mc.player.posZ != (int) mc.player.lastTickPosZ) {

            if (status == Enum.processStatus.BUILD) {
                Blueprint.generate_build();
            }

        }

        if (status == Enum.processStatus.GET_OBSIDIAN || status == Enum.processStatus.FINISH_GET_OBSIDIAN) {
            Blueprint.generate_get_obsidian();
        }

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

        if (status == Enum.processStatus.GET_OBSIDIAN) {

            if (material_in_inventory > Integer.parseInt(Config.config.get("target_obsidian_refill_stacks")) * 64) {

                status = Enum.processStatus.FINISH_GET_OBSIDIAN;
                status_ticks = 0;

            }

        } else if (status == Enum.processStatus.BUILD) {

            if (material_in_inventory < Integer.parseInt(Config.config.get("obsidian_refill_threshold"))) {

                status = Enum.processStatus.GET_OBSIDIAN;
                ForgTools.sendClientChat(String.valueOf(material_in_inventory), true);
                status_ticks = 0;
                Blueprint.blueprint.clear();
                Blueprint.retry_blueprint.clear();
                Blueprint.blueprint_digging.clear();

            } else if (Blueprint.blueprint.isEmpty() && Blueprint.retry_blueprint.isEmpty() && Blueprint.blueprint_digging.isEmpty()) {

                //status = Enum.process_status.CONTINUE;
                //status_ticks = 0;

            }

        }

        // Do the action
        if (status == Enum.processStatus.GET_OBSIDIAN || status == Enum.processStatus.FINISH_GET_OBSIDIAN || status == Enum.processStatus.BUILD) {

            boolean interaction_valid = true;

            if (interaction_valid && !Blueprint.priority_blueprint.isEmpty()) {
                Place.tick(Blueprint.blueprints.priority_blueprint, new Object[] {});
                interaction_valid = false;
            }

            if (interaction_valid && !Blueprint.blueprint_digging.isEmpty()) {
                Dig.tick(Blueprint.blueprints.blueprint_digging);
                interaction_valid = false;
            }

            if (interaction_valid && !Blueprint.retry_blueprint.isEmpty()) {

                for (BlockPos pos : Blueprint.retry_blueprint.keySet()) {
                    if (ClientTick.ticks - (int) Blueprint.retry_blueprint.get(pos)[1] >= 20) {

                        Place.tick(Blueprint.blueprints.retry_blueprint, new Object[] {pos});
                        interaction_valid = false;
                        break;

                    }
                }

            }

            if (interaction_valid && !Blueprint.blueprint.isEmpty()) {
                Place.tick(Blueprint.blueprints.blueprint, new Object[] {});
                interaction_valid = false;
            }

        }

        else if (status == Enum.processStatus.CONTINUE) {

            if (Continue.going) {
                Continue.tick();
            } else {
                status = Enum.processStatus.BUILD;
            }

        }

        status_ticks++;

    }

}