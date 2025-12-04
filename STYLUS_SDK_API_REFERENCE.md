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

### 1.7 OPLUS System Services (oplus-services.jar)

**ソース**: `oplus-services.jar` (ColorOS/OxygenOS システムサービス)

#### PencilManager.java (システム用)
```java
package com.oplus.ipemanager.sdk;

public class PencilManager {
    // サービス接続
    // Action: "com.oplus.ipemanager.ACTION.PENCIL_SYSTEM"
    // Package: "com.oplus.ipemanager"
    
    // 振動開始 (type=5 固定)
    public void startVibration();
    
    // レーザーモード設定
    public void setLaserMode(int mode);
}
```

#### ISystemAidlInterface.java (AIDL)
```java
package com.oplus.ipemanager.sdk;

public interface ISystemAidlInterface extends IInterface {
    String DESCRIPTOR = "com.oplus.ipemanager.sdk.ISystemAidlInterface";
    
    void startVibration(int type);  // 振動開始
    void setLaserMode(int mode);    // レーザーモード
}
```

### 1.8 RichTap Vibrator SDK (AAC)

**パッケージ**: `vendor.aac.hardware.richtap.vibrator`
**ソース**: `oplus-services.jar`

```java
public interface IRichtapVibrator extends IInterface {
    // 基本制御
    void init(IRichtapCallback callback);
    void on(int timeoutMs, IRichtapCallback callback);
    void off(IRichtapCallback callback);
    void stop(IRichtapCallback callback);
    
    // エフェクト再生
    int perform(int effect_id, byte strength, IRichtapCallback callback);
    void performEnvelope(int[] envInfo, boolean fastFlag, IRichtapCallback callback);
    void performRtp(ParcelFileDescriptor hdl, IRichtapCallback callback);
    
    // HE (Haptic Engine) パラメータ
    void performHe(int looper, int interval, int amplitude, int freq, int[] he, IRichtapCallback callback);
    void performHeParam(int interval, int amplitude, int freq, IRichtapCallback callback);
    
    // 設定
    void setAmplitude(int amplitude, IRichtapCallback callback);
    void setDynamicScale(int scale, IRichtapCallback callback);
    void setF0(int f0, IRichtapCallback callback);  // 共振周波数
    void setHapticParam(int[] data, int length, IRichtapCallback callback);
}
```

### 1.9 OPLUS Vibrator HAL

**パッケージ**: `vendor.oplus.hardware.oplusvibrator`
**ソース**: `oplus-services.jar`

```java
public interface IOplusVibrator extends IInterface {
    // RichTap継承メソッド + 追加機能
    
    // リニアモーター振動
    void linearMotorVibratorOn(int waveform_id, int amplitude, boolean is_rtp_mode);
    void linearMotorVibratorOff();
    
    // キャリブレーション
    void startCalibrate();
    int[] getCalibrateResults();
    
    // タッチスタイル
    void setVibratorTouchStyle(int touchStyle);
    int getVibratorTouchStyle();
    
    // ゲーム用振動
    void writeGunType(int gunType);
    void writeGunMode(int gunMode);
    void writeBulletNum(int bulletNum);
    void writeHapticAudio(String buffer);
    
    // 電源設定
    void setPowerOnVibratorSwitch(String data);
    void setVmax(int vmax);
    
    // サポート機能
    byte[] getSupportFeatures();
    int getStatus();
}
```

### 1.10 使用例 (システムサービス)
```kotlin
// システム用PencilService接続
val intent = Intent("com.oplus.ipemanager.ACTION.PENCIL_SYSTEM")
intent.setPackage("com.oplus.ipemanager")
context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

// 振動開始
pencilManager.startVibration()  // type=5 (VIBRATION定数)

// レーザーポインターモード
pencilManager.setLaserMode(1)  // ON
pencilManager.setLaserMode(0)  // OFF
```

### 1.11 OplusLaserPointerController (レーザーポインター)

**パッケージ**: `com.android.server.input`
**ソース**: `oplus-services.jar`

```java
public class OplusLaserPointerController {
    // レーザーモード定数
    public static final int MODE_LASER_POINT = 0;      // ポイントモード
    public static final int MODE_LASER_CURSORLINE = 1; // カーソル線モード
    
    // ペン接続状態
    public static final int PEN_DISCONNECT = 0;
    public static final int PEN_CONNECT = 2;
    
    // スキャンコード (ボタンイベント)
    public static final int SCAN_CODE_LONG_PRESS = 189;    // 長押し
    public static final int SCAN_CODE_DOUBLE_CLICK = 190;  // ダブルクリック
    
    // Settings.Global キー
    // ipe_setting_only_laser_enable - レーザー有効化
    // ipe_pencil_connect_state - ペン接続状態
    // ipe_laser_mode - レーザーモード
    
    // PencilManagerを使用した振動
    private void startVibration() {
        mPencilManager.startVibration();
    }
    
    // レーザーモード変更通知
    private void sendModeToPencilManager(int mode) {
        mPencilManager.setLaserMode(mode);
    }
}
```

### 1.12 OplusPenGestureManager (ペンジェスチャー)

**パッケージ**: `com.android.server.oplus.input.exinputservice.globalgesture`
**ソース**: `oplus-services.jar`

```java
public class OplusPenGestureManager {
    // スクリーンショット設定
    public static final int PEN_SCREENSHOT_SWITCH_OPEN = 1;
    public static final int PEN_SCREENSHOT_SWITCH_CLOSE = 2;
    
    // Settings.Global キー
    // ipe_pencil_swipe_function - ペンスワイプ機能設定
    
    // スタイラスイベント判定
    private boolean isStylusEvent(MotionEvent event) {
        int tool = event.getToolType(0);
        return tool == 2 || tool == 4;  // STYLUS or ERASER
    }
    
    // ジェスチャー開始エリア (右上隅)
    // 画面右上から50dp四方でスワイプ開始
}
```

### 1.13 Haptic Feedback API

**パッケージ**: `com.android.server.vibrator`
**ソース**: `oplus-services.jar`

```java
// WaveformエフェクトID (RTP)
public class VibrationEffectConvertor {
    // キーボード振動
    public static final int RTP_INDEX_KEYBOARD_WEAK = 110;
    public static final int RTP_INDEX_KEYBOARD_MEDIUM = 111;
    public static final int RTP_INDEX_KEYBOARD_STRONG = 112;
    
    // エミュレーションキーボード
    public static final int RTP_INDEX_EMULATION_KEYBOARD_DOWN = 302;
    public static final int RTP_INDEX_EMULATION_KEYBOARD_UP = 303;
    
    // 汎用波形
    public static final int WAVEFORM_INDEX_WEAKEST_SHORT = 1;
    public static final int WAVEFORM_INDEX_WEAK_SHORT = 2;
    public static final int WAVEFORM_INDEX_STRONG_GRANULAR = 6;
    public static final int WAVEFORM_INDEX_WEAK_GRANULAR = 7;
}

// ハプティックフィードバック定数
public class OplusHapticFeedbackVibrationProvider {
    // 標準フィードバック
    // 0: VIRTUAL_KEY
    // 1: LONG_PRESS
    // 3-5: 各種タッチ
    
    // OPLUS拡張フィードバック
    // 300: LONG_VIBRATE
    // 301: KEYBOARD_TOUCH
    // 305: 特殊効果 (effectId=152)
    // 10001: 特殊効果 (effectId=70)
}

// LinearmotorVibratorService
public class LinearmotorVibratorService {
    void vibrate(int uid, String opPkg, WaveformEffect we, IBinder token);
    void cancelVibrate(WaveformEffect we, IBinder token);
    void setVibratorStrength(int strength);
    void setVibratorTouchStyle(int style);
    int getVibratorTouchStyle();
    void updateVibrationAmplitude(int uid, String opPkg, float amplitudeRatio);
}
```

---

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

| 機能 | OPLUS IPE | OPLUS RichTap/HAL | Vivo PenEngine | Huawei PenKit |
|-----|-----------|-------------------|----------------|---------------|
| **振動フィードバック** | ✅ | ✅ | ✅ | ❌ |
| **振動タイプ (ペン種別)** | 5種類 | エフェクトID | 3段階強度 | ❌ |
| **HE振動パラメータ** | ❌ | ✅ (amplitude/freq/interval) | ❌ | ❌ |
| **RTPファイル再生** | ❌ | ✅ | ❌ | ❌ |
| **筆圧検知** | ✅ | ❌ | ✅ | ✅ |
| **傾き検知** | ❌ | ❌ | ❌ | ✅ |
| **軌跡予測** | ✅ (VFX SDK) | ❌ | ✅ (TrackPredCore) | ✅ |
| **図形認識** | ❌ | ❌ | ✅ (IAlgoService) | ✅ (InstantShape) |
| **Bluetooth接続** | ❌ | ❌ | ✅ | ❌ |
| **バッテリー監視** | ❌ | ❌ | ✅ | ❌ |
| **IMUセンサー** | ❌ | ❌ | ✅ | ❌ |
| **E-inkサポート** | ❌ | ❌ | ❌ | ✅ |
| **リニアモーター制御** | ❌ | ✅ | ❌ | ❌ |
| **タッチスタイル** | ❌ | ✅ | ❌ | ❌ |
| **レーザーモード** | ✅ | ❌ | ❌ | ❌ |

---

## ファイル一覧

### OPLUS IPE Manager SDK (OnePlus Note APK)
| ファイル | パス |
|---------|------|
| `IpeFeature.java` | `/stylus_sdk/oplus/ipemanager/` |
| `Vibration.java` | `/stylus_sdk/oplus/ipemanager/` |
| `ISdkAidlInterface.java` | `/stylus_sdk/oplus/ipemanager/` |
| `ISDKAidlCallback.java` | `/stylus_sdk/oplus/ipemanager/` |
| `a.java` (PencilManager) | `/stylus_sdk/oplus/ipemanager/` |

### OPLUS System Services (oplus-services.jar)
| ファイル | パス |
|---------|------|
| `PencilManager.java` | `/stylus_sdk/oplus/system_services/` |
| `ISystemAidlInterface.java` | `/stylus_sdk/oplus/system_services/` |
| `IRichtapVibrator.java` | `/stylus_sdk/oplus/richtap/` |
| `IRichtapCallback.java` | `/stylus_sdk/oplus/richtap/` |
| `IOplusVibrator.java` | `/stylus_sdk/oplus/vibrator/` |
| `ILinearMotorVibrator.java` | `/stylus_sdk/oplus/vibrator/` |
| `LinearmotorVibratorService.java` | `/stylus_sdk/oplus/vibrator/` |
| `IOplusVibratorCallback.java` | `/stylus_sdk/oplus/vibrator/` |

### OPLUS Input Services (oplus-services.jar)
| ファイル | パス |
|---------|------|
| `OplusLaserPointerController.java` | `/stylus_sdk/oplus/input/` |
| `OplusPenGestureManager.java` | `/stylus_sdk/oplus/input/` |

### OPLUS Haptic Feedback (oplus-services.jar)
| ファイル | パス |
|---------|------|
| `VibrationEffectConvertor.java` | `/stylus_sdk/oplus/haptic/` |
| `OplusHapticFeedbackVibrationProvider.java` | `/stylus_sdk/oplus/haptic/` |
| `WaveformEffectParser.java` | `/stylus_sdk/oplus/haptic/` |

### OPLUS VFX SDK (OnePlus Note APK)
| ファイル | パス |
|---------|------|
| `StylusManager.java` | `/stylus_sdk/oplus/vfxsdk/` |
| `VibrationType.java` | `/stylus_sdk/oplus/vfxsdk/` |
| `StylusInterface.java` | `/stylus_sdk/oplus/vfxsdk/` |
| その他 38ファイル | `/stylus_sdk/oplus/vfxsdk/` |

### Vivo PenEngine SDK
| ファイル | パス |
|---------|------|
| `IBluetoothPenCallback.java` | `/stylus_sdk/vivo/bluetoothpen/` |
| `OnPenStateListener.java` | `/stylus_sdk/vivo/bluetoothpen/` |
| `VivoStylusGestureManagerImpl.java` | `/stylus_sdk/vivo/penengine/` |
| `IAlgoService.java` | `/stylus_sdk/vivo/penalgoengine/` |
| `TrackPredCore.java` | `/stylus_sdk/vivo/trackpredictor/` |

### Huawei PenKit SDK
| ファイル | パス |
|---------|------|
| `HwPenEngineManager.java` | `/stylus_sdk/huawei/penengine/` |
| `HwHandWritingView.java` | `/stylus_sdk/huawei/penengine/` |
| `InstantShapeGenerator.java` | `/stylus_sdk/huawei/penengine/` |
| その他 15ファイル | `/stylus_sdk/huawei/penengine/` |

---

## 抽出ファイル統計

| SDK | ファイル数 |
|-----|-----------|
| OPLUS IPE Manager | 5 |
| OPLUS System Services | 4 |
| OPLUS Vibrator HAL | 4 |
| OPLUS Input Services | 2 |
| OPLUS Haptic Feedback | 3 |
| OPLUS VFX SDK | 41 |
| Vivo PenEngine | 17 |
| Huawei PenKit | 18 |
| **合計** | **94** |

---

*Generated: 2025-12-05*
*Source: OnePlus Note APK, JNotes APK, oplus-services.jar*


