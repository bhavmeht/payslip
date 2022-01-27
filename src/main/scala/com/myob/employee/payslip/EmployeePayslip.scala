package com.myob.employee.payslip

case class EmployeePayslip(
  empName: String,
  grossMonthlyIncome: Double,
  monthlyIncomeTax: Double,
  netMonthlyIncome: Double
)
