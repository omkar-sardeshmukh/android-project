#!/bin/bash

# Quick Test Runner for Secure Lockdown App (Linux/Mac)
# This script runs all tests in sequence

echo "======================================"
echo "Secure Lockdown - Test Suite"
echo "======================================"
echo ""

# Check if gradlew exists
if [ ! -f "gradlew" ]; then
    echo "Error: gradlew not found. Make sure you're in the project root directory."
    exit 1
fi

# Step 1: Clean
echo "[1/4] Cleaning project..."
./gradlew clean
if [ $? -ne 0 ]; then
    echo "Clean failed!"
    exit 1
fi
echo "✓ Clean completed"
echo ""

# Step 2: Build
echo "[2/4] Building project..."
./gradlew build -x connectedAndroidTest
if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1
fi
echo "✓ Build completed"
echo ""

# Step 3: Unit Tests
echo "[3/4] Running unit tests..."
./gradlew test
if [ $? -ne 0 ]; then
    echo "Unit tests failed!"
    exit 1
fi
echo "✓ Unit tests completed"
echo ""

# Step 4: Instrumentation Tests (if emulator/device connected)
echo "[4/4] Running instrumentation tests..."
echo "Checking for connected devices..."
adb devices | grep -q "emulator"
if [ $? -ne 0 ]; then
    echo "Warning: No emulator/device found. Skipping instrumentation tests."
    echo "To run instrumentation tests:"
    echo "  1. Start emulator: \$ANDROID_HOME/emulator/emulator -avd TestDevice"
    echo "  2. Run: ./gradlew connectedAndroidTest"
else
    ./gradlew connectedAndroidTest
    if [ $? -ne 0 ]; then
        echo "Instrumentation tests failed!"
        exit 1
    fi
    echo "✓ Instrumentation tests completed"
fi
echo ""

echo "======================================"
echo "✅ All tests completed successfully!"
echo "======================================"
echo ""
echo "Test Reports:"
echo "- Unit Tests: build/reports/tests/test/index.html"
echo "- Instrumentation: build/reports/androidTests/connected/index.html"
echo ""
