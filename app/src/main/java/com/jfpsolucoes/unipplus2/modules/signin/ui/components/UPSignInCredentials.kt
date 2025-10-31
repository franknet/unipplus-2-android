package com.jfpsolucoes.unipplus2.modules.signin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.core.utils.extensions.value
import com.jfpsolucoes.unipplus2.ui.components.button.LoadingButton
import com.jfpsolucoes.unipplus2.ui.components.unipplus.student.UPStudentInfoCard

@Composable
fun SignInCredentials(
    modifier: Modifier = Modifier,
    onLoading: Boolean = false,
    userProfile: UPUserProfileEntity = UPUserProfileEntity(),
    raText: String,
    onEditRa: (String) -> Unit,
    passwordText: String,
    passwordSupportingText: String? = null,
    passwordFieldVisible: Boolean = true,
    onEditPassword: (String) -> Unit,
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
        if (userProfile.user != null) {
            UPStudentInfoCard(
                name = userProfile.user.name.value,
                course = userProfile.academic?.course?.name.value
            )

        } else {
            RaTextField(
                text = raText,
                onEdit = onEditRa
            )
        }

        if (passwordFieldVisible) {
            PasswordTextField(
                enabled = !onLoading,
                text = passwordText,
                onEdit = onEditPassword,
                supportingText = passwordSupportingText
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
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
private fun RaTextFieldPlaceHolder() {
    Text(text = stringResource(id = R.string.sign_in_id_place_holder))
}

@Composable
private fun PasswordTextField(
    enabled: Boolean,
    text: String,
    supportingText: String? = null,
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
        supportingText = {
            supportingText?.let {
                Text(it)
            }
        },
        isError = supportingText != null,
        visualTransformation = passwordVisualTransformation(isPasswordVisible),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
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
    SignInCredentials(
        modifier = Modifier.fillMaxWidth(),
        raText = "",
        onEditRa = {},
        passwordText = "",
        onEditPassword = {},
        onClickSignIn = {}
    )
}