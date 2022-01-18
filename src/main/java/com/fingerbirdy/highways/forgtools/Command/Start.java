package com.fingerbirdy.highways.forgtools.Command;

import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.Session;

import java.util.ArrayList;

public class Start {

    public static ArrayList<String> start_warnings = new ArrayList<>();

    public static boolean execute(String[] args) {

        try {

            ForgTools.sendClientChat("Starting...", true);
            for (String warning : start_warnings) {
                ForgTools.sendClientChat(warning, true);
            }
            ForgTools.enabled = Session.start();
            return true;

        } catch (Exception e) {

            ForgTools.sendClientChat("Failed to start.", true);
            Stop.execute((Config.config.get("prefix").toString() + "stop failed_to_start").split(" "));
            return false;

        }

    }

}
