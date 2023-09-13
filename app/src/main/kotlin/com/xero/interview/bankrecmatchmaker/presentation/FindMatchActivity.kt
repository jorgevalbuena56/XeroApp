package com.xero.interview.bankrecmatchmaker.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xero.interview.bankrecmatchmaker.presentation.home.HomeScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FindMatchActivity : ComponentActivity() {

    companion object {
        const val TARGET_MATCH_VALUE = "com.xero.interview.target_match_value"
    }

    private val viewModel : FindMatchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val target = intent.getFloatExtra(TARGET_MATCH_VALUE, 216.99f)

        setContent {
            XeroAppTheme {
                val state : SalesInvoiceUIState by viewModel.uiState.collectAsStateWithLifecycle(
                    initialValue = SalesInvoiceUIState(), lifecycleOwner = LocalLifecycleOwner.current
                )
                HomeScreen(state) { item, isSelected ->
                    viewModel.updateSalesInvoicesMatchTarget(item, isSelected)
                }
            }
        }

        viewModel.getSaleInvoicesWithTarget(target)
    }
}