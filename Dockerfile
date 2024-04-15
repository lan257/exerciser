#FROM 		#基础镜像，一切从这里开始构建
#MAINTAINER	#镜像是谁写的，姓名+邮箱
#RUN			#镜像构建时需要运行的命令
#ADD			#步骤，tomcat镜像，这个tomcat压缩包；添加内容
#WORKDIR		#镜像工作目录
#VOLUME		#挂载的目录
#EXPOSE		#暴露端口配置
#CMD			#指定这个容器启动的时候要运行的命令,只有最后一个会生效，可被替代
#ENTRYPOINT	#指定这个容器启动的时候要运行的命令，可以追加命令
#ONBUILD		#当构建一个被继承DockerFile 这个时候就会运行 ONBUILD 的指令，触发指令
#COPY		#类似ADD,将我们的文件拷贝至镜像中
#ENV			#构建的时候设置环境变量

# 使用 OpenJDK 21.0.1 作为基础镜像
FROM openjdk:21-ea-32-jdk-bookworm

#镜像是谁写的，姓名+邮箱
MAINTAINER	lan257 zut177@hotmail.com

# 设置工作目录
WORKDIR /app

# 拷贝编译好的 Spring Boot JAR 文件到容器中
COPY target/AAW-0.0.1-SNAPSHOT.jar app.jar

# 暴露容器内部的 8080 端口
EXPOSE 8080

# 定义容器启动时执行的命令
CMD ["java", "-jar", "app.jar"]
