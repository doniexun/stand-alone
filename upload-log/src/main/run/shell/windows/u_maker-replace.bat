@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 从OSS下载文件到本地

Echo  当前日期 ： %date:~0,4%-%date:~5,2%-%date:~8,2%
Echo  当前时间 ： %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.


SET SBIN_DIR=%~dp0
if "%SBIN_DIR:~-1%" == "\" (
  set SBIN_DIR=%SBIN_DIR:~0,-1%
)
::echo SBIN_DIR          = %SBIN_DIR%

for %%i in (%SBIN_DIR%.) do (
  set SBIN_DIR=%%~dpi
)
if "%SBIN_DIR:~-1%" == "\" (
  set APP_HOME=%SBIN_DIR:~0,-1%
)
::echo APP_HOME          = %APP_HOME%


@REM Read config file
Call "%APP_HOME%\bin\read_config" "%APP_HOME%\conf\app.ini"

If "%com.littlehotspot.alone.logs.app.dir.data%" == "" (
	SET U_PATH=%APP_HOME%\data
) Else (
	SET U_PATH=%com.littlehotspot.alone.logs.app.dir.data%
)
SET U_PATH=!U_PATH!\U


Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

@REM Download log-file to OSS
For /F "Skip=1" %%i In ('Wmic Logicaldisk Where "DriveType=2" Get DeviceID') Do (
    Set DeviceID=%%i
    If EXIST "!DeviceID!/" (
        Call "%APP_HOME%\bin\replace-in-u" "%LONG_BIT%" "%U_PATH%" "!DeviceID!"
        If ERRORLEVEL 1 (
            Echo.
            Echo     U 盘[!DeviceID!]制作失败
            Echo.
            GoTo eof
        )
    )
)

Echo.
Echo 机顶盒U盘文件制作完成
Echo.
GoTo eof

:not_found_usb
Echo.
Echo     没有发现 USB 存储设备
Echo.
GoTo eof

:eof
PAUSE
endlocal
