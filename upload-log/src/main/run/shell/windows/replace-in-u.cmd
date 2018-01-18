@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title 替换 U 盘文件及目录……

echo.


SET LONG_BIT=%~1
SET FROM=%~2
SET TO=%~3


Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

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


SET CLASSPATH=.;%JAVA_HOME%\lib\rt.jar;%APP_HOME%\conf\log4j.properties
For /R "%APP_HOME%\lib" %%f In (*) do (
    ::Echo     %%f
    Set CLASSPATH=!CLASSPATH!;%%f
)

ECHO.

@REM Check
If Not EXIST "%FROM%" (
    Echo.
    Echo     目录[%FROM%]不存在
    Echo.
    EXIT /B 404
)

@REM Copy
For /F %%i In ('dir /a/b/on "%FROM%"') do (
    IF EXIST "%TO%\%%~nxi" (
        If EXIST "%TO%\%%~nxi\" (
            RMDIR /S /Q "%TO%\%%~nxi"
            If ERRORLEVEL 1 (
                Echo     目录[%TO%\%%~nxi]删除失败
                EXIT /B 503
            )
        ) Else (
            DEL /F /S /Q "%TO%\%%~nxi"
            If ERRORLEVEL 1 (
                Echo     文件[%TO%\%%~nxi]删除失败
                EXIT /B 503
            )
        )
    )
)

Echo.
Echo.

"%APP_HOME%\bin\copy-tree" "%FROM%" "%TO%"
If ERRORLEVEL 1 (
    Echo     复制文件[%%~fi]到[%TO%]失败
    EXIT /B 503
)


Title 替换 U 盘文件及目录完毕
::PAUSE

:file_end
endlocal
