#!/bin/bash

# by default, we don't know the environment to run
# but when we know it, e.g. test environment, we can add special logic, like adding debug port
# value candidates: test, production
if [ -z ${APP_ENVIRONMENT+x} ]; then
    APP_ENVIRONMENT=""
fi
HOME="$(getent passwd "$UID" | awk -F":" '{print $6}')" # fix "$HOME" by "$UID"
MIDDLEWARE_LOGS="${HOME}/logs"

# os env

export CPU_COUNT="$(grep -c 'cpu[0-9][0-9]*' /proc/stat)"
ulimit -c unlimited

# env check and calculate
APP_NAME=""
if [ -z "$APP_NAME" ]; then
	APP_NAME=$(basename "${APP_HOME}")
fi

JAVA_OPTS="-server -Xss512k"
# use memory based on the available resources in the machine
let memTotal=`cat /proc/meminfo | grep MemTotal | awk '{printf "%d", $2/1024*0.75 }'`
if [ $memTotal -gt 6000 ]; then
    JAVA_OPTS="${JAVA_OPTS} -Xms4g -Xmx4g"
    JAVA_OPTS="${JAVA_OPTS} -Xmn2g"
else
    JAVA_OPTS="${JAVA_OPTS} -Xms2g -Xmx2g"
    JAVA_OPTS="${JAVA_OPTS} -Xmn1g"
fi

JAVA_OPTS="${JAVA_OPTS} -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -XX:MaxDirectMemorySize=1g"
JAVA_OPTS="${JAVA_OPTS} -XX:+AggressiveOpts -XX:+UseBiasedLocking"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseFastAccessorMethods -XX:+DisableExplicitGC"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseParNewGC -XX:+DisableExplicitGC"
JAVA_OPTS="${JAVA_OPTS} -XX:SurvivorRatio=10"
JAVA_OPTS="${JAVA_OPTS} -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:CMSMaxAbortablePrecleanTime=5000"
JAVA_OPTS="${JAVA_OPTS} -XX:+CMSClassUnloadingEnabled -XX:CMSInitiatingOccupancyFraction=80 -XX:+UseCMSInitiatingOccupancyOnly"
JAVA_OPTS="${JAVA_OPTS} -XX:+ExplicitGCInvokesConcurrent -Dsun.rmi.dgc.server.gcInterval=2592000000 -Dsun.rmi.dgc.client.gcInterval=2592000000"
JAVA_OPTS="${JAVA_OPTS} -XX:ParallelGCThreads=${CPU_COUNT}"
JAVA_OPTS="${JAVA_OPTS} -Xloggc:${MIDDLEWARE_LOGS}/gc-%t.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=500k"
JAVA_OPTS="${JAVA_OPTS} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${MIDDLEWARE_LOGS}/java.hprof"
JAVA_OPTS="${JAVA_OPTS} -Djava.awt.headless=true"
JAVA_OPTS="${JAVA_OPTS} -Dsun.net.client.defaultConnectTimeout=10000"
JAVA_OPTS="${JAVA_OPTS} -Dsun.net.client.defaultReadTimeout=30000"
JAVA_OPTS="${JAVA_OPTS} -Dfile.encoding=UTF-8"
JAVA_OPTS="${JAVA_OPTS} -Dproject.name=${APP_NAME}"
JAVA_OPTS="${JAVA_OPTS} -Dspring.diamond.enabled=false"

if [ "$K8S_JAVA_OPTS" ];then
    JAVA_OPTS="${JAVA_OPTS} ${K8S_JAVA_OPTS}"
fi

export JAVA_OPTS
