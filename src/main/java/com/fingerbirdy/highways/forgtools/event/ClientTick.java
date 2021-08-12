package com.fingerbirdy.highways.forgtools.event;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.module.Highways.Highways;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber(modid = ForgTools.MOD_ID)
public class ClientTick {

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {

        if (Highways.enabled) {

            Highways.tick();

        }

    }

}
