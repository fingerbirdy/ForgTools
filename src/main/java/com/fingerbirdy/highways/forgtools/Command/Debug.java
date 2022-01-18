package com.fingerbirdy.highways.forgtools.Command;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

import com.fingerbirdy.highways.forgtools.Action.Continue;
import com.fingerbirdy.highways.forgtools.Config;
import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.Util.ServerTps;
import net.minecraft.inventory.ClickType;

public class Debug {

    public static boolean continu = false;

    public static boolean execute(String[] args) {

        if (args.length == 1) { ForgTools.sendClientChat("Invalid syntax! Use " + Config.config.get("prefix").toString() + "debug [type]", true); return false; }

        else if (args[1].equals("continue")) {

            continu = !continu;

        }

        else { ForgTools.sendClientChat("Debug: Incorrect debug value", true); return false; }

        return true;

    }

    public static void tick() {

        if (continu) {
            Continue.tick();
        }

    }

}
