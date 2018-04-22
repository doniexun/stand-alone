@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.

SET CUR_DIRECTORY=%~dp0
SET CUR_FILE_MAIN_NAME=%~n0
SET VERBOSE=%~1

SHIFT /0

IF "!CUR_DIRECTORY:~-1!" == "\" (
    SET APP_HOME=!CUR_DIRECTORY:~0,-1!
)
@SET APP_HOME=!APP_HOME!
If "%VERBOSE%" == "true" Echo APP_HOME=!APP_HOME!

IF "%~1" == "" (
    SET COMMAND=install
) ELSE (
    SET COMMAND=%~1
)
If "%VERBOSE%" == "true" Echo COMMAND=!COMMAND!

SHIFT /0
ECHO show %APP_HOME%  %CUR_FILE_MAIN_NAME% %COMMAND% %*

:run
START "" "!APP_HOME!\bin\!COMMAND!"

EndLocal
pause