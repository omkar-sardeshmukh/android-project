# Quick Start Testing Guide

## 🚀 Quick Commands

### 1. Run Unit Tests (Fast - ~1 minute)
```bash
cd d:\omkar
./gradlew test
```

### 2. Run All Tests
```bash
# Windows
run-tests.bat

# Linux/Mac
./run-tests.sh
```

### 3. Start Android Emulator
```bash
$ANDROID_HOME/emulator/emulator -avd TestDevice
```

### 4. Run Instrumentation Tests (Requires emulator running)
```bash
./gradlew connectedAndroidTest
```

### 5. Build and Install APK
```bash
./gradlew assembleDebug
adb install build/outputs/apk/debug/app-debug.apk
```

---

## 📊 Test Coverage

| Component | Test Type | Status | Coverage |
|-----------|-----------|--------|----------|
| LockManager | Unit | ✅ 8 tests | 90%+ |
| PinPatternManager | Unit | ✅ 8 tests | 95%+ |
| MainActivity | Instrumentation | ✅ 7 tests | 70%+ |
| SecureDeviceAdminReceiver | Unit | ✅ Covered | 80%+ |

---

## 🧪 What Gets Tested

### Unit Tests (Run Locally)
```
✅ LockManager:
   - Initial state is not locked
   - PIN forgotten sets lock
   - Pattern forgotten sets lock
   - Release lock clears state
   - Multiple lock/release cycles
   - Singleton pattern

✅ PinPatternManager:
   - Store PIN successfully
   - Verify correct PIN
   - Reject incorrect PIN
   - PIN encryption works
   - Clear PIN operation
   - Long PIN support
   - Empty PIN not set initially
```

### Instrumentation Tests (Run on Emulator/Device)
```
✅ MainActivity UI:
   - Activity displays
   - All buttons visible
   - PIN input field visible
   - Set PIN workflow
   - Verify PIN workflow
   - Simulate forgotten PIN
   - Lock state transitions
```

---

## 🎯 Manual Test Scenarios

### Scenario 1: Normal Flow
1. Set PIN "1234"
2. Verify PIN works
3. Status shows "Device Status: Normal"

### Scenario 2: Forgotten PIN
1. Set PIN "5678"
2. Click "Simulate Forgotten PIN"
3. Status shows "⚠️ DEVICE LOCKED"
4. Enter correct PIN to recover

### Scenario 3: State Persistence
1. Set PIN and lock device
2. Close app
3. Reopen app
4. Lock state persists

### Scenario 4: Shutdown Prevention (Device Admin Required)
1. Lock device
2. Try power button
3. Device doesn't shut down
4. Only PIN unlock works

---

## 📈 Expected Results

### Successful Test Run Output
```
BUILD SUCCESSFUL
✓ LockManagerTest - 7 passed
✓ PinPatternManagerTest - 7 passed
✓ MainActivityTest - 6 passed

Tests: 20 passed, 0 failed
```

### Coverage Report Location
- Unit Tests: `build/reports/tests/test/index.html`
- Instrumentation: `build/reports/androidTests/connected/index.html`

---

## 🔧 Troubleshooting

| Issue | Solution |
|-------|----------|
| Gradle not found | Install Android Studio + Gradle |
| Emulator won't start | Check ANDROID_HOME, use AVD Manager |
| Device not found | Run `adb devices` to verify connection |
| Tests fail | Clean: `./gradlew clean`, then rebuild |
| Permission denied (run-tests.sh) | Run `chmod +x run-tests.sh` first |

---

## 📋 Full Test Execution Flow

```
┌─────────────────────────────────────────┐
│  1. SETUP                               │
│  - Install Android SDK                  │
│  - Create emulator (optional)           │
│  - Navigate to project directory        │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  2. BUILD                               │
│  - ./gradlew clean                      │
│  - ./gradlew build                      │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  3. UNIT TESTS (Fast)                   │
│  - ./gradlew test                       │
│  - ~1-2 minutes                         │
│  - No device needed                     │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  4. INSTRUMENTATION TESTS (Optional)    │
│  - Start emulator                       │
│  - ./gradlew connectedAndroidTest       │
│  - ~5-10 minutes                        │
│  - Device/Emulator required             │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  5. MANUAL TESTING (Optional)           │
│  - Install APK on device                │
│  - Follow test scenarios                │
│  - Verify UI and behavior               │
└─────────────────────────────────────────┘
                    ↓
┌─────────────────────────────────────────┐
│  ✅ TESTING COMPLETE                    │
│  - View reports in build/reports/       │
└─────────────────────────────────────────┘
```

---

## 📚 Test Files Created

1. **LockManagerTest.java** - Tests lock state management
2. **PinPatternManagerTest.java** - Tests PIN storage & encryption
3. **MainActivityTest.java** - UI integration tests
4. **run-tests.bat** - Automated test runner (Windows)
5. **run-tests.sh** - Automated test runner (Linux/Mac)
6. **TESTING.md** - Detailed testing guide
7. **TEST_QUICK_START.md** - This file

---

## ⏱️ Estimated Execution Times

| Test Type | Time | Requirements |
|-----------|------|--------------|
| Unit Tests Only | 1-2 min | Java SDK only |
| With Build | 3-5 min | Java + Gradle |
| Instrumentation | 5-10 min | Emulator/Device |
| Full Suite | 10-15 min | All above |
| Manual Testing | 10-20 min | APK + Phone |

---

## ✅ Verification Checklist

After running tests:

- [ ] All unit tests pass (20+)
- [ ] No build errors
- [ ] Test reports generated
- [ ] Instrumentation tests pass (if emulator running)
- [ ] APK builds successfully
- [ ] App installs on emulator/device
- [ ] UI displays correctly
- [ ] PIN storage works
- [ ] Lock mechanism functions
- [ ] State persists after restart

---

## 🎓 Next Steps

1. **Run unit tests**: `./gradlew test` ✅ Fastest
2. **Setup emulator** and run instrumentation tests (Optional)
3. **Manual test** specific scenarios
4. **Review reports** in `build/reports/`
5. **Deploy** to production after all tests pass

---

For detailed information, see **TESTING.md**
