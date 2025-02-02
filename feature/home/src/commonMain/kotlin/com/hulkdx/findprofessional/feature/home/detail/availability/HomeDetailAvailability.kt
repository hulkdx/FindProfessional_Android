@file:Suppress("FunctionName")

package com.hulkdx.findprofessional.feature.home.detail.availability

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.resources.Res
import com.hulkdx.findprofessional.core.resources.availability
import com.hulkdx.findprofessional.core.resources.ic_calendar_left
import com.hulkdx.findprofessional.core.resources.ic_calendar_right
import com.hulkdx.findprofessional.core.ui.theme.AppTheme
import com.hulkdx.findprofessional.core.ui.theme.body1
import com.hulkdx.findprofessional.core.ui.theme.body1Medium
import com.hulkdx.findprofessional.core.ui.theme.h3Medium
import com.hulkdx.findprofessional.core.utils.DateUtils.weekNumberMap
import com.hulkdx.findprofessional.feature.home.detail.utils.HomeScreenDimens.OUTER_HORIZONTAL_PADDING
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

internal fun LazyListScope.Availability(
    availability: AvailabilityData,
    availabilityMonthMinusOne: () -> Unit,
    availabilityMonthPlusOne: () -> Unit,
) {
    item { AvailabilityHeader() }
    item { AvailabilityCalendar(availability, availabilityMonthMinusOne, availabilityMonthPlusOne) }
}

@Composable
private fun AvailabilityHeader() {
    Text(
        modifier = Modifier
            .padding(
                top = 32.dp,
                bottom = 16.dp,
                start = OUTER_HORIZONTAL_PADDING.dp,
            ),
        style = body1Medium,
        text = stringResource(Res.string.availability),
    )
}

@Composable
private fun AvailabilityCalendar(
    availability: AvailabilityData,
    availabilityMonthMinusOne: () -> Unit,
    availabilityMonthPlusOne: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = OUTER_HORIZONTAL_PADDING.dp)
            .padding(bottom = 32.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(bottom = 8.dp)
    ) {
        AvailabilityCalendarTopHeader(
            availability,
            availabilityMonthMinusOne,
            availabilityMonthPlusOne
        )
        AvailabilityCalendarMainContent(availability)
    }
}

@Composable
private fun AvailabilityCalendarTopHeader(
    availability: AvailabilityData,
    availabilityMonthMinusOne: () -> Unit,
    availabilityMonthPlusOne: () -> Unit,
) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        AvailabilityCalendarTopHeaderButton(
            icon = Res.drawable.ic_calendar_left,
            onClick = availabilityMonthMinusOne,
        )
        AvailabilityCalendarTopHeaderMainText(availability.currentMonth)
        AvailabilityCalendarTopHeaderButton(
            icon = Res.drawable.ic_calendar_right,
            onClick = availabilityMonthPlusOne
        )
    }
}

@Composable
private fun RowScope.AvailabilityCalendarTopHeaderMainText(currentMonth: String) {
    Text(
        modifier = Modifier
            .weight(1F)
            .align(Alignment.CenterVertically),
        text = currentMonth,
        style = h3Medium,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun AvailabilityCalendarTopHeaderButton(
    icon: DrawableResource,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.padding(vertical = 6.dp),
        onClick = onClick,
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "",
        )
    }
}

@Composable
private fun AvailabilityCalendarMainContent(
    availability: AvailabilityData,
) {
    Row(Modifier.padding(horizontal = 8.dp)) {
        for (dayIndex in 0..<7) {
            DayColumn(dayIndex, availability)
        }
    }
}

@Composable
private fun RowScope.DayColumn(
    dayIndex: Int,
    availability: AvailabilityData,
) {
    Column(modifier = Modifier.weight(1F)) {
        DayText(dayIndex)
        DayDivider()
        for (weekIndex in 0..<availability.perWeek) {
            Day(availability, dayIndex, weekIndex)
        }
    }
}

@Composable
private fun DayText(dayIndex: Int) {
    val text = remember(dayIndex) { requireNotNull(weekNumberMap[dayIndex]) }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        style = body1Medium,
    )
}

@Composable
private fun DayDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.errorContainer
    )
}

@Composable
private fun Day(
    availability: AvailabilityData,
    dayIndex: Int,
    weekIndex: Int,
) {
    val lastDay = availability.lengthOfMonth
    val firstDay = availability.firstDay
    val day = (dayIndex + 1) + (weekIndex * 7) - firstDay
    val isEmptyDay = day <= 0 || day > lastDay

    if (isEmptyDay) {
        EmptyDay()
    } else if (availability.isSelectedDay(day)) {
        SelectedDay(day)
    } else {
        NormalDay(day)
    }
}

@Composable
private fun EmptyDay() {
    Box(modifier = Modifier.aspectRatio(1F))
}

@Composable
private fun SelectedDay(day: Int) {
    val backgroundColor = MaterialTheme.colorScheme.outlineVariant
    CommonDay(
        modifier = Modifier.drawBehind {
            drawCircle(backgroundColor)
        },
        text = day.toString(),
        textColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
private fun NormalDay(day: Int) {
    CommonDay(
        modifier = Modifier.border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.errorContainer,
            shape = CircleShape,
        ),
        text = day.toString(),
        textColor = MaterialTheme.colorScheme.errorContainer,
    )
}

@Composable
private fun CommonDay(
    modifier: Modifier,
    text: String,
    textColor: Color,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1F)
            .padding(4.dp)
            .then(modifier)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = body1,
            color = textColor
        )
    }
}

@Preview
@Composable
fun AvailabilityPreview() {
    AppTheme {
        LazyColumn(Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
            Availability(
                AvailabilityData(
                    "January 2022",
                    5,
                    31,
                    LocalDate(2022, 1, 1),
                    listOf(
                        LocalDate(2022, 1, 6),
                        LocalDate(2022, 1, 7),
                        LocalDate(2022, 1, 12),
                    ),
                ),
                {},
                {}
            )
        }
    }
}
