# Calculator for SE3800 Course (Winter Quarter - 2015) - MSOE
The purpose of this project is to build a calculator that can perform operations according to the following requirements document: https://goo.gl/7Eruwl.

### Developers:
* Cleber de Souza Alcântara
* Lillian Horn

### System Requirements:
* Java JDK 1.8
* JUnit 4.12 (test tool)
* Maven 2.2.1 (build tool)
* JaCoCo 0.7 (JaCoCo Java Code Coverage Library. This library is optional, once it is used only to check the code coverage of the project.)

### Building:
To build the software, type the folowing command:
```shell
mvn clean install
```
A directory named ```target``` should be created and a file named ```calculator-1.0-SNAPSHOT.jar``` should be created inside this directory. To run this file, double click over it or go to the target directory and type the command:
```shell
java -jar calculator-1.0-SNAPSHOT.jar
```

### Testing:
To test the project, type the following command:
```shell
mvn test
```
You should get an output with some lines provided by Maven. Among these lines should appear a line like the following, indicating that all tests passed:
```shell
Tests run: 46, Failures: 0, Errors: 0, Skipped: 0
```
