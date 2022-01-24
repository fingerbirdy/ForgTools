package com.fingerbirdy.highways.forgtools.gui;

import com.fingerbirdy.highways.forgtools.util.FileSystem;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Settings {

    // dx, dy: dock left center right, top center bottom
    private static final String defaults = "{'hud':{'watermark':{'enabled':0,'dx':1,'dy':0,'x':0,'y':3},'debug':{'enabled':0,'dx':0,'dy':0,'x':3,'y':3},'stats':{'enabled':0,'dx':0,'dy':0,'x':3,'y':3}}}";

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

    // [DANGER] Resets GUI config, called when json object is invalid
    public static void setToDefault() {

        gui = new JsonParser().parse(defaults).getAsJsonObject();
        save();
        ForgTools.sendClientChat("Something went wrong, so we had to reset your HUD.", true);
        ForgTools.sendClientChat("Please submit an issue on github.", true);

    }

}
