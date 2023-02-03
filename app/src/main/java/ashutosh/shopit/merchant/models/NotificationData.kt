package ashutosh.shopit.merchant.models

data class NotificationData(
    val id: Int,
    val title: String,
    val time : String,
    val message: String,
    val image: Int
)