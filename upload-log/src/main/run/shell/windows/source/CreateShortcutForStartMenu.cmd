@ECHO OFF
::���ó�����ļ�������·������ѡ/���
SET Program=%~1

::���ÿ�ݷ�ʽ���ƣ���ѡ/���
SET LnkName=%~2

::���ó���Ĺ���·���������һ��Ϊ������Ŀ¼�����������գ��ű������з���·��
SET WorkDir=%~3

::���ÿ�ݷ�ʽ��ʾ��˵����/���
SET Desc=%~4

::���ó����Ӧ�Ĳ���
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
::ECHO ��ʼ�˵���ݷ�ʽ�����ɹ���
makeLnk.vbs
del /f /q makeLnk.vbs
GOTO :eof

:GetWorkDir
SET WorkDir=%~dp1
SET WorkDir=%WorkDir:~,-1%
GOTO :eof
