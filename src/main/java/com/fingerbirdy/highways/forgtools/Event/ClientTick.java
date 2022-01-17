/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools.Event;

import com.fingerbirdy.highways.forgtools.Action.Dig;
import com.fingerbirdy.highways.forgtools.Action.Process;
import com.fingerbirdy.highways.forgtools.Blueprint;
import com.fingerbirdy.highways.forgtools.Command.Debug;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.Session;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

@Mod.EventBusSubscriber(modid = ForgTools.MOD_ID)
public class ClientTick {

    public static int ticks = 0;

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent event) {

        ticks++;

        if (ForgTools.enabled) {

            try {

                Debug.tick();
                Process.tick();

            } catch (Exception e) {

                e.printStackTrace();
                StackTraceElement[] stack_trace = e.getStackTrace();
                Session.exceptions.add("\n==================================================");
                for (int i = 0; i < stack_trace.length; i++) {
                    Session.exceptions.add(stack_trace[i].toString());
                }

            }

        }

    }

}
