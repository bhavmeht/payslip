package com.myob.employee.payslip

import com.myob.employee.payslip.TaxationConstants._

import java.text.DecimalFormat
import com.typesafe.scalalogging.LazyLogging

class GenerateMonthlyPayslip extends LazyLogging {

  /** Calculates employee monthly payslip by taking is annual salary as input.
   * It uses tax bracket to calculate employee's effective monthly income, tax
   * on income and net in-hand salary.
   * Tax bracket for taxable income is as follows:
   * $0 - %20000 = No tax
   * $20001 - $40000 = 10%
   * $40001 - $80000 = 20%
   * $80001 - $180000 = 30%
   * $180001 and above = 40%
   * @Params: empName - Employee Name
   *           annualSalary - Annual salary of employee
   * @return EmployeePayslip having details as EmpName, Gross monthly salary, Monthly income tax
   *         and net monthly income.
   */
  def generateEmployeeMonthlyPayslip(empName: String, annualSalary: Double): EmployeePayslip = {
    logger.info(s"Started processing monthly payslip generation for employee: $empName")
    logger.info(s"$empName annual salary: $annualSalary")
    val grossMonthlyIncome: Double = annualSalary / 12
    logger.info(s"$empName gross monthly income is: $$$grossMonthlyIncome")
    val monthlyIncomeTax: Double = calculateAnnualIncomeTax(annualSalary) / 12
    logger.info(s"$empName monthly income tax is: $$$monthlyIncomeTax")
    val netMonthlyIncome: Double = grossMonthlyIncome - monthlyIncomeTax
    logger.info(s"$empName net monthly income is: $$$netMonthlyIncome")
    val payslip = EmployeePayslip(
      empName = empName,
      grossMonthlyIncome = grossMonthlyIncome,
      monthlyIncomeTax = monthlyIncomeTax,
      netMonthlyIncome = netMonthlyIncome
    )
    logger.debug(s"Employee payslip: $payslip")
    logger.info(s"Completed generating monthly payslip for employee: $empName")
    payslip
  }

  /** Calculates annual tax on income using tax bracket for calculating employee's total annual tax on salary.
   * Tax bracket for taxable income is as follows:
   * $0 - %20000 = No tax
   * $20001 - $40000 = 10%
   * $40001 - $80000 = 20%
   * $80001 - $180000 = 30%
   * $180001 and above = 40%
   * @param annualSalary
   * @return Double: Annual taxation on employee's input annual salary
   */
  private[payslip] def calculateAnnualIncomeTax(annualSalary: Double): Double = annualSalary match {
    case salary if 0 to 20000 contains salary =>
      logger.debug("Employee annual salary falls in tax bracket of <= 20k")
      // Tax for income upto $20k(inclusive)
      salary * TaxRateForIncomeUpto20k
    case salary if 20001 to 40000 contains salary =>
      logger.debug("Employee annual salary falls in tax bracket of > 20k and <= 40k range")
      // Add tax upto $20k and calculate tax for value between $20k and $40k(inclusive)
      AnnualTaxOn20kIncome + ((annualSalary - 20000) * TaxRateForIncomeBetween20kTo40k)
    case salary if 40001 to 80000 contains salary =>
      logger.debug("Employee annual salary falls in tax bracket of > 40k and <= 80k range")
      // Add tax upto $40k and calculate tax for value between $40k and $80k(inclusive)
      AnnualTaxOn40kIncome + ((annualSalary - 40000) * TaxRateForIncomeBetween40kTo80k)
    case salary if 80001 to 180000 contains salary =>
      logger.debug("Employee annual salary falls in tax bracket of > 80k and <= 180k range")
      // Add tax upto $80k and calculate tax for value between $80k and $180k(inclusive)
      AnnualTaxOn80kIncome + ((annualSalary - 80000) * TaxRateForIncomeBetween80kTo180k)
    case salary if salary > 180000 =>
      logger.debug("Employee annual salary falls in tax bracket of > 180k range")
      // Add tax upto $180k and calculate tax for value above $180k
      AnnualTaxOn180kIncome + ((annualSalary - 180000) * TaxRateForIncomeAbove180k)
  }

}

object GenerateMonthlyPayslip extends LazyLogging {

  val message =
    """Invalid inputs: Please use following format
    Usage: GenerateMonthlyPayslip ["Employee name"] [Annual salary]
    Example: GenerateMonthlyPayslip "Mary Song" 60000"""

  def main(args: Array[String]): Unit = {
    // It should only allow 2 argument parameters, employee Name and its annual salary.
    if (args.length != 2) println(message)
    else {
      // Employee name should be valid
      require(args(0) != null && args(0).nonEmpty, "Employee name is invalid")
      // Employee annual salary should only be non-negative integer value
      require(args(1) != null && args(1).forall(_.isDigit), "Employee salary is invalid")
      val empName = args(0)
      val annualSalary = args(1).toDouble
      val employeePayslip = new GenerateMonthlyPayslip().generateEmployeeMonthlyPayslip(empName, annualSalary)
      logger.info("Printing employee payslip to console")
      // Use formatter to format decimal value upto 2 places and it should not print decimals for whole numbers.
      val formatter = new DecimalFormat("#.##")
      // Print to console.
      println(s"""Monthly Payslip for: "${employeePayslip.empName}"""")
      println(s"Gross Monthly Income: $$${formatter.format(employeePayslip.grossMonthlyIncome)}")
      println(s"Monthly Income Tax: $$${formatter.format(employeePayslip.monthlyIncomeTax)}")
      println(s"Net Monthly Income: $$${formatter.format(employeePayslip.netMonthlyIncome)}")
    }
  }

}
