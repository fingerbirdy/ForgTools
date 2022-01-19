package com.fingerbirdy.highways.forgtools.gui;

import com.fingerbirdy.highways.forgtools.FileSystem;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Settings {

    private static final String defaults = "{'hud':{'watermark':{'enabled':0}}}";

    public static JsonObject gui = new JsonObject();

    public static void load() {

        JsonParser parser = new JsonParser();
        String rawJson = FileSystem.readAll("GUI_SETTINGS");
        if (rawJson == null) {
            gui = parser.parse(defaults).getAsJsonObject();
        } else {

            try {
                gui = parser.parse(rawJson).getAsJsonObject();
            } catch (Exception e) {
                gui = parser.parse(defaults).getAsJsonObject();
            }

        }

        save();

    }

    public static void save() {

        FileSystem.save("GUI_SETTINGS", gui.toString());

    }

}
