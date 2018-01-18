@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE ��OSS�����ļ�������

Echo  ��ǰ���� �� %date:~0,4%-%date:~5,2%-%date:~8,2%
Echo  ��ǰʱ�� �� %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.



SET SBIN_DIR=%~dp0
if "%SBIN_DIR:~-1%" == "\" (
  set SBIN_DIR=%SBIN_DIR:~0,-1%
)

for %%i in (%SBIN_DIR%.) do (
  set SBIN_DIR=%%~dpi
)
if "%SBIN_DIR:~-1%" == "\" (
  set APP_HOME=%SBIN_DIR:~0,-1%
)


@REM Read config file
Call "%APP_HOME%\bin\read_config" "%APP_HOME%\conf\app.ini"

SET /P HOTEL_ID=�������¥ID:

If "%com.littlehotspot.alone.logs.app.dir.data%" == "" (
	SET U_PATH=%APP_HOME%\data
) Else (
	SET U_PATH=%com.littlehotspot.alone.logs.app.dir.data%
)
SET U_PATH=!U_PATH!\U

If "%com.littlehotspot.alone.logs.time.sync.url%" == "" (
	Echo ��ȡ����ʱ��� URL ��ַû�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.time.sync.url'
	GoTo eof
) Else (
	SET TIME_SYNC_URL=%com.littlehotspot.alone.logs.time.sync.url%
)

If "%com.littlehotspot.alone.media.path.list.url%" == "" (
	Echo ��ȡ��¥��Ŀ�� URL ��ַû�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.media.path.list.url'
	GoTo eof
) Else (
	SET ITEM_LIST_URL=%com.littlehotspot.alone.media.path.list.url%
)

If "%com.littlehotspot.alone.logs.oss.bucket.name%" == "" (
	Echo OSS Ͱ��û�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.oss.bucket.name'
	GoTo eof
) Else (
	SET OSS_BUCKET_NAME=%com.littlehotspot.alone.logs.oss.bucket.name%
)

If "%com.littlehotspot.alone.logs.oss.key.media.prefix%" == "" (
	Echo OSS ��Դ����ǰ׺û�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.oss.key.media.prefix'
	GoTo eof
) Else (
	  Set OSS_KEY_PREFIX_MEDIA=%com.littlehotspot.alone.logs.oss.key.media.prefix%
)


@Rem Detecting network connectivity
Call "%APP_HOME%\bin\detecting_network_connectivity"
If ERRORLEVEL 1 (
	Echo     �����û�����ӵ�������
	GoTo eof
)


@Rem Synchronous OS time from Internet
cscript /nologo "%APP_HOME%\bin\synchronous_time_from_internet.vbs" "%TIME_SYNC_URL%"
If ERRORLEVEL 1 (
	Echo     ʱ��У׼ʧ��
	GoTo eof
)


@REM Download log-file to OSS
Call "%APP_HOME%\bin\createfile" "%LONG_BIT%" "%HOTEL_ID%" "%U_PATH%" "%ITEM_LIST_URL%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX_MEDIA%"
If ERRORLEVEL 1 (
	Echo     �ļ�����ʧ��
	GoTo eof
)
GoTo check_usb_disk


:not_found_usb
Echo.
Echo     û�з��� USB �洢�豸
Echo.
GoTo eof

:check_usb_disk
Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb



@REM Replace U disk from u_path
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

RMDir /S /Q "%U_PATH%"
Goto eof



:eof
PAUSE
endlocal
