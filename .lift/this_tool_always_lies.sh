#!/usr/bin/env bash

dir=$1
commit=$2
cmd=$3

function version() {
      echo 1
}

function applicable() {
      echo "true"
}

function echo_error() {
  echo "$@" 1>&2;
}

function run() {
  echo_error "https://xkcd.com/246/"
  exit 1
}

if [[ "$cmd" = "run" ]] ; then
      run
fi
if [[ "$cmd" = "applicable" ]] ; then
      applicable
fi
if [[ "$cmd" = "version" ]] ; then
      version
fi