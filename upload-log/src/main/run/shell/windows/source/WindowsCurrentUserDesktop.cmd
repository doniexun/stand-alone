@ECHO OFF
FOR /f "tokens=2,*" %%i IN ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders" /v "Desktop"') DO (
    SET Desk=%%j
)
@SET WINDOWS_CURRENT_USER_DESKTOP=%Desk%
ECHO WINDOWS_CURRENT_USER_DESKTOP=%WINDOWS_CURRENT_USER_DESKTOP%
::ECHO ×ÀÃæÂ·¾¶ÊÇ%WINDOWS_CURRENT_USER_DESKTOP%
::PAUSE >nul
