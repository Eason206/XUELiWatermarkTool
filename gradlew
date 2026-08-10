#!/bin/sh
#
# Copyright © 2015-2021 the original authors.
# Licensed under the Apache License, Version 2.0.
#

APP_NAME="Gradle"
APP_BASE_NAME=$( basename "$0" )
APP_HOME=$( CDPATH= cd -- "$(dirname -- "$0")" > /dev/null && pwd -P ) || exit

DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

warn () { echo "$*" >&2; }
die () { echo; echo "$*" >&2; echo; exit 1; }

cygwin=false
msys=false
darwin=false
nonstop=false
case "$( uname )" in
  CYGWIN* ) cygwin=true ;;
  Darwin* ) darwin=true ;;
  MSYS* | MINGW* ) msys=true ;;
  NONSTOP* ) nonstop=true ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then JAVACMD=$JAVA_HOME/jre/sh/java
    else JAVACMD=$JAVA_HOME/bin/java
    fi
    [ -x "$JAVACMD" ] || die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME"
else
    JAVACMD=java
    command -v java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH."
fi

if ! "$cygwin" && ! "$darwin" && ! "$nonstop" ; then
    case $MAX_FD in '' | soft) MAX_FD=$( ulimit -H -n ) || warn "Could not query maximum file descriptor limit" ;; esac
    case $MAX_FD in '' | maximum) MAX_FD=$MAX_FD ;; esac
    ulimit -n "$MAX_FD" 2>/dev/null || warn "Could not set maximum file descriptor limit to $MAX_FD"
fi

set -- "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS "-Dorg.gradle.appname=$APP_BASE_NAME" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
