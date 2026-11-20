@echo off
cd /d "%~dp0"
call mvn compile exec:java
pause
