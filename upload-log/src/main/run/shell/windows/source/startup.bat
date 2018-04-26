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
ECHO LONG_BIT          = !LONG_BIT!
ECHO MAIN_EXE          = !MAIN_EXE!

SET WORK_DIR=%CD%
IF "!WORK_DIR:~-1!" == "\" (
    SET WORK_DIR=!WORK_DIR:~0,-1!
)
ECHO WORK_DIR          = !WORK_DIR!

SET DIR_BIN=!APP_HOME!\bin
ECHO DIR_BIN           = !DIR_BIN!

SET DIR_LOG=!APP_HOME!\logs
ECHO DIR_LOG           = !DIR_LOG!

SET DIR_CONF=!APP_HOME!\conf
ECHO DIR_CONF          = !DIR_CONF!

SET DIR_TEMP=!APP_HOME!\temp
ECHO DIR_TEMP          = !DIR_TEMP!

SET DIR_DATA=!APP_HOME!\data
ECHO DIR_DATA          = !DIR_DATA!

SET DIR_BACKUP=!APP_HOME!\backup
ECHO DIR_BACKUP        = !DIR_BACKUP!

SET JRE_HOME=!APP_HOME!\java\jre
ECHO JRE_HOME          = !JRE_HOME!

CALL "!WORK_DIR!\ReadConfig" false "!DIR_CONF!\app.ini"

If Not "%com.littlehotspot.offline.tools.project.name%" == "" (
    SET "JAVA_OPTS=!JAVA_OPTS! -Dsavor.tool.box.project.name=^"%com.littlehotspot.offline.tools.project.name%^""
)

If "%com.littlehotspot.offline.tools.conf%" == "" (
    SET APP_CONFIG=-Dsavor.tool.box.conf="!APP_HOME!\conf\config.properties"
) Else (
    SET APP_CONFIG=-Dsavor.tool.box.conf="%com.littlehotspot.offline.tools.conf%"
)
ECHO APP_CONFIG        = !APP_CONFIG!
SET "JAVA_OPTS=!JAVA_OPTS! !APP_CONFIG!"

If EXIST "!JRE_HOME!\bin\javaw.exe" (
    SET JAVA_EXE=!JRE_HOME!\bin\javaw
) Else (
    If EXIST "!JRE_HOME!\bin\java.exe" (
        SET JAVA_EXE=!JRE_HOME!\bin\java
    ) Else (
        Echo     没有安装 JDK 环境
    )
)
ECHO JAVA_EXE          = !JAVA_EXE!

SET MAIN_CLASS=cn.savor.standalone.log.gui.GuiMain
ECHO MAIN_CLASS        = !MAIN_CLASS!

If "%com.littlehotspot.offline.tools.logconf%" == "" (
    SET LOGGING_CONFIG=-Dlogback.configurationFile="!APP_HOME!\conf\logback.xml"
) Else (
    SET LOGGING_CONFIG=-Dlogback.configurationFile="%com.littlehotspot.offline.tools.logconf%"
)
ECHO LOGGING_CONFIG    = !LOGGING_CONFIG!
SET "JAVA_OPTS=!JAVA_OPTS! !LOGGING_CONFIG!"

SET CLASSPATH=.;!JRE_HOME!\lib\rt.jar
For /R "%APP_HOME%\lib" %%f In (*) do (
    ::Echo     %%f
    Set CLASSPATH=!CLASSPATH!;%%f
)
ECHO CLASSPATH         = !CLASSPATH!



START "" "!JAVA_EXE!" !JAVA_OPTS! -classpath "!CLASSPATH!" -Dsavor.tool.box.home="!APP_HOME!" -Djava.io.tmpdir="!DIR_TEMP!" "!MAIN_CLASS!"

ECHO "已经关闭"

::"!APP_HOME!\bin\sleep" 3000
EndLocal
EXIT
