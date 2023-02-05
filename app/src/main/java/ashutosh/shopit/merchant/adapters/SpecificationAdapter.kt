package ashutosh.shopit.merchant.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ashutosh.shopit.merchant.databinding.SpecificationItemBinding
import ashutosh.shopit.merchant.models.Specs

class SpecificationAdapter(val specificationList: ArrayList<Specs> = ArrayList()) : RecyclerView.Adapter<SpecificationAdapter.ViewHolder>() {

    init {
        specificationList.add(Specs("", ""))
    }

    inner class ViewHolder(private val binding: SpecificationItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(specs: Specs){
            binding.nameETxt.setText(specs.head)
            binding.valueETxt.setText(specs.body)
            binding.nameETxt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    specificationList[adapterPosition].head = binding.nameETxt.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })

            binding.valueETxt.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    specificationList[adapterPosition].body = binding.valueETxt.text.toString()
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SpecificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(specificationList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return specificationList.size
    }
}