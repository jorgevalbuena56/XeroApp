package com.xero.interview.bankrecmatchmaker.data

import com.xero.interview.bankrecmatchmaker.domain.SalesInvoice

class XeroRepositoryImpl : XeroRepository {
    override fun fetchSalesInvoices(): List<SalesInvoice> {
        return SalesInvoice.buildMockData()
    }
}