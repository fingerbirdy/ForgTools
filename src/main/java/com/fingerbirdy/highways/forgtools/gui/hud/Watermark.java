package com.fingerbirdy.highways.forgtools.gui.hud;

import com.fingerbirdy.highways.forgtools.ForgTools;
import net.minecraft.client.gui.FontRenderer;

public class Watermark {

    public static void render(FontRenderer text, int width, int height) {

        String contents = ForgTools.MOD_NAME + " " + ForgTools.VERSION;
        text.drawStringWithShadow(contents, width - text.getStringWidth(contents) - 3, height - 50, 0xFFFFFF);

    }

}