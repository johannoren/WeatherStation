Weather Station
================================

This module contains source code and instructions for turning a Raspberry Pi together with a DS18B20 temperature sensor into a temperature monitoring device.
Each x minutes a reading is made and posted to a server for storage. The server is a generic Java web application with a RESTful interface for 
posting temperature readings and retrieving temperature information in condensed manner.

Storage is solved by a CouchDB database which is called in RESTful fashion from the web application.

The web application also contains a single page HTML application using the REST services for fetching temperature data and presenting it in a human friendly way using charts and diagrams. 

To setup this project on your own, make sure you have 
* A Raspberry Pi (with Ethernet capabilities)
* A DS18B20 temperature sensor ~ $8.
* A 4.7KOhm resistor
* Some wires and a soldering iron

To wire the hardware up, see http://learn.adafruit.com/adafruits-raspberry-pi-lesson-11-ds18b20-temperature-sensing/overview
To make the software run, see /WeatherStationServer/raspberrypi/doc/setup.txt

Install the web application on a Java web server and adjust the python script to run against the correct server.
