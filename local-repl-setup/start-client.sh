#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
workspaceDir="${selfDir}/workspace"

( cd "$workspaceDir" && java -jar "${workspaceDir}/client.jar" )
