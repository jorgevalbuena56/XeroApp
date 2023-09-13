package com.xero.interview.bankrecmatchmaker.presentation.model

import com.xero.interview.bankrecmatchmaker.domain.SalesInvoice

data class SalesInvoiceDataDisplay (
    val invoice: SalesInvoice,
    var isSelected : Boolean
)