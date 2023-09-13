package com.xero.interview.bankrecmatchmaker

import app.cash.turbine.test
import com.xero.interview.bankrecmatchmaker.data.XeroRepository
import com.xero.interview.bankrecmatchmaker.data.XeroRepositoryImpl
import com.xero.interview.bankrecmatchmaker.domain.SalesInvoice
import com.xero.interview.bankrecmatchmaker.presentation.FindMatchViewModel
import com.xero.interview.bankrecmatchmaker.presentation.model.SalesInvoiceDataDisplay
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class FindMatchViewModelTest {

    private lateinit var xeroRepository : XeroRepository

    private lateinit var viewModel: FindMatchViewModel

    @Before
    fun setUp() {
        xeroRepository = XeroRepositoryImpl()
        viewModel = FindMatchViewModel(xeroRepository)
    }

    @Test
    fun initial_ui_state_sales_invoices_list_successfully_fetched() {
        runTest {
            viewModel.uiState.test {
                val item = awaitItem()
                assertEquals(10, item.data.size)
                assertEquals(0.0f, item.target)
                cancelAndIgnoreRemainingEvents()
            }
            xeroRepository.fetchSalesInvoices()
        }
    }

    @Test
    fun match_target_with_sale_invoice_first_start_successfully() {
        runTest {
            viewModel.getSaleInvoicesWithTarget(216.99f)
            viewModel.uiState.test {
                val item = expectMostRecentItem()
                assertEquals(10, item.data.size)
                assertTrue("PC Complete was not selected", item.data[6].isSelected)
                assertEquals(0.0f, item.target)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun match_target_with_sale_invoice_first_start_not_successfully() {
        runTest {
            viewModel.getSaleInvoicesWithTarget(16.99f)
            viewModel.uiState.test {
                val item = expectMostRecentItem()
                assertEquals(10, item.data.size)
                assertNull("One Invoice was selected", item.data.firstOrNull { it.isSelected })
                assertEquals(16.99f, item.target)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun single_sale_invoice_selected_target_reduced_successfully() {
        runTest {
            viewModel.getSaleInvoicesWithTarget(500.00f)
            val selected = SalesInvoiceDataDisplay(
                invoice = SalesInvoice(id = 3, paidTo = "", transactionDate = "", total = 100.0f, docType = ""),
                isSelected = false
            )
            viewModel.updateSalesInvoicesMatchTarget(selected, true)
            //viewModel.updateSalesInvoicesMatchTarget(selected, true)
            viewModel.uiState.test {
                val item = expectMostRecentItem()
                assertEquals(10, item.data.size)
                assertTrue("Invoice was not selected", item.data.first { it.invoice.id == 3 }.isSelected)
                assertEquals(400.00f, item.target)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun single_sale_invoice_already_selected_and_trying_to_select_again_target_should_not_change_success() {
        runTest {
            viewModel.getSaleInvoicesWithTarget(250.00f)
            val selected = SalesInvoiceDataDisplay(
                invoice = SalesInvoice(id = 5, paidTo = "", transactionDate = "", total = 100.0f, docType = ""),
                isSelected = false
            )
            viewModel.updateSalesInvoicesMatchTarget(selected, true)
            viewModel.uiState.test {
                val item = expectMostRecentItem()
                assertEquals(10, item.data.size)
                assertTrue("Invoice was not selected", item.data.first { it.invoice.id == 5 }.isSelected)
                assertEquals(0.00f, item.target)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun single_sale_invoice_unselected_target_increased_successfully() {
        runTest {
            viewModel.getSaleInvoicesWithTarget(250.00f)
            val unSelected = SalesInvoiceDataDisplay(
                invoice = SalesInvoice(id = 5, paidTo = "", transactionDate = "", total = 100.0f, docType = ""),
                isSelected = false
            )
            viewModel.updateSalesInvoicesMatchTarget(unSelected, false)
            viewModel.uiState.test {
                val item = expectMostRecentItem()
                assertEquals(10, item.data.size)
                assertFalse("Invoice was not deselected", item.data.first { it.invoice.id == 5 }.isSelected)
                assertEquals(100.00f, item.target)
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

}