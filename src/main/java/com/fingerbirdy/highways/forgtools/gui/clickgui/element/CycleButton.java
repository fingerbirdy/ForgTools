package com.fingerbirdy.highways.forgtools.gui.clickgui.element;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.clickgui.ProcessInput;
import com.fingerbirdy.highways.forgtools.gui.clickgui.UserInput;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.util.HashMap;

public class CycleButton {

    // Object[] {String elementWrapperId, String text, int value, String[] values}
    public static HashMap<String, Object[]> elements = new HashMap<>();

    public static void addElement(String id, String elementWrapperId, String text, int value, String[] values) {

        elements.put(id, new Object[] {elementWrapperId, text, value, values});

    }

    private static boolean getIfMouseIn(Object[] elementWrapper) {

        return (UserInput.mouseX > (int) elementWrapper[2] && UserInput.mouseX < (int) elementWrapper[2] + Background.width && UserInput.mouseY > (int) elementWrapper[3] && UserInput.mouseY < (int) elementWrapper[3] + ElementWrapper.elementHeight);

    }

    public static void render(FontRenderer fontRenderer) {

        for (String key : elements.keySet()) {

            Object[] element = elements.get(key);
            Object[] elementWrapper = ElementWrapper.elements.get((String) element[0]);

            boolean mouseIn = getIfMouseIn(elementWrapper);

            if (mouseIn && UserInput.leftButtonClicked) {

                int index = (int) element[2] + 1;
                if (index >= ((String[]) element[3]).length) {
                    index = 0;
                }
                ProcessInput.s.put(key, ((String[]) element[3])[index]);

            }

            fontRenderer.drawStringWithShadow((String) element[1], (int) elementWrapper[2] + ElementWrapper.elementMargin, (int) elementWrapper[3] + ElementWrapper.elementMargin, Background.textColor);
            fontRenderer.drawStringWithShadow(((String[]) element[3])[(int) element[2]], (int) elementWrapper[2] - ElementWrapper.elementMargin + Background.width - fontRenderer.getStringWidth(((String[]) element[3])[(int) element[2]]), (int) elementWrapper[3] + ElementWrapper.elementMargin, Background.textColor);

        }

    }

}
