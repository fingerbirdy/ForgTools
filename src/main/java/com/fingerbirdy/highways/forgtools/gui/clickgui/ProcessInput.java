package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.util.Config;

import java.util.HashMap;

public class ProcessInput {

    // Stored as <String id, Object valueIn>
    public static final HashMap<String, Integer> i = new HashMap<>();
    public static final HashMap<String, Boolean> b = new HashMap<>();

    // Called every screen render
    public static void process() {

        if (!i.isEmpty()) {

            for (String id : i.keySet()) {

                // build/width
                if (id.equals("build/element/width/contents")) {

                    Config.config.replace("width", String.valueOf(i.get(id)));

                }

                // build/height
                if (id.equals("build/element/height/contents")) {

                    Config.config.replace("height", String.valueOf(i.get(id)));

                }

                i.remove(id);

            }

            Config.save();

        }

    }

}
