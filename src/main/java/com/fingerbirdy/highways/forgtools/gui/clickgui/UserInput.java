package com.fingerbirdy.highways.forgtools.gui.clickgui;

import com.fingerbirdy.highways.forgtools.ForgTools;
import org.lwjgl.input.Mouse;

public class UserInput {

    public static int mouseX = 0;
    public static int mouseY = 0;
    public static boolean leftButtonDown = false;
    public static boolean leftButtonClicked = false;
    public static double scaleFactor = 0;

    private static boolean allowLeftButtonClick = true;

    public static void update(int displayHeight) {

        scaleFactor = (double) displayHeight / ForgTools.mc.displayHeight;
        mouseX = (int) (Mouse.getX() * scaleFactor);
        mouseY = (int) ((1 + ForgTools.mc.displayHeight - Mouse.getY()) * scaleFactor);
        leftButtonDown = Mouse.isButtonDown(0);
        if (leftButtonClicked) {
            leftButtonClicked = false;
        }
        if (!leftButtonDown) {
            allowLeftButtonClick = true;
        }
        if (allowLeftButtonClick && leftButtonDown) {
            leftButtonClicked = true;
            allowLeftButtonClick = false;
        }

    }

}
