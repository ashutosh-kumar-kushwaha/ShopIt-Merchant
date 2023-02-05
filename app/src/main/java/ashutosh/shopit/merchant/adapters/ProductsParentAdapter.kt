package ashutosh.shopit.merchant.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.merchant.databinding.ProductsParentItemBinding
import ashutosh.shopit.merchant.models.ProductsParentContent

class ProductsParentAdapter : ListAdapter<ProductsParentContent, ProductsParentAdapter.ViewHolder>(DiffUtil()) {
    inner class ViewHolder(private val binding: ProductsParentItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productParentContent: ProductsParentContent){
            binding.categoryTxtVw.text = productParentContent.category.categoryName
            val productChildAdapter = ProductChildAdapter()
            binding.productsRecyclerView.adapter = productChildAdapter
            binding.productsRecyclerView.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL ,false)
            productChildAdapter.submitList(productParentContent.products)
        }
    }

    class DiffUtil: ItemCallback<ProductsParentContent>(){
        override fun areItemsTheSame(
            oldItem: ProductsParentContent,
            newItem: ProductsParentContent
        ): Boolean {
            return oldItem.category.categoryId == newItem.category.categoryId
        }

        override fun areContentsTheSame(
            oldItem: ProductsParentContent,
            newItem: ProductsParentContent
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProductsParentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}