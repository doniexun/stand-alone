@ECHO OFF & SetLocal EnableDelayedExpansion
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
