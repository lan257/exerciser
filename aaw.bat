@echo off
start "ngrok" /d "F:\ngrok-v3-stable-windows-amd64" ngrok.exe http 8080
timeout /nobreak /t 5 >nul
echo ngrok http 8080 | F:\ngrok-v3-stable-windows-amd64\ngrok.exe
timeout /nobreak /t 5 >nul
chcp 65001
"C:\Users\29812\.jdks\openjdk-21.0.1\bin\java.exe" -jar "F:\AndroidAndWeb\target\AAW-0.0.1-SNAPSHOT.jar"



