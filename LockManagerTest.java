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
 * Unit tests for LockManager
 */
@RunWith(AndroidJUnit4.class)
public class LockManagerTest {
    private Context context;
    private LockManager lockManager;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        lockManager = LockManager.getInstance(context);
        // Clear previous state
        clearLockState();
    }

    @After
    public void tearDown() {
        clearLockState();
    }

    private void clearLockState() {
        SharedPreferences prefs = context.getSharedPreferences("secure_lock_prefs", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }

    @Test
    public void testInitialLockStateNotLocked() {
        assertFalse("Device should not be locked initially", lockManager.isDeviceLocked());
    }

    @Test
    public void testPinForgottenSetsLockState() {
        lockManager.pinForgotten();
        assertTrue("Device should be locked after PIN forgotten", lockManager.isDeviceLocked());
        assertTrue("PIN forgotten flag should be set", lockManager.isPinForgotten());
    }

    @Test
    public void testPatternForgottenSetsLockState() {
        lockManager.patternForgotten();
        assertTrue("Device should be locked after pattern forgotten", lockManager.isDeviceLocked());
        assertTrue("Pattern forgotten flag should be set", lockManager.isPatternForgotten());
    }

    @Test
    public void testReleaseLockClearsState() {
        lockManager.pinForgotten();
        assertTrue("Device should be locked", lockManager.isDeviceLocked());
        
        lockManager.releaseLock();
        assertFalse("Device should not be locked after release", lockManager.isDeviceLocked());
        assertFalse("PIN forgotten flag should be cleared", lockManager.isPinForgotten());
    }

    @Test
    public void testMultipleLockReleaseCycles() {
        // First cycle
        lockManager.pinForgotten();
        assertTrue("Device locked on first cycle", lockManager.isDeviceLocked());
        lockManager.releaseLock();
        assertFalse("Device released on first cycle", lockManager.isDeviceLocked());

        // Second cycle
        lockManager.patternForgotten();
        assertTrue("Device locked on second cycle", lockManager.isDeviceLocked());
        lockManager.releaseLock();
        assertFalse("Device released on second cycle", lockManager.isDeviceLocked());
    }

    @Test
    public void testEnforceDeviceLockWithoutAdmin() {
        // Should not crash even if admin not active
        lockManager.enforceDeviceLock();
        // Test passes if no exception thrown
    }

    @Test
    public void testSingletonInstance() {
        LockManager manager1 = LockManager.getInstance(context);
        LockManager manager2 = LockManager.getInstance(context);
        assertSame("Should return same instance", manager1, manager2);
    }
}
