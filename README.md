# Radiate
[![](https://travis-ci.org/tobyweston/radiate.png?branch=master)](https://travis-ci.org/tobyweston/radiate)

TeamCity build monitor. Run as a Windows **screensaver** or from the desktop.

It will auto detect your TeamCity projects and aggregate the builds to an overall RED or GREEN. Job done but see [an introduction video](http://www.youtube.com/watch?v=uy5eP-1Pg3M) if you must.

![](error.png) ![](green.png) ![](red.png)

### Configuration

Set the environment property `teamcity.host` and optionally `teamcity.port` (the port defaults to `8111` if you don't set it).

Currently, TeamCity must have guest login enabled (see instructions for [TeamCity 8](http://confluence.jetbrains.com/display/TCD8/Enabling+Guest+Login) and [TeamCity 7](http://confluence.jetbrains.com/display/TCD7/Enabling+Guest+Login)).

It reports builds form all projects on the TeamCity instance. Once you've run the app once, `config.yml` will be created in your `user.home` where you can prune the list of projects.

### Download

Download the .exe, .scr or executable jar from the [bad robot repository](http://robotooling.com/maven/bad/robot/radiate/).

### Finer grained views

Work on an alternative views is underway to allow per project aggregation or per build type visualisation.

![](chessboard.png) ![](chessboard2.png) ![](chessboard3.png)