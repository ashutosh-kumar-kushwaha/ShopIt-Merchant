package ashutosh.shopit.merchant.models

data class Review(
    val images: List<Image>,
    val rating: String,
    val description: String,
    val issueTime: String,
    val user: User,
    val id: Int
)