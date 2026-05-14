@echo off
REM Gradle Test Runner (No System Permissions Needed)
REM This script runs tests using gradle without system env variables

setlocal enabledelayedexpansion

REM ========== CONFIGURATION ==========
REM Set this to your Gradle installation path
set GRADLE_PATH=C:\Users\osardeshmukh\Downloads\gradle-8.14.5-bin\gradle-8.14.5

REM ===================================

echo ========================================
echo Secure Lockdown - Test Runner
echo ========================================
echo.

REM Check if Gradle exists
if not exist "%GRADLE_PATH%\bin\gradle.bat" (
    echo ERROR: Gradle not found at: %GRADLE_PATH%
    echo.
    echo Please:
    echo 1. Download Gradle 8.14.5 from: https://gradle.org/releases/
    echo 2. Extract to: C:\Users\osardeshmukh\Downloads\gradle-8.14.5-bin\gradle-8.14.5
    echo 3. Run this script again
    echo.
    pause
    exit /b 1
)

echo Using Gradle at: %GRADLE_PATH%
echo.

REM Set local environment
set PATH=%GRADLE_PATH%\bin;%PATH%
set GRADLE_HOME=%GRADLE_PATH%

REM Run tests
cd /d d:\omkar

echo Running tests...
echo.

call "%GRADLE_PATH%\bin\gradle.bat" test

echo.
echo ========================================
echo Test Run Complete
echo ========================================
echo.
echo View detailed results at:
echo   build/reports/tests/test/index.html
echo.

pause
