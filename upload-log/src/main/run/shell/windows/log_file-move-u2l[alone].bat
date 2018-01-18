@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION & CLS

@REM 手动配置开始
Set SOURCE_PATH=H:\log
Set TARGET_PATH=F:\test_logs
@REM 手动配置结束


Title 正在移动文件 ……
Echo 正在移动文件 ……

For /R "%SOURCE_PATH%" %%f In (*) do (
    Echo %%f
    Move /Y "%%f" "%TARGET_PATH%"
    If ERRORLEVEL 1 GoTo move_fail
)
GoTo success


:success
Echo.
Echo.
Echo 已经将目录“%SOURCE_PATH%”中的文件移动到目录“%TARGET_PATH%”，完成
Echo.
GoTo eof


:move_fail
Echo.
Echo.
Echo 文件移动，失败
Echo.
GoTo eof


:eof
Title 文件移动完毕
Pause
endlocal
