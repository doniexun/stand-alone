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

SET APP_TMPDIR=!APP_HOME!\temp
ECHO APP_TMPDIR        = !APP_TMPDIR!

SET JAVA_HOME=!APP_HOME!\java\jre
ECHO JAVA_HOME         = !JAVA_HOME!

SET JAVA_EXE=!JAVA_HOME!\bin\java
ECHO JAVA_EXE          = !JAVA_EXE!

SET MAIN_CLASS=cn.savor.standalone.log.gui.GuiMain
ECHO MAIN_CLASS        = !MAIN_CLASS!

set LOGGING_CONFIG=-Dlogback.configurationFile="!APP_HOME!\conf\logback.xml"
ECHO LOGGING_CONFIG    = !LOGGING_CONFIG!

SET CLASSPATH=.;!JAVA_HOME!\lib\rt.jar
For /R "%APP_HOME%\lib" %%f In (*) do (
    ::Echo     %%f
    Set CLASSPATH=!CLASSPATH!;%%f
)
::ECHO CLASSPATH         = !CLASSPATH!

SET "JAVA_OPTS=!JAVA_OPTS! !LOGGING_CONFIG!"


START "" "!JAVA_EXE!" !JAVA_OPTS! -classpath "!CLASSPATH!" -Dsavor.tool.box.home="!APP_HOME!" -Djava.io.tmpdir="!APP_TMPDIR!" "!MAIN_CLASS!"

ECHO "已经关闭"

::"!APP_HOME!\bin\sleep" 3000
EndLocal
EXIT
