# Simple program to choose who is on duty today

## Introduction

"Who is on duty today" is a command-line utility written in Java to select person who is on duty today based on previous history of being on duty.

Statistics about who was on duty is saved in a file `duty-q.txt` by default.

## Installing

In order to use it, please build it with maven
 
    mvn package

Copy the `target/who-is-on-duty-today.jar` to any folder you like, e.g. `/home/john/who-is-on-duty-today`

## Running

Use java to run the program:

    java -jar who-is-on-duty-today.jar [options]
    
If run without parameters it will show usage.

      Options:
        -a, --alias
          required aliases of users who will eat today
          Default: []
        -x, --exclude
          aliases to exclude from calculation
          Default: []
        -f, --force
          this will force a change - the current person on duty will be remembered
          and queue will be changed
          Default: false
        -n, --nice
          parses aliases nicely, three per alias even if they happen to kludge
          together (what happens if copypasted from google doc)
          Default: false
        -q, --queue
          queue file name to use
          Default: duty-q.txt
        -r, --revert
          revert the queue file
          Default: false
        --volunteer
          volunteer to be on duty

If the `--force` flag is not set, program runs in a soft run and does not change statistics.

Usually you would like to check whom program chooses to be on duty with a soft run, ask a person whether it is possible for him/her to be on duty and then run program with the same parameters with the `--force` flag.

In case chosen person cannot be on duty today, you may exclude his alias by using `--exclude` flag 
 

## Examples

Run:

    > java -jar who-is-on-duty-today.jar -a aaa bbb ccc ddd GGG fff EEE
    INFO: Who is on duty today?
    INFO: This is a soft run, queue will not be updated. Use --force to update the queue
    INFO: EEE
    
Talk:

 - EEEE, can you be on duty today?
 - No, sorry, I have a very important meeting from 11:00 till 13:00
 - Ok, nevermind.

Run:

    > java -jar who-is-on-duty-today.jar -a aaa bbb ccc ddd GGG fff EEE --exclude EEE
    INFO: Who is on duty today?
    INFO: This is a soft run, queue will not be updated. Use --force to update the queue
    INFO: ddd
    
Talk:

 - ddd, can you be on duty today?
 - Yes, sure
 - Perfect, thank you

Run:

    > java -jar who-is-on-duty-today.jar -f -a aaa bbb ccc ddd GGG fff EEE --exclude EEE
    INFO: Who is on duty today?
    INFO: Queue will be updated, use --revert for reverting back
    INFO: ddd


  
