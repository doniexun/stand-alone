@echo off

Title 新建机顶盒文件

echo.


SET LONG_BIT=%~1
SET HOTEL_ID=%~2
SET U_PATH=%~3
SET ITEM_LIST_URL=%~4
SET OSS_BUCKET_NAME=%~5
SET OSS_KEY_PREFIX_MEDIA=%~6


SET MAIN_CLASS=cn.savor.standalone.log.Main

@REM ========== Env ==========
Echo The Env :
SET SCRIPT_DIR=%~dp0
if "%SCRIPT_DIR:~-1%" == "\" (
  set SCRIPT_DIR=%SCRIPT_DIR:~0,-1%
)
echo 	SCRIPT_DIR        = %SCRIPT_DIR%

for %%i in (%SCRIPT_DIR%.) do (
  set APP_HOME=%%~dpi
)
if "%APP_HOME:~-1%" == "\" (
  set APP_HOME=%APP_HOME:~0,-1%
)
echo 	APP_HOME          = %APP_HOME%

::if /i %LONG_BIT%==32 (
::	SET JAVA_HOME=%APPLICATION_HOME%\jdk\jdk1.8.0_05_x86
::) else (
::	SET JAVA_HOME=%APPLICATION_HOME%\jdk\jdk1.8.0_05_x64
::)
SET JAVA_HOME=%APP_HOME%\temp\jre
echo 	JAVA_HOME         = %JAVA_HOME%

SET CLASSPATH=.;%JAVA_HOME%\lib\rt.jar;%APP_HOME%\conf\log4j.properties
For /R "%APP_HOME%\lib" %%f In (*) do (
    ::Echo     %%f
    Set CLASSPATH=!CLASSPATH!;%%f
)

ECHO.


@REM ========== Argumnets ==========
ECHO The Argumnets :
::"%JAVA_HOME%\bin\java" -Xbootclasspath/a:"%CLASSPATH%" -jar "%APP_HOME%\run-jars\upload-log-1.0.0.0.1-SNAPSHOT.jar" createfile "upath=%U_PATH%"  "bucketName=%OSS_BUCKET_NAME%" "keyPrefixMedia=%OSS_KEY_PREFIX_MEDIA%" "hotelId=%HOTEL_ID%" "itemlisturl=%ITEM_LIST_URL%"
"%JAVA_HOME%\bin\java" "%MAIN_CLASS%" createfile "upath=%U_PATH%" "bucketName=%OSS_BUCKET_NAME%" "keyPrefixMedia=%OSS_KEY_PREFIX_MEDIA%" "hotelId=%HOTEL_ID%" "itemlisturl=%ITEM_LIST_URL%"


Title 文件下载完毕
::PAUSE
