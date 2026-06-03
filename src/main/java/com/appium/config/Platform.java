package com.appium.config;

public enum Platform {
    ANDROID,
    IOS;

    public static Platform fromSystemProperty() {
        String value = System.getProperty("platform", "android");
        return value.equalsIgnoreCase("ios") ? IOS : ANDROID;
    }
}
