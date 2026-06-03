# Sample apps for Appium

Free Sauce Labs demo apps for local automation. Binaries are gitignored; re-download with:

```bash
./scripts/download-sample-apps.sh
```

## Layout

| Folder | Android | iOS simulator | iOS real device |
|--------|---------|---------------|-----------------|
| `my-demo-app-native/` | `android.apk` | `ios-simulator.zip` | `ios-real.ipa` |
| `my-demo-app-rn/` | `android.apk` | `ios-simulator.zip` | `ios-real.ipa` |
| `swag-labs/` | `android.apk` | `ios-simulator.zip` | `ios-real.ipa` |

## Config presets (`sample.app`)

| Value | Description |
|-------|-------------|
| `mda-native` | My Demo App (native) — **default** |
| `mda-rn` | My Demo App (React Native) |
| `swag-labs` | Swag Labs mobile sample (archived) |

Switch preset:

```bash
mvn test -Dsample.app=swag-labs
mvn test -Dplatform=ios -Dsample.app=mda-rn
```

Details: `src/test/resources/config/apps.properties`

## iOS notes

- Use **simulator** `.zip` files for local Xcode simulators (configured by default for iOS).
- **`.ipa`** files target real devices and usually need re-signing before install on your own iPhone.
