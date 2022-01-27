# MYOB: Employee monthly payslip generator
*This application generates employee monthly payslip using his/her annual salary*
Inputs to application:
- Employee Name
- Annual salary (only in whole number)

Returns: Employee monthly payslip
- Employee Name
- Gross monthly Income
- Monthly income tax
- Net monthly income (after deducting monthly taxes)

## Assumptions:
- Employee annual salary would always be whole numbers.


## Running from command prompt

#### Prerequisite for running this application.

* install sbt on machine, for macOS use [SBT] link to install and make sure that sbt directory is in PATH. 
* install scala2 on machine. I have used scala 2.12 version for this project. Use [Scala] link to install scala.
* make sure sbt and scala runs from command prompt.


####From root folder *myob* run following command
### Build and Compile application

    sbt clean compile

### Run Test locally

    sbt test

### Create Jar for running locally

    sbt clean assembly

assembly will create fat jar file *myob-assembly.jar* in target folder.

### Run application locally

     scala -cp ./target/scala-2.12/myob-assembly.jar com.myob.employee.payslip.GenerateMonthlyPayslip "Mayo kin" 60000


### Running application from using IDE
#### Running in IntelliJ IDEA
Run GenerateMonthlyPayslip object with the following:

##### Module
    myob

##### Arguments
    "Mayo kin" 60000


[SBT]: https://www.scala-sbt.org/1.x/docs/Installing-sbt-on-Mac.html
[Scala]: https://www.scala-lang.org/download/
