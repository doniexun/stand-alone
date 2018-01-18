@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 转换视频编码

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

If "%com.littlehotspot.alone.media.path.source%" == "" (
	SET MEDIA_FROM=%APP_HOME%\media\source
) Else (
	SET MEDIA_FROM=%com.littlehotspot.alone.media.path.source%
)

If "%com.littlehotspot.alone.media.path.target%" == "" (
	SET MEDIA_TO=%APP_HOME%\media\target
) Else (
	SET MEDIA_TO=%com.littlehotspot.alone.media.path.target%
)


@Rem Transcoding for media
Call "%APP_HOME%\bin\transcoding" "%LONG_BIT%" "%MEDIA_FROM%" "%MEDIA_TO%" ".TS"
If ERRORLEVEL 1 (
	GoTo eof
)



Echo.
Echo.
Echo.
TITLE 转换视频编码完成
Echo 转换视频编码成功
Echo.
Echo.


:eof
PAUSE
endlocal
