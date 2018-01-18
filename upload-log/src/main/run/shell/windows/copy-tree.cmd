@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Set SOURCE=%~1
Set TARGET=%~2

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

If Not Exist "%SOURCE%\" (
    Echo.
    Echo     ԴĿ¼[%SOURCE%]������
    Echo.
    EXIT /B 404
)

If Not Exist "%TARGET%\" (
    Echo.
    Echo     Ŀ��Ŀ¼[%TARGET%]������
    Echo.
    EXIT /B 404
)


For /F %%i In ('dir /a/b/on "%SOURCE%"') do (
    If Exist "%SOURCE%\%%~nxi\" (
        If Exist "%TARGET%\%%~nxi" (
            If Not Exist "%TARGET%\%%~nxi\" (
                Del /F /S /Q "%TARGET%\%%~nxi"
                If ERRORLEVEL 1 (
                    Echo     ɾ���ļ�[%TARGET%\%%~nxi]ʧ��
                    Exit /B 503
                )
                MKDir "%TARGET%\%%~nxi\"
                If ERRORLEVEL 1 (
                    Echo     ����Ŀ¼[%TARGET%\%%~nxi]ʧ��
                    Exit /B 503
                )
            )
        ) Else (
            MKDir "%TARGET%\%%~nxi\"
            If ERRORLEVEL 1 (
                Echo     ����Ŀ¼[%TARGET%\%%~nxi]ʧ��
                Exit /B 503
            )
        )
        Call "%APP_HOME%\bin\copy-tree" "%SOURCE%\%%~nxi" "%TARGET%\%%~nxi"
        If ERRORLEVEL 1 (
            Exit /B 504
        )
    ) Else (
        Echo     ���ڽ��ļ�[%SOURCE%\%%~nxi]���Ƶ�[%TARGET%] ����
        Copy /V /Y "%SOURCE%\%%~nxi" "%TARGET%"
        If ERRORLEVEL 1 (
            Echo     �����ļ�[%%~fi]��[%TO%]ʧ��
            Exit /B 503
        )
    )

)

Echo.

