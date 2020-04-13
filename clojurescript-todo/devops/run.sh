#!/usr/bin/env bash

echo "build release"
shadow-cljs release app --config-merge "{:closure-defines {todo.config/DEBUG $DEBUG}}"


echo "copy resources to nginx"
ls -l /project/resources/public/
cp -r /project/resources/public/* /usr/share/nginx/html


echo "run nginx"
nginx -g "daemon off;"