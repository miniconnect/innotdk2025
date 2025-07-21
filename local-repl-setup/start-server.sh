#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
workspaceDir="${selfDir}/workspace"
holodbDir="${workspaceDir}/holodb"

( cd "$holodbDir" && ./gradlew app:run -q --console=plain --args="${workspaceDir}/config.yaml" )
