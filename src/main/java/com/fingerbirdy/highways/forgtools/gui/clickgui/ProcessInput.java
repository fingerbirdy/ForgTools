package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.command.Start;
import com.fingerbirdy.highways.forgtools.command.Stop;
import com.fingerbirdy.highways.forgtools.util.Config;

import java.util.HashMap;

public class ProcessInput {

    // Stored as <String id, (Object) valueIn>
    public static final HashMap<String, Integer> i = new HashMap<>();
    public static final HashMap<String, Boolean> b = new HashMap<>();
    public static final HashMap<String, String> s = new HashMap<>();

    // Processes any click gui input, called every render
    public static void process() {

        if (!i.isEmpty()) {

            for (String id : i.keySet()) {

                // build/width
                if (id.equals("build/element/width/contents")) {

                    Config.config.replace("width", String.valueOf(i.get(id)));

                }

                // build/height
                else if (id.equals("build/element/height/contents")) {

                    Config.config.replace("height", String.valueOf(i.get(id)));

                }

                i.remove(id);

            }

            Config.save();

        }

        if (!b.isEmpty()) {

            for (String id : b.keySet()) {

                // forgtools/enabled
                if (id.equals("forgtools/element/enabled/contents")) {

                    if (b.get(id)) {

                        Start.execute(new String[]{"clickgui"});

                    } else {

                        Stop.execute(new String[]{"clickgui"});

                    }

                }

                b.remove(id);

            }

            Config.save();

        }

        if (!s.isEmpty()) {

            for (String id : s.keySet()) {

                // build/mode
                if (id.equals("build/element/mode/contents")) {

                    Config.config.replace("build_mode", s.get(id));

                }

                // build/direction
                else if (id.equals("build/element/direction/contents")) {

                    Config.config.replace("direction", s.get(id));

                }

                s.remove(id);

            }

            Config.save();

        }

    }

}
