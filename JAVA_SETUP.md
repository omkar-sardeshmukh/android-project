# 🔧 Java Setup Guide (Windows)

## Step 1: Download Java (5 minutes)

1. Go to: https://www.oracle.com/java/technologies/downloads/
2. Click **Java 17** (or Java 11)
3. Click **Windows x64 Installer**
4. Accept the license
5. Download starts automatically
6. Open the `.exe` file and click **Install**

**Installation Path:** `C:\Program Files\Java\jdk-17.x.x`

---

## Step 2: Set JAVA_HOME Environment Variable (3 minutes)

### On Windows 10/11:

1. **Press:** Windows Key + X
2. Click: **System**
3. Click: **Advanced system settings** (on the left)
4. Click: **Environment Variables** button
5. Under "System variables", click **New**

Fill in:
- **Variable name:** `JAVA_HOME`
- **Variable value:** `C:\Program Files\Java\jdk-17.0.x`

(Replace `jdk-17.0.x` with your actual Java folder name)

6. Click **OK** → **OK** → **OK**

---

## Step 3: Verify Installation (1 minute)

**Close all Command Prompts completely!** (Important)

Then open a **NEW Command Prompt** and run:

```bash
java -version
```

**You should see:**
```
java version "17.0.x" 2024-04-16 LTS
Java(TM) SE Runtime Environment (build 17.0.x+11-LTS-191)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.x+11-LTS-191, mixed mode)
```

If you see this ✅ you're good!

---

## Step 4: Run Tests (2 minutes)

```bash
cd d:\omkar
gradlew.bat test
```

---

## 🆘 Having Issues?

### Issue: "java -version" still doesn't work

**Solution:**
1. Restart your computer completely
2. Open **NEW Command Prompt**
3. Try again

### Issue: Can't find Java folder

**Solution:**
1. Open File Explorer
2. Go to: `C:\Program Files\Java`
3. Look for folder like: `jdk-17.0.x` or `jdk-11.0.x`
4. Copy the exact folder name
5. Use that in JAVA_HOME

### Issue: Still getting errors

Let me know the exact error and I'll help!

---

## ⏱️ Total Time: ~15 minutes

1. Download Java (5 min)
2. Install Java (5 min)
3. Set JAVA_HOME (3 min)
4. Run tests (2 min)

---

## Ready? Start Here:

1. Download Java from oracle.com
2. Install it
3. Set JAVA_HOME variable
4. Restart computer
5. Run: `gradlew.bat test`

Let me know when done! ✅
