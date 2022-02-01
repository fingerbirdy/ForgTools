package com.fingerbirdy.highways.forgtools.gui.clickgui.element;

import com.fingerbirdy.highways.forgtools.gui.clickgui.ProcessInput;
import com.fingerbirdy.highways.forgtools.gui.clickgui.UserInput;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.util.HashMap;

public class Button {

    // Object[] {String elementWrapperId, String text}
    public static HashMap<String, Object[]> elements = new HashMap<>();

    public static void addElement(String id, String elementWrapperId, String text) {

        elements.put(id, new Object[] {elementWrapperId, text});

    }

    private static boolean getIfMouseIn(Object[] elementWrapper) {

        return (UserInput.mouseX > (int) elementWrapper[2] && UserInput.mouseX < (int) elementWrapper[2] + Background.width && UserInput.mouseY > (int) elementWrapper[3] && UserInput.mouseY < (int) elementWrapper[3] + ElementWrapper.elementHeight);

    }

    public static void render(FontRenderer fontRenderer) {

        for (String key : elements.keySet()) {

            Object[] element = elements.get(key);
            Object[] elementWrapper = ElementWrapper.elements.get((String) element[0]);

            boolean mouseIn = getIfMouseIn(elementWrapper);
            int color = Background.elementColor;
            if (mouseIn) {
                color = Background.elementColorHover;
            }

            if (mouseIn && UserInput.leftButtonClicked) {

                ProcessInput.b.put(key, true);

            }

            fontRenderer.drawStringWithShadow((String) element[1], (int) elementWrapper[2] + ElementWrapper.elementMargin, (int) elementWrapper[3] + ElementWrapper.elementMargin, Background.textColor);

        }

    }

}
