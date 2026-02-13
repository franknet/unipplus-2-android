package com.jfpsolucoes.unipplus2.modules.signin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.database.entities.UPSettingsEntity
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.components.button.LoadingButton
import com.jfpsolucoes.unipplus2.ui.components.unipplus.student.UPStudentInfoCard
import com.jfpsolucoes.unipplus2.ui.styles.defaultTextFieldColors
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme


@Composable
fun SignInCredentials(
    modifier: Modifier = Modifier,
    userName: String? = null,
    userCourse: String? = null,
    raText: String,
    onEditRa: (String) -> Unit,
    passwordText: String,
    onEditPassword: (String) -> Unit,
    showPasswordField: Boolean = true,
    autoSignChecked: Boolean = true,
    onAutoSignInChange: (Boolean) -> Unit = {},
    onLoading: Boolean = false,
    onClickSignIn: () -> Unit,
) = Box(
    modifier,
    contentAlignment = Alignment.Center
)  {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val fraction = when {
        windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> 0.5f
        else -> 1f
    }

    Column(
        modifier = Modifier.fillMaxWidth(fraction).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userName != null && userCourse != null) {
            UPStudentInfoCard(
                name = userName,
                course = userCourse
            )
        } else {
            RaTextField(
                text = raText,
                onEdit = onEditRa
            )
        }

        if (showPasswordField) {
            PasswordTextField(
                enabled = !onLoading,
                text = passwordText,
                onEdit = onEditPassword
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.common_auto_sign_in_text))

            Switch(
                enabled = !onLoading,
                checked = autoSignChecked,
                onCheckedChange = onAutoSignInChange
            )
        }

        LoadingButton(
            modifier = Modifier.height(60.dp),
            title = stringResource(id = R.string.sign_in_button_title),
            isLoading = onLoading,
            onClick = onClickSignIn
        )
    }
}

@Composable
private fun RaTextField(text: String = "", onEdit: (String) -> Unit) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = { RaTextFieldPlaceHolder() },
        value = text,
        onValueChange = onEdit,
        colors = defaultTextFieldColors
    )
}

@Composable
private fun RaTextFieldPlaceHolder() {
    Text(text = stringResource(id = R.string.sign_in_id_place_holder))
}

@Composable
private fun PasswordTextField(
    enabled: Boolean = true,
    text: String,
    onEdit: (String) -> Unit
) {
    var isPasswordVisible by false.saveableMutableState
    val setPasswordVisibility: (Boolean) -> Unit = { isPasswordVisible = it }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        placeholder = {
            Text(text = stringResource(id = R.string.sign_in_password_place_holder))
        },
        value = text,
        onValueChange = onEdit,
        trailingIcon = {
            PasswordTextFieldTrailingIcon(
                isVisible = isPasswordVisible,
                onClick = setPasswordVisibility
            )
        },
        visualTransformation = passwordVisualTransformation(isPasswordVisible),
        colors = defaultTextFieldColors
    )
}

@Composable
private fun PasswordTextFieldTrailingIcon(isVisible: Boolean, onClick: (Boolean) -> Unit) {
    PasswordTrailingIcon(
        isVisible = isVisible,
        onClick = { onClick(!isVisible) }
    )
}

@Composable
private fun PasswordTrailingIcon(
    isVisible: Boolean, onClick: () -> Unit = {}
) {
    val drawableId = when {
        isVisible -> R.drawable.ic_outline_visibility_24
        else -> R.drawable.ic_outline_visibility_off_24
    }
    Icon(
        modifier = Modifier.clickable { onClick() },
        painter = painterResource(id = drawableId),
        contentDescription = ""
    )
}

private fun passwordVisualTransformation(isVisible: Boolean): VisualTransformation = when {
    isVisible -> VisualTransformation.None
    else -> PasswordVisualTransformation()
}

@Preview(showBackground = true)
@Composable
private fun SignInBottomSheetViewPreview() {
    UNIPPlus2Theme {
        SignInCredentials(
            modifier = Modifier.fillMaxWidth(),
            onLoading = false,
            raText = "",
            onEditRa = {},
            passwordText = "",
            onEditPassword = {},
            onClickSignIn = {}
        )
    }
}