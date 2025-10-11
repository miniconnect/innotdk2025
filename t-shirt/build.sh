#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
buildDir="${selfDir}/build"

rm -rf "$buildDir"
mkdir -p "$buildDir"

inkscape "${selfDir}/front.svg" --export-type=png --export-filename="${buildDir}/front.png" --export-dpi=300
inkscape "${selfDir}/back.svg" --export-type=png --export-filename="${buildDir}/back.png" --export-dpi=300
