@ECHO OFF
CALL .\gradlew assembleDebug --configure-on-demand --parallel --daemon
CALL adb install -r .\build\outputs\apk\debug\tea-debug.apk

REM adb tcpip 5555
REM adb connect 192.168.1.20
REM adb usb