@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 从移动磁盘移动文件到本机

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
	SET LOCAL_TARGET=%APP_HOME%\data
) Else (
	SET LOCAL_TARGET=%com.littlehotspot.alone.logs.app.dir.data%
)
SET LOCAL_TARGET=!LOCAL_TARGET!\upload

If "%com.littlehotspot.alone.logs.app.dir.temp%" == "" (
	SET LOCAL_TEMP=%APP_HOME%\temp
) Else (
	SET LOCAL_TEMP=%com.littlehotspot.alone.logs.app.dir.temp%
)

If "%com.littlehotspot.alone.logs.app.dir.usb_drive%" == "" (
	Echo 获取网络时间的 URL 地址没有配置，请到 'conf\app.ini' 中配置 'com.littlehotspot.alone.logs.app.dir.usb_drive'
	GoTo eof
) Else (
	SET USB_DIR=%com.littlehotspot.alone.logs.app.dir.usb_drive%
)


@Rem Detecting network connectivity
Call "%APP_HOME%\bin\move-all_usb" "%LONG_BIT%" "%USB_DIR%" "%LOCAL_TARGET%"


TITLE 从移动磁盘移动文件到本机完成

PAUSE


:eof
endlocal
