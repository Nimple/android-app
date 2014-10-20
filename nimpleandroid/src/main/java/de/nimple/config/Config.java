package de.nimple.config;

import de.nimple.BuildConfig;

final public class Config {
    public static final String MIXPANEL_TOKEN;
    public static final String GOOGLE_LICENSE_KEY = "";
    public static final String GOOGLE_PRODUCT_ID = "";

    static {
        if (BuildConfig.DEBUG) {
            // dev token
            MIXPANEL_TOKEN = "6e3eeca24e9b2372e8582b381295ca0c";
        } else {
            // prod token
            MIXPANEL_TOKEN = "c0d8c866df9c197644c6087495151c7e";
        }
    }
}