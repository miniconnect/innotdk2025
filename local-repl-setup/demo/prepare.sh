#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
projectDir="$( realpath -- "${selfDir}/../project" )"
buildDir="${selfDir}/build"

( cd "$projectDir" && ./gradlew build ) &&
rm -rf "$buildDir" &&
mkdir -p "$buildDir" &&
mv "${projectDir}/build/libs/"*"-all.jar" "${buildDir}/repl.jar" &&
git clone -b develop 'https://github.com/miniconnect/holodb' "${buildDir}/holodb" &&
( cd "${buildDir}/holodb" && ./gradlew config:generateSchema ) &&
jq 'walk(if type == "object" and has("valuesBundle") then .valuesBundle += {enum: '"$( echo "${buildDir}/holodb/projects/values/src/main/resources/hu/webarticum/holodb/values/"* | tr ' ' '\n' | sed -E 's/(^.*\/|\.txt$)//g' | jq  -R -s -c 'split("\n") | map(select(length > 0))' )"'} else . end)' "${buildDir}/holodb/projects/config/build/schemas/holodb-config.schema.json" > "${buildDir}/config.schema.json"
