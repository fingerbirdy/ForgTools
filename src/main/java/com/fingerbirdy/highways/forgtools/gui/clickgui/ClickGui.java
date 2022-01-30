package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.gui.ManageRender;
import com.fingerbirdy.highways.forgtools.gui.clickgui.element.*;
import com.fingerbirdy.highways.forgtools.util.Config;
import com.fingerbirdy.highways.forgtools.util.Enum;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;

public class ClickGui {

    public static boolean enabled = true;

    private static final String[] jsonPathForgTools = new String[] {"clickgui", "forgtools"};
    private static final String[] jsonPathBuild = new String[] {"clickgui", "build"};
    private static final String[] jsonPathBehaviour = new String[] {"clickgui", "behaviour"};

    // Renders and processes the entire click gui
    public static void render(FontRenderer fontRenderer, int width, int height) {

        enabled = ForgTools.mc.ingameGUI.getChatGUI().getChatOpen();

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
            CycleButton.elements.clear();

            // FORGTOOLS
            backgroundHeight = 2 * ElementWrapper.elementHeight;
            position = ManageRender.getPosition(jsonPathForgTools, Background.width, backgroundHeight, width, height);
            backgroundId = "forgtools\\background";

            Background.addElement(backgroundId, position[0], position[1], backgroundHeight);

            ElementWrapper.addElement("forgtools/element/header", backgroundId, 0);
            Header.addElement("forgtools/element/header/contents", "forgtools/element/header", "Forg Tools");

            ElementWrapper.addElement("forgtools/element/enabled", backgroundId, 1);
            ToggleButton.addElement("forgtools/element/enabled/contents", "forgtools/element/enabled", "Enabled", ForgTools.enabled);

            // BUILD
            backgroundHeight = 5 * ElementWrapper.elementHeight;
            position = ManageRender.getPosition(jsonPathBuild, Background.width, backgroundHeight, width, height);
            backgroundId = "build\\background";

            Background.addElement(backgroundId, position[0], position[1], backgroundHeight);

            ElementWrapper.addElement("build/element/header", backgroundId, 0);
            Header.addElement("build/element/header/contents", "build/element/header", "Build");

            ElementWrapper.addElement("build/element/mode", backgroundId, 1);
            CycleButton.addElement("build/element/mode/contents", "build/element/mode", "Mode", Enum.enumToInt(Enum.buildMode.valueOf(Config.config.get("build_mode"))), new String[] {Enum.buildMode.PAVE.name(), Enum.buildMode.TUNNEL.name()});

            ElementWrapper.addElement("build/element/direction", backgroundId, 2);
            CycleButton.addElement("build/element/direction/contents", "build/element/direction", "Direction", Enum.enumToInt(Enum.direction.valueOf(Config.config.get("direction"))), new String[] {Enum.direction.PX.name(), Enum.direction.NX.name(), Enum.direction.PZ.name(), Enum.direction.NZ.name(), Enum.direction.PP.name(), Enum.direction.PM.name(), Enum.direction.MP.name(), Enum.direction.MM.name()});

            ElementWrapper.addElement("build/element/width", backgroundId, 3);
            Slider.addElement("build/element/width/contents", "build/element/width", "Width", 0, Integer.parseInt(Config.config.get("width")), 11);

            ElementWrapper.addElement("build/element/height", backgroundId, 4);
            Slider.addElement("build/element/height/contents", "build/element/height", "Height", 0, Integer.parseInt(Config.config.get("height")), 4);


            // Renders all elements
            Background.render();
            ElementWrapper.render();
            Header.render(fontRenderer);
            Slider.render(fontRenderer);
            ToggleButton.render(fontRenderer);
            CycleButton.render(fontRenderer);

        }

    }

}
