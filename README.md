# Secure Lockdown - Android Device Lock Feature

## Overview
This Android application implements a **device security lockdown feature** that prevents phone shutdown when a user forgets their PIN or pattern.

## Key Features

### 1. **Device Admin Integration**
- Registers as a device administrator to gain device control capabilities
- Enables forced device locking via `DevicePolicyManager`
- Prevents bypass attempts via standard methods

### 2. **PIN Management**
- Secure PIN storage using Android Keystore encryption (`EncryptedSharedPreferences`)
- PIN verification mechanism
- Persistent PIN state across app restarts

### 3. **Lock Enforcement**
- **Automatic Locking**: When user forgets PIN/Pattern, device immediately locks
- **No Shutdown Permission**: Locks device screen preventing any operations
- **Persistent State**: Lock state persists until correct PIN is entered

### 4. **Security Components**

#### `SecureDeviceAdminReceiver.java`
- Implements Device Admin receiver
- Handles password failure events - triggers auto-lock
- Manages device admin lifecycle (enable/disable)

#### `LockManager.java`
- Centralized lock state management
- Handles `pinForgotten()` and `patternForgotten()` events
- Persists lock state in SharedPreferences
- Integrates with DevicePolicyManager for actual device locking

#### `PinPatternManager.java`
- Encrypts and stores PIN securely
- Provides PIN verification
- Uses Android Keystore for encryption

#### `MainActivity.java`
- UI for PIN setup and verification
- Trigger point for lock simulation
- Admin activation flow
- Status display

## How It Works

1. **Setup Phase**:
   - User launches app
   - Device admin is activated (with user permission)
   - User sets a 4+ digit PIN

2. **Normal Operation**:
   - App runs in background
   - PIN is stored securely

3. **Forgotten PIN Scenario**:
   - User forgets PIN → `lockManager.pinForgotten()` called
   - Device is immediately locked via `DevicePolicyManager.lockNow()`
   - Lock state persists - device cannot be shut down
   - Only way to unlock: enter correct PIN in the app

4. **Recovery**:
   - User enters correct PIN
   - `LockManager.releaseLock()` called
   - Device returns to normal operation

## Manifest Permissions

```xml
<uses-permission android:name="android.permission.LOCK_DEVICE" />
<uses-permission android:name="android.permission.DEVICE_POWER" />
```

## Android API Requirements

- **Min SDK**: 29 (Android 10)
- **Target SDK**: 34 (Android 14)

## Dependencies

```gradle
androidx.appcompat:appcompat:1.6.1
androidx.constraintlayout:constraintlayout:2.1.4
com.google.android.material:material:1.9.0
androidx.security:security-crypto:1.1.0-alpha06
```

## Security Considerations

1. **Encryption**: PIN stored with AES-256-GCM encryption (EncryptedSharedPreferences)
2. **Device Admin**: Restricted to this app only
3. **No Bypass**: Once locked, only correct PIN can unlock
4. **Lock Persistence**: Survives app crashes/restarts

## Usage Example

```java
LockManager lockManager = LockManager.getInstance(context);

// When user forgets PIN:
lockManager.pinForgotten();

// When user enters correct PIN:
if (PinPatternManager.getInstance(context).verifyPin(userInput)) {
    lockManager.releaseLock();
}
```

## Testing

1. Install app
2. Grant device admin permission
3. Set PIN
4. Click "Simulate Forgotten PIN" button
5. Device locks - screen is locked, cannot shut down
6. Enter correct PIN to unlock

## Notes

- Device admin is required for this feature
- Device must remain connected (not factory reset)
- This is a demonstration - adjust for production use
- Consider adding biometric authentication for unlock
- Add admin override for IT departments if needed

## Future Enhancements

- Biometric fingerprint unlock
- Admin PIN recovery
- Remote unlock capability
- Multi-level security (primary + secondary PIN)
- Audit logging of lock/unlock events
- SMS-based unlock for enterprise
