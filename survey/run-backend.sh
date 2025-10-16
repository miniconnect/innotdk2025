#!/bin/sh

if [ -z "$DATA_DIR" ]; then
    echo 'No data dir given'
    exit 1
fi

php -S localhost:3001 -t backend
