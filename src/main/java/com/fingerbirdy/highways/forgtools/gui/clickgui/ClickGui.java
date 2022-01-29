package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.gui.ManageRender;
import com.fingerbirdy.highways.forgtools.gui.clickgui.element.*;
import com.fingerbirdy.highways.forgtools.util.Config;
import net.minecraft.client.gui.FontRenderer;

public class ClickGui {

    public static boolean enabled = true;

    private static final String[] jsonPathForgTools = new String[] {"clickgui", "forgtools"};
    private static final String[] jsonPathBuild = new String[] {"clickgui", "build"};
    private static final String[] jsonPathBehaviour = new String[] {"clickgui", "behaviour"};

    // Renders and processes the entire click gui
    public static void render(FontRenderer fontRenderer, int width, int height) {

        if (enabled) {

            ProcessInput.process();

            // Clears rendering data
            Integer[] position;
            String backgroundId;
            int backgroundHeight;

            Background.elements.clear();
            ElementWrapper.elements.clear();
            Header.elements.clear();
            Slider.elements.clear();
            ToggleButton.elements.clear();

            // FORGTOOLS
            backgroundHeight = 2 * ElementWrapper.elementHeight;
            position = ManageRender.getPosition(jsonPathForgTools, Background.width, backgroundHeight, width, height);
            backgroundId = "forgtools\\background";

            Background.addElement(backgroundId, position[0], position[1], backgroundHeight);

            ElementWrapper.addElement("forgtools/element/header", backgroundId, 0);
            Header.addElement("forgtools/element/header/contents", "forgtools/element/header", "Forg Tools");

            ElementWrapper.addElement("forgtools/element/enabled", backgroundId, 1);
            ToggleButton.addElement("forgtools/element/enabled/contents", "forgtools/element/enabled", "Enabled", true);

            // BUILD
            backgroundHeight = 3 * ElementWrapper.elementHeight;
            position = ManageRender.getPosition(jsonPathBuild, Background.width, backgroundHeight, width, height);
            backgroundId = "build\\background";

            Background.addElement(backgroundId, position[0], position[1], backgroundHeight);

            ElementWrapper.addElement("build/element/header", backgroundId, 0);
            Header.addElement("build/element/header/contents", "build/element/header", "Build");

            ElementWrapper.addElement("build/element/width", backgroundId, 1);
            Slider.addElement("build/element/width/contents", "build/element/width", "Width", 0, Integer.parseInt(Config.config.get("width")), 11);

            ElementWrapper.addElement("build/element/height", backgroundId, 2);
            Slider.addElement("build/element/height/contents", "build/element/height", "Height", 0, Integer.parseInt(Config.config.get("height")), 4);


            // Renders all elements
            Background.render();
            ElementWrapper.render();
            Header.render(fontRenderer);
            Slider.render(fontRenderer);
            ToggleButton.render(fontRenderer);

        }

    }

}
