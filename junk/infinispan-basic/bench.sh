#!/bin/sh

export JRUBY_OPTS="-J-Xms24g -J-Xmx24g -J-XX:+UseG1GC -J-XX:MaxGCPauseMillis=50 -J-XX:G1HeapRegionSize=4m -J-XX:InitiatingHeapOccupancyPercent=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log g1-p50-r4-gc.log

export JRUBY_OPTS="-J-Xms24g -J-Xmx24g -J-XX:+UseG1GC -J-XX:MaxGCPauseMillis=50 -J-XX:G1HeapRegionSize=8m -J-XX:InitiatingHeapOccupancyPercent=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log g1-p50-r8-gc.log

export JRUBY_OPTS="-J-Xms24g -J-Xmx24g -J-XX:+UseG1GC -J-XX:MaxGCPauseMillis=50 -J-XX:G1HeapRegionSize=16m -J-XX:InitiatingHeapOccupancyPercent=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log g1-p50-r16-gc.log

export JRUBY_OPTS="-J-Xms24g -J-Xmx24g -J-XX:+UseG1GC -J-XX:G1HeapRegionSize=16m -J-XX:InitiatingHeapOccupancyPercent=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log g1-gc.log

export JRUBY_OPTS="-J-Xmn768m -J-Xms24g -J-Xmx24g -J-XX:+UseConcMarkSweepGC -J-XX:CMSInitiatingOccupancyFraction=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log cms-n768-gc.log

export JRUBY_OPTS="-J-Xmn512m -J-Xms24g -J-Xmx24g -J-XX:+UseConcMarkSweepGC -J-XX:CMSInitiatingOccupancyFraction=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log cms-n512-gc.log

export JRUBY_OPTS="-J-Xms24g -J-Xmx24g -J-XX:+UseConcMarkSweepGC -J-XX:CMSInitiatingOccupancyFraction=30 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log cms-gc.log

export JRUBY_OPTS="-J-Xms24g -J-Xmx24g -J-XX:+UseConcMarkSweepGC -J-XX:CMSInitiatingOccupancyFraction=30 -J-XX:TargetSurvivorRatio=90 -J-XX:MaxTenuringThreshold=15 -J-XX:+ParallelRefProcEnabled"
sh run.sh
mv gc.log cms-tsr90-mtt15-gc.log

export JRUBY_OPTS=""
