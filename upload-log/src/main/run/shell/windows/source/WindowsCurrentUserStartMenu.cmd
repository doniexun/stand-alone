@ECHO OFF
::SetLocal EnableDelayedExpansion
FOR /f "tokens=2,*" %%i IN ('REG QUERY "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders" /v "Start Menu"') DO (
    SET Start=%%j
)
@SET WINDOWS_CURRENT_USER_START_MENU=%Start%
ECHO WINDOWS_CURRENT_USER_START_MENU=%WINDOWS_CURRENT_USER_START_MENU%
::EndLocal
