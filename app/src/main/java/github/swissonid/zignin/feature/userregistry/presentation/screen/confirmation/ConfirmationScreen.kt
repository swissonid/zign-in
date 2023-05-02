@file:OptIn(ExperimentalMaterial3Api::class)

package github.swissonid.zignin.feature.userregistry.presentation.screen.confirmation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import github.swissonid.zignin.R
import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser
import github.swissonid.zignin.feature.userregistry.domain.model.UserId
import github.swissonid.zignin.ui.theme.ZigninTheme
import kotlinx.datetime.toLocalDate

@Composable
fun SmartConfirmationScreen(
    confirmationViewModel: ConfirmationViewModel,
) {
    val confirmationUiState by confirmationViewModel.uiState.collectAsState()
    ConfirmationScreen(
        uiState = confirmationUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationScreen(
    uiState: ConfirmationUiState = ConfirmationUiState(),
) {
    Scaffold {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.confirmation_screen_thank_you),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 48.sp,
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.fillMaxWidth().height(96.dp))

            if (uiState.user != null) {

                Column {
                    val spacerModifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Heart()
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(64.dp))
                    TextWithLabel(
                        label = stringResource(R.string.registration_screen__label_name),
                        text = "${uiState.user.name}"
                    )
                    Spacer(modifier = spacerModifier)
                    TextWithLabel(
                        label = stringResource(R.string.registration_screen__label_email),
                        text = "${uiState.user.email}"
                    )
                    Spacer(modifier = spacerModifier)
                    TextWithLabel(
                        label = stringResource(R.string.registration_screen__label_birthday),
                        text = "${uiState.user.birthday}"
                    )
                }
            } else {
                Text(stringResource(id = R.string.error__general))
            }
        }
    }
}

@Composable
private fun Heart() {
    val infiniteTransition = rememberInfiniteTransition()
    val heartbeatAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        )
    )
    Icon(
        Icons.Sharp.Favorite,
        tint = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .size(72.dp)
            .scale(heartbeatAnimation),
        contentDescription = null
    )
}

@Composable
private fun TextWithLabel(label: String, text: String) {
    Text(
        label,
        style = MaterialTheme.typography.labelLarge.copy(
            color = MaterialTheme.colorScheme.primary
        )
    )
    Text(text, style = MaterialTheme.typography.bodyLarge)
}


@Preview(locale = "de")
@Composable
fun ConfirmationScreenPreview() {
    ZigninTheme {
        ConfirmationScreen(
            uiState = ConfirmationUiState(
                user = RegisteredUser(
                    id = UserId("2387432"),
                    name = Name("Test Name"),
                    birthday = Birthday("1985-11-01".toLocalDate()),
                    email = EMail("test@testing.com")
                )
            )
        )
    }
}