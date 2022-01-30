package com.fingerbirdy.highways.forgtools.gui.clickgui.element;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.clickgui.UserInput;

import java.util.HashMap;

public class ElementWrapper {

    public static final int elementMargin = 3;
    public static final int elementHeight = ForgTools.mc.fontRenderer.FONT_HEIGHT + (elementMargin * 2);

    // Object[] {String backgroundId, String elementNumber, int x, int y}
    public static HashMap<String, Object[]> elements = new HashMap<>();

    public static void addElement(String id, String backgroundId, int elementNumber) {

        Integer[] backgroundPosition = Background.elements.get(backgroundId);
        elements.put(id, new Object[] {backgroundId, elementNumber, backgroundPosition[0], backgroundPosition[1] + elementHeight * elementNumber});

    }

    public static boolean getIfMouseIn(Object[] elementWrapper) {

        return (UserInput.mouseX > (int) elementWrapper[2] && UserInput.mouseX < (int) elementWrapper[2] + Background.width && UserInput.mouseY > (int) elementWrapper[3] && UserInput.mouseY < (int) elementWrapper[3] + ElementWrapper.elementHeight);

    }

    public static void render() {

    }

}
