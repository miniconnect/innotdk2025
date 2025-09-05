#!/bin/sh

( cd -- "$( dirname -- "$0" )" && ./gradlew test -Djunit.platform.tags.include=smoke )
