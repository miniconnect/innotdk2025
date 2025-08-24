#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"

export LOG_PATH="${selfDir}/log/holodb.log"

mkdir -p "${selfDir}/log" &&
java -jar "${selfDir}/build/repl.jar" "${selfDir}/config.yaml"
