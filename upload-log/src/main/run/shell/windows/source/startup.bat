@ECHO OFF & SetLocal EnableDelayedExpansion & CLS & Echo.

TITLE 启动程序

Echo  当前时间 ： %date:~0,4%-%date:~5,2%-%date:~8,2% %time:~0,2%:%time:~3,2%:%time:~6,2%
Echo.

IF "!APP_HOME!" == "" (
    ECHO "APP_HOME 没有配置"
    GOTO eof
)
IF "!APP_HOME:~-1!" == "\" (
    SET APP_HOME=!APP_HOME:~0,-1!
)
ECHO APP_HOME          = !APP_HOME!

SET JAVA_HOME=!APP_HOME!\java\jre
ECHO JAVA_HOME         = %JAVA_HOME%

SET CLASSPATH=.;%JAVA_HOME%\lib\rt.jar
For /R "%APP_HOME%\lib" %%f In (*) do (
    ::Echo     %%f
    Set CLASSPATH=!CLASSPATH!;%%f
)

SET MAIN_CLASS=cn.savor.standalone.log.gui.GuiMain

"!JAVA_HOME!\bin\java" -Dsavor.tool.box.home="!APP_HOME!" -Dlogback.configurationFile="!APP_HOME!\conf\logback.xml" "%MAIN_CLASS%" -classpath "!CLASSPATH!"

ECHO "已经关闭"
::EXIT /B 200
EndLocal
EXIT
