@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE 从OSS下载文件到本地

Echo  当前日期 ： %date:~0,4%-%date:~5,2%-%date:~8,2%
Echo  当前时间 ： %time:~0,2%:%time:~3,2%:%time:~6,2%
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

SET /P HOTEL_ID=请输入酒楼ID:

If "%com.littlehotspot.alone.logs.app.dir.data%" == "" (
	SET U_PATH=%APP_HOME%\data
) Else (
	SET U_PATH=%com.littlehotspot.alone.logs.app.dir.data%
)
SET U_PATH=!U_PATH!\U

If "%com.littlehotspot.alone.logs.time.sync.url%" == "" (
	Echo 获取网络时间的 URL 地址没有配置，请到 'conf\app.ini' 中配置 'com.littlehotspot.alone.logs.time.sync.url'
	GoTo eof
) Else (
	SET TIME_SYNC_URL=%com.littlehotspot.alone.logs.time.sync.url%
)

If "%com.littlehotspot.alone.media.path.list.url%" == "" (
	Echo 获取酒楼节目单 URL 地址没有配置，请到 'conf\app.ini' 中配置 'com.littlehotspot.alone.media.path.list.url'
	GoTo eof
) Else (
	SET ITEM_LIST_URL=%com.littlehotspot.alone.media.path.list.url%
)


If "%com.littlehotspot.alone.logs.oss.bucket.name%" == "" (
	Echo OSS 桶名没有配置，请到 'conf\app.ini' 中配置 'com.littlehotspot.alone.logs.oss.bucket.name'
	GoTo eof
) Else (
	SET OSS_BUCKET_NAME=%com.littlehotspot.alone.logs.oss.bucket.name%
)

If "%com.littlehotspot.alone.logs.oss.key.media.prefix%" == "" (
	Echo OSS 资源对象前缀没有配置，请到 'conf\app.ini' 中配置 'com.littlehotspot.alone.logs.oss.key.media.prefix'
	GoTo eof
) Else (
	  Set OSS_KEY_PREFIX_MEDIA=%com.littlehotspot.alone.logs.oss.key.media.prefix%
)


@Rem Detecting network connectivity
Call "%APP_HOME%\bin\detecting_network_connectivity"
If ERRORLEVEL 1 (
	Echo     计算机没有连接到互联网
	GoTo eof
)


@Rem Synchronous OS time from Internet
::Call "%APP_HOME%\bin\synchronous_time_from_internet.vbs" "%TIME_SYNC_URL%"
cscript /nologo "%APP_HOME%\bin\synchronous_time_from_internet.vbs" "%TIME_SYNC_URL%"
::Echo %ERRORLEVEL%
If ERRORLEVEL 1 (
	Echo     时间校准失败
	GoTo eof
)



::@REM Resync os time
::Call "%APP_HOME%\bin\resync"



@REM Download log-file to OSS
Call "%APP_HOME%\bin\createfile" "%LONG_BIT%" "%HOTEL_ID%" "%U_PATH%" "%ITEM_LIST_URL%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX_MEDIA%"
::START "开始制作机顶盒U盘文件" "%APP_HOME%\bin\createfile" "%LONG_BIT%" "%HOTEL_ID%" "%U_PATH%" "%ITEM_LIST_URL%" "%OSS_BUCKET_NAME%" "%OSS_KEY_PREFIX_MEDIA%"
If ERRORLEVEL 1 (
	Echo     文件制作失败
	GoTo eof
)

Echo.
Echo 机顶盒U盘文件制作完成
Echo.



:eof
PAUSE
endlocal
