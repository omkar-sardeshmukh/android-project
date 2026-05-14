@echo off
REM Gradle Setup Script for Secure Lockdown
REM This script downloads and sets up Gradle

setlocal enabledelayedexpansion

echo ========================================
echo Gradle Setup - Downloading Gradle 8.0
echo ========================================
echo.

cd d:\omkar

REM Create gradle directory structure
if not exist "gradle\wrapper" mkdir gradle\wrapper

echo Downloading gradle-wrapper.jar...

REM Download gradle wrapper jar
powershell -Command "(New-Object System.Net.ServicePointManager).SecurityProtocol = [System.Net.SecurityProtocolType]::Tls12; (New-Object System.Net.WebClient).DownloadFile('https://gradle.org/next-steps/?version=8.0&format=bin', '%cd%\gradle-8.0.zip')"

if exist "gradle-8.0.zip" (
    echo Extracting gradle...
    powershell -Command "Expand-Archive 'gradle-8.0.zip' -DestinationPath '%cd%' -Force"
    
    echo Moving gradle-wrapper.jar...
    if exist "gradle-8.0\lib\gradle-wrapper.jar" (
        copy "gradle-8.0\lib\gradle-wrapper.jar" "gradle\wrapper\gradle-wrapper.jar"
        echo ✓ gradle-wrapper.jar installed
    )
    
    echo Cleaning up...
    rmdir /s /q gradle-8.0
    del gradle-8.0.zip
) else (
    echo Failed to download gradle
    exit /b 1
)

echo.
echo ========================================
echo ✓ Gradle setup complete!
echo ========================================
echo.
echo Next: Run gradlew.bat test
echo.

pause
