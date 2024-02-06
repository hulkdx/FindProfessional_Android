package com.hulkdx.findprofessional.utils

import androidx.annotation.StringRes
import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performScrollToNode
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.hulkdx.findprofessional.MainActivity

typealias Rule = AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

fun AndroidComposeTestRule<*, *>.onNodeWithTextRes(
    @StringRes res: Int,
    substring: Boolean = false,
    ignoreCase: Boolean = false,
    useUnmergedTree: Boolean = false,
): SemanticsNodeInteraction {
    return onNodeWithText(activity.getString(res), substring, ignoreCase, useUnmergedTree)
}

fun AndroidComposeTestRule<*, *>.onNodeWithTagRes(@StringRes res: Int): SemanticsNodeInteraction {
    return onNodeWithTag(activity.getString(res))
}

fun Rule.waitUntilAppear(
    testTag: String,
    timeoutMillis: Long = 10_000,
) {
    try {
        waitUntil(timeoutMillis) {
            onAllNodesWithTag(testTag, useUnmergedTree = true).fetchSemanticsNodes().size == 1
        }
    } catch (e: ComposeTimeoutException) {
        throw RuntimeException("cannot find a node with test tag : $testTag after 10 seconds.")
    }
}

fun Rule.waitUntilAppearText(
    text: String,
    timeoutMillis: Long = 10_000,
) {
    try {
        waitUntil(timeoutMillis) {
            onAllNodesWithText(text, useUnmergedTree = true).fetchSemanticsNodes().size == 1
        }
    } catch (e: ComposeTimeoutException) {
        throw RuntimeException("cannot find a node with text : $text after 10 seconds.")
    }
}

fun Rule.assertNodeIsDisplayed(testTag: String) {
    waitUntilAppear(testTag = testTag)
    onNodeWithTag(testTag)
        .assertIsDisplayed()
}

fun Rule.pressBackButton() {
    Espresso.pressBackUnconditionally()
}

fun Rule.assertAppIsClosed(timeoutMillis: Long = 10_000) {
    try {
        waitUntil(timeoutMillis) {
            runCatching { onRoot().assertDoesNotExist() }.isSuccess
        }
    } catch (e: ComposeTimeoutException) {
        throw RuntimeException("The app is not closed after 10 seconds.")
    }
}

fun Rule.lazyColumnScrollTo(
    lazyColumnTestTag: String,
    nodeTestTag: String,
): SemanticsNodeInteraction {
    onNodeWithTag(lazyColumnTestTag)
        .assertIsDisplayed()
        .performScrollToNode(hasTestTag(nodeTestTag))
    return onNodeWithTag("showAllReviews")
}
