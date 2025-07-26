#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
workspaceDir="${selfDir}/workspace"

rm -rf "${workspaceDir}/"  &&
cp -r "${selfDir}/sample" "$workspaceDir" &&
git clone -b develop 'https://github.com/miniconnect/holodb' "${workspaceDir}/holodb" && # FIXME: develop?
( cd "${workspaceDir}/holodb" && ./gradlew config:generateSchema ) &&

# FIXME: should this be handled in the config project?
#cp "${workspaceDir}/holodb/projects/config/build/schemas/holodb-config.schema.json" "${workspaceDir}/config.schema.json" &&
jq 'walk(if type == "object" and has("valuesBundle") then .valuesBundle += {enum: '"$( echo "${workspaceDir}/holodb/projects/values/src/main/resources/hu/webarticum/holodb/values/"* | tr ' ' '\n' | sed -E 's/(^.*\/|\.txt$)//g' | jq  -R -s -c 'split("\n") | map(select(length > 0))' )"'} else . end)' "${workspaceDir}/holodb/projects/config/build/schemas/holodb-config.schema.json" > "${workspaceDir}/config.schema.json"
