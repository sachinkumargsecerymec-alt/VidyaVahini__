@echo off
set DIR=%~dp0
set LOCAL_GRADLE=C:\Users\DELL\.gradle\wrapper\dists\gradle-9.2.1-bin\2t0n5ozlw9xmuyvbp7dnzaxug\gradle-9.2.1\bin\gradle.bat
if exist "%LOCAL_GRADLE%" (
  call "%LOCAL_GRADLE%" %*
) else (
  echo Local Gradle 9.2.1 was not found. Open the project in Android Studio to sync and build.
  exit /b 1
)
