package com.fingerbirdy.highways.forgtools.event;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.ManageRender;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ForgTools.MOD_ID)
public class RenderGameOverlay {

    @SubscribeEvent
    public static void renderGameOverlayEvent(RenderGameOverlayEvent event) {

        ForgTools.mc.entityRenderer.setupOverlayRendering();
        ManageRender.render();

    }

}