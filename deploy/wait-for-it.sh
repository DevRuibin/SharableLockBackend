#!/bin/sh

HOST=$1
PORT=$2
shift 2
CMD="$@"

echo "Waiting indefinitely until the service at $HOST:$PORT is available..."
until nc -z "$HOST" "$PORT"; do
  echo "Service at $HOST:$PORT is not available - trying again later..."
  sleep 2
done
echo "Service at $HOST:$PORT is available!"

exec $CMD