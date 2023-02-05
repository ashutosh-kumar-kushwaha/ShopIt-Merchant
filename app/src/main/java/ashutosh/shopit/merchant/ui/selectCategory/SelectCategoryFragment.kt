package ashutosh.shopit.merchant.ui.selectCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.shopit.merchant.R
import ashutosh.shopit.merchant.adapters.CategoryAdapter
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.databinding.FragmentSelectCategoryBinding
import ashutosh.shopit.merchant.interfaces.CategoryClickListener

class SelectCategoryFragment : Fragment() {

    private var _binding: FragmentSelectCategoryBinding? = null
    private val binding: FragmentSelectCategoryBinding get() = _binding!!

    private val selectCategoryViewModel by viewModels<SelectCategoryViewModel>()

    private lateinit var categoriesAdapter : CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectCategoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = selectCategoryViewModel

        val categoryClickListener = object : CategoryClickListener {
            override fun onCategoryClick(categoryId: Int) {
                val bundle = Bundle()
                bundle.putInt("categoryId", categoryId)
                findNavController().navigate(R.id.action_selectCategoryFragment_to_addProductFragment, bundle)
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        categoriesAdapter = CategoryAdapter(categoryClickListener)

        binding.categoriesRecyclerView.adapter = categoriesAdapter
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        selectCategoryViewModel.getCategories()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectCategoryViewModel.categoriesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {}

                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Success -> {
                    categoriesAdapter.submitList(it.data?.content)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}