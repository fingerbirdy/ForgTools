package com.fingerbirdy.highways.forgtools.gui.hud;

import com.fingerbirdy.highways.forgtools.action.Process;
import com.fingerbirdy.highways.forgtools.action.Session;
import com.fingerbirdy.highways.forgtools.event.ClientTick;
import com.fingerbirdy.highways.forgtools.gui.ManageRender;
import net.minecraft.client.gui.FontRenderer;

public class Debug {

    public static void renderText(FontRenderer text, int width, int height) {

        if (com.fingerbirdy.highways.forgtools.command.Debug.debug) {

            String[] contents = new String[] {

                    "Tick: " + ClientTick.ticks,
                    "Process status: " + Process.status.name(),
                    "Current interaction: " + Session.current_action

            };

            int i = 0;
            for (String line : contents) {

                Integer[] position = ManageRender.getPosition(text, new String[]{"hud", "debug"}, width, height, line, i);
                text.drawStringWithShadow(line, position[0], position[1], 0xFFFFFF);
                i++;

            }

        }

    }

}
