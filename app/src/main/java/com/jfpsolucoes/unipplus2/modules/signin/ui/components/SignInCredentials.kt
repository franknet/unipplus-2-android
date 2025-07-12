package com.jfpsolucoes.unipplus2.modules.signin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.utils.extensions.saveableMutableState
import com.jfpsolucoes.unipplus2.modules.signin.domain.models.UPSignInCredentialsProperties
import com.jfpsolucoes.unipplus2.ui.components.button.LoadingButton

@Composable
fun SignInCredentials(
    modifier: Modifier = Modifier,
    properties: UPSignInCredentialsProperties
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(properties.contentWidthFraction)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RaTextField(
            text = properties.idText,
            onEdit = properties.onEditId
        )

        PasswordTextField(
            text = properties.passwordText,
            onEdit = properties.onEditPassword
        )

        LoadingButton(
            modifier = Modifier.height(60.dp),
            title = stringResource(id = R.string.sign_in_button_title),
            isLoading = properties.isLoading,
            onClick = properties.onClickSignIn
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
private fun PasswordTextField(text: String, onEdit: (String) -> Unit) {
    var isPasswordVisible by false.saveableMutableState
    val setPasswordVisibility: (Boolean) -> Unit = { isPasswordVisible = it }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = { PasswordTextFieldPlaceHolder() },
        value = text,
        onValueChange = onEdit,
        trailingIcon = {
            PasswordTextFieldTrailingIcon(
                isVisible = isPasswordVisible,
                onClick = setPasswordVisibility
            )
        },
        visualTransformation = passwordVisualTransformation(isPasswordVisible),
        shape = CircleShape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Composable
private fun PasswordTextFieldPlaceHolder() {
    Text(text = stringResource(id = R.string.sign_in_password_place_holder))
}

@Composable
private fun PasswordTextFieldTrailingIcon(isVisible: Boolean, onClick: (Boolean) -> Unit) {
    PasswordTrailingIcon(
        isVisible = isVisible,
        onClick = { onClick(!isVisible) }
    )
}

@Composable
private fun PasswordTrailingIcon(isVisible: Boolean, onClick: () -> Unit = {}) {
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
        properties = UPSignInCredentialsProperties(
            idText = "",
            passwordText = ""
        )
    )
}