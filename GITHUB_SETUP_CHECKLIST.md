# ✅ GitHub Actions Setup Checklist

## Phase 1: Install Git (5 minutes)

- [ ] Download Git: https://git-scm.com/download/win
- [ ] Run installer (keep default options)
- [ ] Restart Command Prompt
- [ ] Verify: `git --version` (should show version number)

---

## Phase 2: Create GitHub Account (5 minutes)

- [ ] Go to: https://github.com/signup
- [ ] Enter email, password, username
- [ ] Verify email
- [ ] Log in

---

## Phase 3: Create Repository (2 minutes)

- [ ] Go to: https://github.com/new
- [ ] Repository name: `secure-lockdown`
- [ ] Public (leave unchecked if you want private)
- [ ] Click **Create repository**
- [ ] Copy the repository URL (looks like: `https://github.com/YOUR_USERNAME/secure-lockdown.git`)

---

## Phase 4: Push Code to GitHub (5 minutes)

Open Command Prompt in `d:\omkar` and run:

```bash
git config --global user.email "your.email@example.com"
git config --global user.name "Your Name"

git init
git add .
git commit -m "Initial: Secure Lockdown app"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/secure-lockdown.git
git push -u origin main
```

Replace:
- `your.email@example.com` = your email
- `Your Name` = your name
- `YOUR_USERNAME` = your GitHub username
- Use the full repository URL you copied

---

## Phase 5: Add GitHub Actions Workflow (2 minutes)

Create folder structure:
```
d:\omkar\.github\
d:\omkar\.github\workflows\
```

Create file: `d:\omkar\.github\workflows\build.yml`

Copy this content:

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

## Phase 6: Push Workflow to GitHub (2 minutes)

```bash
cd d:\omkar
git add .github/workflows/build.yml
git commit -m "Add GitHub Actions workflow"
git push
```

---

## Phase 7: Watch Build Happen (5 minutes)

1. Go to: https://github.com/YOUR_USERNAME/secure-lockdown
2. Click **Actions** tab
3. Watch the workflow run
4. Wait for ✅ **Build successful**

---

## Phase 8: Download APK

When build completes:

1. Click the workflow run in Actions tab
2. Scroll to **Artifacts** section
3. Click **app-debug** to download ZIP
4. Extract to get: `app-debug.apk`

---

## Phase 9: Test on Phone

**Option A: Using ADB (if you have Android tools)**
```bash
adb install app-debug.apk
```

**Option B: Online Emulator (easiest, no setup)**
1. Go to: https://appetize.io
2. Upload the APK file
3. Test in browser immediately!

---

## ✅ All Done!

You now have:
- ✅ Automatic APK building (every time you push code)
- ✅ Free cloud-based build system
- ✅ Ready to download and test anytime
- ✅ No local Android Studio needed!
