@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title �����ƶ��ļ� ����
Echo �����ƶ��ļ� ����

SET LONG_BIT=%~1
::SET SOURCE_PATH=h:\log
SET SOURCE_PATH=%~2
SET TARGET_PATH=%~3


@REM ========== Env ==========
::Echo The Env :
SET SCRIPT_DIR=%~dp0
if "%SCRIPT_DIR:~-1%" == "\" (
    SET SCRIPT_DIR=%SCRIPT_DIR:~0,-1%
)
::echo 	SCRIPT_DIR        = %SCRIPT_DIR%

for %%i in (%SCRIPT_DIR%.) do (
    SET APP_HOME=%%~dpi
)
if "%APP_HOME:~-1%" == "\" (
    SET APP_HOME=%APP_HOME:~0,-1%
)
::echo 	APP_HOME          = %APP_HOME%

Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

IF NOT EXIST "%TARGET_PATH%\" (
    Echo.
    Echo     Ŀ��Ŀ¼ '%TARGET_PATH%' ������
    Echo.
    GoTo eof
)

For %%i in (%SOURCE_PATH%\) do (
    SET LOG_PATH_IN_USB=%%~pi
)

For /F "Skip=1" %%i In ('Wmic Logicaldisk Where "DriveType=2" Get DeviceID') Do (
    Set DeviceID=%%i
    If EXIST "!DeviceID!/" (
        If Exist "!DeviceID!!LOG_PATH_IN_USB!" (
            Echo     ��־ԴĿ¼ [!DeviceID!!LOG_PATH_IN_USB!]
            Call "%APP_HOME%\bin\move" "!LONG_BIT!" "!DeviceID!!LOG_PATH_IN_USB!" "%TARGET_PATH%"
            Echo.
        ) Else (
            Set NotFoundUSBPath=!NotFoundUSBPath!, !DeviceID!!LOG_PATH_IN_USB!
        )
    )
)


Echo.
Echo �����ڵ�ԴĿ¼�У�
Echo     [!NotFoundUSBPath!]
Echo.

Goto eof


:not_found_usb
Echo.
Echo     û�з��� USB �洢�豸
Echo.
Exit /B 503
GoTo eof


:eof
Echo.
Echo �� USB �洢�豸�ƶ��ļ��� '%TARGET_PATH%' ���
Echo.
endlocal
