@echo off



%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit



set myjdkpath=C:\Java\jdk1.8



echo **********************************************

echo.

echo             ��ӭʹ��һ����װjdk

echo.

echo       ��װ�밴��������˳�ֱ�ӹرմ���

echo.

echo **********************************************



pause


echo.

echo ���ڰ�װjdk���벻Ҫִ����������

echo.

echo ���Եȣ����Լ��Ҫ�����ķ���

echo.


start /WAIT C:\jdk-8u171-windows-x64.exe /qn INSTALLDIR=`C:\Java\jdk1.8`

echo jdk��װ���



set JAVA_HOME=C:\Java\jdk1.8

set PATH=%PATH%;%%JAVA_HOME%%\bin;%%JAVA_HOME%%\jre\bin

set CLASSPATH=.;%%JAVA_HOME%%\lib\dt.jar;%%JAVA_HOME%%\lib\tools.jar



set RegV=HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment



reg add "%RegV%" /v "JAVA_HOME" /d "%JAVA_HOME%" /f

reg add "%RegV%" /v "Path" /t REG_EXPAND_SZ /d "%PATH%" /f

reg add "%RegV%" /v "CLASSPATH" /d "%CLASSPATH%" /f

mshta vbscript:msgbox("Java�����ѳɹ�ע�ᣡ",64,"�ɹ�")(window.close)



#-Dfile.encoding=utf-8



exit