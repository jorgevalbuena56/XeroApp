package com.xero.interview.bankrecmatchmaker.data

import com.xero.interview.bankrecmatchmaker.domain.SalesInvoice

interface XeroRepository {
    fun fetchSalesInvoices() : List<SalesInvoice>
}