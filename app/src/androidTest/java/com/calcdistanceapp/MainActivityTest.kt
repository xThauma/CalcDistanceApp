package com.calcdistanceapp

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.calcdistanceapp.di.DatabaseModule
import com.calcdistanceapp.di.NetworkModule
import com.calcdistanceapp.presentation.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DatabaseModule::class, NetworkModule::class)
class MainActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_texts_are_displayed() {
        rule.onNodeWithText("Calculate distance").assertIsDisplayed()
        rule.onNodeWithText("Choose 2 stations to calculate distance between them").assertIsDisplayed()
        rule.onNodeWithText("Starting station").assertIsDisplayed()
        rule.onNodeWithText("Ending station").assertIsDisplayed()

        // first search
        rule.onNodeWithText("Starting station").performClick()
        rule.onNodeWithText("Central Station").assertIsDisplayed()
        rule.onNodeWithText("Central Station").performClick()
        rule.onNodeWithText("Central Station").assertIsDisplayed()

        // second search
        rule.onNodeWithText("Ending station").performClick()
        rule.onNodeWithText("King's Cross").assertIsDisplayed()
        rule.onNodeWithText("King's Cross").performClick()
        rule.onNodeWithText("King's Cross").assertIsDisplayed()

        // check distance
        rule.onNodeWithText("Total distance: 3460 km").assertIsDisplayed()

        // check for 2 same stations
        rule.onNodeWithText("King's Cross").performClick()
        rule.onNodeWithContentDescription("delete text").assertIsDisplayed()
        rule.onNodeWithContentDescription("delete text").performClick()
        val kingsCrosses = rule.onAllNodesWithText("Central Station")
        kingsCrosses.assertCountEquals(2)
        kingsCrosses[0].assertIsDisplayed()
        kingsCrosses[1].assertIsDisplayed()
        kingsCrosses[1].performClick()

        rule.onNodeWithText("You can't calculate distance between 2 same stations").assertIsDisplayed()
    }
}