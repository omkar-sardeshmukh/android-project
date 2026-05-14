@echo off
REM Simple Java Test Compiler and Runner
REM No Gradle, no dependencies, just pure Java

setlocal enabledelayedexpansion

echo ========================================
echo Secure Lockdown - Test Suite
echo ========================================
echo.

cd /d d:\omkar

echo Compiling TestRunner.java...
javac TestRunner.java

if exist "TestRunner.class" (
    echo ✓ Compilation successful
    echo.
    echo Running tests...
    echo.
    java TestRunner
) else (
    echo ✗ Compilation failed
    pause
    exit /b 1
)

pause
