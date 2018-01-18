@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title �ϴ��ļ��� OSS �洢�С���

echo.


SET LONG_BIT=%~1
SET FROM=%~2
SET TEMP=%~3
SET BACKUP=%~4
SET OSS_BUCKET_NAME=%~5
SET OSS_KEY_PREFIX=%~6
SET AREA_PATH_URL=%~7

SET MAIN_CLASS=cn.savor.standalone.log.Main

@REM ========== Env ==========
Echo The Env :
SET SCRIPT_DIR=%~dp0
if "%SCRIPT_DIR:~-1%" == "\" (
    SET SCRIPT_DIR=%SCRIPT_DIR:~0,-1%
)
echo 	SCRIPT_DIR        = %SCRIPT_DIR%

for %%i in (%SCRIPT_DIR%.) do (
    SET APP_HOME=%%~dpi
)
if "%APP_HOME:~-1%" == "\" (
    SET APP_HOME=%APP_HOME:~0,-1%
)
echo 	APP_HOME          = %APP_HOME%

::if /i %LONG_BIT%==32 (
::	SET JAVA_HOME=%APP_HOME%\jre\jre1.8.0_05_x86.zip
::) else (
::	SET JAVA_HOME=%APP_HOME%\jre\jre1.8.0_05_x64.zip
::)
SET JAVA_HOME=%APP_HOME%\temp\jre
echo 	JAVA_HOME         = %JAVA_HOME%

SET CLASSPATH=.;%JAVA_HOME%\lib\rt.jar;%APP_HOME%\conf\log4j.properties
For /R "%APP_HOME%\lib" %%f In (*) do (
    ::Echo     %%f
    Set CLASSPATH=!CLASSPATH!;%%f
)

ECHO.


@REM ========== Arguments ==========
ECHO The Arguments :
::"%JAVA_HOME%\bin\java" -Xbootclasspath/a:"%CLASSPATH%" -jar "%APP_HOME%\run-jars\upload-log-1.0.0.0.1-SNAPSHOT.jar" upload "fromDir=%FROM%" "tempDir=%TEMP%" "bucketName=%OSS_BUCKET_NAME%" "keyPrefix=%OSS_KEY_PREFIX%"
"%JAVA_HOME%\bin\java" "%MAIN_CLASS%" upload "fromDir=%FROM%" "tempDir=%TEMP%" "backupDir=%BACKUP%" "bucketName=%OSS_BUCKET_NAME%" "keyPrefix=%OSS_KEY_PREFIX%" "areaUrl=%AREA_PATH_URL%"
If ERRORLEVEL 1 (
	Echo     �ļ��ϴ�ʧ��
	EXIT /B 503
)

Title �ļ��ϴ��� OSS �洢���
::PAUSE
endlocal
