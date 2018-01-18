@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE �ϴ���־ѹ������ OSS

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
	SET LOCAL_FROM=%APP_HOME%\data
) Else (
	SET LOCAL_FROM=%com.littlehotspot.alone.logs.app.dir.data%
)
SET LOCAL_BACKUP=!LOCAL_FROM!\upload-bak\%date:~0,4%-%date:~5,2%-%date:~8,2%
SET LOCAL_FROM=!LOCAL_FROM!\upload

If "%com.littlehotspot.alone.logs.app.dir.temp%" == "" (
	SET LOCAL_TEMP=%APP_HOME%\temp
) Else (
	SET LOCAL_TEMP=%com.littlehotspot.alone.logs.app.dir.temp%
)

If "%com.littlehotspot.alone.logs.time.sync.url%" == "" (
	Echo ��ȡ����ʱ��� URL ��ַû�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.time.sync.url'
	GoTo eof
) Else (
	SET TIME_SYNC_URL=%com.littlehotspot.alone.logs.time.sync.url%
)

If "%com.littlehotspot.alone.logs.app.dir.area%" == "" (
	Echo ��������û�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.app.dir.area'
	GoTo eof
) Else (
	SET HOTEL_AREA=%com.littlehotspot.alone.logs.app.dir.area%
)

If "%com.littlehotspot.alone.area.path.url%" == "" (
	Echo ��ȡ���б���� URL ��ַû�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.area.path.url'
	GoTo eof
) Else (
	SET AREA_URL=%com.littlehotspot.alone.area.path.url%=%HOTEL_AREA%
)

If "%com.littlehotspot.alone.logs.oss.bucket.name%" == "" (
	Echo OSS Ͱ��û�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.oss.bucket.name'
	GoTo eof
) Else (
	SET OSS_BUCKET_NAME=%com.littlehotspot.alone.logs.oss.bucket.name%
)

If "%com.littlehotspot.alone.logs.oss.key.prefix%" == "" (
	Echo OSS ����ǰ׺û�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.oss.key.prefix'
	GoTo eof
) Else (
	If "%com.littlehotspot.alone.logs.oss.key.prefix:~-1%" == "\" (
	  Set OSS_KEY_PREFIX=%com.littlehotspot.alone.logs.oss.key.prefix:~0,-1%
	)
	If "%com.littlehotspot.alone.logs.oss.key.prefix:~-1%" == "/" (
	  Set OSS_KEY_PREFIX=%com.littlehotspot.alone.logs.oss.key.prefix:~0,-1%
	)
	SET OSS_KEY_PREFIX=!OSS_KEY_PREFIX!/%HOTEL_AREA%/%date:~0,4%%date:~5,2%%date:~8,2%
)

If "%com.littlehotspot.alone.logs.oss.key.bibasic.prefix%" == "" (
	Echo OSS ����ǰ׺û�����ã��뵽 'conf\app.ini' ������ 'com.littlehotspot.alone.logs.oss.key.bibasic.prefix'
	GoTo eof
) Else (
	If "%com.littlehotspot.alone.logs.oss.key.bibasic.prefix:~-1%" == "\" (
	  Set OSS_KEY_BIBASIC_PREFIX=%com.littlehotspot.alone.logs.oss.key.bibasic.prefix:~0,-1%
	)
	If "%com.littlehotspot.alone.logs.oss.key.bibasic.prefix:~-1%" == "/" (
	  Set OSS_KEY_BIBASIC_PREFIX=%com.littlehotspot.alone.logs.oss.key.bibasic.prefix:~0,-1%
	)
	SET OSS_KEY_BIBASIC_PREFIX=!OSS_KEY_BIBASIC_PREFIX!/{}/%date:~0,4%%date:~5,2%%date:~8,2%
)


@Rem Detecting network connectivity
Call "%APP_HOME%\bin\detecting_network_connectivity"
If ERRORLEVEL 1 (
	Echo     �����û�����ӵ�������
	GoTo eof
)


@Rem Synchronous OS time from Internet
::Call "%APP_HOME%\bin\synchronous_time_from_internet.vbs" "%TIME_SYNC_URL%"
cscript /nologo "%APP_HOME%\bin\synchronous_time_from_internet.vbs" "%TIME_SYNC_URL%"
::Echo %ERRORLEVEL%
If ERRORLEVEL 1 (
	Echo     ʱ��У׼ʧ��
	GoTo eof
)



::@REM Resync os time
::Call "%APP_HOME%\bin\resync"



@REM Upload log-file to OSS
Call "%APP_HOME%\bin\upload" "%LONG_BIT%" "%LOCAL_FROM%\generation" "%TEMP%\generation" "%LOCAL_BACKUP%\generation" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX%"
::START "�ϴ���־ѹ������ OSS" %APP_HOME%\bin\upload "%FROM%" "%TEMP%" "%LOCAL_BACKUP%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX%"
If ERRORLEVEL 1 (
	GoTo eof
)

Call "%APP_HOME%\bin\upload" "%LONG_BIT%" "%LOCAL_FROM%\bibasic" "%TEMP%\bibasic" "%LOCAL_BACKUP%\bibasic" "%OSS_BUCKET_NAME%" "%OSS_KEY_BIBASIC_PREFIX%" "%AREA_URL%"
::START "�ϴ���־ѹ������ OSS" %APP_HOME%\bin\upload "%FROM%" "%TEMP%" "%LOCAL_BACKUP%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX%"
If ERRORLEVEL 1 (
	GoTo eof
)

TITLE �ϴ���־ѹ������ OSS ���



:eof
PAUSE
endlocal
