#!/bin/sh

selfDir="$( dirname -- "$( realpath -- "$0" )" )"
vendorDir="${selfDir}/vendor"
pagedjsFile="${vendorDir}/paged.polyfill.js"
pagedjsUrl='https://unpkg.com/pagedjs/dist/paged.polyfill.js'

mkdir -p "$vendorDir"
if ! [ -e "$pagedjsFile" ]; then
    echo "Missing ${pagedjsFile}, download from ${pagedjsUrl}"
    if ! curl -L "$pagedjsUrl" -o "$pagedjsFile"; then
        echo "Download failed"
        exit 1
    fi
fi

( cd "$selfDir" && php -S localhost:8080 )
