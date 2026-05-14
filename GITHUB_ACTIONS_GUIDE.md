# 🚀 GitHub Actions APK Build Guide

## Step 1: Install Git

Download from: https://git-scm.com/download/win

Install with default options.

---

## Step 2: Create GitHub Account

Go to: https://github.com/signup

Sign up with email, password, username.

---

## Step 3: Create GitHub Repository

1. Go to: https://github.com/new
2. Repository name: `secure-lockdown`
3. Description: "Android PIN Lock Security App"
4. Public (so you can see it)
5. Click **Create repository**

---

## Step 4: Setup Git Locally

Open Command Prompt in `d:\omkar`:

```bash
cd d:\omkar

git config --global user.email "your.email@example.com"
git config --global user.name "Your Name"

git init
git add .
git commit -m "Initial commit: Secure Lockdown app with tests"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/secure-lockdown.git
git push -u origin main
```

Replace:
- `your.email@example.com` with your email
- `Your Name` with your name
- `YOUR_USERNAME` with your GitHub username

---

## Step 5: Add Workflow File

Create folder: `.github/workflows/`

Inside, create file: `build.yml`

Content:
```yaml
name: Build APK

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 11
      uses: actions/setup-java@v3
      with:
        java-version: '11'
        distribution: 'temurin'
    
    - name: Make Gradle executable
      run: chmod +x gradlew
    
    - name: Build APK
      run: ./gradlew assembleDebug
    
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: build/outputs/apk/debug/
```

---

## Step 6: Push to GitHub

```bash
cd d:\omkar
git add .github/workflows/build.yml
git commit -m "Add GitHub Actions build workflow"
git push
```

---

## Step 7: Watch Build Happen

1. Go to: https://github.com/YOUR_USERNAME/secure-lockdown
2. Click **Actions** tab
3. Watch the build run in real-time!
4. It will show: ✅ Build successful or ❌ Failed

---

## Step 8: Download APK

After build completes:

1. Click the workflow run
2. Scroll down to **Artifacts**
3. Download: `app-debug.zip`
4. Extract to get APK file

APK location: `app-debug/build/outputs/apk/debug/app-debug.apk`

---

## Step 9: Install on Phone/Emulator

**Connect phone via USB:**
```bash
adb install app-debug.apk
```

**Or use online emulator:**
- Go to: https://appetize.io
- Upload APK
- Test in browser

---

## 🎉 Done!

Your APK is automatically built in the cloud whenever you push code to GitHub!

No local Android Studio needed! ✅
