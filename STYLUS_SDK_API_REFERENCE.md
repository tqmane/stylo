# Android Stylus SDK API リファレンス

> APK解析によって抽出されたOEM固有スタイラスSDKの完全なAPIドキュメント

---

## 目次
1. [OPLUS IPE Manager SDK](#1-oplus-ipe-manager-sdk-oppooneplus)
2. [Vivo PenEngine SDK](#2-vivo-penengine-sdk)
3. [Huawei PenKit SDK](#3-huawei-penkit-sdk)
4. [SDK比較表](#4-sdk比較表)

---

## 1. OPLUS IPE Manager SDK (OPPO/OnePlus)

**パッケージ**: `com.oplus.ipemanager.sdk`
**ソース**: OnePlus Note (`com.oneplus.note_16.3.31.apk`)

### 1.1 IpeFeature.java
```java
package com.oplus.ipemanager.sdk;

public enum IpeFeature implements Parcelable {
    DEMO_MODE,           // デモモード
    FUNCTION_VIBRATION,  // 機能振動 (ボタンクリック等)
    FEEDBACK_VIBRATION   // フィードバック振動 (書き心地シミュレーション)
}
```

### 1.2 Vibration.java
```java
package com.oplus.ipemanager.sdk;

public enum Vibration {
    PENCIL,             // 鉛筆 - ordinal: 0
    ERASER,             // 消しゴム - ordinal: 1
    BALLPEN,            // ボールペン - ordinal: 2
    PEN,                // ペン - ordinal: 3
    FUNCTION_VIBRATION  // 機能振動 - ordinal: 4
}
```

### 1.3 ISdkAidlInterface.java (AIDL)
```java
package com.oplus.ipemanager.sdk;

public interface ISdkAidlInterface extends IInterface {
    String DESCRIPTOR = "com.oplus.ipemanager.sdk.ISdkAidlInterface";
    
    // === 振動制御 ===
    void startVibration(int type);           // 振動開始
    void setVibrationType(int type);         // 振動タイプ設定 (0-4)
    boolean getVibrationSwitchState();       // 振動スイッチ状態取得
    boolean isFunctionVibrationEnable();     // 機能振動有効確認
    
    // === ペンシル接続 ===
    int getPencilConnectState();             // 0=未接続, 2=接続中
    
    // === SDK情報 ===
    int getSuportSdkVersion();               // SDKバージョン取得
    List<IpeFeature> getSupportFeatures();   // サポート機能リスト
    boolean isDemoModeEnable();              // デモモード確認
    
    // === コールバック ===
    void setISdkAidlCallback(ISDKAidlCallback callback);
    void unsetISdkAidlCallback(ISDKAidlCallback callback);
}
```

### 1.4 ISDKAidlCallback.java (AIDL)
```java
package com.oplus.ipemanager.sdk;

public interface ISDKAidlCallback extends IInterface {
    String DESCRIPTOR = "com.oplus.ipemanager.sdk.ISDKAidlCallback";
    
    void onConnectionChanged(int state);             // 接続状態変更
    void onVibrationSwitchStateChange(boolean isOpen);  // 振動スイッチ変更
    void onFunctionFeedbackStateChange(boolean enable); // 機能フィードバック変更
    void onDemoModeEnableChange(boolean enable);        // デモモード変更
}
```

### 1.5 PencilManager 使用例
```kotlin
// 1. サービスに接続
val intent = Intent("com.oplus.ipemanager.ACTION.PENCIL_SDK")
intent.setPackage("com.oplus.ipemanager")
context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

// 2. ServiceConnection実装
val serviceConnection = object : ServiceConnection {
    override fun onServiceConnected(name: ComponentName, binder: IBinder) {
        val aidlInterface = ISdkAidlInterface.Stub.asInterface(binder)
        aidlInterface.setISdkAidlCallback(callback)
    }
}

// 3. 振動タイプ設定 (書き心地)
aidlInterface.setVibrationType(Vibration.PENCIL.ordinal)  // 鉛筆
aidlInterface.setVibrationType(Vibration.BALLPEN.ordinal) // ボールペン

// 4. 振動開始
aidlInterface.startVibration(type)

// 5. 接続状態確認
val isConnected = aidlInterface.getPencilConnectState() == 2
```

### 1.6 OplusOS VFX SDK
**パッケージ**: `com.oplusos.vfxsdk.doodleengine`

```java
// VibrationType.java
public enum VibrationType {
    PENCIL, ERASE, BALL, PEN, NONE
}

// StylusManager.java - 主要定数
public class StylusManager {
    public static final String COMMAND_START_VIBRATION = "3";
    public static final String COMMAND_STOP_VIBRATION = "2";
    public static final int PENCIL_CONTROL_NODE = 38;
    public static final int PENCIL_STATE_OFF = 0;
    public static final int PENCIL_STATE_ON = 1;
}
```

---

## 2. Vivo PenEngine SDK

**パッケージ**: `com.vivo.penengine`, `com.vivo.bluetoothpen`, `com.vivo.penalgoengine`
**ソース**: JNotes (`com.jideos.jnotes_3.2.0.1.apk`)

### 2.1 IBluetoothPenCallback.java (AIDL)
```java
package com.vivo.bluetoothpen;

public interface IBluetoothPenCallback extends IInterface {
    String DESCRIPTOR = "com.vivo.bluetoothpen.IBluetoothPenCallback";
    
    // === 振動制御 ===
    void vibrate(int duration, int intensity, int pattern);
    void setWritingVibrationIntensity(int intensity);  // 96=弱, 128=中, 160=強
    void setMotorMode(int mode);
    
    // === ペン接続 ===
    void scanPen();
    void connectPen(String address);
    void disconnectPen(String address);
    boolean isPenConnect(String address);
    boolean hasPenBonded();
    BluetoothDevice getConnectedPen();
    
    // === ペン情報 ===
    String getPenName();
    String getSoftWareVersion();
    String getFirmwareVersion();
    long getSnCode();
    int getBatteryLevel();
    int getChargeState();
    
    // === 筆圧設定 ===
    int getPressureLevel();
    void setPressureLevel(int level);
    int getPressThreshold();
    int getReleaseThreshold();
    
    // === モード設定 ===
    int getWorkMode();
    void setWorkMode(int mode);
    int getMotorMode();
    void setIMUMode(int mode);
    
    // === リスナー登録 ===
    void registerScanListener(OnScanListener listener);
    void registerConnectListener(OnPenConnectListener listener);
    void registerPenStateListener(OnPenStateListener listener);
    void registerPenBatteryListener(OnPenBatteryListener listener);
}
```

### 2.2 OnPenStateListener.java (AIDL)
```java
package com.vivo.bluetoothpen;

public interface OnPenStateListener extends IInterface {
    void onFilmStateChange(int state);        // フィルム状態
    void onPressureStateChange(int state);    // 筆圧状態
    void onPressureLevelChange(int level);    // 筆圧レベル
    void onWorkModeChange(int mode);          // 動作モード
    void onShakeStateChange(int state);       // シェイク状態
    void onMotorModeChange(int mode);         // モーターモード
    void onSoftwareVersionChange(String ver); // ソフトウェアバージョン
    void onIMUModeChange(int mode);           // IMUモード
    void onIMUCoordinateChange(int x, int y); // IMU座標
}
```

### 2.3 VivoStylusGestureManagerImpl.java
```java
package com.vivo.penengine.impl;

public class VivoStylusGestureManagerImpl {
    // ジェスチャー定数
    public static final int STYLUS_PRIMARY_BUTTON_CLICK = 33;
    public static final int STYLUS_SECONDLY_BUTTON_CLICK = 34;
    public static final int FILM_STATE_DOUBLE_CLICK = 3;
    
    // 振動強度
    public static final int VIBRATION_INTENSITY_WEAK = 96;
    public static final int VIBRATION_INTENSITY_NORMAL = 128;
    public static final int VIBRATION_INTENSITY_STRONG = 160;
    
    // Settings.Secure キー
    public static final String KEY_HAPTIC_GESTURE = "vivo_stylus_haptic_feedback_gesture_switch";
    public static final String KEY_HAPTIC_WRITE = "vivo_stylus_haptic_feedback_write_switch";
    public static final String KEY_HAPTIC_LEVEL = "vivo_stylus_haptic_feedback_write_select_level";
    
    // 書き心地振動
    public void writingVibrate(boolean enabled, int intensity);
}
```

### 2.4 TrackPredCore.java (軌跡予測)
```java
package com.vivo.trackpredictor.core;

public class TrackPredCore {
    // ネイティブライブラリ
    static { System.loadLibrary("track_prediction"); }
    
    // 初期化 (設定ファイル読み込み)
    public boolean init(Context context);
    
    // 軌跡予測
    public HwPoint doPredict(List<HwPoint> points, int mode);
    public HwPoint doPredict(HwPoint point, int mode);
    
    // リソース解放
    public native void release();
    
    // ネイティブメソッド
    private native int initial(String cfg1, String cfg2);
    private native float[] predict(int time, float x, float y, 
                                   int action, float pressure, int mode);
}
```

### 2.5 IAlgoService.java (図形認識)
```java
package com.vivo.penalgoengine;

public interface IAlgoService extends IInterface {
    ShapeData computeShapeData(List<TouchPoint> points);
}

// ShapeData 図形タイプ定数
public class ShapeData {
    public static final int LINE = 6;
    public static final int CIRCLE = 7;
    public static final int ELLIPSE = 8;
    public static final int TRIANGLE = 9;
    public static final int RECTANGLE = 10;
    // ... その他の図形
}
```

### 2.6 サービス接続
```kotlin
// BluetoothPenService接続
val intent = Intent()
intent.setPackage("com.vivo.penwrite")
intent.setAction("com.vivo.bluetoothpen.service.BluetoothPenService")
context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

// AlgoService接続
val algoIntent = Intent("com.vivo.penalgoengine.intent.ACTION_ALGO_SERVICE")
context.bindService(algoIntent, algoConnection, Context.BIND_AUTO_CREATE)
```

---

## 3. Huawei PenKit SDK

**パッケージ**: `com.huawei.stylus.penengine`, `com.huawei.penkit.impl`
**ソース**: JNotes (`com.jideos.jnotes_3.2.0.1.apk`)

### 3.1 HwPenEngineManager.java
```java
package com.huawei.stylus.penengine;

public class HwPenEngineManager {
    // SDK利用可能チェック
    public static boolean isEngineRuntimeAvailable(Context context);
    
    // 機能サポート確認
    public static boolean isSupportEink(Context context);
    public static boolean isSupportFeature(Context context, int apiLevel);
    
    // APIレベル取得
    public static int getApiLevel();
}
```

### 3.2 HwHandWritingView.java
```java
package com.huawei.stylus.penengine.view;

public class HwHandWritingView {
    // === Pencil Tilt (傾き検知) ===
    public boolean enablePencilTilt(boolean enable);
    
    // === InstantShape (自動図形補正) ===
    public boolean enableInstantShape(boolean enable);
    public boolean enableInstantTable(boolean enable);
    
    // === キャンバス操作 ===
    public boolean enableCanvasScale(boolean enable);
    public boolean enableDrag(boolean enable);
    
    // === Undo/Redo ===
    public void undo();
    public void redo();
    public boolean canUndo();
    public boolean canRedo();
    
    // === データ操作 ===
    public void clear();
    public void save(String path);
    public void load(String path);
}
```

### 3.3 InstantShapeGenerator.java
```java
package com.huawei.stylus.penengine.instantshape;

public class InstantShapeGenerator {
    public InstantShapeGenerator(Context context);
    
    // タッチイベント処理
    public void processTouchEvent(MotionEvent event);
    
    // 結果取得
    public Path getPath();                    // 補正後のパス
    public int getCurveType();                // 図形タイプ
    public String getStringResult();          // 文字列表現
    
    // シリアライズ
    public Path getPathFromString(String data, float scale);
    
    // 設定
    public void setStopTime(int ms);          // 認識停止時間
    public void notifyViewSizeChange(int w, int h);
    
    // リソース解放
    public void destroy();
}
```

---

## 4. SDK比較表

| 機能 | OPLUS IPE | Vivo PenEngine | Huawei PenKit |
|-----|-----------|----------------|---------------|
| **振動フィードバック** | ✅ | ✅ | ❌ |
| **振動タイプ (ペン種別)** | 5種類 | 3段階強度 | ❌ |
| **筆圧検知** | ✅ | ✅ | ✅ |
| **傾き検知** | ❌ | ❌ | ✅ |
| **軌跡予測** | ✅ (VFX SDK) | ✅ (TrackPredCore) | ✅ |
| **図形認識** | ❌ | ✅ (IAlgoService) | ✅ (InstantShape) |
| **Bluetooth接続** | ❌ | ✅ | ❌ |
| **バッテリー監視** | ❌ | ✅ | ❌ |
| **IMUセンサー** | ❌ | ✅ | ❌ |
| **E-inkサポート** | ❌ | ❌ | ✅ |

---

## ファイル一覧

### OPLUS IPE Manager SDK
- `/decompiled/oneplus_note/sources/com/oplus/ipemanager/sdk/IpeFeature.java`
- `/decompiled/oneplus_note/sources/com/oplus/ipemanager/sdk/Vibration.java`
- `/decompiled/oneplus_note/sources/com/oplus/ipemanager/sdk/ISdkAidlInterface.java`
- `/decompiled/oneplus_note/sources/com/oplus/ipemanager/sdk/ISDKAidlCallback.java`
- `/decompiled/oneplus_note/sources/com/oplus/ipemanager/sdk/a.java` (PencilManager)
- `/decompiled/oneplus_note/sources/com/oplusos/vfxsdk/doodleengine/stylus/StylusManager.java`

### Vivo PenEngine SDK
- `/decompiled/jnotes/sources/com/vivo/penengine/impl/VivoStylusGestureManagerImpl.java`
- `/decompiled/jnotes/sources/com/vivo/bluetoothpen/IBluetoothPenCallback.java`
- `/decompiled/jnotes/sources/com/vivo/bluetoothpen/OnPenStateListener.java`
- `/decompiled/jnotes/sources/com/vivo/trackpredictor/core/TrackPredCore.java`
- `/decompiled/jnotes/sources/com/vivo/penalgoengine/IAlgoService.java`

### Huawei PenKit SDK
- `/decompiled/jnotes/sources/com/huawei/stylus/penengine/HwPenEngineManager.java`
- `/decompiled/jnotes/sources/com/huawei/stylus/penengine/view/HwHandWritingView.java`
- `/decompiled/jnotes/sources/com/huawei/stylus/penengine/instantshape/InstantShapeGenerator.java`

---

*Generated: 2025-12-05*
