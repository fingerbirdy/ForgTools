package com.fingerbirdy.highways.forgtools.gui.hud;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.action.Session;
import com.fingerbirdy.highways.forgtools.gui.ManageRender;
import com.fingerbirdy.highways.forgtools.util.Enum;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;

public class Stats {

    private static final String[] jsonPath = new String[] {"hud", "stats"};

    public static int renderValue = -1;

    public static void render(FontRenderer text, int width, int height) {

        ArrayList<String> contents = new ArrayList<>();

        renderValue = ManageRender.getRenderValue(jsonPath);

        if (renderValue == 1) {

            if (ForgTools.enabled) {

                long run_time = (System.nanoTime() - Session.start_timestamp) / 1000000000;

                contents.add("This session: " + (int) Math.floor(run_time / 3600F) + "h " + (int) Math.floor(run_time / 60F) % 60 + "m " + (int) Math.floor(run_time) % 60 + "s");

                if (Session.buildMode == Enum.build_mode.PAVE) {
                    contents.add("Obsidian placed: " + Session.obsidian_placed);
                } else if (Session.buildMode == Enum.build_mode.TUNNEL) {
                    contents.add("Netherrack mined: " + Session.blocks_mined);
                }

                int i = 0;
                for (String line : contents) {

                    Integer[] position = ManageRender.getPosition(text, jsonPath, width, height, line, i);
                    text.drawStringWithShadow(line, position[0], position[1], 0xededed);
                    i++;

                }

            }

        }

    }

}
