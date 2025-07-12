@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.jfpsolucoes.unipplus2.modules.dashboard.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.modules.dashboard.domain.models.UPDashBoardNavigationItem
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun UPDashboardStudentStatusCard(
    modifier: Modifier = Modifier,
    fullName: String,
    photoBitmap: ImageBitmap? = null,
    courseName: String,
    statusColor: Color = MaterialTheme.colorScheme.inversePrimary,
    notificationsCount: Int = 0,
    onClickNotifications: (ThreePaneScaffoldDestinationItem<UPDashBoardNavigationItem>) -> Unit = {}
) = Card(
    modifier = modifier.padding(horizontal = 16.dp),
    colors = CardDefaults.cardColors(
        containerColor = colorResource(id = R.color.secondaryContainer)
    )
) {
    val photoPainter = if (photoBitmap == null) painterResource(id = R.drawable.ic_filled_person_24)
    else BitmapPainter(photoBitmap)
    Row(
        modifier = Modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileImageView(
            modifier = Modifier.size(80.dp),
            statusColor = statusColor,
            painter = photoPainter
        )

        ProfileInfo(
            modifier = Modifier.weight(1f),
            fullName = fullName,
            courseName = courseName
        )

        BadgeView(
            "$notificationsCount",
            onClick = { onClickNotifications(UPDashBoardNavigationItem.NOTIFICATIONS.destination) }
        )
    }
}

@Composable
fun ProfileInfo(
    modifier: Modifier = Modifier,
    fullName: String,
    courseName: String
) = Column(
    modifier = modifier
) {
    Text(text = fullName, color = colorResource(id = R.color.onSecondaryContainer))
    Text(text = courseName, color = colorResource(id = R.color.onSecondaryContainer))
}

@Composable
private fun ProfileImageView(
    modifier: Modifier = Modifier,
    statusColor: Color,
    painter: Painter
) = Surface(
    shape = CircleShape,
    border = BorderStroke(width = 4.dp, color = statusColor)
) {
    Icon(
        modifier = modifier
            .size(80.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .clipToBounds(),
        painter = painter,
        contentDescription = ""
    )
}

@Composable
private fun BadgeView(
    count: String,
    onClick: () -> Unit = {}
) = BadgedBox(
    modifier = Modifier.clickable(onClick = onClick),
    badge = { Badge { Text(text = count) } }
) {
    Icon(
        imageVector = Icons.Outlined.Notifications,
        contentDescription = "",
        tint = colorResource(id = R.color.onSecondaryContainer)
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeStudentStatusPreview() {
    UNIPPlus2Theme {
        UPDashboardStudentStatusCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fullName = "Student Name",
            courseName = "Course Name",
            notificationsCount = 10
        )
    }
}