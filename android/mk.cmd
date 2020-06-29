@ECHO OFF

REM Always do the below
CALL .\gradlew assembleDebug --configure-on-demand --parallel --daemon

IF [%1]==[2] GOTO DEPLOY
GOTO END

:DEPLOY
ECHO DO DEPLOY
CALL adb install -r .\build\outputs\apk\debug\tea-debug.apk

:END
REM ECHO END
REM ZX: adb common usage:
REM adb tcpip 5555
REM adb connect 192.168.1.25
REM adb usb
