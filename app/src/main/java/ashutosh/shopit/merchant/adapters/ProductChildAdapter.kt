package ashutosh.shopit.merchant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.merchant.databinding.ProductChildItemBinding
import ashutosh.shopit.merchant.models.Product
import coil.load
import kotlin.math.roundToInt

class ProductChildAdapter: ListAdapter<Product, ProductChildAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding: ProductChildItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product){
            binding.productImgVw.load(product.imageUrls.imageUrl)
            val discountedPrice = "₹${(product.originalPrice-(product.offerPercentage/100)*product.originalPrice).roundToInt()}"
            binding.discountedPriceTxtVw.text = discountedPrice
            binding.ratingTxtVw.text = product.rating.toString()
            val soldText = "${product.noOfOrders} sold"
            binding.soldTxtVw.text = soldText
            binding.productTitleTxtVw.text = product.productName
            val originalPrice = "₹${product.originalPrice.roundToInt()}"
            binding.originalPriceTxtVw.text = originalPrice
        }
    }

    class DiffUtil: ItemCallback<Product>(){
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductChildItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}