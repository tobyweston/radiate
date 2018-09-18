# Radiate
![](https://travis-ci.org/tobyweston/radiate.svg?branch=master)

The `radiate` app is a TeamCity build monitor written in Scala. It can be run as a Windows **screensaver** or from the desktop.

It will auto detect your TeamCity projects and aggregate the builds to an overall RED or GREEN. Have a look on YouTube for [an introduction video](http://www.youtube.com/watch?v=ZMQn-J435Lk).

![](grey-busy.png) ![](green-radial.png) ![](red-radial.png)


### Version 2.0 Released!

Version 2.x is a conversion from Java to Scala. The Java version is still available on a [branch](https://github.com/tobyweston/radiate/tree/java-version). Note that the config file format has changed. If you're upgrading from version 1.x, **you'll need to migrate to the new `.cfg` format from `.yml`**. Locate the `radiate.cfg` after first boot and customise as required. See the [Setup]() section below for more details.


### Setup

To setup, simply set the environment property `TEAMCITY_URL` to point to your TeamCity instance.

For example, the following are all valid. If you don't set a port, it will default to `8111`

    http://localhost
    http://myteamcity.com:8991
    https://localhost.com:80

The first time it loads, `radiate` will attempt to load the list of projects using the guest login and start monitoring those. Once you've run the app once, a `radiate.cfg` will be created in your `user.home` where you can prune the list of projects and change other settings.
 
The app supports both TeamCity user and guest login. To switch on TeamCity guest login, see instructions for [TeamCity 8](http://confluence.jetbrains.com/display/TCD8/Enabling+Guest+Login) and [TeamCity 7](http://confluence.jetbrains.com/display/TCD7/Enabling+Guest+Login)).



### Usage

There are a few key you can use to control the application. Hit any of the following whilst running.

* **Esc** - quit
* **F1** or **i** - toggle the console for additional information
* **f** - switch to full screen mode across all monitors (screen saver mode)
* **d** - switch to desktop mode (display in a single window)
* **a** - monitor all projects & builds as a single aggregate (default)
* **c** - chessboard mode; display each project as an aggregate 
* **x** - demo mode; rotate each mode for demonstration purposes

A log will also be created in the `user.home` folder.


### Configuration

For `radiate` to start up, you'll need to add at least one environment variable to tell it where your TeamCity instance is running (`TEAMCITY_URL` described above). It will try to bootstrap with sensible defaults but if things don't start up, you can check for errors either in the generated log file or via the UI, by pressing `F1`.

Once started, the `radiate.cfg` file is created in a folder called `.radiate` in your `user.home` (ie `/Users/toby/.radiate/radiate.cfg`). It will contain the default configuration and from that point on, will override any environment variables. Environment variables are therefore a handy way to start the app with minimal configuration but to fine tune things (for example, set which projects to monitor), edit the config file.

**NB.** The location is set using [Java's notion of `user.home`](https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html). This will vary depending on platform and JVM version. Best run a Java app to print it out using `System.getProperty("user.home")` if you're not sure where it is. Don't forget that the folder `.radiate` might also show up as a hidden folder on your platform. 

The bootstraping environment variables are below (remember, these will be ignored once the `radiate.cfg` is in place).

Environment variable | Required | Example
--- | --- | ---
TEAMCITY_URL | Required, port defaults to `8111` is missing | http://localhost:80
TEAMCITY_USERNAME | Optional, defaults to guest auth if missing | secret
TEAMCITY_PASSWORD | Optional, defaults to guest auth if missing | bob_fossil


### Download

Download the .exe, .scr or executable jar from the [bad robot repository](http://robotooling.com/maven/bad/robot/radiate_2.11/).


### Finer grained views

Work on an alternative views is underway to allow per project aggregation or per build type visualisation.

![](chessboard.png) ![](chessboard2.png) ![](chessboard3.png)
