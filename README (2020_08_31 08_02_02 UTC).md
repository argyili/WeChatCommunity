1.微信小程序前端环境部署

微信小程序开发环境（阿里云服务搭建+可运行的demo），可参考一下链接：

https://blog.csdn.net/li420248878/article/details/79120604

2.Java开发环境配置

一键安装JDK和JRE并自动配置Java环境变量：参考如下链接https://blog.csdn.net/tang_chuanlin/article/details/80240672

博客中新建两个.bat文件，已上传到项目；

JDK,JRE默认安装目录在C:\\java\ 

如需更改安装目录，需改以下四行代码：

set myjdkpath=C:\Java\jdk1.8

set JAVA_HOME=C:\Java\jdk1.8

set myjrepath=C:\java\jre

set JAVA_HOME=C:\Java

3.使用微信小程序开发工具，需要注册APPID,可参考如下链接：微信公众平台

https://developers.weixin.qq.com/ebook?action=get_post_info&docid=000e8842960070ab0086d162c5b80a

4.安装IDEA,如下链接下载：

https://www.jetbrains.com/idea/ 

5.使用IDEA打开backdevelop项目

使用微信开发工具打开infocommunity项目

6.项目内的文件结构
backdevelop 后端代码
infocommunity 前端代码
infocommunity_Readme.md 前端说明文档
community.sql 数据库文件
JDKinstall.bat, JREinstall.bat 配置环境文件名
