@ECHO OFF

Set VERBOSE=%~1
Set CONFIG_FILE=%~2

If "%VERBOSE%" == "true" (
    Echo "CONFIG_FILE=%CONFIG_FILE%"
)


Echo / =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= Loading Data =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= \ & Echo.

For /F "eol=; usebackq delims=" %%a In ("%CONFIG_FILE%") Do (
	Set rowContent=%%a
	If Not "!rowContent:~0,1!" == "#" (
        If Not "!rowContent:~0,1!" == "[" (
	        If "!VERBOSE!" == "true" Echo. & Echo     Row Content  : [!rowContent!]
            For /F "delims=; tokens=1" %%b In ("!rowContent!") Do (
                Set lineContent=%%b
                If "!VERBOSE!" == "true" Echo         Row Data : [!lineContent!]
                For /F "delims== tokens=1,*" %%i In ("!lineContent!") Do (
                    Set key=%%i
                    @REM 去除前后空格
                    Set key=!key: =!
                    If "!VERBOSE!" == "true" Echo         Key      : [!key!]
                    Set value=%%j
                    Set value=!value: =!
                    If "!VERBOSE!" == "true" Echo         Value    : [!value!]
                    @Set !key!=!value!
                    Echo     {!key!=!value!}
                )
            )
        ) Else (
            If "!VERBOSE!" == "true" Echo. & Echo. & Echo Model : !rowContent!
        )
    ) Else (
	    If "!VERBOSE!" == "true" Echo !rowContent!
    )
)

Echo. & Echo \ =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= Loaded Data =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= /
Echo.
