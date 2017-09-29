# Fixed Hash Map

## Introduction

This project was created by Aryik Bhattacharya for the coding challenge portion
of the [KPCB fellows program](http://kpcbfellows.com). The problem statement 
was:
> Using only primitive types, implement a fixed-size hash map that associates 
> string keys with arbitrary data object references (you don't need to copy the
> object). Your data structure should be optimized for algorithmic runtime and
> memory usage. You should not import any external libraries, and may not use
> primitive hash map or dictionary types in languages like Python or Ruby.

## Building and Testing

To test the project, enter `mvn clean test` on the command line. To package the
project into a .jar, enter `mvn package` on the command line. The built jar 
file will be located at `target/fixedhasmap-1.0.0.jar`.

## Dependencies

The project is built using [Maven](https://maven.apache.org) with the standard
Maven project structure (src/main/* and src/test/*). The tests are written
using [Spock](http://spockframework.org), a testing/specification framework
with a highly expressive syntax that is compatible with most IDEs, build tools,
and CI servers due to its jUnit runner. You do not need to do anything to 
obtain spock as Maven handles dependency management.

You must have Maven installed to test or package the project. If you do not
have Maven, you can still compile the source class using `javac` but the 
test file is not guaranteed to compile because the dependencies (groovy and 
spock) may not be installed on your machine. Maven is open source and readily
available on all operating systems. To install Maven, either use your favorite
package manager (homebrew, apt-get, etc.), or download it in one of many 
formats [here](https://Maven.apache.org/download.cgi).

## Hash Map Information

The important class for this project is `FixedHashMap`. `FixedHashMap`'s default
initializer creates a hash map with a size of 10. The other constructor takes 
an integer that represents the desired size of the hash map. The map itself is
a an array with a length equal to the smallest power of 2 that is smaller than
the specified size. The map uses Java's inbuilt `hashCode()` function for 
hashing. Hashing collisions are dealt with by creating a linked list of `Node`
objects. Each `Node` simply contains the key, value, and a reference to the 
next `Node` in the list. 

Accesses and insertions are in constant time in the average case. In the worst
case, which occurs when every key inserted into the map has the same hash,
accesses and insertions are in O(n) time as the linked list has to be 
traversed. The map uses O(n) space. The map supports the following functions:
* `boolean set(String key, Object value)`
* `Object get(String key)`
* `Object delete(String key)`
* `float load()`

## Tests

The test suite was written with a focus on covering edge cases and all 
possible execution paths. Thanks to Spock's expressive syntax, it should be
fairly clear what each test does.
