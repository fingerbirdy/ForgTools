package com.fingerbirdy.highways.forgtools.command;

import com.fingerbirdy.highways.forgtools.util.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.action.Session;

import java.util.ArrayList;

public class Start {

    public static final ArrayList<String> start_warnings = new ArrayList<>();

    public static void execute(String[] args) {

        try {

            for (String warning : start_warnings) {
                ForgTools.sendClientChat(warning, true);
            }
            ForgTools.enabled = Session.start();

        } catch (Exception e) {

            ForgTools.sendClientChat("Failed to start.", true);
            Stop.execute((Config.config.get("prefix") + "stop failed_to_start").split(" "));

        }

    }

}
