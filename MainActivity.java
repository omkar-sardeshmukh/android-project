package com.example.securelockdown;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Activity - PIN/Pattern setup and authentication
 */
public class MainActivity extends AppCompatActivity {
    private DevicePolicyManager devicePolicyManager;
    private ComponentName adminComponent;
    private LockManager lockManager;
    private EditText pinInput;
    private Button setButton, submitButton, lockButton;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lockManager = LockManager.getInstance(this);
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(this, SecureDeviceAdminReceiver.class);

        initializeViews();
        setupDeviceAdmin();
        updateUI();
    }

    private void initializeViews() {
        pinInput = findViewById(R.id.pin_input);
        setButton = findViewById(R.id.set_button);
        submitButton = findViewById(R.id.submit_button);
        lockButton = findViewById(R.id.lock_button);
        statusText = findViewById(R.id.status_text);

        setButton.setOnClickListener(v -> setPinPattern());
        submitButton.setOnClickListener(v -> verifyPinPattern());
        lockButton.setOnClickListener(v -> simulateForgottenPin());
    }

    private void setupDeviceAdmin() {
        if (!lockManager.isDeviceAdminActive()) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Enable to prevent device shutdown if PIN is forgotten");
            startActivity(intent);
        }
    }

    private void setPinPattern() {
        String pin = pinInput.getText().toString().trim();
        if (pin.isEmpty()) {
            Toast.makeText(this, "Please enter a PIN", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pin.length() < 4) {
            Toast.makeText(this, "PIN must be at least 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }
        PinPatternManager.getInstance(this).storePin(pin);
        Toast.makeText(this, "PIN saved successfully", Toast.LENGTH_SHORT).show();
        pinInput.setText("");
        updateUI();
    }

    private void verifyPinPattern() {
        String pin = pinInput.getText().toString().trim();
        if (PinPatternManager.getInstance(this).verifyPin(pin)) {
            lockManager.releaseLock();
            Toast.makeText(this, "PIN verified! Device unlocked", Toast.LENGTH_SHORT).show();
            updateUI();
        } else {
            Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show();
            pinInput.setText("");
        }
    }

    private void simulateForgottenPin() {
        lockManager.pinForgotten();
        statusText.setText("Device locked! User forgot PIN.\nDevice will not shut down.");
        Toast.makeText(this, "Device locked - PIN forgotten scenario", Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        if (lockManager.isDeviceLocked()) {
            statusText.setText("⚠️ DEVICE LOCKED\nUser forgot PIN/Pattern\nDevice cannot be shut down");
            pinInput.setEnabled(true);
            submitButton.setEnabled(true);
            setButton.setEnabled(false);
        } else {
            statusText.setText("Device Status: Normal\nAdmin Active: " + 
                (lockManager.isDeviceAdminActive() ? "✓ Yes" : "✗ No"));
            pinInput.setEnabled(true);
            setButton.setEnabled(true);
            submitButton.setEnabled(false);
        }
    }
}
