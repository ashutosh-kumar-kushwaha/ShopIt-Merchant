package ashutosh.shopit.merchant.models

data class ProductsParentContent(
    val category: Category,
    var products: List<Product>
)