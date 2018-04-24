@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.

TITLE 安装程序

Echo  当前时间 ： %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.





@REM ================================ Set Env ================================
ECHO / ===================================== 初始化环境变量 ===================================== \
IF "!APP_HOME!" == "" (
    Call :msg_env_not_set "APP_HOME"
    GOTO eof
)
IF "!APP_HOME:~-1!" == "\" (
    SET APP_HOME=!APP_HOME:~0,-1!
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

SET JRE_ZIP_DOWNLOAD=!DIR_TEMP!\jre.zip
ECHO JRE_ZIP_DOWNLOAD  = !JRE_ZIP_DOWNLOAD!

SET URL_JRE_X86=http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x86.zip
ECHO URL_JRE_X86       = !URL_JRE_X86!

SET URL_JRE_X64=http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x64.zip
ECHO URL_JRE_X64       = !URL_JRE_X64!


SET SHORTCUT_PROGRAM=!APP_HOME!\!MAIN_EXE!
SET SHORTCUT_WORK_DIR=!APP_HOME!
SET SHORTCUT_DESC=用于热点信息科技有限公司运维处理
SET SHORTCUT_NAME_DESKTOP=热点运维工具
SET SHORTCUT_NAME_STARTUP=热点运维工具\启动
SET SHORTCUT_PROGRAM_PARAMETER_STARTUP=startup
SET SHORTCUT_NAME_UNINSTALL=热点运维工具\卸载
SET SHORTCUT_PROGRAM_PARAMETER_UNINSTALL=uninstall
SET SHORTCUT_PROGRAM_PARAMETER_VERBOSE=true
ECHO \ ===================================== 初始化环境变量 ===================================== /
ECHO.





@REM ================================ Create dir ================================
ECHO / ===================================== 初始化目录 ===================================== \
If NOT EXIST "!DIR_LOG!\" (
    MKDIR "!DIR_LOG!\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_LOG!\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_LOG!\"
)
If NOT EXIST "!DIR_TEMP!\upload\standalone_v1\" (
    MKDIR "!DIR_TEMP!\upload\standalone_v1\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_TEMP!\upload\standalone_v1\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_TEMP!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_TEMP!\upload\standalone_v3\" (
    MKDIR "!DIR_TEMP!\upload\standalone_v3\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_TEMP!\upload\standalone_v3\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_TEMP!\upload\standalone_v3\"
)
If NOT EXIST "!DIR_DATA!\upload\standalone_v1\" (
    MKDIR "!DIR_DATA!\upload\standalone_v1\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_DATA!\upload\standalone_v1\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_DATA!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_DATA!\upload\standalone_v3\" (
    MKDIR "!DIR_DATA!\upload\standalone_v3\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_DATA!\upload\standalone_v3\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_DATA!\upload\standalone_v3\"
)
If NOT EXIST "!DIR_BACKUP!\upload\standalone_v1\" (
    MKDIR "!DIR_BACKUP!\upload\standalone_v1\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_BACKUP!\upload\standalone_v1\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_BACKUP!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_BACKUP!\upload\standalone_v3\" (
    MKDIR "!DIR_BACKUP!\upload\standalone_v3\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!DIR_BACKUP!\upload\standalone_v3\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_BACKUP!\upload\standalone_v3\"
)
ECHO \ ===================================== 初始化目录 ===================================== /
ECHO.





@REM ================================ Install JRE ================================
ECHO / ===================================== 安装 JRE 环境 ===================================== \
@REM Delete %JRE_HOME% If Exist
If EXIST "!JRE_HOME!" (
    If NOT EXIST "!JRE_HOME!\" (
        DEL /F /Q /S "!JRE_HOME!"
        If ErrorLevel 1 (
            Call :msg_del_dir_failed "!JRE_HOME!"
            GoTo eof
        )
        Call :msg_del_dir_success "!JRE_HOME!"
    )
)
@REM Create %JRE_HOME%
If NOT EXIST "!JRE_HOME!\" (
    MKDIR "!JRE_HOME!\"
    If ErrorLevel 1 (
        Call :msg_make_dir_failed "!JRE_HOME!\"
        GoTo eof
    )
    Call :msg_make_dir_success "!JRE_HOME!\"
)
@REM Download JRE-Package
if /i %LONG_BIT%==32 (
    "!DIR_BIN!\wget" "!URL_JRE_X86!" -O "!JRE_ZIP_DOWNLOAD!"
    If ErrorLevel 1 (
        Call :msg_download_jre_fail "!URL_JRE_X86!"
        GoTo eof
    )
    Call :msg_download_jre_success "!URL_JRE_X86!"
) else (
    "!DIR_BIN!\wget" "!URL_JRE_X64!" -O "!JRE_ZIP_DOWNLOAD!"
    If ErrorLevel 1 (
        Call :msg_download_jre_fail "!URL_JRE_X64!"
        GoTo eof
    )
    Call :msg_download_jre_success "!URL_JRE_X64!"
)
@REM Install JRE
"!DIR_BIN!\7z" x "!JRE_ZIP_DOWNLOAD!" -o"!JRE_HOME!" -aoa
If ErrorLevel 1 (
    Call :msg_install_jre_fail "!JRE_HOME!"
    GoTo eof
)
Call :msg_install_jre_success "!JRE_HOME!"
@REM Delete JRE-Package
DEL "!JRE_ZIP_DOWNLOAD!"
If ErrorLevel 1 (
    Call :msg_del_file_failed "!JRE_ZIP_DOWNLOAD!"
    GoTo eof
)
Call :msg_del_file_success "!JRE_ZIP_DOWNLOAD!"
ECHO \ ===================================== 安装 JRE 环境 ===================================== /
Echo.





@REM ================================ Create Shortcut ================================
ECHO / ===================================== 创建快捷方式 ===================================== \
CALL "%WORK_DIR%\CreateShortcutForDesktop" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME_DESKTOP!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER_VERBOSE! !SHORTCUT_PROGRAM_PARAMETER_STARTUP!"
If ErrorLevel 1 (
    Echo     桌面快捷方式创建失败
    MSG %UserName% /server:127.0.0.1 "桌面快捷方式创建失败"
    GoTo eof
)
Echo     桌面快捷方式创建成功

CALL "%WORK_DIR%\CreateShortcutForStartMenu" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME_STARTUP!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER_VERBOSE! !SHORTCUT_PROGRAM_PARAMETER_STARTUP!"
If ErrorLevel 1 (
    Echo     开始菜单[启动]快捷方式创建失败
    MSG %UserName% /server:127.0.0.1 "开始菜单[启动]快捷方式创建失败"
    GoTo eof
)
Echo     开始菜单[启动]快捷方式创建成功

CALL "%WORK_DIR%\CreateShortcutForStartMenu" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME_UNINSTALL!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER_VERBOSE! !SHORTCUT_PROGRAM_PARAMETER_UNINSTALL!"
If ErrorLevel 1 (
    Echo     开始菜单[卸载]快捷方式创建失败
    MSG %UserName% /server:127.0.0.1 "开始菜单[卸载]快捷方式创建失败"
    GoTo eof
)
Echo     开始菜单[卸载]快捷方式创建成功
ECHO \ ===================================== 创建快捷方式 ===================================== /
Echo.





Echo     程序安装成功。请点击 [开始/所有程序/!SHORTCUT_NAME_STARTUP!] 或 [桌面/!SHORTCUT_NAME_DESKTOP!] 来启动程序！
MSG %UserName% /server:127.0.0.1 /time:10 "程序安装成功。请点击 [开始/所有程序/!SHORTCUT_NAME_STARTUP!] 或 [桌面/!SHORTCUT_NAME_DESKTOP!] 来启动程序！"
"!APP_HOME!\bin\sleep" 3000
EndLocal & GoTo eof












@REM ================================================ Message
@REM Env
:msg_env_not_set
    Echo     环境变量'%~1'没有设置
    MSG %UserName% /server:127.0.0.1 "环境变量'%~1'没有设置"
    GoTo :EOF


@REM Make Directory
:msg_make_dir_success
    Echo     目录[%~1]创建成功
    GoTo :EOF

:msg_make_dir_failed
    Echo     目录[%~1]创建失败
    MSG %UserName% /server:127.0.0.1 "目录[%~1]创建失败"
    GoTo :EOF


@REM Delete Directory
:msg_del_dir_success
    Echo     目录[%~1]删除成功
    GoTo :EOF

:msg_del_dir_failed
    Echo     目录[%~1]删除失败
    MSG %UserName% /server:127.0.0.1 "目录[%~1]删除失败"
    GoTo :EOF


@REM Delete File
:msg_del_file_success
    Echo     文件[%~1]删除成功
    GoTo :EOF

:msg_del_file_failed
    Echo     文件[%~1]删除失败
    MSG %UserName% /server:127.0.0.1 "目录[%~1]删除失败"
    GoTo :EOF


@REM Download JRE
:msg_download_jre_success
    Echo     JRE 安装包[%~1]下载成功
    GoTo :EOF

:msg_download_jre_fail
    Echo     JRE 安装包[%~1]下载失败
    MSG %UserName% /server:127.0.0.1 "JRE 安装包[%~1]下载失败"
    GoTo :EOF


@REM Download JRE
:msg_install_jre_success
    Echo     JRE 环境[%~1]安装成功
    GoTo :EOF

:msg_install_jre_fail
    Echo     JRE 环境[%~1]安装失败
    MSG %UserName% /server:127.0.0.1 "JRE 环境[%~1]安装失败"
    GoTo :EOF
