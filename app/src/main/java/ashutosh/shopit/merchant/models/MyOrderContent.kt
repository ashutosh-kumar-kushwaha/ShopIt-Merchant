package ashutosh.shopit.merchant.models

data class MyOrderContent(
    val orderId: String,
    val dateOfPurchase: Any?,
    val createdAt: String,
    val deliveryStatus: List<Any>,
    val amount: Int,
    val paymentId: String?,
    val status: String
)