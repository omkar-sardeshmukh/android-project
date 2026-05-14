package com.example.securelockdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Manages PIN/Pattern storage with encryption
 */
public class PinPatternManager {
    private static PinPatternManager instance;
    private final SharedPreferences encryptedPrefs;

    private static final String PREFS_NAME = "secure_pin_prefs";
    private static final String KEY_PIN = "user_pin";

    private PinPatternManager(Context context) throws GeneralSecurityException, IOException {
        MasterKey masterKey = new MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build();

        this.encryptedPrefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public static synchronized PinPatternManager getInstance(Context context) {
        if (instance == null) {
            try {
                instance = new PinPatternManager(context);
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException("Failed to initialize PinPatternManager", e);
            }
        }
        return instance;
    }

    /**
     * Store PIN securely (encrypted)
     */
    public void storePin(String pin) {
        encryptedPrefs.edit().putString(KEY_PIN, hashPin(pin)).apply();
    }

    /**
     * Verify PIN
     */
    public boolean verifyPin(String pin) {
        String storedHash = encryptedPrefs.getString(KEY_PIN, null);
        if (storedHash == null) return false;
        return storedHash.equals(hashPin(pin));
    }

    /**
     * Simple hash function - in production use bcrypt or Argon2
     */
    private String hashPin(String pin) {
        return Integer.toHexString(pin.hashCode());
    }

    /**
     * Check if PIN is set
     */
    public boolean isPinSet() {
        return encryptedPrefs.getString(KEY_PIN, null) != null;
    }

    /**
     * Clear PIN
     */
    public void clearPin() {
        encryptedPrefs.edit().remove(KEY_PIN).apply();
    }
}
