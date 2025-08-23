#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"

java -jar "${selfDir}/build/repl.jar" "${selfDir}/config.yaml"
