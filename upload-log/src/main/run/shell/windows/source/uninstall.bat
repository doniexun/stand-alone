@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.

TITLE ��װ����

Echo  ��ǰʱ�� �� %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

@REM ================================ Set Env ================================
ECHO / ===================================== ��ʼ���������� ===================================== \
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

SET SHORTCUT_NAME=�ȵ���ά����
ECHO \ ===================================== ��ʼ���������� ===================================== /
ECHO.





@REM ================================ Create Shortcut ================================
ECHO / ===================================== ɾ����ݷ�ʽ ===================================== \
RMDIR /S /Q "!WINDOWS_CURRENT_USER_PROGRAMS!\!SHORTCUT_NAME!"
If Not ErrorLevel 0 (
    Echo     ɾ����ʼ�˵���ݷ�ʽʧ��
    MSG %UserName% /server:127.0.0.1 "'%~1' ɾ����ʼ�˵���ݷ�ʽʧ��"
    GoTo eof
)

DEL /F /S /Q "!WINDOWS_CURRENT_USER_DESKTOP!\!SHORTCUT_NAME!.lnk"
If Not ErrorLevel 0 (
    Echo     ɾ�������ݷ�ʽʧ��
    MSG %UserName% /server:127.0.0.1 "'%~1' ɾ�������ݷ�ʽʧ��"
    GoTo eof
)
ECHO \ ===================================== ɾ����ݷ�ʽ ===================================== /
Echo.





Echo     ����ж�سɹ�
MSG %UserName% /server:127.0.0.1 /time:7 "����ж�����"
"!APP_HOME!\bin\sleep" 3000
EndLocal & Echo. & GoTo :eof

:msg_env_load_fail
    Echo     '%~1' ����ʧ��
    MSG %UserName% /server:127.0.0.1 "'%~1' ����ʧ��"
    GoTo :EOF
