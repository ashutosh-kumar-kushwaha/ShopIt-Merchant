package ashutosh.shopit.merchant.models

data class VerifyOtpRequest(
    val email: String,
    val one_time_password: String
)