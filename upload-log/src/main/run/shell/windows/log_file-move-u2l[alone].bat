@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS

@REM �ֶ����ÿ�ʼ
Set SOURCE_PATH=H:\log
Set TARGET_PATH=F:\test_logs
@REM �ֶ����ý���


Title �����ƶ��ļ� ����
Echo �����ƶ��ļ� ����

For /R "%SOURCE_PATH%" %%f In (*) do (
    Echo %%f
    Move /Y "%%f" "%TARGET_PATH%"
    If ERRORLEVEL 1 GoTo move_fail
)
GoTo success


:success
Echo.
Echo.
Echo �Ѿ���Ŀ¼��%SOURCE_PATH%���е��ļ��ƶ���Ŀ¼��%TARGET_PATH%�������
Echo.
GoTo eof


:move_fail
Echo.
Echo.
Echo �ļ��ƶ���ʧ��
Echo.
GoTo eof


:eof
Title �ļ��ƶ����
Pause
endlocal
