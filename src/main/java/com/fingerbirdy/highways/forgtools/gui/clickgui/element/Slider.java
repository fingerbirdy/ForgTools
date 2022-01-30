package com.fingerbirdy.highways.forgtools.gui.clickgui.element;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.clickgui.ProcessInput;
import com.fingerbirdy.highways.forgtools.gui.clickgui.UserInput;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

import java.util.HashMap;

public class Slider {

    // Object[] {String elementWrapperId, String text, int min, int value, int max}
    public static HashMap<String, Object[]> elements = new HashMap<>();

    public static void addElement(String id, String elementWrapperId, String text, int min, int value, int max) {

        elements.put(id, new Object[] {elementWrapperId, text, min, value, max});

    }

    private static int getSliderWidth(int min, int value, int max) {

        return (int) ((float) value / (max - min) * Background.width);

    }

    private static int getSliderValue(Object[] elementWrapper, int min, int max) {

        return Math.round(((float) UserInput.mouseX - (int) elementWrapper[2]) / (Background.width) * (max - min));

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

            if (mouseIn && UserInput.leftButtonDown) {

                ProcessInput.i.put(key, getSliderValue(elementWrapper, (int) element[2], (int) element[4]));

            }

            Gui.drawRect((int) elementWrapper[2], (int) elementWrapper[3], (int) elementWrapper[2] + getSliderWidth((int) element[2], (int) element[3], (int) element[4]), (int) elementWrapper[3] + ElementWrapper.elementHeight, color);

            fontRenderer.drawStringWithShadow((String) element[1], (int) elementWrapper[2] + ElementWrapper.elementMargin, (int) elementWrapper[3] + ElementWrapper.elementMargin, Background.textColor);
            fontRenderer.drawStringWithShadow(String.valueOf(element[3]), (int) elementWrapper[2] - ElementWrapper.elementMargin + Background.width - fontRenderer.getStringWidth(String.valueOf(element[3])), (int) elementWrapper[3] + ElementWrapper.elementMargin, Background.textColor);

        }

    }

}
