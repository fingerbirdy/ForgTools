package com.fingerbirdy.highways.forgtools.command;

import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.gui.Settings;

public class RefreshConfig {

    public static void execute(String[] args) {

        Config.init();
        Settings.load();

    }

}
