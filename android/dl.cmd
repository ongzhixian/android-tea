@ECHO OFF
CALL adb logcat -c
REM CALL adb logcat SINKING_SHIP:V *:S
CALL adb logcat *:S MainActivity:V ScanBarcodeActivity:V BarcodeProcessor:V BorrowBlazerActivity:V ViewLoansActivity:v

REM ReturnBlazerActivity:V BorrowBlazerActivity:V ViewStatisticsActivity:V BlazerLoanDbHelper:V ViewDatabaseActivity:V