package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.command.Start;
import com.fingerbirdy.highways.forgtools.command.Stop;
import com.fingerbirdy.highways.forgtools.util.Config;

import java.net.URI;
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

                // behaviour/delayticks
                else if (id.equals("behaviour/element/delayticks/contents")) {

                    Config.config.replace("delay_ticks", String.valueOf(i.get(id)));

                }

                // behaviour/obsidiangoal
                else if (id.equals("behaviour/element/obsidiangoal/contents")) {

                    Config.config.replace("target_obsidian_refill_stacks", String.valueOf(i.get(id)));

                }

                // behaviour/obsidianthreshold
                else if (id.equals("behaviour/element/obsidianthreshold/contents")) {

                    Config.config.replace("obsidian_refill_threshold", String.valueOf(i.get(id)));

                }

                // behaviour/nukerlimit
                else if (id.equals("behaviour/element/nukerlimit/contents")) {

                    Config.config.replace("nuker_limit", String.valueOf(i.get(id)));

                }

                // behaviour/nukerlimit
                else if (id.equals("behaviour/element/nukertimeout/contents")) {

                    Config.config.replace("nuker_timeout", String.valueOf(i.get(id)));

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
                // forgtools/issue
                if (id.equals("forgtools/element/issue/contents")) {

                    try {
                        java.awt.Desktop.getDesktop().browse(URI.create(ForgTools.GITHUB + "/issues/new"));
                        ForgTools.sendClientChat("Opening github...", true);
                        ForgTools.sendClientChat("When writing an issue, keep in mind the following -", true);
                        ForgTools.sendClientChat(" - The title should describe the issue", true);
                        ForgTools.sendClientChat(" - The issue should include context and, if possible, how to reproduce it", true);
                    } catch (Exception e) {
                        ForgTools.sendClientChat("Failed to open the webpage... ironic", true);
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
