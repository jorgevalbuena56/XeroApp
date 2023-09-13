package com.xero.interview.bankrecmatchmaker.domain
data class SalesInvoice (
    val id: Int,
    val paidTo: String,
    val transactionDate: String,
    val total: Float,
    val docType: String
) {
    companion object {
        fun buildMockData() : List<SalesInvoice> {
            return listOf(
                SalesInvoice(1,"City Limousines", "30 Aug", 249.00f, "Sales Invoice"),
                SalesInvoice(2, "Ridgeway University", "12 Sep", 618.50f, "Sales Invoice"),
                SalesInvoice(3, "Cube Land", "22 Sep", 495.00f, "Sales Invoice"),
                SalesInvoice(4, "Bayside Club", "23 Sep", 234.00f, "Sales Invoice"),
                SalesInvoice(5, "SMART Agency", "12 Sep", 250.0f, "Sales Invoice"),
                SalesInvoice(6, "PowerDirect", "11 Sep", 108.60f, "Sales Invoice"),
                SalesInvoice(7, "PC Complete", "17 Sep", 216.99f, "Sales Invoice"),
                SalesInvoice(8, "Truxton Properties", "17 Sep", 181.25f, "Sales Invoice"),
                SalesInvoice(9, "MCO Cleaning Services", "17 Sep", 170.50f, "Sales Invoice"),
                SalesInvoice(10, "Gateway Motors", "18 Sep", 411.35f, "Sales Invoice"),
            )
        }
    }
}