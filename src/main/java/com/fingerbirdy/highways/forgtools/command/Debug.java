package com.fingerbirdy.highways.forgtools.command;

import com.fingerbirdy.highways.forgtools.action.Continue;
import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;

public class Debug {

    public static boolean continu = false;

    public static void execute(String[] args) {

        if (args.length == 1) { ForgTools.sendClientChat("Invalid syntax! Use " + Config.config.get("prefix") + "debug [type]", true);
        }

        else if (args[1].equals("continue")) {

            continu = !continu;

        }

        else { ForgTools.sendClientChat("Debug: Incorrect debug value", true);
        }

    }

    public static void tick() {

        if (continu) {
            Continue.tick();
        }

    }

}
