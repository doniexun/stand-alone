@ECHO OFF
::���ó�����ļ�������·������ѡ��
SET Program=%~1

::���ÿ�ݷ�ʽ���ƣ���ѡ��
SET LnkName=%~2

::���ó���Ĺ���·����һ��Ϊ������Ŀ¼�����������գ��ű������з���·��
SET WorkDir=%~3

::���ÿ�ݷ�ʽ��ʾ��˵��
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
ECHO �����ݷ�ʽ�����ɹ���
makelnk.vbs
del /f /q makelnk.vbs
GOTO :eof

:GetWorkDir
SET WorkDir=%~dp1
SET WorkDir=%WorkDir:~,-1%
GOTO :eof
