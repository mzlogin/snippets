@echo off
if "%1" equ "" goto usage

printf %1 | md5sum
goto end

:usage
printf "usage:\n\tstrmd5 <string>\n"

:end
