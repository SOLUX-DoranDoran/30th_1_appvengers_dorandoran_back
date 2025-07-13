#!/usr/bin/env bash

PROJECT_ROOT="/home/ec2-user/apps/dorandoran_backend"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# 가장 최신 JAR 파일 탐색
ORIGINAL_JAR=$(ls $PROJECT_ROOT/build/libs/*.jar 2>/dev/null | head -n 1)

if [ ! -f "$ORIGINAL_JAR" ]; then
  echo "$TIME_NOW > 빌드된 JAR 파일을 찾을 수 없습니다." >> $DEPLOY_LOG
  exit 1
fi

# JAR 복사
echo "$TIME_NOW > $ORIGINAL_JAR → $JAR_FILE 복사" >> $DEPLOY_LOG
cp "$ORIGINAL_JAR" "$JAR_FILE"

# JAR 실행
echo "$TIME_NOW > $JAR_FILE 실행 시작" >> $DEPLOY_LOG
nohup java -jar "$JAR_FILE" > "$APP_LOG" 2> "$ERROR_LOG" &

# 실행된 PID 기록
CURRENT_PID=$(pgrep -f "$JAR_FILE")
if [ -n "$CURRENT_PID" ]; then
  echo "$TIME_NOW > 실행된 프로세스 PID: $CURRENT_PID" >> $DEPLOY_LOG
else
  echo "$TIME_NOW > 애플리케이션 실행 실패" >> $DEPLOY_LOG
fi