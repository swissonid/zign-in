package github.swissonid.zignin.feature.userregistry.presenation.registration

enum class FormFieldId {
    NAME_ID,
    EMAIL_ID,
    BIRTHDAY_ID,
}


data class RegistrationUiState(
    val currentFocusFieldId: FormFieldId? = null,
    // Possible improvement to make here a collection instead of this three fields
    val name: FormField = FormField(FormFieldId.NAME_ID),
    val email: FormField = FormField(FormFieldId.EMAIL_ID),
    val birthday: FormField = FormField(FormFieldId.BIRTHDAY_ID),
    val isFormValid: Boolean = false
)

data class FormField(
    val id: FormFieldId,
    val isDirty: Boolean = false,
    val isValid: Boolean = false,
    val value: String? = null,
)