package com.example.securelockdown;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manager to handle device locking when PIN/Pattern is forgotten
 */
public class LockManager {
    private static LockManager instance;
    private final Context context;
    private final SharedPreferences prefs;
    private final DevicePolicyManager devicePolicyManager;
    private final ComponentName adminComponent;

    private static final String PREFS_NAME = "secure_lock_prefs";
    private static final String KEY_PIN_FORGOT = "pin_forgotten";
    private static final String KEY_PATTERN_FORGOT = "pattern_forgotten";
    private static final String KEY_DEVICE_LOCKED = "device_locked";

    private LockManager(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.adminComponent = new ComponentName(context, SecureDeviceAdminReceiver.class);
    }

    public static synchronized LockManager getInstance(Context context) {
        if (instance == null) {
            instance = new LockManager(context);
        }
        return instance;
    }

    /**
     * Called when user forgets PIN - enforces device lock
     */
    public void pinForgotten() {
        prefs.edit()
            .putBoolean(KEY_PIN_FORGOT, true)
            .putBoolean(KEY_DEVICE_LOCKED, true)
            .apply();
        enforceDeviceLock();
    }

    /**
     * Called when user forgets Pattern - enforces device lock
     */
    public void patternForgotten() {
        prefs.edit()
            .putBoolean(KEY_PATTERN_FORGOT, true)
            .putBoolean(KEY_DEVICE_LOCKED, true)
            .apply();
        enforceDeviceLock();
    }

    /**
     * Enforces device lock - prevents shutdown and all operations
     */
    public void enforceDeviceLock() {
        if (isDeviceAdminActive()) {
            // Lock the device screen
            devicePolicyManager.lockNow();
            prefs.edit().putBoolean(KEY_DEVICE_LOCKED, true).apply();
        }
    }

    /**
     * Releases lock after successful authentication
     */
    public void releaseLock() {
        prefs.edit()
            .putBoolean(KEY_PIN_FORGOT, false)
            .putBoolean(KEY_PATTERN_FORGOT, false)
            .putBoolean(KEY_DEVICE_LOCKED, false)
            .apply();
    }

    /**
     * Checks if device is currently locked
     */
    public boolean isDeviceLocked() {
        return prefs.getBoolean(KEY_DEVICE_LOCKED, false);
    }

    /**
     * Checks if PIN was forgotten
     */
    public boolean isPinForgotten() {
        return prefs.getBoolean(KEY_PIN_FORGOT, false);
    }

    /**
     * Checks if Pattern was forgotten
     */
    public boolean isPatternForgotten() {
        return prefs.getBoolean(KEY_PATTERN_FORGOT, false);
    }

    /**
     * Checks if device admin is active
     */
    public boolean isDeviceAdminActive() {
        return devicePolicyManager != null && devicePolicyManager.isAdminActive(adminComponent);
    }

    /**
     * Activates device admin
     */
    public ComponentName getAdminComponent() {
        return adminComponent;
    }
}
