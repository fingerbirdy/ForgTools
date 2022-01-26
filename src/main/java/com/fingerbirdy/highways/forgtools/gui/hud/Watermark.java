package com.fingerbirdy.highways.forgtools.gui.hud;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.ManageRender;
import net.minecraft.client.gui.FontRenderer;

public class Watermark {

    private static final String[] jsonPath = new String[] {"hud", "watermark"};

    public static String contents = null;
    public static int renderValue = -1;

    public static void render(FontRenderer text, int width, int height) {

        renderValue = ManageRender.getRenderValue(jsonPath);

        if (contents == null) {
            if (!ForgTools.latestVersion.equals(ForgTools.VERSION)) {
                contents = "New update available for " + ForgTools.MOD_NAME + ": " + ForgTools.latestVersion;
            } else {
                contents = ForgTools.MOD_NAME + " " + ForgTools.VERSION;
            }
        }

        if (renderValue == 0 || (renderValue == 1 && !ForgTools.latestVersion.equals(ForgTools.VERSION))) {

            Integer[] position = ManageRender.getPosition(text, jsonPath, width, height, contents, 0);
            text.drawStringWithShadow(contents, position[0], position[1], 0xededed);

        }

    }

}