package com.appium.locators;

import com.appium.config.Platform;
import org.openqa.selenium.By;

/**
 * Pairs an Android and iOS {@link By} under a human-readable name.
 * <p>
 * Prefer the builder so each platform is explicit (IDE autocomplete shows {@code android()} / {@code ios()}):
 * <pre>{@code
 * CrossPlatformLocator.named("Username field")
 *     .android(AppiumBy.id("com.example:id/username"))
 *     .ios(AppiumBy.accessibilityId("Username"))
 *     .build();
 * }</pre>
 */
public final class CrossPlatformLocator {

    private final String name;
    private final By android;
    private final By ios;

    private CrossPlatformLocator(String name, By android, By ios) {
        this.name = name;
        this.android = android;
        this.ios = ios;
    }

    public static Builder named(String name) {
        return new Builder(name);
    }

    /** Resolves to the {@link By} for the current {@code -Dplatform}. */
    public By get() {
        return Platform.fromSystemProperty() == Platform.ANDROID ? android : ios;
    }

    public String name() {
        return name;
    }

    public By android() {
        return android;
    }

    public By ios() {
        return ios;
    }

    @Override
    public String toString() {
        return "CrossPlatformLocator{name='%s', android=%s, ios=%s}".formatted(name, android, ios);
    }

    public static final class Builder {

        private final String name;
        private By android;
        private By ios;

        private Builder(String name) {
            this.name = name;
        }

        public Builder android(By locator) {
            this.android = locator;
            return this;
        }

        public Builder ios(By locator) {
            this.ios = locator;
            return this;
        }

        /** Returns the platform-specific {@link By} (same as {@link CrossPlatformLocator#get()}). */
        public By build() {
            return buildLocator().get();
        }

        public CrossPlatformLocator buildLocator() {
            if (android == null || ios == null) {
                throw new IllegalStateException(
                        "Both android() and ios() are required for '%s'".formatted(name));
            }
            return new CrossPlatformLocator(name, android, ios);
        }
    }
}
