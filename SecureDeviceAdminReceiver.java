package com.example.securelockdown;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

/**
 * Device Admin Receiver to handle device administration actions
 */
public class SecureDeviceAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        super.onEnabled(context, intent);
        // Device admin has been enabled
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
        super.onDisabled(context, intent);
        // Device admin has been disabled
    }

    @Override
    public void onPasswordChanged(@NonNull Context context, @NonNull Intent intent, android.os.UserHandle user) {
        super.onPasswordChanged(context, intent, user);
        // Password has been changed
    }

    @Override
    public void onPasswordFailed(@NonNull Context context, @NonNull Intent intent, android.os.UserHandle user) {
        super.onPasswordFailed(context, intent, user);
        // Password attempt failed - lock device
        LockManager.getInstance(context).enforceDeviceLock();
    }

    @Override
    public void onPasswordSucceeded(@NonNull Context context, @NonNull Intent intent, android.os.UserHandle user) {
        super.onPasswordSucceeded(context, intent, user);
        // Password succeeded - unlock features
        LockManager.getInstance(context).releaseLock();
    }
}
