# Testing Guide for Secure Lockdown App

## Testing Strategy

This application uses a **three-tier testing approach**:

1. **Unit Tests** - Test individual components in isolation
2. **Instrumentation Tests** - Test components on Android device/emulator
3. **Manual Testing** - Test user scenarios and workflows

---

## Prerequisites

### Installation Requirements

```bash
# Install Android SDK (if not already installed)
# Download from: https://developer.android.com/studio

# Ensure Gradle is configured
# Set ANDROID_HOME environment variable

# For Windows:
setx ANDROID_HOME "C:\Users\<YourUsername>\AppData\Local\Android\sdk"
```

### Emulator Setup

```bash
# Create a virtual device
# Open Android Studio > AVD Manager > Create Virtual Device
# Choose: Pixel 4, Android 14 (API 34)
# Recommended specs: 2GB RAM, 2GB storage

# Or from command line:
$ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd -n TestDevice -k "system-images;android-34;google_apis;x86_64"
```

---

## 1. Unit Tests (Local JVM)

### Run Unit Tests

```bash
cd d:\omkar

# Run all unit tests
./gradlew test

# Run specific test class
./gradlew test --tests LockManagerTest

# Run with detailed output
./gradlew test --info
```

### Test Files

- **LockManagerTest.java** - Tests lock state management
  - ✅ PIN forgotten sets lock
  - ✅ Pattern forgotten sets lock
  - ✅ Release lock clears state
  - ✅ Multiple lock/release cycles

- **PinPatternManagerTest.java** - Tests PIN storage/verification
  - ✅ Store PIN successfully
  - ✅ Verify correct PIN
  - ✅ Reject incorrect PIN
  - ✅ Encryption works
  - ✅ Clear PIN operation

### Example Output

```
LockManagerTest:
  ✓ testInitialLockStateNotLocked PASSED
  ✓ testPinForgottenSetsLockState PASSED
  ✓ testReleaseLockClearsState PASSED
  ✓ testMultipleLockReleaseCycles PASSED

PinPatternManagerTest:
  ✓ testStorePinSuccessfully PASSED
  ✓ testVerifyCorrectPin PASSED
  ✓ testVerifyIncorrectPin PASSED
  ✓ testEncryption PASSED

BUILD SUCCESSFUL
```

---

## 2. Instrumentation Tests (Android Emulator/Device)

### Start Emulator

```bash
# List available AVDs
$ANDROID_HOME/emulator/emulator -list-avds

# Start emulator
$ANDROID_HOME/emulator/emulator -avd TestDevice

# Wait for boot (2-3 minutes on first run)
```

### Run Instrumentation Tests

```bash
cd d:\omkar

# Run all instrumentation tests
./gradlew connectedAndroidTest

# Run specific test class
./gradlew connectedAndroidTest --tests MainActivityTest

# Run with logging
./gradlew connectedAndroidTest --info
```

### Test Files

- **MainActivityTest.java** - UI integration tests
  - ✅ Main activity loads
  - ✅ All buttons displayed
  - ✅ PIN input field displayed
  - ✅ Set PIN workflow
  - ✅ Forgotten PIN simulation
  - ✅ PIN verification flow

### Verify Emulator Connection

```bash
# Check connected devices
$ANDROID_HOME/platform-tools/adb devices

# Output should show:
# List of attached devices
# emulator-5554  device
```

---

## 3. Manual Testing Checklist

### Test Scenario 1: Initial Setup

- [ ] Install app on device/emulator
- [ ] Launch app
- [ ] Grant device admin permission when prompted
- [ ] Status shows "Device Status: Normal" with "Admin Active: ✓ Yes"

### Test Scenario 2: PIN Setup

- [ ] Enter 4-digit PIN (e.g., "1234")
- [ ] Click "Set PIN"
- [ ] Toast message shows "PIN saved successfully"
- [ ] PIN input field clears
- [ ] Status updates

### Test Scenario 3: PIN Verification

- [ ] Click "Verify PIN" without entering PIN
- [ ] App rejects with "PIN not found"
- [ ] Enter correct PIN (same as setup)
- [ ] Click "Verify PIN"
- [ ] Toast shows "PIN verified! Device unlocked"

### Test Scenario 4: Incorrect PIN

- [ ] After setting PIN, enter wrong PIN (e.g., "9999")
- [ ] Click "Verify PIN"
- [ ] Toast shows "Incorrect PIN"
- [ ] PIN input field clears
- [ ] Device remains locked if in locked state

### Test Scenario 5: Forgotten PIN Lock

- [ ] Set PIN first (e.g., "5678")
- [ ] Click "Simulate Forgotten PIN" button
- [ ] Status text changes to:
  ```
  ⚠️ DEVICE LOCKED
  User forgot PIN/Pattern
  Device cannot be shut down
  ```
- [ ] "Verify PIN" button becomes enabled
- [ ] "Set PIN" button becomes disabled

### Test Scenario 6: Recovery from Locked State

- [ ] With device locked (from Scenario 5)
- [ ] Device screen should be locked visually
- [ ] Enter correct PIN (e.g., "5678")
- [ ] Click "Verify PIN"
- [ ] Toast shows "PIN verified! Device unlocked"
- [ ] Status returns to "Device Status: Normal"

### Test Scenario 7: App Restart State Persistence

- [ ] Set PIN to "1111"
- [ ] Simulate forgotten PIN (lock device)
- [ ] Verify status shows locked
- [ ] Close app completely
- [ ] Reopen app
- [ ] Status should still show locked state
- [ ] Enter correct PIN and verify unlock works

### Test Scenario 8: Device Shutdown Prevention

**Note: This requires actual device admin permissions**

- [ ] Lock device via "Simulate Forgotten PIN"
- [ ] Try to shutdown device (power button)
- [ ] Device should NOT shut down
- [ ] Only way to unlock: enter correct PIN

---

## Running Tests from Command Line

### Build APK

```bash
cd d:\omkar

# Build debug APK
./gradlew assembleDebug

# APK location: build/outputs/apk/debug/app-debug.apk
```

### Install and Test

```bash
# Install APK on emulator/device
$ANDROID_HOME/platform-tools/adb install build/outputs/apk/debug/app-debug.apk

# Run all tests
./gradlew test connectedAndroidTest

# View test report
# Open: build/reports/tests/index.html
```

---

## Troubleshooting

### Emulator Won't Start

```bash
# Clear emulator data
$ANDROID_HOME/emulator/emulator -avd TestDevice -wipe-data

# Or restart AVD Manager in Android Studio
```

### Tests Fail with "Device not found"

```bash
# Reconnect device
$ANDROID_HOME/platform-tools/adb kill-server
$ANDROID_HOME/platform-tools/adb devices

# If emulator: restart it from AVD Manager
```

### Device Admin Permission Issues

```bash
# Grant via adb (if needed)
$ANDROID_HOME/platform-tools/adb shell dpm set-device-owner \
    com.example.securelockdown/.SecureDeviceAdminReceiver
```

### Build Fails

```bash
# Clean and rebuild
./gradlew clean build

# Update Gradle
./gradlew wrapper --gradle-version=8.0
```

---

## Test Coverage Report

Generate coverage report:

```bash
./gradlew testDebugUnitTestCoverage

# View report at:
# build/reports/coverage/index.html
```

Expected coverage:
- **LockManager**: 90%+
- **PinPatternManager**: 95%+
- **MainActivity**: 70%+ (UI-heavy)

---

## Continuous Integration

### GitHub Actions Example

```yaml
name: Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: 11
      - run: ./gradlew test
      - run: ./gradlew connectedAndroidTest
```

---

## Performance Testing

Monitor lock performance:

```bash
# Check logs during lock
$ANDROID_HOME/platform-tools/adb logcat | grep SecureLockdown

# Should show minimal latency (<100ms) for lock operation
```

---

## Test Results Summary

After running all tests, you should see:

```
✅ Unit Tests: PASSED
✅ Instrumentation Tests: PASSED
✅ Manual Tests: PASSED
✅ State Persistence: VERIFIED
✅ Device Admin: FUNCTIONAL
✅ PIN Encryption: WORKING
```

---

## Next Steps

- Run `./gradlew test` for unit tests
- Start emulator and run `./gradlew connectedAndroidTest`
- Follow manual testing checklist
- Review test reports in `build/reports/`
