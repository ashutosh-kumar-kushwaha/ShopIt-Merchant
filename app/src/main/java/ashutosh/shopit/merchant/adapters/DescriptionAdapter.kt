package ashutosh.shopit.merchant.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.merchant.databinding.DescriptionItemBinding
import ashutosh.shopit.merchant.models.Desc

class DescriptionAdapter(val descriptionList: ArrayList<Desc> = ArrayList()) : RecyclerView.Adapter<DescriptionAdapter.ViewHolder>() {

    init {
        descriptionList.add(Desc("", ""))
    }

    inner class ViewHolder(private val binding: DescriptionItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(description: Desc){
            binding.head.setText(description.head)
            binding.body.setText(description.body)
            binding.head.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    descriptionList[adapterPosition].head = binding.head.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            binding.body.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    descriptionList[adapterPosition].body = binding.body.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DescriptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(descriptionList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return descriptionList.size
    }
}