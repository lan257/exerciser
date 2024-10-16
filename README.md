
# AAW后端开发（服务器）

## 简介
在Android and Web(AAW)跨平台开发中，作为后端服务器为Android和Web端提供服务  

## 技术栈
采用springboot框架开发后端，使用MySql,mybatis管理数据库，采用jwt，拦截器登录校验保护用户数据与管理用户权限。  
使用哈希编码加密敏感数据，已在release 发布编译服务器jar文件，配置Dockerfile,可直接用于服务器部署。

## 配置环境
jdk:OpenJDK 21.0.1  
端口：8080  
mysql : Ver 8.3.0 for Win64 on x86_64

## 服务器配置

### docker镜像
1.在dockers 官网下载并安装合适的版本
2.注册账号并登录
3.下载安装该镜像 ‘docker push lan258/aaw:tagname’
4.运行该镜像

### windows配置
1.根据上述环境 配置环境  

2.打开项目根目录，修改bat文件。"C:\Users\29812\.jdks\openjdk-21.0.1\bin\java.exe" -jar "F:\AndroidAndWeb\target\AAW-0.0.1-SNAPSHOT.jar"将前面引号中目录改为你电脑中jdk下载位置，后面引号中目录改为  AAW-0.0.1-SNAPSHOT.jar的地址。

3.管理员权限打开“服务器.bat”运行服务器

## 现状
目前备考，准备秋招，故完善项目  

（<del>目前大三下学期，在校辅修计算机，目前学校在学springboot，我在上学期已经学过了，但会为了跟老师的进度根据需求简单更新我库当中服务器项目，另外现在在备考研究生考试，时间不充裕，只有空余时间更新项目，在复习计算机基础知识时，会拿vue练手实践，所以会相对经常更新，而android项目目前没什么想做的，暂时不会做太大的更新。</del>）

