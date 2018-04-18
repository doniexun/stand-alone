@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE ��װ����

Echo  ��ǰʱ�� �� %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.





@REM ================================ Set Env ================================
ECHO / ===================================== ��ʼ���������� ===================================== \
IF "!APP_HOME!" == "" (
    Call :msg_env_not_set "APP_HOME"
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
SET SHORTCUT_WORK_DIR=!APP_HOME!
SET SHORTCUT_DESC=�����ȵ���Ϣ�Ƽ����޹�˾��ά����
SET SHORTCUT_NAME_DESKTOP=�ȵ���ά����
SET SHORTCUT_NAME_STARTUP=�ȵ���ά����\����
SET SHORTCUT_PROGRAM_PARAMETER_STARTUP="startup"
SET SHORTCUT_NAME_UNINSTALL=�ȵ���ά����\ж��
SET SHORTCUT_PROGRAM_PARAMETER_UNINSTALL="uninstall"
ECHO \ ===================================== ��ʼ���������� ===================================== /
ECHO.





@REM ================================ Create dir ================================
ECHO / ===================================== ��ʼ��Ŀ¼ ===================================== \
If NOT EXIST "!DIR_LOG!\" (
    MKDIR "!DIR_LOG!\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_LOG!\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_LOG!\"
)
If NOT EXIST "!DIR_TEMP!\upload\standalone_v1\" (
    MKDIR "!DIR_TEMP!\upload\standalone_v1\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_TEMP!\upload\standalone_v1\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_TEMP!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_TEMP!\upload\standalone_v3\" (
    MKDIR "!DIR_TEMP!\upload\standalone_v3\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_TEMP!\upload\standalone_v3\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_TEMP!\upload\standalone_v3\"
)
If NOT EXIST "!DIR_DATA!\upload\standalone_v1\" (
    MKDIR "!DIR_DATA!\upload\standalone_v1\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_DATA!\upload\standalone_v1\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_DATA!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_DATA!\upload\standalone_v3\" (
    MKDIR "!DIR_DATA!\upload\standalone_v3\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_DATA!\upload\standalone_v3\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_DATA!\upload\standalone_v3\"
)
If NOT EXIST "!DIR_BACKUP!\upload\standalone_v1\" (
    MKDIR "!DIR_BACKUP!\upload\standalone_v1\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_BACKUP!\upload\standalone_v1\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_BACKUP!\upload\standalone_v1\"
)
If NOT EXIST "!DIR_BACKUP!\upload\standalone_v3\" (
    MKDIR "!DIR_BACKUP!\upload\standalone_v3\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!DIR_BACKUP!\upload\standalone_v3\"
        GoTo eof
    )
    Call :msg_make_dir_success "!DIR_BACKUP!\upload\standalone_v3\"
)
ECHO \ ===================================== ��ʼ��Ŀ¼ ===================================== /
ECHO.





@REM ================================ Install JRE ================================
ECHO / ===================================== ��װ JRE ���� ===================================== \
@REM Delete %JRE_HOME% If Exist
If EXIST "!JRE_HOME!" (
    If NOT EXIST "!JRE_HOME!\" (
        DEL /F /Q /S "!JRE_HOME!"
        If ERRORLEVEL 1 (
            Call :msg_del_dir_failed "!JRE_HOME!"
            GoTo eof
        )
        Call :msg_del_dir_success "!JRE_HOME!"
    )
)
@REM Create %JRE_HOME%
If NOT EXIST "!JRE_HOME!\" (
    MKDIR "!JRE_HOME!\"
    If ERRORLEVEL 1 (
        Call :msg_make_dir_failed "!JRE_HOME!\"
        GoTo eof
    )
    Call :msg_make_dir_success "!JRE_HOME!\"
)
@REM Download JRE-Package
if /i %LONG_BIT%==32 (
    "!DIR_BIN!\wget" "!URL_JRE_X86!" -O "!JRE_ZIP_DOWNLOAD!"
    If ERRORLEVEL 1 (
        Call :msg_download_jre_fail "!URL_JRE_X86!"
        GoTo eof
    )
    Call :msg_download_jre_success "!URL_JRE_X86!"
) else (
    "!DIR_BIN!\wget" "!URL_JRE_X64!" -O "!JRE_ZIP_DOWNLOAD!"
    If ERRORLEVEL 1 (
        Call :msg_download_jre_fail "!URL_JRE_X64!"
        GoTo eof
    )
    Call :msg_download_jre_success "!URL_JRE_X64!"
)
@REM Install JRE
"!DIR_BIN!\7z" x "!JRE_ZIP_DOWNLOAD!" -o"!JRE_HOME!" -aoa
If ERRORLEVEL 1 (
    Call :msg_install_jre_fail "!JRE_HOME!"
    GoTo eof
)
Call :msg_install_jre_success "!JRE_HOME!"
@REM Delete JRE-Package
DEL "!JRE_ZIP_DOWNLOAD!"
If ERRORLEVEL 1 (
    Call :msg_del_file_failed "!JRE_ZIP_DOWNLOAD!"
    GoTo eof
)
Call :msg_del_file_success "!JRE_ZIP_DOWNLOAD!"
ECHO \ ===================================== ��װ JRE ���� ===================================== /
Echo.





@REM ================================ Create Shortcut ================================
ECHO / ===================================== ������ݷ�ʽ ===================================== \
CALL "%WORK_DIR%\CreateShortcutForDesktop" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME_DESKTOP!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER_STARTUP!"
If ERRORLEVEL 1 (
    Echo     �����ݷ�ʽ����ʧ��
    MSG %UserName% /server:127.0.0.1 "�����ݷ�ʽ����ʧ��"
    GoTo eof
)
CALL "%WORK_DIR%\CreateShortcutForStartMenu" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME_STARTUP!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER_STARTUP!"
If ERRORLEVEL 1 (
    Echo     ��ʼ�˵�[����]��ݷ�ʽ����ʧ��
    MSG %UserName% /server:127.0.0.1 "��ʼ�˵�[����]��ݷ�ʽ����ʧ��"
    GoTo eof
)
CALL "%WORK_DIR%\CreateShortcutForStartMenu" "!SHORTCUT_PROGRAM!" "!SHORTCUT_NAME_UNINSTALL!" "!SHORTCUT_WORK_DIR!" "!SHORTCUT_DESC!" "!SHORTCUT_PROGRAM_PARAMETER_UNINSTALL!"
If ERRORLEVEL 1 (
    Echo     ��ʼ�˵�[ж��]��ݷ�ʽ����ʧ��
    MSG %UserName% /server:127.0.0.1 "��ʼ�˵�[ж��]��ݷ�ʽ����ʧ��"
    GoTo eof
)
ECHO \ ===================================== ������ݷ�ʽ ===================================== /
Echo.





Echo     ����װ�ɹ������� [��ʼ�˵�] �� [����] ������'!SHORTCUT_NAME!',����������
MSG %UserName% /server:127.0.0.1 /time:10 "����װ��ϣ����� [��ʼ�˵�] �� [����] ������'!SHORTCUT_NAME!',����������"
GoTo eof












@REM ================================================ Message
@REM Env
:msg_env_not_set
Echo     ��������'%~1'û������
MSG %UserName% /server:127.0.0.1 "��������'%~1'û������"
GoTo :EOF


@REM Make Directory
:msg_make_dir_success
Echo     Ŀ¼[%~1]�����ɹ�
GoTo :EOF

:msg_make_dir_failed
Echo     Ŀ¼[%~1]����ʧ��
MSG %UserName% /server:127.0.0.1 "Ŀ¼[%~1]����ʧ��"
GoTo :EOF


@REM Delete Directory
:msg_del_dir_success
Echo     Ŀ¼[%~1]ɾ���ɹ�
GoTo :EOF

:msg_del_dir_failed
Echo     Ŀ¼[%~1]ɾ��ʧ��
MSG %UserName% /server:127.0.0.1 "Ŀ¼[%~1]ɾ��ʧ��"
GoTo :EOF


@REM Delete File
:msg_del_file_success
Echo     �ļ�[%~1]ɾ���ɹ�
GoTo :EOF

:msg_del_file_failed
Echo     �ļ�[%~1]ɾ��ʧ��
MSG %UserName% /server:127.0.0.1 "Ŀ¼[%~1]ɾ��ʧ��"
GoTo :EOF


@REM Download JRE
:msg_download_jre_success
Echo     JRE ��װ��[%~1]���سɹ�
GoTo :EOF

:msg_download_jre_fail
Echo     JRE ��װ��[%~1]����ʧ��
MSG %UserName% /server:127.0.0.1 "JRE ��װ��[%~1]����ʧ��"
GoTo :EOF


@REM Download JRE
:msg_install_jre_success
Echo     JRE ����[%~1]��װ�ɹ�
GoTo :EOF

:msg_install_jre_fail
Echo     JRE ����[%~1]��װʧ��
MSG %UserName% /server:127.0.0.1 "JRE ����[%~1]��װʧ��"
GoTo :EOF

:::end
::GoTo eof

:::eof
::PAUSE
::::EXIT /B 0
::EXIT
