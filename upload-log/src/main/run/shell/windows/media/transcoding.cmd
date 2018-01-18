@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title ����ת����Ƶ���롭��

echo.


SET LONG_BIT=%~1
SET SOURCE_PATH=%~2
SET TARGET_PATH=%~3
SET TARGET_SUFFIX=%~4

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

ECHO.


For /R "%SOURCE_PATH%" %%f In (*) do (
    Set FILE_ITEM=%%f
    Echo     !FILE_ITEM!
    "%APP_HOME%\bin\ffmpeg" -y -i "!FILE_ITEM!" -codec copy "%TARGET_PATH%\%%~nf%TARGET_SUFFIX%"
    If ERRORLEVEL 1 (
        Echo.
        Echo.
        Echo.
        Echo     ��Ƶ�ļ� [!FILE_ITEM!] ת��ʧ��
        Echo.
        Echo.
        EXIT /B 505
    )
)


Title ��Ƶ����ת�����
::PAUSE
endlocal
