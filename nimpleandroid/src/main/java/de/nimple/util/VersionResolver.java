package de.nimple.util;

import android.os.Build;

final public class VersionResolver {
	private VersionResolver() {
	}

	public static boolean isAtLeastHoneycomb() {
		return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean isAtLeastICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
}