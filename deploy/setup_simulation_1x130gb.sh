#!/bin/bash


function join { local IFS=" "; shift 3; echo "$*"; }

s3_bucket=$1
s3_output_data=$2
icarus_jar=$3
outputdir=$4

icarus_flags=$(join $@)

mkdir $outputdir
echo "${icarus_jar} ${icarus_flags[@]}" >> jar_input.out

java8 -Djava.awt.headless=true -Xms1g -Xmx130g -jar  $icarus_jar ${icarus_flags[@]}

zip -r "${4}.zip" "${4}"
aws s3 cp "${4}.zip" $(echo "s3://${s3_bucket}/${4}.zip")
