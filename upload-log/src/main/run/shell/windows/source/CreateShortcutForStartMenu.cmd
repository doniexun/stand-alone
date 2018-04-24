@ECHO OFF & SetLocal EnableDelayedExpansion
::设置程序或文件的完整路径（必选/必填）
SET Program=%~1

::设置快捷方式名称（必选/必填）
SET LnkName=%~2

::设置程序的工作路径（必填）。一般为程序主目录，此项若留空，脚本将自行分析路径
SET WorkDir=%~3

::设置快捷方式显示的说明（/必填）
SET Desc=%~4

::设置程序对应的参数
SET ProgramParameter=%~5


SET VBS_SCRIPT_NAME=%Temp%\makeLnk.vbs

IF NOT DEFINED WorkDir call:GetWorkDir "%Program%"
(ECHO Set WshShell=CreateObject("WScript.Shell"^)
ECHO strDesktop=WshShell.SpecialFolders("Programs"^)
ECHO Set fso=CreateObject("Scripting.FileSystemObject"^)
ECHO lnkFileName=strDesktop^&"\%LnkName%.lnk"
ECHO lnkFilePath=left(lnkFileName, instrrev(lnkFileName, "\"^)^)
ECHO If fso.folderExists(lnkFilePath^) Then
ECHO Else
ECHO 	fso.createFolder(lnkFilePath^)
ECHO End If
ECHO Set operatingSystemShellLink=WshShell.CreateShortcut(lnkFileName^)
ECHO operatingSystemShellLink.TargetPath="%Program%"
ECHO operatingSystemShellLink.Arguments="%ProgramParameter%"
ECHO operatingSystemShellLink.WorkingDirectory="%WorkDir%"
ECHO operatingSystemShellLink.WindowStyle=1
ECHO operatingSystemShellLink.Description="%Desc%"
ECHO operatingSystemShellLink.Save) > "!VBS_SCRIPT_NAME%!"
If ErrorLevel 1 (
    Exit /B 1
)

Call "!VBS_SCRIPT_NAME%!"
If ErrorLevel 1 (
    DEL /f /q "!VBS_SCRIPT_NAME%!"
    Exit /B 1
)

DEL /f /q "!VBS_SCRIPT_NAME%!"
If ErrorLevel 1 (
    Exit /B 1
)

EndLocal & GOTO :eof

:GetWorkDir
SET WorkDir=%~dp1
SET WorkDir=%WorkDir:~,-1%
GOTO :eof
