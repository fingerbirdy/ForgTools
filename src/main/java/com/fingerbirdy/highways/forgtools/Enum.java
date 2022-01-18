/*
This class is a part of Forg Tools. Feel free to PM #fingerbirdy#8056 on Discord if you would like to use any code that is within this class.
 */

package com.fingerbirdy.highways.forgtools;

public class Enum {

    public enum build_mode {
        TUNNEL,
        PAVE
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

    public enum process_status {
        GETOBSIDIAN,
        BUILD,
        CONTINUE,
    }

}
