package com.fingerbirdy.highways.forgtools.gui.clickgui.element;

import net.minecraft.client.gui.FontRenderer;

import java.util.HashMap;

public class Header {

    public static HashMap<String, String[]> elements = new HashMap<>();

    public static void addElement(String id, String elementWrapperId, String text) {

        elements.put(id, new String[] {elementWrapperId, text});

    }

    public static void render(FontRenderer fontRenderer) {

        for (String key : elements.keySet()) {

            String[] element = elements.get(key);
            Object[] elementWrapper = ElementWrapper.elements.get(element[0]);

            fontRenderer.drawStringWithShadow(element[1], (int) elementWrapper[2] + ElementWrapper.elementMargin, (int) elementWrapper[3] + ElementWrapper.elementMargin, Background.textColor);

        }

    }

}
