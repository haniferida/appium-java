package com.appium.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads layered properties: environment.properties + apps.properties + {env}.properties.
 * Override via system properties: -Denv=staging -Dplatform=android -Dsample.app=swag-labs
 */
public final class ConfigReader {

    private static final String DEFAULT_ENV = "local";
    private static final Properties PROPERTIES = new Properties();

    static {
        load("config/environment.properties");
        load("config/apps.properties");
        String env = System.getProperty("env", DEFAULT_ENV);
        load("config/" + env + ".properties");
        if ("ios".equalsIgnoreCase(System.getProperty("platform", "android"))) {
            load("config/ios." + env + ".properties");
        }
        applySampleAppPreset();
    }

    private ConfigReader() {
    }

    private static void load(String resourcePath) {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (in != null) {
                PROPERTIES.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + resourcePath, e);
        }
    }

    private static void applySampleAppPreset() {
        String sampleApp = get("sample.app");
        if (sampleApp == null || sampleApp.isBlank()) {
            sampleApp = get("sample.app.default");
        }
        if (sampleApp == null || sampleApp.isBlank()) {
            return;
        }

        boolean ios = Platform.fromSystemProperty() == Platform.IOS;
        String platformKey = ios ? "ios" : "android";
        putIfAbsent("app.path", PROPERTIES.getProperty(sampleApp + "." + platformKey + ".app"));
        if (ios) {
            putIfAbsent("bundle.id", PROPERTIES.getProperty(sampleApp + ".ios.bundle"));
        } else {
            putIfAbsent("app.package", PROPERTIES.getProperty(sampleApp + ".android.package"));
            putIfAbsent("app.activity", PROPERTIES.getProperty(sampleApp + ".android.activity"));
        }
    }

    private static void putIfAbsent(String key, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        String existing = PROPERTIES.getProperty(key);
        if (existing == null || existing.isBlank()) {
            PROPERTIES.setProperty(key, value);
        }
    }

    public static String get(String key) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys;
        }
        return PROPERTIES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value != null && !value.isBlank() ? value : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    /**
     * Resolves {@code app.path} to an absolute filesystem path (project root = user.dir).
     */
    public static Path resolveAppPath() {
        String appPath = get("app.path");
        if (appPath == null || appPath.isBlank()) {
            return null;
        }
        appPath = appPath.trim();
        Path path;
        if (appPath.startsWith("file:")) {
            try {
                path = Paths.get(new URI(appPath));
            } catch (URISyntaxException e) {
                throw new IllegalStateException("Invalid file URI in app.path: " + appPath, e);
            }
        } else {
            path = Paths.get(appPath);
        }
        if (!path.isAbsolute()) {
            path = Paths.get(System.getProperty("user.dir")).resolve(path).normalize();
        }
        if (!Files.isRegularFile(path)) {
            throw new IllegalStateException("App file not found: " + path
                    + " — run scripts/download-sample-apps.sh or set app.path");
        }
        return path;
    }
}
