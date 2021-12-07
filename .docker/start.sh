#! /bin/bash

if [ ! -d "/home/admin/${APP_NAME}/logs" ];then
mkdir -p /home/admin/${APP_NAME}/logs
fi

useradd admin
chown admin:admin -R /home/admin/
chmod -R a+x /home/admin/${APP_NAME}/bin/
chmod -R 755 /home/admin/${APP_NAME}

cd /home/admin
su admin -c "/home/admin/${APP_NAME}/bin/appctl.sh restart"
#su admin -c "java -jar /home/admin/java-scaffold/target/java-scaffold.jar"
sleep 5

while true; do
  if [ -f "/home/admin/${APP_NAME}/bin/preload.sh" ]; then
    su admin -c "/home/admin/${APP_NAME}/bin/preload.sh"
    if [ $? -ne 0 ]; then
      echo "health check failed, exit now"
      exit -1
    fi
  fi
  sleep 5
done

