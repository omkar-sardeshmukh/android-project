@echo off
REM Quick Test Runner for Secure Lockdown App
REM This script runs all tests in sequence

setlocal enabledelayedexpansion

echo ======================================
echo Secure Lockdown - Test Suite
echo ======================================
echo.

REM Check if Gradle exists
if not exist "gradlew.bat" (
    echo Error: gradlew.bat not found. Make sure you're in the project root directory.
    exit /b 1
)

REM Step 1: Clean
echo [1/4] Cleaning project...
call gradlew clean
if !errorlevel! neq 0 (
    echo Clean failed!
    exit /b 1
)
echo ✓ Clean completed
echo.

REM Step 2: Build
echo [2/4] Building project...
call gradlew build -x connectedAndroidTest
if !errorlevel! neq 0 (
    echo Build failed!
    exit /b 1
)
echo ✓ Build completed
echo.

REM Step 3: Unit Tests
echo [3/4] Running unit tests...
call gradlew test
if !errorlevel! neq 0 (
    echo Unit tests failed!
    exit /b 1
)
echo ✓ Unit tests completed
echo.

REM Step 4: Instrumentation Tests (if emulator/device connected)
echo [4/4] Running instrumentation tests...
echo Checking for connected devices...
adb devices | find "emulator" >nul
if !errorlevel! neq 0 (
    echo Warning: No emulator/device found. Skipping instrumentation tests.
    echo To run instrumentation tests:
    echo   1. Start emulator: %%ANDROID_HOME%%\emulator\emulator -avd TestDevice
    echo   2. Run: gradlew connectedAndroidTest
) else (
    call gradlew connectedAndroidTest
    if !errorlevel! neq 0 (
        echo Instrumentation tests failed!
        exit /b 1
    )
    echo ✓ Instrumentation tests completed
)
echo.

echo ======================================
echo ✅ All tests completed successfully!
echo ======================================
echo.
echo Test Reports:
echo - Unit Tests: build\reports\tests\test\index.html
echo - Instrumentation: build\reports\androidTests\connected\index.html
echo.

pause
