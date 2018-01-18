@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE ��OSS�����ļ�������

Echo  ��ǰ���� �� %date:~0,4%-%date:~5,2%-%date:~8,2%
Echo  ��ǰʱ�� �� %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.


SET SBIN_DIR=%~dp0
if "%SBIN_DIR:~-1%" == "\" (
  set SBIN_DIR=%SBIN_DIR:~0,-1%
)
::echo SBIN_DIR          = %SBIN_DIR%

for %%i in (%SBIN_DIR%.) do (
  set SBIN_DIR=%%~dpi
)
if "%SBIN_DIR:~-1%" == "\" (
  set APP_HOME=%SBIN_DIR:~0,-1%
)
::echo APP_HOME          = %APP_HOME%


@REM Read config file
Call "%APP_HOME%\bin\read_config" "%APP_HOME%\conf\app.ini"

If "%com.littlehotspot.alone.logs.app.dir.data%" == "" (
	SET U_PATH=%APP_HOME%\data
) Else (
	SET U_PATH=%com.littlehotspot.alone.logs.app.dir.data%
)
SET U_PATH=!U_PATH!\U


Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

@REM Download log-file to OSS
For /F "Skip=1" %%i In ('Wmic Logicaldisk Where "DriveType=2" Get DeviceID') Do (
    Set DeviceID=%%i
    If EXIST "!DeviceID!/" (
        Call "%APP_HOME%\bin\replace-in-u" "%LONG_BIT%" "%U_PATH%" "!DeviceID!"
        If ERRORLEVEL 1 (
            Echo.
            Echo     U ��[!DeviceID!]����ʧ��
            Echo.
            GoTo eof
        )
    )
)

Echo.
Echo ������U���ļ��������
Echo.
GoTo eof

:not_found_usb
Echo.
Echo     û�з��� USB �洢�豸
Echo.
GoTo eof

:eof
PAUSE
endlocal
