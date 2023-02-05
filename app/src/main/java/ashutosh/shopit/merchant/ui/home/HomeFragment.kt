package ashutosh.shopit.merchant.ui.home

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
import ashutosh.shopit.merchant.adapters.ProductsParentAdapter
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    private lateinit var productsParentAdapter : ProductsParentAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        productsParentAdapter = ProductsParentAdapter()
        binding.productsRecyclerView.adapter = productsParentAdapter
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        homeViewModel.getCategories()

        binding.addProductBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectCategoryFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.categoriesResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    it.data?.content?.forEach{ category ->
                        homeViewModel.getProductsByCategory(category)
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {}
            }
        }

        homeViewModel.productsResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    if(it.data?.products!!.isNotEmpty()){
                        homeViewModel.productsByCategory.add(it.data)
                        productsParentAdapter.submitList(homeViewModel.productsByCategory + listOf())
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}