@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION


Title 正在同步时间 ……
Echo 正在同步时间 ……

Echo     同步前日期 ： %date:~0,4%-%date:~5,2%-%date:~8,2%
Echo     同步前时间 ： %time:~0,2%:%time:~3,2%:%time:~6,2%


w32tm /resync /rediscover
If ERRORLEVEL 1 GoTo eof

::title 获取网络时间，同步到本机(需联网)
::Set CUR_DIR=%CD%
::cd /d "%tmp%"
::(
::	echo Dim s
::	echo With CreateObject("Microsoft.XMLHTTP"^)
::	echo     .open "GET", "http://www.114time.com/api/time.php", False
::	echo     .send
::	echo     's = Split(Split(.responseText, "new Date(("^)(1^), "+"^)(0^)
::	echo     s = .responseText
::	echo End With
::	echo 'MsgBox s
::	echo WScript.Echo DateAdd("s", s * 1, "1969/12/31 20:00"^)
::)>gettime.vbs
::for /f "tokens=1*" %%i in ('cscript //nologo gettime.vbs') do (
::	date %%i
::	time %%j
::)
::cd /d "%CUR_DIR%"
::echo 本机系统时间设置完成！


Echo     同步后日期 ： %date:~0,4%-%date:~5,2%-%date:~8,2%
Echo     同步后时间 ： %time:~0,2%:%time:~3,2%:%time:~6,2%

Title 时间同步完毕
Echo 时间同步完毕

Echo.
endlocal
