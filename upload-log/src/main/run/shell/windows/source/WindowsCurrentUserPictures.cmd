@ECHO OFF
::SetLocal EnableDelayedExpansion
FOR /f "tokens=3,*" %%i IN ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders" /v "My Pictures"') DO (
    SET Pic=%%j
)
@SET WINDOWS_CURRENT_USER_PICTURES=%Pic%
ECHO WINDOWS_CURRENT_USER_PICTURES=%WINDOWS_CURRENT_USER_PICTURES%
::EndLocal
