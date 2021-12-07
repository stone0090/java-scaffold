#!/bin/bash

PROG_NAME=$0
ACTION=$1
if [ "$UID" -eq 0 ]; then
    echo "can't run as root, please use: sudo -u admin $0 $@"
    exit 3 # bad user
fi

if [ $# -lt 1 ]; then
    usage
    exit 2 # bad usage
fi

APP_HOME=$(cd $(dirname $0)/..; pwd)
echo "APP_HOME: $APP_HOME"
source "$APP_HOME/bin/setenv.sh"

usage() {
    echo "Usage: $PROG_NAME {start|stop|restart}"
    exit 2 # bad usage
}

start() {
    echo "[ INFO] -- start java process"
    JAVA_OUT=${APP_HOME}/logs/server.log
    nohup $JAVA_HOME/bin/java -jar ${APP_HOME}/target/${APP_NAME}.jar &>$JAVA_OUT &
#    nohup $JAVA_HOME/bin/java -jar $JAVA_OPTS  ${APP_HOME}/target/${APP_NAME}.jar &>$JAVA_OUT &
}

stop() {
    echo "[ INFO] -- stop java process"
    times=60
    for e in $(seq 60)
    do
        sleep 1
        COSTTIME=$(($times - $e ))
        javapid=`ps -ef|grep java|grep $APP_NAME|grep -v appctl.sh|grep -v jbossctl| grep -v restart.sh |grep -v grep`
        if [[ $javapid ]];then
                javapid=`ps -ef|grep java|grep $APP_NAME|grep -v appctl.sh|grep -v jbossctl | grep -v restart.sh |grep -v grep|awk '{print $2}'`
                kill -9 $javapid
                echo -n -e  "\r        -- stopping java lasts `expr $COSTTIME` seconds."
        else
                break;
        fi
        break;
    done
}

case "$ACTION" in
    start)
        start
    ;;
    stop)
        stop
    ;;
    restart)
        stop
        start
    ;;
    *)
        usage
    ;;
esac