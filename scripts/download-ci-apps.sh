#!/usr/bin/env bash
# Minimal app binaries for CI (mda-native Android only).
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

mkdir -p apps/my-demo-app-native

curl -fL --retry 3 --retry-delay 2 \
  -o apps/my-demo-app-native/android.apk \
  "https://github.com/saucelabs/my-demo-app-android/releases/download/2.2.0/mda-2.2.0-25.apk"

echo "CI app ready: apps/my-demo-app-native/android.apk"
