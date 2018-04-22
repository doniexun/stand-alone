@ECHO OFF
::SetLocal EnableDelayedExpansion
FOR /f "tokens=2,*" %%i IN ('REG QUERY "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders" /v "Startup"') DO (
    SET Start=%%j
)
@SET WINDOWS_CURRENT_USER_STARTUP=%Start%
ECHO WINDOWS_CURRENT_USER_STARTUP=%WINDOWS_CURRENT_USER_STARTUP%
::EndLocal
