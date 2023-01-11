package com.egoriku.grodnoroads.map.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.R
import com.egoriku.grodnoroads.resources.R as R_resources

@Composable
fun UsersCount(modifier: Modifier = Modifier, count: Int) {
    Surface(modifier = modifier.clip(RoundedCornerShape(10.dp))) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.padding(end = 3.dp)
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_info),
                contentDescription = null
            )
            Text(
                text = stringResource(id = R_resources.string.map_user_count, count),
                style = MaterialTheme.typography.overline
            )
        }
    }
}