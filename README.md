# Radiate
[![](https://travis-ci.org/tobyweston/radiate.png?branch=master)](https://travis-ci.org/tobyweston/radiate)

TeamCity build monitor. Run as a Windows **screensaver** or from the desktop.

It will auto detect your TeamCity projects and aggregate the builds to an overall RED or GREEN. Have a look on YouTube for [an introduction video](http://www.youtube.com/watch?v=ZMQn-J435Lk).

![](grey-busy.png) ![](green-radial.png) ![](red-radial.png)

### Configuration

Set the environment property `TEAMCITY_HOST` and optionally `TEAMCITY_PORT` (the port defaults to `8111` if you don't set it).

Both TeamCity user and guest login are supported. To enable TeamCity guest login see instructions for [TeamCity 8](http://confluence.jetbrains.com/display/TCD8/Enabling+Guest+Login) and [TeamCity 7](http://confluence.jetbrains.com/display/TCD7/Enabling+Guest+Login)).

It reports builds form **all** projects on the TeamCity instance. Once you've run the app once, `config.yml` will be created in your `user.home` where you can prune the list of projects and change other settings.

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

### Environment configurations

For Radiate to start up, you'll need to add at least one environment variable to tell it where your TeamCity instance is running (described in below). It will try to bootstrap things with sensible defaults but if things don't start up, you can check for errors either in the generated log file or via the UI, by pressing `F1`.

Once started, the `config.yml` file is created in a folder called `.radiate` in your `user.home` (ie `/Users/toby/.radiate/config.yml`). It will contain the default configuration and from that point on will override any environment variables. Environment variables are therefore a handy way to start the app with minimal configuration but to fine tune things (for example, set which projects to monitor), edit the config file. 

**NB.** The location is set using [Java's notion of `user.home`](https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html). This will vary depending on platform and JVM version. Best run a Java app to print it out using `System.getProperty("user.home")` if you're not sure where it is. Don't forget that the folder `.radiate` might also show up as a hidden folder on your platform. 

The bootstrap environment variables are

Environment variable | Required | Example
--- | --- | ---
TEAMCITY_HOST | Required | http://localhost
TEAMCITY_PORT | Optional, defaults to 8111 | 8001
TEAMCITY_PASSWORD | Optional, defaults to guest auth | bob_fossil
TEAMCITY_USER | Optional, defaults to guest auth | secret


### Download

Download the .exe, .scr or executable jar from the [bad robot repository](http://robotooling.com/maven/bad/robot/radiate/).

### Finer grained views

Work on an alternative views is underway to allow per project aggregation or per build type visualisation.

![](chessboard.png) ![](chessboard2.png) ![](chessboard3.png)
