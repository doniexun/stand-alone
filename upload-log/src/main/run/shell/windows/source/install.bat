@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 安装程序

Echo  当前时间 ： %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

@REM ================================ Set Env ================================
IF "!APP_HOME!" == "" (
    ECHO "APP_HOME 没有配置"
    GOTO eof
)
IF "!APP_HOME:~-1!" == "\" (
    SET APP_HOME=!APP_HOME:~0,-1!
)
ECHO APP_HOME          = !APP_HOME!

SET WORK_DIR=%CD%
IF "!WORK_DIR:~-1!" == "\" (
    SET WORK_DIR=!WORK_DIR:~0,-1!
)
ECHO WORK_DIR          = !WORK_DIR!

SET SHELL_DIR=%CD%
IF "!SHELL_DIR:~-1!" == "\" (
    SET SHELL_DIR=!SHELL_DIR:~0,-1!
)
ECHO SHELL_DIR         = !SHELL_DIR!

if /i %PROCESSOR_IDENTIFIER:~0,3%==x86 (
	SET LONG_BIT=32
) else (
	SET LONG_BIT=64
)
ECHO LONG_BIT          = !LONG_BIT!

SET MAIN_EXE=STB.exe
ECHO MAIN_EXE          = !MAIN_EXE!

SET DIR_BIN=!APP_HOME!\bin
ECHO DIR_BIN           = !DIR_BIN!

SET DIR_LOG=!APP_HOME!\logs
ECHO DIR_LOG           = !DIR_LOG!

SET DIR_CONF=!APP_HOME!\conf
ECHO DIR_CONF          = !DIR_CONF!

SET DIR_TEMP=!APP_HOME!\temp
ECHO DIR_TEMP          = !DIR_TEMP!

SET DIR_DATA=!APP_HOME!\data
ECHO DIR_DATA          = !DIR_DATA!

SET DIR_BACKUP=!APP_HOME!\backup
ECHO DIR_BACKUP        = !DIR_BACKUP!

SET JRE_HOME=!APP_HOME!\java\jre
ECHO JRE_HOME          = !JRE_HOME!

SET JRE_ZIP_DOWNLOAD=!DIR_TEMP!\jre.zip
ECHO JRE_ZIP_DOWNLOAD  = !JRE_ZIP_DOWNLOAD!

SET URL_JRE_X86=http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x86.zip
ECHO URL_JRE_X86       = !URL_JRE_X86!

SET URL_JRE_X64=http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x64.zip
ECHO URL_JRE_X64       = !URL_JRE_X64!


SET SHORTCUT_PROGRAM=!APP_HOME!\!MAIN_EXE!
SET SHORTCUT_PROGRAM_PARAMETER="startup"
SET SHORTCUT_WORK_DIR=!APP_HOME!
SET SHORTCUT_NAME=热点运维工具
SET SHORTCUT_DESC=用于热点信息科技有限公司运维处理




@REM ================================ Create dir ================================
ECHO 正在创建需要的目录……
If NOT EXIST "!DIR_LOG!\" (
    MKDIR "!DIR_LOG!\"
)
If NOT EXIST "!DIR_TEMP!\upload\standalone_v1\" (
    MKDIR "!DIR_TEMP!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_TEMP!\upload\standalone_v3\" (
    MKDIR "!DIR_TEMP!\upload\standalone_v3\"
)
If NOT EXIST "!DIR_DATA!\upload\standalone_v1\" (
    MKDIR "!DIR_DATA!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_DATA!\upload\standalone_v3\" (
    MKDIR "!DIR_DATA!\upload\standalone_v3\"
)
If NOT EXIST "!DIR_BACKUP!\upload\standalone_v1\" (
    MKDIR "!DIR_BACKUP!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_BACKUP!\upload\standalone_v3\" (
    MKDIR "!DIR_BACKUP!\upload\standalone_v3\"
)
ECHO 目录创建完毕。
ECHO.


@REM ================================ Install JRE ================================

Echo 正在安装 JRE 环境 ……
If EXIST "!JRE_HOME!" (
    If NOT EXIST "!JRE_HOME!\" (
        DEL /F /Q /S "!JRE_HOME!"
    )
)

If NOT EXIST "!JRE_HOME!\" (
    MKDIR "!JRE_HOME!\"
)

if /i %LONG_BIT%==32 (
    "!DIR_BIN!\wget" "!URL_JRE_X86!" -O "!JRE_ZIP_DOWNLOAD!"
    If ERRORLEVEL 1 GoTo download_jre_fail
) else (
    "!DIR_BIN!\wget" "!URL_JRE_X64!" -O "!JRE_ZIP_DOWNLOAD!"
    If ERRORLEVEL 1 GoTo download_jre_fail
)

"!DIR_BIN!\7z" x "!JRE_ZIP_DOWNLOAD!" -o"!JRE_HOME!" -aoa
If ERRORLEVEL 1 GoTo jre_fail
DEL "!JRE_ZIP_DOWNLOAD!"
Echo JRE 环境安装完毕。
Echo.

@REM ================================ Create Shortcut ================================
ECHO 创建快捷方式……
CALL "%WORK_DIR%\CreateShortcutForDesktop" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER!"
CALL "%WORK_DIR%\CreateShortcutForStartMenu" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER!"
ECHO 快捷方式创建完毕。

GoTo end

:download_jre_fail
Echo     下载 JRE 安装包失败
MSG %UserName% /server:127.0.0.1 "下载 JRE 安装包失败"
Echo.
GoTo eof

:jre_fail
Echo     JRE 环境安装失败
MSG %UserName% /server:127.0.0.1 "JRE 环境安装失败"
Echo.
GoTo eof

:end
Echo     程序安装成功，请在 [开始菜单] 或 [桌面] 中运行'!SHORTCUT_NAME!',来启动程序！
MSG %UserName% /server:127.0.0.1 /time:10 "服务安装完毕，请在 [开始菜单] 或 [桌面] 中运行'!SHORTCUT_NAME!',来启动程序！"
Echo.
GoTo eof

:::eof
::PAUSE
::::EXIT /B 0
::EXIT
