# FA5 Developer Package

This repository contains the developer reference materials for the **FA5** product model. The package is centered around the **Uniwin RK3562 Android 13 system API**, plus board-level I/O notes for the **M352-V motherboard** used by the device.

## Package Contents

- `FA5 API Documentation-en.pdf`: English API documentation for the Uniwin RK3562 Android 13 platform.
- `FA5-M352-V Motherboard User Manual - en.docx`: English motherboard usage notes for GPIO-driven peripherals and input detection.
- `rk3562-android13-uniwinApi-1.0.1.jar`: Android SDK library used to access the platform APIs from applications.

## Product Overview

FA5 is an Android-based smart display / connectivity device built on the **RK3562** platform. The included SDK exposes APIs for:

- Device and firmware information
- Display control and screenshot capture
- Power control and boot animation replacement
- Silent app install / uninstall / launch management
- Wi-Fi hotspot and Ethernet configuration
- Mobile network and airplane mode control
- Time, timezone, and language settings
- USB OTG mode control
- GPIO read/write access
- Watchdog operations
- OTA package verification and system upgrade
- Factory reset

## Quick Start

### 1. Add the SDK JAR

Copy `rk3562-android13-uniwinApi-1.0.1.jar` into your Android project's `libs/` directory and add it to your build.

### 2. Import the main manager class

```java
import com.uniwin.system.manager.UniwinManager;
```

### 3. Get the API entry point

```java
UniwinManager uniwinManager = UniwinManager.getInstance(context);
```

### 4. Call platform APIs

Examples from the documentation:

```java
String model = uniwinManager.getDeviceModel();
String sn = uniwinManager.getSerialNumber();
String apiVer = uniwinManager.getApiVersion();
String firmware = uniwinManager.getFirmwareVersion();

uniwinManager.setSystemBrightness(200);
uniwinManager.reboot(true);
uniwinManager.setWifiApEnabled(true);

int gpioValue = uniwinManager.readGpioByName("gpio3_d4");
int result = uniwinManager.writeGpioByName("led1-en", 1);
```

## Main API Areas

Based on `FA5 API Documentation-en.pdf`, the SDK is organized around the following capability groups:

### Device Information

- Get device model
- Get serial number
- Get total RAM size
- Get total internal storage size

### Version Information

- Get API version
- Get Android version
- Get kernel version
- Get firmware version

### Display Control

- Get main and secondary screen resolution
- Get and set screen orientation
- Show / hide status bar
- Show / hide navigation bar
- Get and set system brightness
- Turn screen off / on
- Capture screenshots
- Enable or disable touch / input events

### Power and Application Management

- Shutdown and reboot
- Replace boot animation
- Silent app install
- Silent app uninstall
- Force stop applications
- Detect whether an app is in foreground
- Start applications by package and class name

### Network Management

- Check Wi-Fi status
- Enable / disable hotspot
- Configure hotspot SSID and password
- Enable Ethernet
- Switch DHCP / static Ethernet
- Read Ethernet IP, gateway, netmask, DNS, and MAC
- Control mobile data and roaming
- Read mobile operator and network type
- Toggle airplane mode

### System Settings

- Set system time
- Set time from milliseconds
- Set timezone
- Get and set system language
- Enable / disable USB OTG mode

### Hardware Control

- Read GPIO level
- Write GPIO level
- Initialize, feed, configure, and disable watchdog

### System Upgrade

- Verify OTA packages
- Trigger OTA upgrades
- Restore factory settings

## M352-V Board I/O Reference

The motherboard manual maps several named control lines to GPIOs used by FA5:

### Output Control

- Relay: `gpio3_d7` -> `relay`
- Tri-color LED 1: `gpio0_c4` -> `led1-en`
- Tri-color LED 2: `gpio0_c0` -> `led2-en`
- Tri-color LED 3: `gpio0_a6` -> `led3-en`
- Infrared fill light: `gpio4_b6` -> `led-ir-en`
- General output 1: `gpio3_d4` -> `gpio1`
- General output 2: `gpio3_d5` -> `gpio2`
- Doorbell output: `gpio1_d0` -> `doorbell-out`

Typical usage:

```java
Gpio.writeGpio("relay", 1);
Gpio.writeGpio("led-ir-en", 1);
```

### Input Detection

- Door open button: `gpio0_a4` -> `door-open`
- Door magnetic sensor: `gpio3_a7` -> `door-det`

The board notes indicate these input events can be received through the Android broadcast action:

```java
com.uniwin.io.input
```

Polling paths mentioned in the manual:

- Door open button state: `/sys/class/switch/gpio_pin_0/state`
- Door magnetic sensor state: `/sys/class/switch/gpio_pin_1/state`

### Wiegand Notes

- Wiegand input: `gpio1_b5` / `gpio1_b6`
- Wiegand output: `gpio1_b3` / `gpio1_b4`

## Development Notes

- The English API PDF appears to be machine-translated in some sections; verify important behavior against the Chinese documentation or on-device testing when integrating critical functions.
- API names in the JAR are under `com.uniwin.system.*`.
- The package is intended for Android application integration on the FA5 platform rather than for general Android devices.

## Recommended Repository Usage

If you keep this folder as a product SDK package, a practical workflow is:

1. Read `FA5 API Documentation-en.pdf` for full API signatures and behavior.
2. Use `FA5-M352-V Motherboard User Manual - en.docx` when wiring or controlling board-level peripherals.
3. Import `rk3562-android13-uniwinApi-1.0.1.jar` into your Android project.
4. Validate GPIO behavior directly on FA5 hardware before release.

## Tested Source Summary

This README was prepared from the documents currently present in this folder:

- `FA5 API Documentation-en.pdf`
- `FA5-M352-V Motherboard User Manual - en.docx`
- `rk3562-android13-uniwinApi-1.0.1.jar`


---

# SUNTEK FA5 M352-V Motherboard & GPIO Guide

### M352-V motherboard

Instructions for use of relay/tricolor lamp/infrared fill light/IO port output/doorbell output functions

### Instructions for using the door open button/door magnetic detection function

---

M352-V Mainboard Relay/Tri-color LED/Infrared Complementary Light/IO Port Output/Doorbell Output Function User Manual:

The GPIO output ports used by these functions and their corresponding GPIO names are as follows:

### Relay control I/O:

gpio3_d7, name is "relay"

### Tri-color LED control IO:

gpio0_c4, "led1-en

gpio0_c0, "led2-en

gpio0_a6, "led3-en

### Infrared fill light control IO:

gpio4_b6, "led-ir-en

### 2-channel I/O output:

gpio3_d4, "gpio1"

gpio3_d5, "gpio2"

### Doorbell output:

gpio1_d0, "doorbell-out

### The corresponding code usage method is as follows:

### See the document "Uniwin-rk356x_Android11 System API Documentation.pdf"

1. Import uniwinapi.jar into the libs directory of your project;

2. Import android.uniwin.Gpio;

### 3. Detailed I/O instructions:

### a. Example of reading I/O port status:

int value = readGpio("gpio0");  // Reads the value of gpio1; low level returns 0, high level returns 1.

### b. Example of writing I/O port status:

Gpio.writeGpio("gpio0",0);

Gpio.writeGpio("gpio0",1);

### Following the method described above,

### For example, to set the output relay I/O to high: `Gpio.writeGpio("relay",1);`

### For example, to set the output infrared fill light to low: `Gpio.writeGpio("led-ir-en",1);`

---

### M352-V Mainboard Door Opening Button/Door Magnetic Detection Function User Manual:

### The GPIO used by these functions and the defined IO names are as follows:

### Door opening button:

gpio0_a4, "door-open"

### Door magnetic detection:

gpio3_a7, "door-det"

### The door opening button and door magnetic sensor function are used as follows:

The API usage instructions are as follows; for the specific demo source code, please refer to the compressed file "UniwinIOInput.rar".

public void onReceive(Context context, Intent intent) {

// TODO Auto-generated method stub

if(intent.getAction().equals("com.uniwin.io.input")){

String ioName =intent.getStringExtra("ioname");

int state = intent.getIntExtra("state", 0);

String text = "Received io.input event, ioname:"+ioName+", state:"+state+"!";

Toast.makeText(context, text, Toast.LENGTH_SHORT).show();

}

}

(1) When the value obtained by ioname is "door-open", it means that the signal to open the door button has been received.

The state value represents the input value detected by the door opening button. 1 indicates a high level is detected, and 0 indicates a low level. The default hardware setting is a high level.

If you want to use polling to obtain the status of the door open button's I/O port, you can directly read the following path:

"/sys/class/switch/gpio_pin_0/state"

(2) When the value obtained by ioname is "door-det", it means that a door magnetic sensor signal has been received.

The state value represents the input value detected by the door magnetic sensor I/O port. 1 indicates a high level is detected, and 0 indicates a low level. The default hardware level is high.

To obtain the status of the door magnetic sensor I/O port using a polling method, simply read the following path:

"/sys/class/switch/gpio_pin_1/state"

---

// Wiegand input: gpio1_b5/gpio1_b6

// Wiegand output: gpio1_b3/gpio1_b4

---
