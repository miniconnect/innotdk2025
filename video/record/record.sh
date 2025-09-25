#!/bin/sh

ffmpeg -video_size 540x960 -framerate 60 -f x11grab -i :0.0+100,200 -c:v libx264 -preset ultrafast -crf 18 out.mp4
