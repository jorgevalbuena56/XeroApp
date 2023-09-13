package com.xero.interview.bankrecmatchmaker

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.xero.interview.bankrecmatchmaker.presentation.FindMatchActivity
import org.junit.Rule
import org.junit.Test

class XeroHomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<FindMatchActivity>()

    @Test
    fun show_home_screen_with_initial_target_successful() {
        val matchesLabel = composeTestRule.activity.getString(R.string.select_matches, 0.0f)
        composeTestRule.onNodeWithText(matchesLabel).assertIsDisplayed()
    }

    @Test
    fun show_sales_invoice_pc_complete_is_selected_on_start_successful() {
        composeTestRule.onNodeWithTag("InvoicesList").onChildren().assertCountEquals(50)
        composeTestRule.onNodeWithTag("InvoicesList")
            .performScrollToNode(hasText("PC Complete"))

        composeTestRule.onNodeWithTag("PC Complete").assertExists()

        composeTestRule.onNode(
            (hasTestTag("Check_PC Complete") and isToggleable())
        ).assertIsOn()
    }

}