package com.appium.locators;

import org.openqa.selenium.By;

/**
 * @deprecated Use {@link CrossPlatformLocator#named(String)} with {@code .android()} / {@code .ios()}
 *             so platform locators are labeled in code and in IDE parameter hints.
 */
@Deprecated
public final class PlatformLocator {

    private PlatformLocator() {
    }

    @Deprecated
    public static By by(By android, By ios) {
        return CrossPlatformLocator.named("unnamed")
                .android(android)
                .ios(ios)
                .build();
    }
}
