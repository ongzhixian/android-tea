# Adb Shell

## To connect to ADB shell (only 1 device)

```
adb shell
```

## To connect to ADB shell (multiple devices)

Alternatively if you have multiple devices connected:
1. Run 'adb devices' first to list devices
2. Use '-s' argument when calling shell

```
$ adb devices
List of devices attached
192.168.1.20:5555       device
```

Then use the '-s' parameter:

```
adb -s 192.168.1.20:5555 shell
```

## Accessing your application data

Use the 'run-as' command.
This will bring you the package data directory.
And you will have permissions to do 'ls' commands on that directory

```
$run-as com.emptool.blazerloan;
```

```
shell@android:/data/data/com.domain.yourapp $ ls

cache
databases
lib
shared_prefs   
```


adb shell "run-as com.emptool.blazerloan chmod 666 /data/data/com.emptool.blazerloan/databases/BlazerLoan.db"
adb pull /data/data/com.emptool.blazerloan/databases/BlazerLoan.db C:\src\gitlab.com\hci.emptool.com\blazer-loan\data
adb shell "run-as com.emptool.blazerloan chmod 660 /data/data/com.emptool.blazerloan/databases/BlazerLoan.db"

## Pre-Android 5+
adb shell "run-as package.name chmod 666 /data/data/package.name/databases/file"
adb pull /data/data/package.name/databases/file .
adb shell "run-as package.name chmod 600 /data/data/package.name/databases/file"

-rw- rw- ---
660

## Android 5+
adb shell "run-as package.name chmod 666 /data/data/package.name/databases/file"
adb exec-out run-as package.name cat databases/file > newOutFileName
adb shell "run-as package.name chmod 600 /data/data/package.name/databases/file"