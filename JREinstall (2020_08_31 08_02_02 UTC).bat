@echo off



%1 mshta vbscript:CreateObject("Shell.Application").ShellExecute("cmd.exe","/c %~s0 ::","","runas",1)(window.close)&&exit



set myjrepath=C:\Java\jre



echo **********************************************

echo.

echo           ��Ҫ��װ������л���jre

echo.

echo       ��װ�밴��������˳�ֱ�ӹرմ���

echo.

echo **********************************************



pause



echo.

echo ���ڰ�װjre���벻Ҫִ����������

echo.

echo ���Եȣ����ʱ���Լ��Ҫ�ġ������

echo.

start /WAIT C:\jre-8u171-windows-x64.exe /s INSTALLDIR=C:\Java\jre

echo jre��װ���



set JAVA_HOME=C:\Java

set PATH=%PATH%;%%JAVA_HOME%%\jre\bin

set CLASSPATH=.;%%JAVA_HOME%%\jre\lib



set RegV=HKLM\SYSTEM\CurrentControlSet\Control\Session Manager\Environment



reg add "%RegV%" /v "JAVA_HOME" /d "%JAVA_HOME%" /f

reg add "%RegV%" /v "Path" /t REG_EXPAND_SZ /d "%PATH%" /f

reg add "%RegV%" /v "CLASSPATH" /d "%CLASSPATH%" /f

mshta vbscript:msgbox("Java�����ѳɹ�ע�ᣡ",64,"�ɹ�")(window.close)



exit