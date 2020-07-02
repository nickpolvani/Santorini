# Prova Finale Ingegneria del Software 2020
## Gruppo GC11

- ###   10618641    Niccolò Polvani ([@niccolopolvani](https://github.com/niccolopolvani))<br>niccolo.polvani@mail.polimi.it
- ###   10595640    Francesco Puoti ([@FPuoti255](https://github.com/FPuoti255))<br>francesco.puoti@mail.polimi.it
- ###   10611820    Juri Sacchetta ([@juriSacchetta](https://github.com/juriSacchetta))<br>juri.sacchetta@mail.polimi.it

## Getting Started

The Politecnico di Milano committed the project as the final test of the course 
Software Engineering year 2019/2020.<br>The project specifications require the development 
of a software version of the already existing game table lamp by Cranio Creations.<br>
The pdf with the rules of the game is available [HERE](https://github.com/niccolopolvani/ing-sw-2020-Polvani-Puoti-Sacchetta/blob/master/deliveries/santorini_rules_en.pdf)


| Functionality | State |
|:-----------------------|:------------------------------------:|
| Basic rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Complete rules | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Socket |[![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| CLI |[![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| GUI | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Advanced features
| Multiple games | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#)|
| Advance Gods | [![GREEN](https://placehold.it/15/44bb44/44bb44)](#) |
| Persistence | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |
| Undo | [![RED](https://placehold.it/15/f03c15/f03c15)](#) |

<!--
[![RED](https://placehold.it/15/f03c15/f03c15)](#)
[![YELLOW](https://placehold.it/15/ffdd00/ffdd00)](#)
[![GREEN](https://placehold.it/15/44bb44/44bb44)](#)
-->

### Prerequisites

Install maven on your PC. You can download it from this [link](https://maven.apache.org/download.cgi)

### Installing

Open a terminal in the project folder, then run:

```
mvn clean
mvn package
```
If it is the first time you build the project you can run only:

```
mvn package
```
### Run the project

To launch the server run: 
```
java -jar target\ing-sw-2020-GC11-1.0-SNAPSHOT-jar-with-dependencies.jar server
```
To launch the client gui:
 ```
 java -jar target\ing-sw-2020-GC11-1.0-SNAPSHOT-jar-with-dependencies.jar gui
 ```
To launch the client cli:
 ```
 java -jar target\ing-sw-2020-GC11-1.0-SNAPSHOT-jar-with-dependencies.jar cli
 ```

### Running the tests
 ```
 mvn test
 ```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [JUnit4](https://junit.org/junit4/) - The test framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [jansi](https://github.com/fusesource/jansi) - Used for command-line style
