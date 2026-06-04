# appium-java

Java mobile automation scaffold using **Appium 2**, **TestNG**, and **Maven**. Supports **Android** (UiAutomator2) and **iOS** (XCUITest) with shared page objects and per-platform locators.

## Prerequisites

| Tool | Purpose |
|------|---------|
| **JDK 17+** | Compile and run tests |
| **Maven 3.9+** | Build and TestNG execution |
| **Node.js + npm** | Appium server and drivers |
| **Appium 2** | Mobile automation server |
| **Android SDK** | Android emulator tests (`ANDROID_HOME` / `ANDROID_SDK_ROOT`) |
| **Xcode + iOS Simulator** | iOS simulator tests |

### Install Appium and drivers

```bash
npm install -g appium
appium driver install uiautomator2   # Android
appium driver install xcuitest      # iOS
appium driver list --installed
```

### Android SDK (macOS)

Add to `~/.zprofile` or `~/.zshrc`:

```bash
export ANDROID_SDK_ROOT="$HOME/Library/Android/sdk"
export ANDROID_HOME="$ANDROID_SDK_ROOT"
export PATH="$ANDROID_SDK_ROOT/platform-tools:$ANDROID_SDK_ROOT/emulator:$PATH"
```

Restart the **Appium server** after changing env vars (the running `appium` process does not pick up new variables).

### iOS Simulator

1. Install **Xcode** from the App Store.
2. **Xcode → Settings → Platforms** — download an **iOS Simulator** runtime (required; an empty runtime list means no devices in Simulator).
3. Match `device.name` and `platform.version` in `ios.local.properties` to an installed simulator:

```bash
xcrun simctl list devices available
```

`platform.version` is the **iOS version** on the simulator (e.g. `26.5`), not the Xcode app version.

### Appium Inspector (optional)

Download the desktop app from [Appium Inspector releases](https://github.com/appium/appium-inspector/releases) (`.dmg` for macOS). Use `config/capabilities/*.json` as a starting point for Inspector session capabilities — those files are **not** loaded by tests.

## Project layout

```
appium-java/
├── apps/                         # Sample APK/IPA/zips — see apps/README.md
├── config/capabilities/          # Example JSON for Appium Inspector only (not used at runtime)
├── scripts/download-sample-apps.sh
├── src/main/java/com/appium/
│   ├── config/                   # ConfigReader, Platform
│   └── drivers/                  # DriverFactory, DriverManager
├── src/test/java/com/appium/
│   ├── base/                     # BaseTest (@BeforeMethod / @AfterMethod)
│   ├── locators/                 # CrossPlatformLocator helper
│   ├── pages/                    # Page objects (e.g. LoginPage)
│   └── tests/                    # TestNG test classes
├── src/test/resources/config/    # Properties-based configuration
├── testng.xml
└── pom.xml
```

## Sample apps

Demo app binaries live under `apps/` and are **gitignored**. Download them once:

```bash
chmod +x scripts/download-sample-apps.sh
./scripts/download-sample-apps.sh
```

For preset names, folder layout, and iOS simulator vs real-device notes, see **[apps/README.md](apps/README.md)**.

Default preset: **`mda-native`** (My Demo App native). Apps are installed automatically via the `appium:app` capability when a session starts — no manual `adb install` required for normal runs.

## Configuration

Runtime config uses **`.properties` files**, not the JSON under `config/capabilities/`.

| File | Purpose |
|------|---------|
| `environment.properties` | Shared defaults |
| `apps.properties` | App paths, package/activity, bundle IDs per `sample.app` preset |
| `local.properties` | Local Android settings |
| `ios.local.properties` | Loaded when `-Dplatform=ios` (overlays local) |
| `staging.properties` | Staging overrides |

| System property | Purpose |
|-----------------|--------|
| `-Denv=local` | Environment file (`local`, `staging`, …) — default: `local` |
| `-Dplatform=android` | `android` (default) or `ios` |
| `-Dsample.app=mda-native` | App preset: `mda-native`, `mda-rn`, `swag-labs` |
| `-Dapp.path=...` | Override app file path |
| `-Dappium.server.url=...` | Appium server URL (default `http://127.0.0.1:4723`) |

Copy `.env.example` to `.env` for local notes; Maven passes `-Denv` and `-Dplatform` via Surefire.

### Driver automation

| Platform | `automation.name` | Set in |
|----------|-------------------|--------|
| Android | `UiAutomator2` | `local.properties` |
| iOS | `XCUITest` | `ios.local.properties` |

## Run tests

### Android

1. Start an emulator (`adb devices` shows a device).
2. Start Appium in a terminal where `ANDROID_HOME` is set:

```bash
source ~/.zprofile   # if needed
appium
```

3. Run tests:

```bash
mvn clean test
```

### iOS Simulator

1. Install an iOS Simulator runtime in Xcode (**Settings → Platforms**).
2. Start Appium: `appium`
3. Run:

```bash
mvn clean test -Dplatform=ios -Denv=local
```

### More examples

```bash
# Staging config file
mvn clean test -Denv=staging

# Different sample app
mvn clean test -Dsample.app=swag-labs

# iOS + React Native demo app
mvn clean test -Dplatform=ios -Dsample.app=mda-rn

# Single test class
mvn test -Dplatform=ios -Dtest=MyDemoAppNativeTest
```

## Adding tests

1. Create page objects under `src/test/java/com/appium/pages/` extending `BasePage`.
2. Define locators on the page class using `CrossPlatformLocator.named("…").android(…).ios(…)` when platforms differ.
3. Create tests under `src/test/java/com/appium/tests/` extending `BaseTest`.
4. Register classes in `testng.xml`.

Example flow: `LoginPage` + `ProductsPage` in `MyDemoAppNativeTest` (`sample.app=mda-native`).

`BaseTest` runs `@BeforeMethod` (create driver) and `@AfterMethod` (quit driver) for each `@Test` method.

## Allure report

Tests publish results to `target/allure-results/`. On failure, **screenshot** and **page source** are attached automatically.

**View report locally** (requires [Allure CLI](https://allurereport.org/docs/install/) or use Maven):

```bash
mvn clean test
mvn allure:serve
```

`allure:serve` opens the HTML report in your browser and shuts down when you close it.

Generate a static report without opening a browser:

```bash
mvn allure:report
# output: target/site/allure-maven-plugin/index.html
```

Allure labels include `platform`, `env`, and `sample.app` (when set). Page actions use `@Step` for a readable timeline in the report.

## Troubleshooting

| Issue | What to check |
|-------|----------------|
| `ANDROID_HOME` / `ANDROID_SDK_ROOT` not exported | Set vars in shell profile; **restart Appium** after `source ~/.zprofile` |
| No devices in Simulator | Download iOS runtime in **Xcode → Settings → Platforms** |
| Session fails on device name / iOS version | Align `device.name` and `platform.version` with `xcrun simctl list devices available` |
| App path / `file:` protocol error | Use a normal path in `app.path`; project resolves paths under `apps/` automatically |
| 16 KB compatibility popup (Android) | Warning from sample APK on newer emulators; usually safe to dismiss with **OK** |

## CI (optional)

Point `appium.server.url` at your grid or cloud Appium endpoint and pass `-Denv` for the target environment.

## Dependencies

- **Appium Java Client** `10.1.1` (brings compatible Selenium modules)
- **TestNG** `7.10.2`
- **Java** 17
