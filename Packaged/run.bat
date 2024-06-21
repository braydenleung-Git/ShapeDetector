@echo off
REM This script runs any .jar file in the current directory using Java

echo Searching for .jar file in the current directory...
for %%f in (*.jar) do (
    echo Running %%f...
    java -jar "%%f"
    goto end
)

echo No .jar file found in the current directory.
:end
pause
