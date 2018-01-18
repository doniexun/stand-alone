@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 安装程序

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
if "!SBIN_DIR:~-1!" == "\" (
    set APP_HOME=!SBIN_DIR:~0,-1!
)
::echo APP_HOME          = !APP_HOME!



@REM Read config file
Call "!APP_HOME!\bin\read_config" "!APP_HOME!\conf\app.ini"

Echo 正在安装 JRE 环境 ……
If EXIST "!APP_HOME!\temp\jre" (
    If NOT EXIST "!APP_HOME!\temp\jre\" (
        DEL /F /Q /S "!APP_HOME!\temp\jre"
    )
)

If NOT EXIST "!APP_HOME!\temp\jre\" (
    MKDIR "!APP_HOME!\temp\jre\"
)

@REM Install Jre
if /i %LONG_BIT%==32 (
    "!APP_HOME!\bin\7z" x "!APP_HOME!\soft\jre1.8.0_05_x86.zip" -o"!APP_HOME!\temp\jre"
    If ERRORLEVEL 1 GoTo jre_fail
) else (
    "!APP_HOME!\bin\7z" x "!APP_HOME!\soft\jre1.8.0_05_x64.zip" -o"!APP_HOME!\temp\jre"
    If ERRORLEVEL 1 GoTo jre_fail
)
Echo.
Echo JRE 环境安装完毕
Echo.

@REM Create dir
If NOT EXIST "!APP_HOME!\data\upload\" (
    MKDIR "!APP_HOME!\data\upload\"
)
If NOT EXIST "!APP_HOME!\data\upload\generation" (
    MKDIR "!APP_HOME!\data\upload\generation"
)
If NOT EXIST "!APP_HOME!\data\upload\bibasic" (
    MKDIR "!APP_HOME!\data\upload\bibasic"
)
If NOT EXIST "!APP_HOME!\data\download\" (
    MKDIR "!APP_HOME!\data\download\"
)
If NOT EXIST "!APP_HOME!\data\download\generation" (
    MKDIR "!APP_HOME!\data\download\generation"
)
If NOT EXIST "!APP_HOME!\data\download\bibasic" (
    MKDIR "!APP_HOME!\data\download\bibasic"
)
If NOT EXIST "!APP_HOME!\data\decompression\" (
    MKDIR "!APP_HOME!\data\decompression\"
)
If NOT EXIST "!APP_HOME!\data\decompression\generation" (
    MKDIR "!APP_HOME!\data\decompression\generation"
)
If NOT EXIST "!APP_HOME!\data\decompression\bibasic" (
    MKDIR "!APP_HOME!\data\decompression\bibasic"
)
If NOT EXIST "!APP_HOME!\data\U\" (
    MKDIR "!APP_HOME!\data\U\"
)
GoTo end

:jre_fail
Echo.
Echo     JRE 环境安装失败
Echo.
GoTo eof

:end
Echo.
Echo.
Echo     程序安装成功
Echo.
Echo.
GoTo eof

:eof
PAUSE

