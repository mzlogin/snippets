@echo off
if "%2" equ "" goto usage

if "%2" equ "-d" goto dec
if "%2" equ "-e" goto enc
goto end

:dec
:enc
printf %1 | python -m base64 %2
goto end

:usage
printf "usage:\n\tbase64 ""<string>"" <-e|-b>\n"

:end
