@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.

TITLE 安装程序

Echo  当前时间 ： %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

@REM ================================ Set Env ================================
ECHO / ===================================== 初始化环境变量 ===================================== \
SET WORK_DIR=%CD%
IF "!WORK_DIR:~-1!" == "\" (
    SET WORK_DIR=!WORK_DIR:~0,-1!
)
ECHO APP_HOME          = !APP_HOME!
ECHO LONG_BIT          = !LONG_BIT!
ECHO MAIN_EXE          = !MAIN_EXE!

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

CALL "!WORK_DIR!\ReadConfig" false "!DIR_CONF!\app.ini"

CALL "!WORK_DIR!\WindowsCurrentUserPrograms"
If Not ErrorLevel 0 (
    Call :msg_env_load_fail "!WORK_DIR!\WindowsCurrentUserPrograms"
    GoTo eof
)

CALL "!WORK_DIR!\WindowsCurrentUserDesktop"
If Not ErrorLevel 0 (
    Call :msg_env_load_fail "!WORK_DIR!\WindowsCurrentUserDesktop"
    GoTo eof
)


If "%com.littlehotspot.offline.tools.project.name%" == "" (
    SET SHORTCUT_NAME_PROJECT=热点运维工具
) Else (
    SET SHORTCUT_NAME_PROJECT=%com.littlehotspot.offline.tools.project.name%
)
ECHO \ ===================================== 初始化环境变量 ===================================== /
ECHO.





@REM ================================ Create Shortcut ================================
ECHO / ===================================== 删除快捷方式 ===================================== \
RMDIR /S /Q "!WINDOWS_CURRENT_USER_PROGRAMS!\!SHORTCUT_NAME_PROJECT!"
If Not ErrorLevel 0 (
    Echo     删除开始菜单快捷方式失败
    MSG %UserName% /server:127.0.0.1 "'%~1' 删除开始菜单快捷方式失败"
    GoTo eof
)

DEL /F /S /Q "!WINDOWS_CURRENT_USER_DESKTOP!\!SHORTCUT_NAME_PROJECT!.lnk"
If Not ErrorLevel 0 (
    Echo     删除桌面快捷方式失败
    MSG %UserName% /server:127.0.0.1 "'%~1' 删除桌面快捷方式失败"
    GoTo eof
)
ECHO \ ===================================== 删除快捷方式 ===================================== /
Echo.





Echo     程序卸载成功
MSG %UserName% /server:127.0.0.1 /time:7 "服务卸载完毕"
"!APP_HOME!\bin\sleep" 3000
EndLocal & Echo. & GoTo :eof

:msg_env_load_fail
    Echo     '%~1' 加载失败
    MSG %UserName% /server:127.0.0.1 "'%~1' 加载失败"
    GoTo :EOF
