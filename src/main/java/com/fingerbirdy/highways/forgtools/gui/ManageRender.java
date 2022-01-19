package com.fingerbirdy.highways.forgtools.gui;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.hud.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class ManageRender {

    // Renders everything related to ForgTools, called on RenderGameOverlayEvent
    public static void render() {

        FontRenderer font_renderer = ForgTools.mc.fontRenderer;

        int width = new ScaledResolution(ForgTools.mc).getScaledWidth();
        int height = new ScaledResolution(ForgTools.mc).getScaledWidth();

        Watermark.render(font_renderer, width, height);

    }

}
