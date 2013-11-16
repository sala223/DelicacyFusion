echo off
set CWD=%~dp0
set CWD=%CWD:~0,-1%
echo set current work directory to %CWD%
pushd %CWD%
echo setup environment variables. 
call %CWD%/env.bat
if %DROP_DB_FIRST% == 1 (
    echo drop database %DATABASE%
	%MYSQL% -u %DATABASE_USER% -e "DROP DATABASE %DATABASE%"
    %MYSQL% -u %DATABASE_USER% --verbose < %CWD%/../db-init/create-db.sql
	call :executeSqlFile %CWD%/../db-init/create.sql
)else (
	echo DROP_DB_FIRST is set to %DROP_DB_FIRST%, ignore drop datbase %DATABASE%
)

echo Begin to prepare sample data
%MYSQL% -u %DATABASE_USER% --verbose --default-character-set=utf8 %DATABASE% < %CWD%/sample/sample.sql
echo Done prepare sample data.
pause


goto:eof 
:executeSqlFile 
	echo start to execute sql in file %~1
	setLocal EnableDelayedExpansion
	for /f "tokens=* delims= usebackq" %%a in ("%~1") do (
	     echo %%a 
		 %MYSQL% --host=%HOST% -u %DATABASE_USER% --password=%PASSWORD% --database=%DATABASE% -e "%%a"
	)
goto:eof 
