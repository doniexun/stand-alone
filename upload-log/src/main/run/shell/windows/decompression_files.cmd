@ECHO OFF

Title 正在解压文件 ……
Echo 正在解压文件 ……

echo.


SET LONG_BIT=%~1
SET SOURCE_PATH=%~2
SET TARGET_PATH=%~3

IF NOT EXIST "%SOURCE_PATH%\" (
    Echo.
    Echo     源目录 '%SOURCE_PATH%' 不存在
    Echo.
    GoTo eof
)

IF NOT EXIST "%TARGET_PATH%\" (
    Echo.
    Echo     目标目录 '%TARGET_PATH%' 不存在
    Echo.
    GoTo eof
)

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

For /R "%SOURCE_PATH%" %%f In (*) do (
    Set FILE_ITEM=%%f
    Echo     !FILE_ITEM!
    "%APP_HOME%\bin\7z.exe" x "!FILE_ITEM!" -o"%TARGET_PATH%" -y
    If ERRORLEVEL 1 GoTo decompression_files_fail "!FILE_ITEM!"
)
GoTo decompression_files_success


:decompression_files_success
Echo.
Echo.
Echo 已经将目录“%SOURCE_PATH%”中的文件解压到目录“%TARGET_PATH%”，完成
Echo.
GoTo end


:decompression_files_fail
Echo.
Echo.
Echo 文件 '!FILE_ITEM!' 解压失败
Echo.
Exit /B 502
GoTo end

:end
Title 文件解压完毕

:eof
Echo.
