FROM centos:7.9.2009

#安装jer
COPY jre-8u301-linux-x64.rpm /tmp/jre-8u301-linux-x64.rpm
RUN rpm -i /tmp/jre-8u301-linux-x64.rpm
RUN rm -rf /tmp/jre-8u301-linux-x64.rpm
ENV JAVA_VERSION=8u301
ENV JAVA_HOME=/usr/java/latest

#安装中文包
RUN yum install kde-l10n-Chinese -y
RUN yum reinstall glibc-common -y
RUN localedef -c -f UTF-8 -i zh_CN zh_CN.utf8
ENV LC_ALL zh_CN.UTF-8