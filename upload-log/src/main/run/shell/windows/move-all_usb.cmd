@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title 正在移动文件 ……
Echo 正在移动文件 ……

SET LONG_BIT=%~1
::SET SOURCE_PATH=h:\log
SET SOURCE_PATH=%~2
SET TARGET_PATH=%~3


@REM ========== Env ==========
::Echo The Env :
SET SCRIPT_DIR=%~dp0
if "%SCRIPT_DIR:~-1%" == "\" (
    SET SCRIPT_DIR=%SCRIPT_DIR:~0,-1%
)
::echo 	SCRIPT_DIR        = %SCRIPT_DIR%

for %%i in (%SCRIPT_DIR%.) do (
    SET APP_HOME=%%~dpi
)
if "%APP_HOME:~-1%" == "\" (
    SET APP_HOME=%APP_HOME:~0,-1%
)
::echo 	APP_HOME          = %APP_HOME%

Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

IF NOT EXIST "%TARGET_PATH%\" (
    Echo.
    Echo     目标目录 '%TARGET_PATH%' 不存在
    Echo.
    GoTo eof
)

For %%i in (%SOURCE_PATH%\) do (
    SET LOG_PATH_IN_USB=%%~pi
)

For /F "Skip=1" %%i In ('Wmic Logicaldisk Where "DriveType=2" Get DeviceID') Do (
    Set DeviceID=%%i
    If EXIST "!DeviceID!/" (
        If Exist "!DeviceID!!LOG_PATH_IN_USB!" (
            Echo     日志源目录 [!DeviceID!!LOG_PATH_IN_USB!]
            Call "%APP_HOME%\bin\move" "!LONG_BIT!" "!DeviceID!!LOG_PATH_IN_USB!" "%TARGET_PATH%"
            Echo.
        ) Else (
            Set NotFoundUSBPath=!NotFoundUSBPath!, !DeviceID!!LOG_PATH_IN_USB!
        )
    )
)


Echo.
Echo 不存在的源目录有：
Echo     [!NotFoundUSBPath!]
Echo.

Goto eof


:not_found_usb
Echo.
Echo     没有发现 USB 存储设备
Echo.
Exit /B 503
GoTo eof


:eof
Echo.
Echo 从 USB 存储设备移动文件到 '%TARGET_PATH%' 完毕
Echo.
endlocal
