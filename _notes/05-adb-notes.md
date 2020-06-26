# Debugging notes

## Contents

1.  Using wireless (adb tcpip 5555)
2.  Using usb (adb usb)
3.  logcat (adb logcat)
4.  shell (adb shell)


# 1.  Using wireless (adb tcpip 5555)

Android Wireless Debugging

1.	adb devices
2.	adb tcpip 5555
3.	Disconnect your device (remove the USB cable).
4.	Go to the Settings -> About phone -> Status to view the IP address of your phone
5.	adb connect <IP address of your device>:5555
	adb connect 192.168.1.20:5555

# 2.  Using usb (adb usb)

```
adb usb
```

# Using logcat for debugging

```
adb logcat ActivityManager:I MyApp:D *:S
```


brief: Display priority/tag and PID of the process issuing the message.
process: Display PID only.
tag: Display the priority/tag only.
raw: Display the raw log message, with no other metadata fields.
time: Display the date, invocation time, priority/tag, and PID of the process issuing the message.
threadtime (the default format): Display the date, invocation time, priority, tag, and the PID and TID of the thread issuing the message.
long: Display all metadata fields and separate messages with blank lines.


# Reference

https://developer.android.com/studio/command-line/logcat.html#filteringOutput