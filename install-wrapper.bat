@echo off
REM Direct Gradle Wrapper JAR Download
REM Using curl (built into Windows 10+)

echo Downloading gradle-wrapper.jar...
echo This may take 1-2 minutes...
echo.

cd /d d:\omkar

REM Create gradle\wrapper directory if it doesn't exist
if not exist gradle\wrapper mkdir gradle\wrapper

REM Download using curl
curl -L -o gradle-8.0-all.zip "https://github.com/gradle/gradle/releases/download/v8.0/gradle-8.0-all.zip"

if exist "gradle-8.0-all.zip" (
    echo Extracting gradle...
    tar -xf gradle-8.0-all.zip
    
    if exist "gradle-8.0\lib\gradle-wrapper.jar" (
        copy "gradle-8.0\lib\gradle-wrapper.jar" "gradle\wrapper\gradle-wrapper.jar"
        rmdir /s /q gradle-8.0
        del gradle-8.0-all.zip
        
        echo.
        echo ✓ SUCCESS! gradle-wrapper.jar installed
        echo.
        echo Now run:
        echo   gradlew.bat test
        echo.
    )
) else (
    echo.
    echo ✗ Download failed
    echo.
    echo MANUAL WORKAROUND:
    echo 1. Go to: https://gradle.org/releases/
    echo 2. Download: Gradle 8.0 (Binary only)
    echo 3. Unzip to C:\gradle-8.0
    echo 4. Add to PATH: C:\gradle-8.0\bin
    echo 5. Then run: gradle test
    echo.
)

pause
