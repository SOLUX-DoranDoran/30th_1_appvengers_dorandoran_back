#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/apps/dorandoran_backend"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

CURRENT_PID=$(pgrep -f "$JAR_FILE")

if [ -z "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 현재 실행중인 애플리케이션이 없습니다." >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 실행 중인 프로세스 $CURRENT_PID 종료 시도" >> $DEPLOY_LOG
  kill -15 "$CURRENT_PID"
  sleep 5
  echo "$TIME_NOW > 종료 완료 확인: $(pgrep -f "$JAR_FILE")" >> $DEPLOY_LOG
fi