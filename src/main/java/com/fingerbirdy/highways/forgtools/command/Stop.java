package com.fingerbirdy.highways.forgtools.command;

import com.fingerbirdy.highways.forgtools.ForgTools;
import com.fingerbirdy.highways.forgtools.Session;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.fingerbirdy.highways.forgtools.ForgTools.mc;

public class Stop {

    public static void execute(String[] args) {

        ForgTools.enabled = false;

        // Saves file containing exceptions for bug reporting
        if (Session.exceptions.size() != 0) {

            String path = mc.gameDir.getAbsolutePath() + "\\ForgTools\\logs\\" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd HH_mm_ss_SSS")) + ".txt";
            ForgTools.sendClientChat("During your session, an error occurred. Please send " + path + " to fingerbirdy#8056", true);
            try {
                File exceptions_file = new File(path);
                exceptions_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {

                FileWriter exceptions_file = new FileWriter(path);
                exceptions_file.append("The below contents contain unintended results. Please send this file to fingerbirdy#8056.");
                for (int i = 0; i < Session.exceptions.size(); i++) {
                    exceptions_file.append("\n").append(Session.exceptions.get(i));
                }
                exceptions_file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
