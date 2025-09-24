#!/bin/sh

RESET='\r\033[2K'

clear

printf '\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n'
printf '############################################\n'
printf '#                                          #\n'
printf '#   Yet Another Database Seeder  \e[1;36mv4.3\e[0m      #\n'
printf '#   The Flexible Migration Framework       #\n'
printf '#   John Doe, 2025                         #\n'
printf '#                                          #\n'
printf '############################################\n'
printf '\n\n'

printf '\e[0;33mWarning: The previous migration was forcefully aborted, tmp files will be dropped\e[0m\n'
printf '\e[0;33mWarning: Some extensions are outdated, please check the logs\e[0m\n'
printf 'Loading faker extensions ... \e[1;32mDONE\e[0m\n'
printf 'Discovering entities ... \e[1;32mDONE\e[0m\n'
printf 'Discover factories ... \e[1;32mDONE\e[0m\n'
printf 'Discover raw SQL migrations ... \e[1;32mDONE\e[0m\n'
printf '\e[0;33mWarning: No raw SQL migrations found, seeder can be slow\e[0m\n'
printf 'Connecting to the database server ... \e[1;32mDONE\e[0m\n'
printf '\e[0;33mWarning: The database schema is not empty, will be erased\e[0m\n'
printf 'Build unindexed schema ... \e[1;32mDONE\e[0m\n'
printf 'Writing rows into users ... \e[1;32mDONE\e[0m\n'
printf 'Writing rows into categories ... \e[1;32mDONE\e[0m\n'
printf 'Writing rows into authors ... \e[1;32mDONE\e[0m\n'
printf 'Writing rows into posts ... \e[1;32mDONE\e[0m\n'
printf 'Writing rows into post_comments ... \e[1;32mDONE\e[0m\n'

printf ' '
for i in $(seq 0 10); do
    for S in ⠋ ⠙ ⠹ ⠸ ⠼ ⠴ ⠦ ⠧ ⠇ ⠏; do
        printf "$RESET"'Writing rows into post_reactions ... \e[1;93m'"$S"' \e[0m'
        sleep 0.1
    done
done
printf "$RESET"'Writing rows into post_reactions ... \e[1;32mDONE\e[0m\n'

printf ' '
for S in ⠋ ⠙ ⠹ ⠸; do
    printf "$RESET"'Building indexes ... \e[1;93m'"$S"' \e[0m'
    sleep 0.1
done
printf "$RESET"'Building indexes ... \e[1;32mDONE\e[0m\n'

printf ' '
for S in ⠋ ⠙ ⠹ ⠸ ⠼ ⠴; do
    printf "$RESET"'Executing smoke tests ... \e[1;93m'"$S"' \e[0m'
    sleep 0.1
done
printf "$RESET"'Executing smoke tests ... \e[1;31mFAILED!\e[0m\n'

printf '\e[1;31mError: Unknown column "post_it" in table "post_comments"\e[0m\n'
printf '\e[1;92mgipsz@jakab\e[0m:\e[1;94m~/projektek/nagyon-fontos-projekt\e[0m$ '

sleep 100

printf '\n'
