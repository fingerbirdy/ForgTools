package com.fingerbirdy.highways.forgtools.gui.clickgui.element;

import net.minecraft.client.gui.Gui;

import java.util.HashMap;

public class Background {

    public static final int width = 150;
    public static final int backgroundColor = 0x77000000;
    public static final int elementColor = 0xFF00AA00;
    public static final int elementColorHover = 0xFF00CC00;
    public static final int textColor = 0xededed;

    // Integer[] {x, y, height}
    public static HashMap<String, Integer[]> elements = new HashMap<>();

    public static void addElement(String id, int x, int y, int height) {

        elements.put(id, new Integer[] {x, y, height});

    }

    public static void render() {

        for (Integer[] element : elements.values()) {

            Gui.drawRect(element[0], element[1], element[0] + width, element[1] + element[2], backgroundColor);

        }

    }

}
