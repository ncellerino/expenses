#!/usr/bin/env bash

get_EXPENSES_GIT_ROOT() {
    # The directory in which this script exists. See
    # http://stackoverflow.com/a/246128/38140
    dir_of_this_file="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
    pushd "$dir_of_this_file" > /dev/null
    # the path to the root of the Git repository
    # See http://stackoverflow.com/a/957978/38140
    EXPENSES_GIT_ROOT="$(git rev-parse --show-toplevel)/back-end"
    popd > /dev/null
    unset dir_of_this_file
}

errors=0
errorcheck() {
  #execute the command (arguments) following this function
  ${@:1}
  #check error status  (errors get lost in console scroll sometimes)
  if [ "$?" -ne "0" ]; then
     ((errors++))
  fi
}

# paths needed by scripts
get_EXPENSES_GIT_ROOT
export EXPENSES_GIT_ROOT
export PATH="$EXPENSES_GIT_ROOT/bin:$PATH"

alias gradlew="$EXPENSES_GIT_ROOT/./gradlew"
alias build-docker-fast='gradlew clean && gradlew build -x test -Pprod --parallel --max-workers=6 && gradlew -Pprod -x test buildDocker'
alias uuid="uuidgen | tr -d '\n' | tr '[:upper:]' '[:lower:]'  | pbcopy && pbpaste && echo"
alias gitp="git rev-parse --abbrev-ref HEAD | xargs git push origin"

# don't rely on docker machine...
EXPENSES_MACHINE_HOST="$(docker-machine ip expenses)"
if [[ -z "$EXPENSES_MACHINE_HOST" ]]; then
    echo "docker machine not found, defaulting to localhost..."
    EXPENSES_MACHINE_HOST=localhost
fi
export EXPENSES_MACHINE_HOST


# Set the terminal tabname to "expenses"
function tabname {
  printf "\e]1;$1\a"
}
tabname "Expenses"

# alias timeout if needed
alias timeout=gtimeout

# provide some feedback
env | grep "EXPENSES"

if [ $errors -ne "0" ]; then
   echo "***********************************************"
   echo "***********************************************"
   echo "$errors errors encountered"
   echo "***********************************************"
   echo "***********************************************"
fi
