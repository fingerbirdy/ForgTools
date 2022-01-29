package com.fingerbirdy.highways.forgtools.gui;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.action.Session;
import com.fingerbirdy.highways.forgtools.gui.clickgui.*;
import com.fingerbirdy.highways.forgtools.gui.hud.*;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public class ManageRender {

    // Renders the text
    public static void renderText() {

        try {

            FontRenderer font_renderer = ForgTools.mc.fontRenderer;

            int width = new ScaledResolution(ForgTools.mc).getScaledWidth();
            int height = new ScaledResolution(ForgTools.mc).getScaledHeight();
            UserInput.update(height);

            Watermark.renderText(font_renderer, width, height);
            Debug.renderText(font_renderer, width, height);
            Stats.renderText(font_renderer, width, height);
            ClickGui.render(font_renderer, width, height);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static Integer getRenderValue(String[] json_settings_path) {

        for (int i = 0; i < 2; i++) {

            try {

                JsonObject settings = Settings.gui;
                for (String next : json_settings_path) {
                    settings = settings.getAsJsonObject(next);
                }

                return settings.get("enabled").getAsInt();

            } catch (Exception e) {

                Settings.setToDefault();
                e.printStackTrace();

            }

        }

        return 0;

    }

    public static Integer[] getPosition(FontRenderer renderer, String[] json_settings_path,  int width, int height, String text, int line) {

        int dx = 0;
        int dy = 0;
        int x = 0;
        int y = 0;

        // Safely access the settings
        for (int i = 0; i < 2; i++) {

            try {

                JsonObject settings = Settings.gui;
                for (String next : json_settings_path) {
                    settings = settings.getAsJsonObject(next);
                }

                dx = settings.get("dx").getAsInt();
                dy = settings.get("dy").getAsInt();
                x = settings.get("x").getAsInt();
                y = settings.get("y").getAsInt();
                break;

            } catch (Exception e) {

                Settings.setToDefault();
                e.printStackTrace();

            }

        }

        Integer[] position = new Integer[] {0, 0};
        int textWidth = renderer.getStringWidth(text);

        switch (dx) {

            case 0:
                position[0] = x;
                break;
            case 1:
                position[0] = (width / 2) - (textWidth / 2) + x;
                break;
            case 2:
                position[0] = width - textWidth + x;
                break;

        }

        switch (dy) {

            case 0:
                position[1] = y + renderer.FONT_HEIGHT * line;
                break;
            case 1:
                position[1] = (height / 2) - (renderer.FONT_HEIGHT) + y + renderer.FONT_HEIGHT * line;
                break;
            case 2:
                position[1] = height - (renderer.FONT_HEIGHT) + y - renderer.FONT_HEIGHT * line;
                break;

        }

        return position;

    }

    public static Integer[] getPosition(String[] json_settings_path, int elementWidth, int elementHeight, int width, int height) {

        int dx = 0;
        int dy = 0;
        int x = 0;
        int y = 0;

        // Safely access the settings
        for (int i = 0; i < 2; i++) {

            try {

                JsonObject settings = Settings.gui;
                for (String next : json_settings_path) {
                    settings = settings.getAsJsonObject(next);
                }

                dx = settings.get("dx").getAsInt();
                dy = settings.get("dy").getAsInt();
                x = settings.get("x").getAsInt();
                y = settings.get("y").getAsInt();
                break;

            } catch (Exception e) {

                Settings.setToDefault();
                e.printStackTrace();

            }

        }

        Integer[] position = new Integer[] {0, 0};

        switch (dx) {

            case 0:
                position[0] = x;
                break;
            case 1:
                position[0] = (width / 2) - (elementWidth / 2) + x;
                break;
            case 2:
                position[0] = width - elementWidth + x;
                break;

        }

        switch (dy) {

            case 0:
                position[1] = y;
                break;
            case 1:
                position[1] = (height / 2) - (elementHeight / 2) + y;
                break;
            case 2:
                position[1] = height + y - elementHeight;
                break;

        }

        return position;

    }

}
