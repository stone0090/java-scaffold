FROM shi0090/centos-java-8u301:7.9.2009

ENV APP_NAME java-scaffold
ENV HEALTH_URL http://localhost:8080/demo/heartbeat/status

# 将应用启动脚本复制到镜像中
COPY .docker/java-cmd/ /home/admin/${APP_NAME}/bin/
COPY .docker/start.sh /start.sh

# copy 复制文件到容器地址 , 目标路径不存在时，会自动创建
COPY start/target/${APP_NAME}.jar /home/admin/${APP_NAME}/target/${APP_NAME}.jar

EXPOSE 8080

RUN chmod -R a+x /start.sh
CMD ["/bin/bash", "-c", "/start.sh"]