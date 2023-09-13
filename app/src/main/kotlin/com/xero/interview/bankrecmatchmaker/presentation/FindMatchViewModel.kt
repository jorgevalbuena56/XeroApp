package com.xero.interview.bankrecmatchmaker.presentation

import androidx.lifecycle.ViewModel
import com.xero.interview.bankrecmatchmaker.data.XeroRepository
import com.xero.interview.bankrecmatchmaker.domain.SalesInvoice
import com.xero.interview.bankrecmatchmaker.presentation.model.SalesInvoiceDataDisplay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SalesInvoiceUIState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val errorMsg: String = "",
    val target: Float = 0.0f,
    val maxTarget : Float = 0.0f,
    val data: List<SalesInvoiceDataDisplay> = emptyList()
)
class FindMatchViewModel(
    xeroRepository: XeroRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SalesInvoiceUIState())
    val uiState = _uiState.asStateFlow()

    init {
        val salesInvoicesDisplay = xeroRepository.fetchSalesInvoices().map { salesInvoice ->
            SalesInvoiceDataDisplay(invoice = salesInvoice, isSelected = false)
        }
        _uiState.value = SalesInvoiceUIState().copy(data = salesInvoicesDisplay)
    }

    fun getSaleInvoicesWithTarget(target: Float) {
        _uiState.value = _uiState.value.copy(isLoading = true)

        val exactMatch  = _uiState.value.data.firstOrNull { it.invoice.total == target }

        val newData = if (exactMatch != null) {
            uiState.value.data.map { salesInvoiceDataDisplay ->
                if (exactMatch.invoice.id == salesInvoiceDataDisplay.invoice.id) {
                    salesInvoiceDataDisplay.copy(isSelected = true)
                } else {
                    salesInvoiceDataDisplay
                }
            }
        } else {
            uiState.value.data
        }

        val newTarget = target - (exactMatch?.invoice?.total ?: 0.0f)

        _uiState.value = _uiState.value.copy(isLoading = false, maxTarget = target, target = newTarget, data = newData)
    }

    fun updateSalesInvoicesMatchTarget(item: SalesInvoiceDataDisplay, isSelected: Boolean) {
        var newTarget = _uiState.value.target

        val newList = _uiState.value.data.map { inv ->
            if (inv.invoice.id == item.invoice.id && inv.isSelected != isSelected) {
                newTarget = if (isSelected) {
                    _uiState.value.target - item.invoice.total
                } else {
                    _uiState.value.target + item.invoice.total
                }
                inv.copy(isSelected = isSelected)
            } else {
                inv
            }
        }
        _uiState.value = _uiState.value.copy(hasError = false, errorMsg = "", target = newTarget, data = newList)

    }
}