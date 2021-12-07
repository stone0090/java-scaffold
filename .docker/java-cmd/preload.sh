#! /bin/bash

health_url="http://localhost:8080/status"
if [ "$HEALTH_URL" ];then
    health_url="${HEALTH_URL}"
fi
echo "-----------------------------------------------------------------------------"
echo "[  CMD] -- HEALTH CHECK WAITING FOR 10 SECOND ... "
echo "[  CMD] -- HEALTH CHECK URL: ${health_url}"
echo "-----------------------------------------------------------------------------"

status=0
times=10
for e in $(seq $times); do
	sleep 30
	COSTTIME=$(($times - $e ))
	HEALTH_CHECK_CODE=$(curl -s --connect-timeout 5 --max-time 5 --retry 3 --retry-delay 5 ${health_url} -o /tmp/health_check.log -w %{http_code})
	if [ "$HEALTH_CHECK_CODE" == "200" ]; then
		status=1
		echo "[  INFO] -- HEALTH CHECK OK. "
		break;
	else
		echo -n -e  "\r[  WARN] -- check heath lasts `expr $COSTTIME` times."
	fi
done

if [ $status -eq 0 ]; then
    echo "[  FAILURE] -- health check failed. "
    exit 1
fi