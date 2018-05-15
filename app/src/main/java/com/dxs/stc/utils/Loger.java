package com.dxs.stc.utils;

import android.util.Log;

public final class Loger {

    public static boolean IS_DEBUG = true;
    public static boolean DEBUG_LOG = true;
    public static boolean SHOW_ACTIVITY_STATE = true;

    public static final void openDebutLog(boolean enable) {
        IS_DEBUG = enable;
        DEBUG_LOG = enable;
    }


    private static String getClassName() {
        // 这里的数组的index，即2，是根据你工具类的层级取的值，可根据需求改变
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    public static final void openActivityState(boolean enable) {
        SHOW_ACTIVITY_STATE = enable;
    }

    public static final void debug(String msg) {
        if (IS_DEBUG) {
            Log.i(getClassName()+":debug---", msg);
        }
    }

    public static final void log(String packName, String state) {
        debugLog(packName, state);
    }

    public static final void debug(String msg, Throwable tr) {
        if (IS_DEBUG) {
            Log.i(getClassName()+":debug---", msg, tr);
        }
    }

    public static final void state(String packName, String state) {
        if (SHOW_ACTIVITY_STATE) {
            Log.d(getClassName()+"activity_state", packName + state);
        }
    }

    public static final void debugLog(String packName, String state) {
        if (DEBUG_LOG) {
            Log.d(getClassName()+":debug---", packName + state);
        }
    }

    public static final void exception(Exception e) {
        if (DEBUG_LOG) {
            e.printStackTrace();
        }
    }

    public static final void debug(String msg, Object... format) {
        debug(String.format(msg, format));
    }

    public static final void e(String str) {
        if (DEBUG_LOG) {
            Log.e(getClassName()+"--lookState--", "" + str);
        }
    }
}
