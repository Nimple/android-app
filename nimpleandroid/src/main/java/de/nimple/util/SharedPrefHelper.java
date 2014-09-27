package de.nimple.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

public abstract class SharedPrefHelper {
	public static boolean exists(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.contains(key);
	}

	public static void remove(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		prefs.edit().remove(key).commit();
	}

	public static void putString(String key, String value, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor edit = prefs.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public static String getString(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getString(key, "");
	}

    public static void putInt(String key, int value, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        Editor edit = prefs.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static int getInt(String key, Context ctx){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getInt(key,0);
    }

	public static void putBoolean(String key, boolean value, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		Editor edit = prefs.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	public static boolean getBoolean(String key, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getBoolean(key, false);
	}

	public static boolean getBoolean(String key, boolean defValue, Context ctx) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		return prefs.getBoolean(key, defValue);
	}
=======
public class SharedPrefHelper {
    public static boolean exists(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.contains(key);
    }

    public static void remove(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().remove(key).apply();
    }

    public static void putString(String key, String value, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putString(key, value).apply();
    }

    public static String getString(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getString(key, "");
    }

    public static void putBoolean(String key, boolean value, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        prefs.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue, Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        return prefs.getBoolean(key, defValue);
    }
>>>>>>> master
}