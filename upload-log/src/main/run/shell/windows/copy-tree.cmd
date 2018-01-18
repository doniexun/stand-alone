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
    Echo     源目录[%SOURCE%]不存在
    Echo.
    EXIT /B 404
)

If Not Exist "%TARGET%\" (
    Echo.
    Echo     目标目录[%TARGET%]不存在
    Echo.
    EXIT /B 404
)


For /F %%i In ('dir /a/b/on "%SOURCE%"') do (
    If Exist "%SOURCE%\%%~nxi\" (
        If Exist "%TARGET%\%%~nxi" (
            If Not Exist "%TARGET%\%%~nxi\" (
                Del /F /S /Q "%TARGET%\%%~nxi"
                If ERRORLEVEL 1 (
                    Echo     删除文件[%TARGET%\%%~nxi]失败
                    Exit /B 503
                )
                MKDir "%TARGET%\%%~nxi\"
                If ERRORLEVEL 1 (
                    Echo     创建目录[%TARGET%\%%~nxi]失败
                    Exit /B 503
                )
            )
        ) Else (
            MKDir "%TARGET%\%%~nxi\"
            If ERRORLEVEL 1 (
                Echo     创建目录[%TARGET%\%%~nxi]失败
                Exit /B 503
            )
        )
        Call "%APP_HOME%\bin\copy-tree" "%SOURCE%\%%~nxi" "%TARGET%\%%~nxi"
        If ERRORLEVEL 1 (
            Exit /B 504
        )
    ) Else (
        Echo     正在将文件[%SOURCE%\%%~nxi]复制到[%TARGET%] ……
        Copy /V /Y "%SOURCE%\%%~nxi" "%TARGET%"
        If ERRORLEVEL 1 (
            Echo     复制文件[%%~fi]到[%TO%]失败
            Exit /B 503
        )
    )

)

Echo.

