@ECHO OFF
::设置程序或文件的完整路径（必选）
SET Program=%~1

::设置快捷方式名称（必选）
SET LnkName=%~2

::设置程序的工作路径，一般为程序主目录，此项若留空，脚本将自行分析路径
SET WorkDir=%~3

::设置快捷方式显示的说明
SET Desc=%~4

SET NewParameter=%~5

IF NOT DEFINED WorkDir call:GetWorkDir "%Program%"
(ECHO Set WshShell=CreateObject("WScript.Shell"^)
ECHO strDesKtop=WshShell.SpecialFolders("DesKtop"^)
ECHO Set oShellLink=WshShell.CreateShortcut(strDesKtop^&"\%LnkName%.lnk"^)
ECHO oShellLink.TargetPath="%Program%"
ECHO oShellLink.Arguments=%NewParameter%
ECHO oShellLink.WorkingDirectory="%WorkDir%"
ECHO oShellLink.WindowStyle=1
ECHO oShellLink.Description="%Desc%"
ECHO oShellLink.Save) > makelnk.vbs
ECHO 桌面快捷方式创建成功！
makelnk.vbs
del /f /q makelnk.vbs
GOTO :eof

:GetWorkDir
SET WorkDir=%~dp1
SET WorkDir=%WorkDir:~,-1%
GOTO :eof
