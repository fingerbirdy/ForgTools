/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools.util;

public class Enum {

    public static int enumToInt(buildMode enumIn) {

        if (enumIn == buildMode.PAVE) {
            return 0;
        } if (enumIn == buildMode.TUNNEL) {
            return 1;
        }
        return -1;

    }

    public static int enumToInt(direction enumIn) {

        if (enumIn == direction.PX) {
            return 0;
        } if (enumIn == direction.NX) {
            return 1;
        } if (enumIn == direction.PZ) {
            return 2;
        } if (enumIn == direction.NZ) {
            return 3;
        } if (enumIn == direction.PP) {
            return 4;
        } if (enumIn == direction.PM) {
            return 5;
        } if (enumIn == direction.MP) {
            return 6;
        } if (enumIn == direction.MM) {
            return 7;
        }
        return -1;

    }

    public enum buildMode {
        PAVE,
        TUNNEL
    }

    public enum direction {
        PX, // +x
        NX, // -x
        PZ, // +z
        NZ, // -z
        PP, // ++
        PM, // +-
        MP, // -+
        MM, // --
    }

    public enum processStatus {
        GET_OBSIDIAN, // Needs to get more obsidian
        FINISH_GET_OBSIDIAN, // Breaking the final ender chest
        BUILD, // Building/digging
        CONTINUE, // Moving forward
    }

    public enum action {
        PLACE,
        BREAK
    }

    public enum actionPriority {
        PLACE3,
        BREAK2,
        PLACE1
    }

}
