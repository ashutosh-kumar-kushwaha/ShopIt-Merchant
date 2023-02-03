package ashutosh.shopit.merchant.models

data class OtpVerificationRequest(
    val email: String,
    val oneTimePassword: String
)