# Chess Safe Positions

[![Build Status](https://travis-ci.org/MateuszKubuszok/ChessSafePositions.svg)](https://travis-ci.org/MateuszKubuszok/ChessSafePositions)

Generates all positions where none of the pieces would be attacking any other
piece. Input are sizes of the board and numbers of each non-pawn pieces.

## Requirements

 * JVM installed (Java 7+),
 * SBT installed (SBT 0.13.8).
 
## Running

First, build your project as a single executable JAR:

    $ sbt assembly
    
It will build executable JAR and place it within `target/scala-2.10` directory.

From there you can run it like:

    $ java -jar "Chess Safe Positions-0.1.0.jar" -n:4 -m:4 --kings:1 --knights:1 --rooks:1 --bishops:1 --queens:1    

To show only number of possible solutions use `--size-only`.

To additionally measure time of execution use `--show-time` 

Only width and height are required, rest parameters are optional and defaults to 0:

    $ java -jar "Chess Safe Positions-0.1.0.jar" -n:7 -m:7 --kings:2 --queens:2 --bishops:2 --knights:1 --size-only --show-time
    Solution size: 3063828
    Time: 40.871s

All possible arguments can be checked using `--help` flag.
