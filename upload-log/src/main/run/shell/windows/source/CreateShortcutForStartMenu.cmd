@ECHO OFF
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

IF NOT DEFINED WorkDir call:GetWorkDir "%Program%"
(ECHO Set WshShell=CreateObject("WScript.Shell"^)
ECHO strDesktop=WshShell.SpecialFolders("Programs"^)
ECHO Set operatingSystemShellLink=WshShell.CreateShortcut(strDesktop^&"\%LnkName%.lnk"^)
ECHO operatingSystemShellLink.TargetPath="%Program%"
ECHO operatingSystemShellLink.Arguments=%ProgramParameter%
ECHO operatingSystemShellLink.WorkingDirectory="%WorkDir%"
ECHO operatingSystemShellLink.WindowStyle=1
ECHO operatingSystemShellLink.Description="%Desc%"
ECHO operatingSystemShellLink.Save) > makeLnk.vbs
::ECHO 开始菜单快捷方式创建成功！
makeLnk.vbs
del /f /q makeLnk.vbs
GOTO :eof

:GetWorkDir
SET WorkDir=%~dp1
SET WorkDir=%WorkDir:~,-1%
GOTO :eof
