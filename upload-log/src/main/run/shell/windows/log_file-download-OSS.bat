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
	SET DATA_PATH=%APP_HOME%\data
) Else (
	SET DATA_PATH=%com.littlehotspot.alone.logs.app.dir.data%
)
Set LOCAL_FILEPATH=!DATA_PATH!\download
Set DECOM_FILEPATH=!DATA_PATH!\decompression

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

    If %date:~8,2% LEQ 15 (
	  SET OSS_KEY_PREFIX=!OSS_KEY_PREFIX!/%HOTEL_AREA%/%date:~0,4%%date:~5,2%[01-16]
	) Else if %date:~8,2% GEQ 16 (
	  SET OSS_KEY_PREFIX=!OSS_KEY_PREFIX!/%HOTEL_AREA%/%date:~0,4%%date:~5,2%[16-32]
	) Else (
	  SET OSS_KEY_PREFIX=!OSS_KEY_PREFIX!/%HOTEL_AREA%/%date:~0,4%%date:~5,2%%date:~8,2%
	)
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

    If %date:~8,2% LEQ 15 (
	  SET OSS_KEY_BIBASIC_PREFIX=!OSS_KEY_BIBASIC_PREFIX!/{}/%date:~0,4%%date:~5,2%[01-16]
	) Else if %date:~8,2% GEQ 16 (
	  SET OSS_KEY_BIBASIC_PREFIX=!OSS_KEY_BIBASIC_PREFIX!/{}/%date:~0,4%%date:~5,2%[16-32]
	) Else (
	  SET OSS_KEY_BIBASIC_PREFIX=!OSS_KEY_BIBASIC_PREFIX!/{}/%date:~0,4%%date:~5,2%%date:~8,2%
	)
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



@REM Download log-file to OSS
Call "%APP_HOME%\bin\download" "%LONG_BIT%" "%LOCAL_FILEPATH%\generation" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX%"
::START "��OSS�����ļ�������" %APP_HOME%\bin\download "%FILEPATH%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX%"
If ERRORLEVEL 1 (
	Echo     �ļ�����ʧ��
	GoTo eof
)

Call "%APP_HOME%\bin\download" "%LONG_BIT%" "%LOCAL_FILEPATH%\bibasic" "%OSS_BUCKET_NAME%" "%OSS_KEY_BIBASIC_PREFIX%" "%AREA_URL%"
::START "��OSS�����ļ�������" %APP_HOME%\bin\download "%FILEPATH%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX%"
If ERRORLEVEL 1 (
	Echo     �ļ�����ʧ��
	GoTo eof
)

TITLE �ļ��������
Echo �ļ��������
Echo.

Echo.
Call "%APP_HOME%\bin\decompression_files" "%LONG_BIT%" "%LOCAL_FILEPATH%\generation" "%DECOM_FILEPATH%\generation"
If ERRORLEVEL 1 (
	GoTo eof
)

Call "%APP_HOME%\bin\decompression_files" "%LONG_BIT%" "%LOCAL_FILEPATH%\bibasic" "%DECOM_FILEPATH%\bibasic"
If ERRORLEVEL 1 (
	GoTo eof
)

Echo.
Echo �ļ��� OSS ���أ�����ѹ���
Echo.



:eof
PAUSE
endlocal
