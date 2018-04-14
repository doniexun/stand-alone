@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 安装程序

Echo  当前时间 ： %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

IF "!APP_HOME!" == "" (
    ECHO "APP_HOME 没有配置"
    GOTO eof
)
IF "!APP_HOME:~-1!" == "\" (
    SET APP_HOME=!APP_HOME:~0,-1!
)
ECHO APP_HOME          = !APP_HOME!

if /i %PROCESSOR_IDENTIFIER:~0,3%==x86 (
	SET LONG_BIT=32
) else (
	SET LONG_BIT=64
)


@REM ================================ Create dir ================================
ECHO 正在创建需要的目录……
If NOT EXIST "!APP_HOME!\logs\" (
    MKDIR "!APP_HOME!\logs\"
)
If NOT EXIST "!APP_HOME!\temp\upload\standalone_v1\" (
    MKDIR "!APP_HOME!\temp\upload\standalone_v1\"
)
If NOT EXIST "!APP_HOME!\temp\upload\standalone_v3\" (
    MKDIR "!APP_HOME!\temp\upload\standalone_v3\"
)
If NOT EXIST "!APP_HOME!\data\upload\standalone_v1\" (
    MKDIR "!APP_HOME!\data\upload\standalone_v1\"
)
If NOT EXIST "!APP_HOME!\data\upload\standalone_v3\" (
    MKDIR "!APP_HOME!\data\upload\standalone_v3\"
)
If NOT EXIST "!APP_HOME!\backup\upload\standalone_v1\" (
    MKDIR "!APP_HOME!\backup\upload\standalone_v1\"
)
If NOT EXIST "!APP_HOME!\backup\upload\standalone_v3\" (
    MKDIR "!APP_HOME!\backup\upload\standalone_v3\"
)
ECHO 目录创建完毕。
ECHO.


@REM ================================ Install JRE ================================
SET JRE_HOME=!APP_HOME!\java\jre

Echo 正在安装 JRE 环境 ……
If EXIST "!JRE_HOME!" (
    If NOT EXIST "!JRE_HOME!\" (
        DEL /F /Q /S "!JRE_HOME!"
    )
)

If NOT EXIST "!JRE_HOME!\" (
    MKDIR "!JRE_HOME!\"
)

SET JRE_ZIP_TEMP=!APP_HOME!\temp\jre.zip
if /i %LONG_BIT%==32 (
    "!APP_HOME!\bin\wget" "http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x86.zip" -O "!JRE_ZIP_TEMP!"
    If ERRORLEVEL 1 GoTo download_jre_fail
) else (
    "!APP_HOME!\bin\wget" "http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x64.zip" -O "!JRE_ZIP_TEMP!"
    If ERRORLEVEL 1 GoTo download_jre_fail
)

"!APP_HOME!\bin\7z" x "!JRE_ZIP_TEMP!" -o"!JRE_HOME!" -aoa
If ERRORLEVEL 1 GoTo jre_fail
DEL "!JRE_ZIP_TEMP!"
Echo JRE 环境安装完毕。
Echo.

ECHO 创建快捷方式……
CALL "!APP_HOME!\bin\shortcut" "!APP_HOME!\STB.exe" "热点运维工具" "!APP_HOME!" "用于热点信息科技有限公司运维处理" ""startup""
ECHO 快捷方式创建完毕。

GoTo end

:download_jre_fail
Echo     下载 JRE 安装包失败
Echo.
GoTo eof

:jre_fail
Echo     JRE 环境安装失败
Echo.
GoTo eof

:end
Echo     程序安装成功
Echo.
GoTo eof

:eof
PAUSE
::EXIT /B 0
EXIT
