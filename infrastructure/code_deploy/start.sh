#!/usr/bin/env bash

# Ensure all java instances are dead
killall -9 java

if [[ ${DEPLOYMENT_GROUP_NAME} =~ "WebServer" ]]
then
    echo "Stopping nginx"
    service nginx stop
    sudo cp /var/www/streamercontracts/nginx.conf /etc/nginx/nginx.conf
    echo "Starting nginx StreamerContracts-WebServer. Service wont work until you ssh onto this instance and update nginx.conf."
    sudo service nginx start & exit $?
elif [[ ${DEPLOYMENT_GROUP_NAME} =~ "Web" ]]
then
    echo "Starting StreamerContracts-Web SpringBoot Application"
    java -jar /var/www/streamercontracts/web-1.0-SNAPSHOT.jar > /dev/null 2>&1 &
    exit $?
elif [[ ${DEPLOYMENT_GROUP_NAME} =~ "Api" ]]
then
    echo "Starting StreamerContracts-Api SpringBoot Application"
    java -jar /var/www/streamercontracts/api-1.0-SNAPSHOT.jar > /dev/null 2>&1 &
    exit $?
else
    echo "Invalid deployment group name '${DEPLOYMENT_GROUP_NAME}', no role found " 2>&1 &
    exit 1
fi