@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title ���ڸ����ļ� ����
Echo ���ڸ����ļ� ����

SET LONG_BIT=%~1
SET SOURCE_PATH=%~2
SET TARGET_PATH=%~3


Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

IF NOT EXIST "%SOURCE_PATH%\" (
    Echo.
    Echo     ԴĿ¼ '%SOURCE_PATH%' ������
    Echo.
    GoTo eof
)

IF NOT EXIST "%TARGET_PATH%\" (
    Echo.
    Echo     Ŀ��Ŀ¼ '%TARGET_PATH%' ������
    Echo.
    GoTo eof
)

Set "ne=standalone"
For /R "%SOURCE_PATH%" %%f In (*) do (
     Set FILE_ITEM=%%f
     Set TARGET_PATH_TEMP=%TARGET_PATH%\standalone_v3
     Echo     !FILE_ITEM!
     echo %%~nf|findStr /v "%ne%" && Set TARGET_PATH_TEMP=%TARGET_PATH%\standalone_v1
     Copy /Y "!FILE_ITEM!" "!TARGET_PATH_TEMP!"
     If ERRORLEVEL 1 GoTo move_fail "!FILE_ITEM!"
)
GoTo move_success


:not_found_usb
Echo.
Echo     û�з��� USB �洢�豸
Echo.
Exit /B 503
GoTo end


:move_success
Echo.
Echo     �Ѿ���Ŀ¼��%SOURCE_PATH%���е��ļ����Ƶ�Ŀ¼��%TARGET_PATH%�������
Echo.
GoTo end


:move_fail
Echo.
Echo     �ļ� '!FILE_ITEM!' ����ʧ��
Echo.
Exit /B 501
GoTo end

:end
Title �ļ��������

:eof
endlocal
