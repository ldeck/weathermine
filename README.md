# Weather Observations POC

Imagine mining the incoming logs of a supersonic weather balloon traipsing around the globe.

Assuming such a balloon continues to make such a tremendous journey, its logs are filled with observation points.

At each observation, the balloon records the temperature and its current location. When possible, the balloon relays this data back to observation posts on the ground.

Excellent idea!

## Assumptions

Our infamous balloon has been travelling since y2k. Who knows, given enough log entries we might even travel into the future!

Log lines returned from the weather balloon looks something like this:

    2014-12-31T13:44|10,5|243|AU

More formally this is:

    <timestamp>|<location>|<temperature>|<observatory>

These are assumed to be of the following format:

| KEY             | FORMAT                                     |
| -------------   | ------------------------------------------ |
| **timestamp**   | `yyyy-MM-ddThh:mm` in UTC                  |
| **location**    | `x, y` co-ordinate                         |
| **temperature** | `integer` (celsius, kelvin, or fahrenheit) |
| **observatory** | ISO 3166 two-letter country codes          |

 Mind you, since we're dealing with the uncontrollable forces of nature, flakiness is expected.

 Each of the following are examples of log lines that may arise:

    2014-12-31T13:44|10,5|243|AU     => parsable
    2014-12-31T13:44|||AU            => lost co-ordinates and temp
    44|10,6|243|AU                   => invalid timestamp
    |||                              => ping?
    ///                              => mayday?
    ---                              => crashed?
                                     => weak signal?
                                     => and so on

**Note well...**

Logs can and *will be* be out of order, they can arrive in LARGE batches, have missing data points, or be just plain corrupt.


## Interpretations

A lookup table is required to interpret the temperature and location values.

| Observatory | Temperature | Distance |
| ----------- | ----------- | -------- |
| AU          | celsius     | km       |
| US          | fahrenheit  | miles    |
| FR          | kelvin      | m        |
| All Others  | kelvin      | km       |

Observations points can be from any country code in the world (at least those known to Java's Locale).

## Roadmap

**0. Iteration 0** — defining and testing basic logging types [DONE]

 Tech stack:
  - Scala 2.11 / Java 1.8
  - SBT 0.13.7 (simple build tool)
  - ScalaTest / ScalaCheck test frameworks

  a. enums for the lookup table
  b. case classes for observations and optional fields
  c. main class to produce one random observation, appended to the given file

    Usage:
    $ sbt
    sbt> clean
    sbt> test

**1. Simulated logger** [DONE]

Given that it is difficult to obtain real data from the weather balloon, let's generate a representative sample log file

    Usage: sbt "run <count> <logfile>"
        count: the number of log entries to produce
        logfile: the path to the new or existing logfile to append to

    Example:
    $ sbt
    > run 500000000 target/readings.log
    [info] Running start 500000000 target/readings.log
    [success] Total time: 3001 s, completed 29/10/2015 6:16:26 AM
    > exit
    $ ls -lh target/readings.log
      -rw-r--r--  1 ldeck  staff    18G 29 Oct 06:16 target/readings.log


**Note:** the above would create a file about 20Gb in size!!!

Note to self: compress output in future!

**2. Produce statistics of the flight**

The program should be able to compute any combination of the following on request:

    * The minimum temperature.
    * The maximum temperature.
    * The mean temperature.
    * The number of observations from each observatory.
    * The total distance travelled.

**3. Produce a normalized output of the data**

Given desired units for temperature and distance, an output file is produced containing all observations with the specified output units.
