@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.
FOR /f "tokens=2,*" %%i IN ('REG QUERY "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders" /v "Programs"') DO (
    SET Prog=%%j
)
@SET WINDOWS_CURRENT_USER_PROGRAMS=%Prog%
ECHO WINDOWS_CURRENT_USER_PROGRAMS=%WINDOWS_CURRENT_USER_PROGRAMS%
::ECHO 开始菜单路径是%WINDOWS_CURRENT_USER_STARTUP%
::PAUSE >nul
EndLocal
