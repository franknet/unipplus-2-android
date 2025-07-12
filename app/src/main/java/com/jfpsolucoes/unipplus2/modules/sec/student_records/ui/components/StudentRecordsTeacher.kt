package com.jfpsolucoes.unipplus2.modules.sec.student_records.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.jfpsolucoes.unipplus2.ui.theme.Typography

@Composable
fun StudentRecordsTeacher(
    modifier: Modifier = Modifier,
    name: String,
    onEdit: (String) -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(4.dp)
) {
    var isEditingTeacherName by rememberSaveable {
        mutableStateOf(false)
    }

    val focusRequester = FocusRequester()

    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        text = "Professor"
    )

    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TeacherNameField(
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .onGloballyPositioned {
                    if (isEditingTeacherName) focusRequester.requestFocus()
                },
            name = name,
            onEdit = onEdit,
            isEditing = isEditingTeacherName,
        )

        EditSaveButton(isEditing = isEditingTeacherName) {
            isEditingTeacherName = !isEditingTeacherName
        }
    }
}

@Composable
private fun TeacherNameField(
    modifier: Modifier = Modifier,
    isEditing: Boolean,
    name: String,
    onEdit: (String) -> Unit
) = when {
    isEditing -> OutlinedTextField(
        modifier = modifier,
        value = name,
        onValueChange = onEdit
    )
    else -> Text(
        modifier = modifier,
        text = name,
        style = Typography.bodyLarge
    )
}

@Composable
private fun EditSaveButton(
    isEditing: Boolean,
    onClick: () -> Unit
) = IconButton(onClick = onClick) {
    when {
        isEditing -> Icon(imageVector = Icons.Filled.Done, contentDescription = "")
        else -> Icon(imageVector = Icons.Filled.Edit, contentDescription = "")
    }
}