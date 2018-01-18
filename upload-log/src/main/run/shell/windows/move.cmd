@ECHO OFF & SETLOCAL ENABLEDELAYEDEXPANSION

Title 正在移动文件 ……
Echo 正在移动文件 ……

SET LONG_BIT=%~1
SET SOURCE_PATH=%~2
SET TARGET_PATH=%~3


Wmic Logicaldisk Where "DriveType=2" Get DeviceID | find "DeviceID" || GoTo not_found_usb

IF NOT EXIST "%SOURCE_PATH%\" (
    Echo.
    Echo     源目录 '%SOURCE_PATH%' 不存在
    Echo.
    GoTo eof
)

IF NOT EXIST "%TARGET_PATH%\" (
    Echo.
    Echo     目标目录 '%TARGET_PATH%' 不存在
    Echo.
    GoTo eof
)

For /R "%SOURCE_PATH%" %%f In (*) do (
    Set FILE_ITEM=%%f
    Echo     !FILE_ITEM!
    Move /Y "!FILE_ITEM!" "%TARGET_PATH%"
    If ERRORLEVEL 1 GoTo move_fail "!FILE_ITEM!"
)
GoTo move_success


:not_found_usb
Echo.
Echo     没有发现 USB 存储设备
Echo.
Exit /B 503
GoTo end


:move_success
Echo.
Echo     已经将目录“%SOURCE_PATH%”中的文件移动到目录“%TARGET_PATH%”，完成
Echo.
GoTo end


:move_fail
Echo.
Echo     文件 '!FILE_ITEM!' 移动失败
Echo.
Exit /B 501
GoTo end

:end
Title 文件移动完毕

:eof
endlocal
