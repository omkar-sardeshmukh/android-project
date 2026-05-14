package com.example.securelockdown;

import static org.junit.Assert.*;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Unit tests for PinPatternManager
 */
@RunWith(AndroidJUnit4.class)
public class PinPatternManagerTest {
    private Context context;
    private PinPatternManager pinManager;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        pinManager = PinPatternManager.getInstance(context);
        pinManager.clearPin();
    }

    @After
    public void tearDown() {
        pinManager.clearPin();
    }

    @Test
    public void testStorePinSuccessfully() {
        String testPin = "1234";
        pinManager.storePin(testPin);
        assertTrue("PIN should be set after storing", pinManager.isPinSet());
    }

    @Test
    public void testVerifyCorrectPin() {
        String testPin = "5678";
        pinManager.storePin(testPin);
        assertTrue("Correct PIN should verify successfully", pinManager.verifyPin(testPin));
    }

    @Test
    public void testVerifyIncorrectPin() {
        String testPin = "1234";
        pinManager.storePin(testPin);
        assertFalse("Incorrect PIN should not verify", pinManager.verifyPin("9999"));
    }

    @Test
    public void testPinEncryption() {
        String pin1 = "1111";
        String pin2 = "1111";
        pinManager.storePin(pin1);
        assertTrue("Same PIN should verify consistently", pinManager.verifyPin(pin2));
    }

    @Test
    public void testClearPin() {
        pinManager.storePin("1234");
        assertTrue("PIN should be set", pinManager.isPinSet());
        pinManager.clearPin();
        assertFalse("PIN should be cleared", pinManager.isPinSet());
    }

    @Test
    public void testEmptyPinNotSet() {
        assertFalse("No PIN should be set initially", pinManager.isPinSet());
    }

    @Test
    public void testLongPin() {
        String longPin = "123456789";
        pinManager.storePin(longPin);
        assertTrue("Long PIN should verify correctly", pinManager.verifyPin(longPin));
    }
}
