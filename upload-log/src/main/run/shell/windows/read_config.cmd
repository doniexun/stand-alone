@ECHO OFF

Echo ��ʼ�������� ����

for /f "usebackq delims=" %%a in ("%~1") do (
	set content=%%a
	if not "!content:~0,1!" == "[" (
		for /f "delims=; tokens=1" %%b in ("!content!") do (
			set content=%%b
			for /f "delims== tokens=1-2" %%i in ("!content!") do (
				set key=%%i
				set key=!key: =!
				set value=%%j
				set value=!value: =!
				set !key!=!value!
				Echo     [Config-Key] !key!     [Config-Value] !value!
			)
		)
	)
)


if /i %PROCESSOR_IDENTIFIER:~0,3%==x86 (
	SET LONG_BIT=32
) else (
	SET LONG_BIT=64
)

Echo ���ü������
Echo.
Echo.
