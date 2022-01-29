package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.ForgTools;
import org.lwjgl.input.Mouse;

public class UserInput {

    public static int mouseX = 0;
    public static int mouseY = 0;
    public static boolean leftButtonDown = false;
    public static double scaleFactor = 0;

    public static void update(int displayHeight) {

        scaleFactor = (double) displayHeight / ForgTools.mc.displayHeight;
        mouseX = (int) (Mouse.getX() * scaleFactor);
        mouseY = (int) ((1 + ForgTools.mc.displayHeight - Mouse.getY()) * scaleFactor);
        leftButtonDown = Mouse.isButtonDown(0);

    }

}
