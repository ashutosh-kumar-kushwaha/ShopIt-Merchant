package ashutosh.shopit.merchant.models

data class AddProductResponse(
    val productId: Int,
    val availInWishlist: Boolean,
    val productName: String,
    val imageUrls: List<ImageUrl>,
    val originalPrice: Double,
    val offerPercentage: Double,
    val rating: Double,
    val quantityAvailable: Int,
    val warranty: String,
    val offers: List<OfferXX>,
    val services: String,
    val specification: List<SpecificationXX>,
    val description: List<DescriptionXX>,
    val category: List<CategoryX>,
    val provider: ProviderX,
    val highlights: String,
    val noOfOrders: Int
)