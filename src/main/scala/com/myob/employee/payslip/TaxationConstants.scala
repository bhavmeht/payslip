package com.myob.employee.payslip

object TaxationConstants {

  // Tax rates for income brackets. If modified, it will automatically update effective
  // annual tax computation on employee's annual income.
  val TaxRateForIncomeUpto20k: Double = 0D
  val TaxRateForIncomeBetween20kTo40k: Double = 0.1
  val TaxRateForIncomeBetween40kTo80k: Double = 0.2
  val TaxRateForIncomeBetween80kTo180k: Double = 0.3
  val TaxRateForIncomeAbove180k: Double = 0.4

  // Annual taxation on $20k income
  lazy val AnnualTaxOn20kIncome: Double = (20000 * TaxRateForIncomeUpto20k)
  // Annual taxation on $40k income
  lazy val AnnualTaxOn40kIncome: Double = AnnualTaxOn20kIncome + (20000 * TaxRateForIncomeBetween20kTo40k)
  // Annual taxation on $80k income
  lazy val AnnualTaxOn80kIncome: Double = AnnualTaxOn40kIncome + (40000 * TaxRateForIncomeBetween40kTo80k)
  // Annual taxation on $180k income
  lazy val AnnualTaxOn180kIncome: Double = AnnualTaxOn80kIncome + (100000 * TaxRateForIncomeBetween80kTo180k)

}
