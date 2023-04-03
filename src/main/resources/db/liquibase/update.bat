@ECHO OFF
REM ddlとdml配下の全yamlファイルに対し、liquibase updateを実行するバッチスクリプト

REM タイムスタンプを用いてタグを設定
SET time2=%time: =0%
SET timestamp=%date:~0,4%%date:~5,2%%date:~8,2%%time2:~0,2%%time2:~3,2%%time2:~6,2%
call liquibase tag %timestamp%

REM ddl配下を実行
FOR %%F IN (ddl\*.yml) DO (
    ECHO update: %%F
    call liquibase update --changelog-file=%%F
)

REM dml配下を実行
FOR %%F IN (dml\*.yml) DO (
    ECHO update: %%F
    call liquibase update --changelog-file=%%F
)
