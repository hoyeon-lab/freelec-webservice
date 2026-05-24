#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=freelec-webservice

function clean_old_jars() {
    echo "> 오래된 JAR 정리 - 최신 5개만 유지"

    JAR_COUNT=$(ls -1 "$REPOSITORY"/*.jar 2>/dev/null | grep -v plain | wc -l)

    if [ "$JAR_COUNT" -le 5 ]; then
        echo "> 보관된 JAR 개수: $JAR_COUNT, 삭제하지 않습니다."
        return
    fi

    echo "> 보관된 JAR 개수: $JAR_COUNT, 오래된 JAR 삭제를 진행합니다."

    ls -tr "$REPOSITORY"/*.jar | grep -v plain | head -n -5 | xargs -r rm -f
}

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

clean_old_jars

echo "> 새 어플리케이션 배포"

JAR_NAME=$(ls -tr "$REPOSITORY"/*.jar | grep -v plain | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> $JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 를 profile=$IDLE_PROFILE 로 실행합니다."

nohup java \
    -Dspring.config.location=classpath:/application.properties,classpath:/application-${IDLE_PROFILE}.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
    -Dspring.profiles.active=${IDLE_PROFILE},oauth2,real-db \
    -jar ${JAR_NAME} \
    > ${REPOSITORY}/nohup.out 2>&1 &