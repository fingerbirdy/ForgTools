package com.fingerbirdy.highways.forgtools.command;

import com.fingerbirdy.highways.forgtools.Config;

public class RefreshConfig {

    public static void execute(String[] args) {

        Config.init();

    }

}
