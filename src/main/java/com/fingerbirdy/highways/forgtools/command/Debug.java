package com.fingerbirdy.highways.forgtools.command;

import com.fingerbirdy.highways.forgtools.action.Continue;
import com.fingerbirdy.highways.forgtools.ForgTools;

public class Debug {

    public static boolean debug = false;
    public static boolean continu = false;
    public static boolean render = false;

    public static void execute(String[] args) {

        if (args.length == 1) {

            debug = !debug;

        }

        else if (args[1].equals("continue")) {

            continu = !continu;

        }

        else if (args[1].equals("render")) {

            render = !render;

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
