package com.fingerbirdy.highways.forgtools.util;

import com.fingerbirdy.highways.forgtools.ForgTools;

import java.util.ArrayList;

public class ServerTps {

    private static final int cache_length = 30;
    private static final ArrayList<Double> tick_rates = new ArrayList<>();
    private static long last_time_update = -1L;

    public static double tick_rate = 20D;
    public static double dynamic_delay_multiplier = 1D;

    // Resets all tps data, called on connection
    public static void init() {

        tick_rates.clear();
        tick_rates.add(20D);
        last_time_update = System.nanoTime();

    }

    // Calculates approx tps, called on time update packet
    public static void received_time_update_packet() {

        double elapsed = (System.nanoTime() - last_time_update) / 1E9;
        if (tick_rates.size() >= cache_length) {
            tick_rates.remove(0);
        }

        tick_rates.add(Math.min(20D / elapsed, 20D));
        tick_rate = tick_rates.stream().mapToDouble(a -> a).average().orElse(20D);
        dynamic_delay_multiplier = 20D / tick_rate;

        last_time_update = System.nanoTime();
        ForgTools.sendClientChat(tick_rate + " " + dynamic_delay_multiplier, true);

    }

}