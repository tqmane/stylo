# Stylus SDK 抽出ファイル

各OEMのスタイラスSDKから抽出した主要なJavaファイル集です。
一般化されたスタイラスAPIの実装に活用できます。

## ディレクトリ構造

```
stylus_sdk/
├── oplus/                    # OPPO/OnePlus
│   ├── ipemanager/          # IPE Manager SDK (振動制御)
│   │   ├── IpeFeature.java         # 機能enum
│   │   ├── Vibration.java          # 振動タイプenum
│   │   ├── ISdkAidlInterface.java  # AIDL Interface
│   │   ├── ISDKAidlCallback.java   # AIDL Callback
│   │   └── a.java                  # PencilManager
│   ├── system_services/     # システムサービス (oplus-services.jar)
│   │   ├── PencilManager.java      # システム用PencilManager
│   │   └── ISystemAidlInterface.java
│   ├── richtap/             # RichTap振動 (AAC)
│   │   ├── IRichtapVibrator.java   # 高度な振動制御
│   │   └── IRichtapCallback.java
│   ├── vibrator/            # OPLUS振動HAL
│   │   ├── IOplusVibrator.java     # リニアモーター制御
│   │   └── ILinearMotorVibrator.java
│   └── vfxsdk/              # VFX Doodle Engine SDK
│       ├── StylusManager.java
│       ├── StylusInterface.java
│       ├── VibrationType.java
│       └── ...
├── vivo/                     # Vivo
│   ├── bluetoothpen/        # Bluetooth Pen SDK
│   │   ├── IBluetoothPenCallback.java
│   │   ├── OnPenStateListener.java
│   │   ├── OnPenConnectListener.java
│   │   └── ...
│   ├── penengine/           # Pen Engine SDK
│   │   ├── VivoStylusGestureManagerImpl.java
│   │   ├── VivoAlgorithmManagerImpl.java
│   │   └── ...
│   ├── penalgoengine/       # 図形認識 SDK
│   │   ├── IAlgoService.java
│   │   ├── ShapeData.java
│   │   └── TouchPoint.java
│   └── trackpredictor/      # 軌跡予測 SDK
│       ├── TrackPredCore.java
│       └── HwPoint.java
└── huawei/                   # Huawei
    └── penengine/           # PenKit SDK
        ├── HwPenEngineManager.java
        ├── HwHandWritingView.java
        ├── InstantShapeGenerator.java
        ├── HwStrokeEstimate.java
        └── ...
```

## 主要API一覧

### OPLUS IPE Manager
| クラス | 機能 |
|-------|------|
| `ISdkAidlInterface` | `startVibration()`, `setVibrationType()`, `getPencilConnectState()` |
| `ISDKAidlCallback` | `onConnectionChanged()`, `onVibrationSwitchStateChange()` |
| `Vibration` | PENCIL, ERASER, BALLPEN, PEN |

### OPLUS RichTap/Vibrator HAL
| クラス | 機能 |
|-------|------|
| `IRichtapVibrator` | `perform()`, `performHe()`, `on()`, `off()`, `setAmplitude()` |
| `IOplusVibrator` | `linearMotorVibratorOn()`, `setVibratorTouchStyle()` |
| `PencilManager` (system) | `startVibration()`, `setLaserMode()` |

### Vivo BluetoothPen
| クラス | 機能 |
|-------|------|
| `IBluetoothPenCallback` | `vibrate()`, `setWritingVibrationIntensity()`, `connectPen()` |
| `OnPenStateListener` | `onPressureLevelChange()`, `onIMUCoordinateChange()` |
| `TrackPredCore` | `doPredict()` - 軌跡予測 |
| `IAlgoService` | `computeShapeData()` - 図形認識 |

### Huawei PenKit
| クラス | 機能 |
|-------|------|
| `HwPenEngineManager` | `isEngineRuntimeAvailable()`, `isSupportEink()` |
| `HwHandWritingView` | `enablePencilTilt()`, `enableInstantShape()` |
| `InstantShapeGenerator` | `processTouchEvent()`, `getPath()` |
| `HwStrokeEstimate` | 軌跡補間 |

## サービス接続情報

### OPLUS (アプリ用)
```kotlin
Intent("com.oplus.ipemanager.ACTION.PENCIL_SDK")
    .setPackage("com.oplus.ipemanager")
```

### OPLUS (システム用)
```kotlin
Intent("com.oplus.ipemanager.ACTION.PENCIL_SYSTEM")
    .setPackage("com.oplus.ipemanager")
```

### Vivo
```kotlin
Intent()
    .setPackage("com.vivo.penwrite")
    .setAction("com.vivo.bluetoothpen.service.BluetoothPenService")
```

## 一般化のための抽象インターフェース例

```kotlin
interface StylusManager {
    // 接続
    fun connect(context: Context)
    fun disconnect()
    fun isConnected(): Boolean
    
    // 振動
    fun setVibrationEnabled(enabled: Boolean)
    fun setVibrationType(type: VibrationType)
    fun startVibration()
    fun stopVibration()
    
    // 状態リスナー
    fun addConnectionListener(listener: ConnectionListener)
    fun addPressureListener(listener: PressureListener)
}

enum class VibrationType {
    PENCIL, PEN, BALLPEN, ERASER, NONE
}
```

---
*ファイル数: 87*
*抽出日: 2025-12-05*
*ソース: OnePlus Note APK, JNotes APK, oplus-services.jar*

