#!/bin/sh 

# changelog-master.yamlを初期化
echo "# 自動生成ファイルのため、直接編集は原則禁止" > changelog-master.yaml
echo "databaseChangeLog:" >> changelog-master.yaml

ddl_files=`find ./ddl/* -type f -name *.yaml`
dml_files=`find ./dml/* -type f -name *.yaml`

# ddl配下のファイル
echo "  #ddl" >> changelog-master.yaml
for file in $ddl_files;
do
    echo "  - include:" >> changelog-master.yaml
    echo "      file: liquibase/ddl/$file" >> changelog-master.yaml
done

# dml配下のファイル
echo "  #dml" >> changelog-master.yaml
for file in $dml_files;
do
    echo "  - include:" >> changelog-master.yaml
    echo "      file: liquibase/dml/$file" >> changelog-master.yaml
done
