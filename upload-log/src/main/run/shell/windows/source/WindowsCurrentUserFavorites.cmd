@ECHO OFF
::SetLocal EnableDelayedExpansion
FOR /f "tokens=2,*" %%i IN ('reg query "HKCU\Software\Microsoft\Windows\CurrentVersion\Explorer\Shell Folders" /v "Favorites"') DO (
    SET Fav=%%j
)
@SET WINDOWS_CURRENT_USER_FAVORITES=%Fav%
ECHO WINDOWS_CURRENT_USER_FAVORITES=%WINDOWS_CURRENT_USER_FAVORITES%
::EndLocal
