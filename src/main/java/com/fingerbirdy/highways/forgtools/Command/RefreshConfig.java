package com.fingerbirdy.highways.forgtools.Command;

import com.fingerbirdy.highways.forgtools.Config;

public class RefreshConfig {

    public static void execute(String[] args) {

        Config.init();

    }

}
