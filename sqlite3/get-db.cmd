@ECHO OFF
REM adb shell "run-as com.emptool.tea chmod 666 /data/data/com.emptool.blazerloan/databases/BlazerLoan.db"
REM adb pull /data/data/com.emptool.blazerloan/databases/BlazerLoan.db C:\src\gitlab.com\hci.emptool.com\blazer-loan\data

adb -s "192.168.1.25:5555" shell "run-as com.emptool.tea chmod 666 /data/data/com.emptool.tea/databases/tea.sqlite3"
adb -s "192.168.1.25:5555" pull /data/data/com.emptool.tea/databases/tea.sqlite3 C:\src\github.com\ongzhixian\tagged-event-attendance\code\sqlite3
adb -s "192.168.1.25:5555" shell "run-as com.emptool.tea chmod 660 /data/data/com.emptool.tea/databases/tea.sqlite3"