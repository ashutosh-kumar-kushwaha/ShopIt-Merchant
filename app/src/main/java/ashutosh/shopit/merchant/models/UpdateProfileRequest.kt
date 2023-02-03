package ashutosh.shopit.merchant.models

data class UpdateProfileRequest(
    val firstname: String,
    val lastname: String,
    val gender: String
)