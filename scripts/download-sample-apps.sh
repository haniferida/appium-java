#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

mkdir -p apps/my-demo-app-native apps/my-demo-app-rn apps/swag-labs

download() {
  curl -fL --retry 3 --retry-delay 2 -o "$1" "$2"
  echo "OK $1"
}

download apps/my-demo-app-native/android.apk \
  "https://github.com/saucelabs/my-demo-app-android/releases/download/2.2.0/mda-2.2.0-25.apk"
download apps/my-demo-app-native/ios-real.ipa \
  "https://github.com/saucelabs/my-demo-app-ios/releases/download/2.2.2/SauceLabs-Demo-App.ipa"
download apps/my-demo-app-native/ios-simulator.zip \
  "https://github.com/saucelabs/my-demo-app-ios/releases/download/2.2.2/SauceLabs-Demo-App.Simulator.zip"

download apps/my-demo-app-rn/android.apk \
  "https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/Android-MyDemoAppRN.1.3.0.build-244.apk"
download apps/my-demo-app-rn/ios-real.ipa \
  "https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/iOS-Real-Device-MyRNDemoApp.1.3.0-162.ipa"
download apps/my-demo-app-rn/ios-simulator.zip \
  "https://github.com/saucelabs/my-demo-app-rn/releases/download/v1.3.0/iOS-Simulator-MyRNDemoApp.1.3.0-162.zip"

download apps/swag-labs/android.apk \
  "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk"
download apps/swag-labs/ios-real.ipa \
  "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa"
download apps/swag-labs/ios-simulator.zip \
  "https://github.com/saucelabs/sample-app-mobile/releases/download/2.7.1/iOS.Simulator.SauceLabs.Mobile.Sample.app.2.7.1.zip"

echo "All sample apps downloaded under apps/"
