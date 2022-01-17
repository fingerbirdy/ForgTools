package com.fingerbirdy.highways.forgtools.Util;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritoneProvider;
import baritone.api.pathing.goals.GoalXZ;

public class BaritoneHelper {

    public static IBaritoneProvider provider;

    // goto
    // TODO make y coord work
    public static void go_to (int x, int y, int z) {

        provider.getPrimaryBaritone().getCustomGoalProcess().setGoal(new GoalXZ(x, z));

    }

    // Sets up baritone
    public static void setup() {

        if (provider != null) {
            provider = BaritoneAPI.getProvider();
        }

    }

}
