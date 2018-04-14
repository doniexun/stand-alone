@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS & ECHO.

TITLE ��װ����

Echo  ��ǰʱ�� �� %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

IF "!APP_HOME!" == "" (
    ECHO "APP_HOME û������"
    GOTO eof
)
IF "!APP_HOME:~-1!" == "\" (
    SET APP_HOME=!APP_HOME:~0,-1!
)
ECHO APP_HOME          = !APP_HOME!

if /i %PROCESSOR_IDENTIFIER:~0,3%==x86 (
	SET LONG_BIT=32
) else (
	SET LONG_BIT=64
)


@REM ================================ Create dir ================================
ECHO ���ڴ�����Ҫ��Ŀ¼����
If NOT EXIST "!APP_HOME!\logs\" (
    MKDIR "!APP_HOME!\logs\"
)
If NOT EXIST "!APP_HOME!\temp\upload\standalone_v1\" (
    MKDIR "!APP_HOME!\temp\upload\standalone_v1\"
)
If NOT EXIST "!APP_HOME!\temp\upload\standalone_v3\" (
    MKDIR "!APP_HOME!\temp\upload\standalone_v3\"
)
If NOT EXIST "!APP_HOME!\data\upload\standalone_v1\" (
    MKDIR "!APP_HOME!\data\upload\standalone_v1\"
)
If NOT EXIST "!APP_HOME!\data\upload\standalone_v3\" (
    MKDIR "!APP_HOME!\data\upload\standalone_v3\"
)
If NOT EXIST "!APP_HOME!\backup\upload\standalone_v1\" (
    MKDIR "!APP_HOME!\backup\upload\standalone_v1\"
)
If NOT EXIST "!APP_HOME!\backup\upload\standalone_v3\" (
    MKDIR "!APP_HOME!\backup\upload\standalone_v3\"
)
ECHO Ŀ¼������ϡ�
ECHO.


@REM ================================ Install JRE ================================
SET JRE_HOME=!APP_HOME!\java\jre

Echo ���ڰ�װ JRE ���� ����
If EXIST "!JRE_HOME!" (
    If NOT EXIST "!JRE_HOME!\" (
        DEL /F /Q /S "!JRE_HOME!"
    )
)

If NOT EXIST "!JRE_HOME!\" (
    MKDIR "!JRE_HOME!\"
)

SET JRE_ZIP_TEMP=!APP_HOME!\temp\jre.zip
if /i %LONG_BIT%==32 (
    "!APP_HOME!\bin\wget" "http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x86.zip" -O "!JRE_ZIP_TEMP!"
    If ERRORLEVEL 1 GoTo download_jre_fail
) else (
    "!APP_HOME!\bin\wget" "http://redian-produce.oss-cn-beijing.aliyuncs.com/soft/jre/windows/jre1.8.0_05_x64.zip" -O "!JRE_ZIP_TEMP!"
    If ERRORLEVEL 1 GoTo download_jre_fail
)

"!APP_HOME!\bin\7z" x "!JRE_ZIP_TEMP!" -o"!JRE_HOME!" -aoa
If ERRORLEVEL 1 GoTo jre_fail
DEL "!JRE_ZIP_TEMP!"
Echo JRE ������װ��ϡ�
Echo.

ECHO ������ݷ�ʽ����
CALL "!APP_HOME!\bin\shortcut" "!APP_HOME!\STB.exe" "�ȵ���ά����" "!APP_HOME!" "�����ȵ���Ϣ�Ƽ����޹�˾��ά����" ""startup""
ECHO ��ݷ�ʽ������ϡ�

GoTo end

:download_jre_fail
Echo     ���� JRE ��װ��ʧ��
Echo.
GoTo eof

:jre_fail
Echo     JRE ������װʧ��
Echo.
GoTo eof

:end
Echo     ����װ�ɹ�
Echo.
GoTo eof

:eof
PAUSE
::EXIT /B 0
EXIT
