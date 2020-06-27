@ECHO OFF
adb shell "run-as com.emptool.blazerloan chmod 666 /data/data/com.emptool.blazerloan/databases/BlazerLoan.db"
adb pull /data/data/com.emptool.blazerloan/databases/BlazerLoan.db C:\src\gitlab.com\hci.emptool.com\blazer-loan\data
adb shell "run-as com.emptool.blazerloan chmod 660 /data/data/com.emptool.blazerloan/databases/BlazerLoan.db"
