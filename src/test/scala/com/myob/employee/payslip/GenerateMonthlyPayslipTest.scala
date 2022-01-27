package com.myob.employee.payslip

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GenerateMonthlyPayslipTest extends AnyFlatSpec with Matchers {
  val empName = "Mary kom"
  val generateMonthlyPayslip = new GenerateMonthlyPayslip()
  behavior of "generateEmployeeMonthlyPayslip"

  it should "not generate any monthly income tax for employee whose taxable income is < 20k" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 12000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 0
    empPayslip.grossMonthlyIncome shouldBe 1000
    empPayslip.netMonthlyIncome shouldBe 1000
  }

  it should "not generate any monthly income tax for employee whose taxable income is exactly 20k" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 20000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 0
    empPayslip.grossMonthlyIncome shouldBe 1666.67 +- 0.01
    empPayslip.netMonthlyIncome shouldBe 1666.67 +- 0.01
  }

  it should "generate and deduct monthly income tax from employee net monthly payment, taxable at 10% bracket" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 32000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 100
    empPayslip.grossMonthlyIncome shouldBe 2666.67 +- 0.01
    empPayslip.netMonthlyIncome shouldBe 2566.67 +- 0.01
  }

  it should "generate and deduct monthly income tax from employee net monthly payment, " +
    "taxable at 10% bracket for 40k salary" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 40000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 166.67 +- 0.01
    empPayslip.grossMonthlyIncome shouldBe 3333.34 +- 0.01
    empPayslip.netMonthlyIncome shouldBe 3166.67 +- 0.01
  }

  it should "generate and deduct monthly income tax from employee net monthly payment, " +
    "for salary taxable in 20% bracket" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 60000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 500
    empPayslip.grossMonthlyIncome shouldBe 5000
    empPayslip.netMonthlyIncome shouldBe 4500
  }

  it should "generate and deduct monthly income tax from employee net monthly payment, " +
    "for salary taxable in 30% bracket" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 120000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 1833.33 +- 0.01
    empPayslip.grossMonthlyIncome shouldBe 10000
    empPayslip.netMonthlyIncome shouldBe 8166.67 +- 0.01
  }

  it should "generate and deduct monthly income tax from employee net monthly payment, " +
    "for salary taxable in 40% bracket" in {
    val empPayslip = generateMonthlyPayslip.generateEmployeeMonthlyPayslip(empName, 200000)
    empPayslip.empName shouldBe empName
    empPayslip.monthlyIncomeTax shouldBe 4000
    empPayslip.grossMonthlyIncome shouldBe 16666.67 +- 0.01
    empPayslip.netMonthlyIncome shouldBe 12666.67 +- 0.01
  }

  behavior of "calculateAnnualIncomeTax"

  it should "return annual income tax as 0 for annual income below $20k" in {
    val result = generateMonthlyPayslip.calculateAnnualIncomeTax(20000)
    result shouldBe 0
  }

  it should "return expected annual income tax for employee falling in >20k to <=40k bracket" in {
    val result = generateMonthlyPayslip.calculateAnnualIncomeTax(40000)
    result shouldBe 2000
  }

  it should "return expected annual income tax for employee falling in >40k to <=80k bracket" in {
    val result = generateMonthlyPayslip.calculateAnnualIncomeTax(80000)
    result shouldBe 10000
  }

  it should "return expected annual income tax for employee falling in >80k to <=180k bracket" in {
    val result = generateMonthlyPayslip.calculateAnnualIncomeTax(120000)
    result shouldBe 22000
  }

  it should "return expected annual income tax for employee falling in >180k bracket" in {
    val result = generateMonthlyPayslip.calculateAnnualIncomeTax(200000)
    result shouldBe 48000
  }

}
