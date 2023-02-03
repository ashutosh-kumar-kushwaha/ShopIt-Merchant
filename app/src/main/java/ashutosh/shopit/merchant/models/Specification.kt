package ashutosh.shopit.merchant.models

data class Specification(
    val id: Int,
    val head: String,
    val body: List<Body>
)