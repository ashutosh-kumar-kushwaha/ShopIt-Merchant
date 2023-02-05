package ashutosh.shopit.merchant.models

data class AddProductRequest(
    val productName: String,
    val originalPrice: String,
    val offerPercentage: String,
    val quantityAvailable: String,
    val offers: List<OfferX>,
    val warranty: String,
    val highlights: String,
    val services: String,
    val specification: List<SpecificationX>,
    val description: List<Desc>,
    val questions: List<QuestionX>
)