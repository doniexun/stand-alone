On Error Resume Next

WScript.Echo "正在校正计算机时间 ……"

Set args = WScript.Arguments
If args.Count >= 1 Then
	timeSyncURL= WScript.Arguments(0)
    WScript.Echo "    时间校准地址 ： " & timeSyncURL
	'MsgBox "test  " & name
Else
	WScript.Quit 999
End If

call runAsAdmin()
If Err.Number <> 0 Then
	WScript.Quit Err.Number
End If

timeStamp = getTimeFromInternet()
If Err.Number <> 0 Then
	WScript.Quit Err.Number
End If

strNewDateTime = convertDateTime(timeStamp)
If Err.Number <> 0 Then
	WScript.Quit Err.Number
End If

call syncDateTime(strNewDateTime, Now())
If Err.Number <> 0 Then
	WScript.Quit Err.Number
End If


WScript.Echo "计算机时间校正完成"

Function getTimeFromInternet()
    Dim strUrl, strText
    'strUrl = "http://open.baidu.com/special/time/"
    strUrl = timeSyncURL
    With CreateObject("MSXML2.XmlHttp")
        .Open "GET", strUrl, False
        .Send()
        strText = .responseText
    End With
    'strText = Split(LCase(strText), "window.baidu_time(")(1)
    'MsgBox strText
    If len(strText) >= 13 Then
    	getTimeFromInternet = Int(Left(strText, 13)/1000)
	ElseIf len(strText) < 13 And len(strText) >= 10 Then
    	getTimeFromInternet = Int(Left(strText, 10))
	End If
    'MsgBox Err.Number
    'If Err.Number <> 0 Then WScript.Quit Err.Number
End Function

Function convertDateTime(intUnixTime)
	'On Error Resume Next
    Dim objWMI, colOSes, objOS, tmZone
    Set objWMI = GetObject("winmgmts:\\.\root\cimv2")
    Set colOSes =objWMI.ExecQuery("Select * from Win32_OperatingSystem")
    For Each objOS in colOSes
        tmZone = objOS.CurrentTimeZone
    Next
    intUnixTime = intUnixTime + tmZone * 60
    convertDateTime = DateAdd("s", intUnixTime, "1970-1-1 00:00:00")
End Function

Sub syncDateTime(ByVal strNewDateTime, strOldDateTime)
	'On Error Resume Next
    Dim ss, objDateTime, dtmNewDateTime
    ss = DateDiff("s", strOldDateTime, strNewDateTime)
    If Abs(ss) < 1 Then
    	WScript.Echo "    本机时间非常准确无需校对！"
        'MsgBox "本机时间非常准确无需校对！"
        Exit Sub
    End If

    Set objDateTime = CreateObject("WbemScripting.SWbemDateTime")
    objDateTime.SetVarDate strNewDateTime, true 
    dtmNewDateTime = objDateTime.Value

    Dim objWMI, colOSes, objOS
    Set objWMI = GetObject("winmgmts:{(Systemtime)}\\.\root\cimv2")
    Set colOSes =objWMI.ExecQuery("Select * from Win32_OperatingSystem")
    For Each objOS in colOSes
        objOS.SetDateTime dtmNewDateTime
    Next
    WScript.Echo "    校准前：" & strOldDateTime & vbLf & "    校准后：" & Now()
    'MsgBox "校准前：" & strOldDateTime & vbLf & "校准后：" & Now()
End Sub

Sub runAsAdmin()
	'On Error Resume Next
    Dim objWMI, colOSes, objOS, strVer
    Set objWMI = GetObject("winmgmts:\\.\root\cimv2") 
    Set colOSes =objWMI.ExecQuery("Select * from Win32_OperatingSystem")
    For Each objOS in colOSes
        strVer = Split(objOS.Version, ".")(0)
    Next
    If CInt(strVer) >= 6 Then
        Dim objShell
        Set objShell = CreateObject("Shell.Application")
        If WScript.Arguments.Count = 0 Then
            objShell.ShellExecute "WScript.exe", _
                """" & WScript.ScriptFullName & """ OK", , "runAs", 1
            Set objShell = Nothing
            WScript.Quit
        End If
    End If
End Sub