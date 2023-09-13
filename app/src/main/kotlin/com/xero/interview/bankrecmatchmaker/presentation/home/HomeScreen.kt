package com.xero.interview.bankrecmatchmaker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xero.interview.bankrecmatchmaker.R
import com.xero.interview.bankrecmatchmaker.domain.SalesInvoice
import com.xero.interview.bankrecmatchmaker.presentation.SalesInvoiceUIState
import com.xero.interview.bankrecmatchmaker.presentation.model.SalesInvoiceDataDisplay
import com.xero.interview.bankrecmatchmaker.presentation.textRegularColor
import com.xero.interview.bankrecmatchmaker.presentation.textSubTextColor

@Composable
fun HomeScreen(state: SalesInvoiceUIState,
               onItemChecked: (SalesInvoiceDataDisplay, Boolean) -> Unit) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Column {
                Row {
                    TopAppBar(
                        title = { Text("Find match") },
                        backgroundColor = colors.primary,
                        elevation = 4.dp,
                        navigationIcon = {
                            IconButton(modifier = Modifier, onClick = {}) {
                                Icon(imageVector = Icons.Default.ArrowBack,
                                    tint = Color.White,
                                    contentDescription = "navIcon")
                            }
                        }
                    )
                }
                Row(modifier = Modifier
                    .height(36.dp)
                    .background(colors.primary)) {
                        Text(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = 10.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.select_matches, state.target),
                            style = MaterialTheme.typography.body2
                        )
                }
            }
        },
        content = { pValues ->
            LazyColumn(modifier = Modifier.padding(top = 5.dp).testTag("InvoicesList")) {
                items(state.data) {
                    SaleInvoiceItemView(it, onItemChecked)
                }
            }
        }
    )
}

@Composable
@Preview
fun HomeScreenPreview() {
    val mockData = SalesInvoice.buildMockData().map {
        SalesInvoiceDataDisplay(invoice = SalesInvoice(it.id,it.paidTo, it.transactionDate, it.total, it.docType), isSelected = false)
    }
    HomeScreen(SalesInvoiceUIState().copy(data = mockData), onItemChecked = { _, _ ->})
}

@Composable
fun SaleInvoiceItemView(data: SalesInvoiceDataDisplay,
                        onItemChecked: (SalesInvoiceDataDisplay, Boolean) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = Modifier.testTag("Check_${data.invoice.paidTo}"),
            checked = data.isSelected,
            onCheckedChange = {
                onItemChecked(data, it)
            }
        )

        SaleInvoiceColumnText(title = data.invoice.paidTo,
                              subtitle = data.invoice.transactionDate,
                              modifier = Modifier, textAlign = TextAlign.Start)

        SaleInvoiceColumnText(title = data.invoice.total.toString(),
                              subtitle = data.invoice.docType,
                              modifier = Modifier.fillMaxWidth().padding(end = 15.dp), textAlign = TextAlign.End)
    }
}

@Composable
@Preview
fun SaleInvoiceItemViewPreview() {
    SaleInvoiceItemView(data = SalesInvoiceDataDisplay(invoice = SalesInvoice(1,"City Limousines", "30 Aug", 249.00f, "Sales Invoice"), isSelected = false),
                        onItemChecked = {_, _ -> })
}

@Composable
fun SaleInvoiceColumnText(title: String, subtitle: String,
                          modifier: Modifier, textAlign: TextAlign) {
    Column(modifier = Modifier.height(72.dp),
           verticalArrangement = Arrangement.Center) {
        Row {
            Text(modifier = modifier.testTag(title),
                 text = title,
                 textAlign = textAlign,
                 style = MaterialTheme.typography.body1.copy(color = textRegularColor))
        }
        Row {
            Text(modifier = modifier.testTag(subtitle),
                 text = subtitle,
                 textAlign = textAlign,
                 style = MaterialTheme.typography.body2.copy(color = textSubTextColor))
        }
    }
}

@Composable
@Preview
fun SaleInvoiceColumnTextPreview() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        Spacer(modifier = Modifier.fillMaxWidth(0.1f))
        SaleInvoiceColumnText(
            title = "Gateway Motors", subtitle = "18 Sep",
            modifier = Modifier, textAlign = TextAlign.Start
        )
    }
}