@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.

TITLE 安装程序

Echo  当前时间 ： %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

@REM ================================ Set Env ================================
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

@REM Loading Env
CALL "!WORK_DIR!\WindowsCurrentUserPrograms"
CALL "!WORK_DIR!\WindowsCurrentUserDesktop"


SET SHORTCUT_NAME=热点运维工具

ECHO 删除快捷方式……
RMDIR /S /Q "!WINDOWS_CURRENT_USER_PROGRAMS!\!SHORTCUT_NAME!"
DEL /F /S /Q "!WINDOWS_CURRENT_USER_DESKTOP!\!SHORTCUT_NAME!.lnk"
ECHO 删除方式创建完毕。

EndLocal & GoTo end

:end
Echo     程序安装成功
MSG %UserName% /server:127.0.0.1 /time:7 "服务卸载完毕"
Echo.
pause
GoTo eof

:::eof
::PAUSE
::::EXIT /B 0
::EXIT
