#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
workspaceDir="${selfDir}/workspace"

rm -rf "${workspaceDir}/"  &&
cp -r "${selfDir}/sample" "$workspaceDir" &&
git clone -b develop 'https://github.com/miniconnect/holodb' "${workspaceDir}/holodb" &&
( cd "${workspaceDir}/holodb" && ./gradlew config:generateSchema ) &&
jq 'walk(if type == "object" and has("valuesBundle") then .valuesBundle += {enum: '"$( echo "${workspaceDir}/holodb/projects/values/src/main/resources/hu/webarticum/holodb/values/"* | tr ' ' '\n' | sed -E 's/(^.*\/|\.txt$)//g' | jq  -R -s -c 'split("\n") | map(select(length > 0))' )"'} else . end)' "${workspaceDir}/holodb/projects/config/build/schemas/holodb-config.schema.json" > "${workspaceDir}/config.schema.json" &&
git clone -b develop 'https://github.com/miniconnect/miniconnect-client' "${workspaceDir}/miniconnect-client" &&
( cd "${workspaceDir}/miniconnect-client" && ./gradlew client:shadowJar ) &&
cp "${workspaceDir}/miniconnect-client/projects/client/build/libs/client-0.3.1-SNAPSHOT-all.jar" "${workspaceDir}/client.jar"
