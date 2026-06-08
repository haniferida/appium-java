package com.appium.drivers;

import com.appium.config.ConfigReader;
import com.appium.config.Platform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static AppiumDriver createDriver() throws MalformedURLException {
        URL serverUrl = new URL(ConfigReader.get("appium.server.url", "http://127.0.0.1:4723"));
        MutableCapabilities caps = buildCapabilities();
        Platform platform = Platform.fromSystemProperty();

        AppiumDriver driver = switch (platform) {
            case ANDROID -> new AndroidDriver(serverUrl, caps);
            case IOS -> new IOSDriver(serverUrl, caps);
        };

        int implicitWait = ConfigReader.getInt("implicit.wait.seconds", 10);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        return driver;
    }

    private static MutableCapabilities buildCapabilities() {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("platformName", ConfigReader.get("platform.name"));
        setIfPresent(caps, "appium:automationName", ConfigReader.get("automation.name"));
        setIfPresent(caps, "appium:deviceName", ConfigReader.get("device.name"));
        setIfPresent(caps, "appium:platformVersion", ConfigReader.get("platform.version"));

        Path appPath = ConfigReader.resolveAppPath();
        if (appPath != null) {
            caps.setCapability("appium:app", appPath.toAbsolutePath().toString());
        }

        setIfPresent(caps, "appium:appPackage", ConfigReader.get("app.package"));
        setIfPresent(caps, "appium:appActivity", ConfigReader.get("app.activity"));
        setIfPresent(caps, "appium:bundleId", ConfigReader.get("bundle.id"));
        setIfPresent(caps, "appium:appWaitActivity", ConfigReader.get("app.wait.activity"));

        setIfPresentInt(caps, "appium:adbExecTimeout", ConfigReader.get("adb.exec.timeout"));
        setIfPresentInt(caps, "appium:uiautomator2ServerInstallTimeout",
                ConfigReader.get("uiautomator2.server.install.timeout"));
        setIfPresentInt(caps, "appium:uiautomator2ServerLaunchTimeout",
                ConfigReader.get("uiautomator2.server.launch.timeout"));

        return caps;
    }

    private static void setIfPresent(MutableCapabilities caps, String name, String value) {
        if (value != null && !value.isBlank()) {
            caps.setCapability(name, value);
        }
    }

    private static void setIfPresentInt(MutableCapabilities caps, String name, String value) {
        if (value != null && !value.isBlank()) {
            caps.setCapability(name, Integer.parseInt(value.trim()));
        }
    }
}
