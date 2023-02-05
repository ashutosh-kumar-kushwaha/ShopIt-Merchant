package ashutosh.shopit.merchant.ui.addProduct

import android.app.Activity.RESULT_OK
import android.content.ClipData.Item
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import ashutosh.shopit.merchant.R
import ashutosh.shopit.merchant.URIPathHelper
import ashutosh.shopit.merchant.adapters.DescriptionAdapter
import ashutosh.shopit.merchant.adapters.ImageAdapter
import ashutosh.shopit.merchant.adapters.SpecificationAdapter
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.databinding.FragmentAddProductBinding
import ashutosh.shopit.merchant.models.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding: FragmentAddProductBinding get() = _binding!!

    private var specificationAdapter = SpecificationAdapter()

    private var descriptionAdapter = DescriptionAdapter()
    private val imageAdapter = ImageAdapter()

    private val imageList = mutableListOf<Uri>()

    private val addProductViewModel by viewModels<AddProductViewModel>()

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val imageUri = result.data?.data
                imageList.add(imageUri!!)
                imageAdapter.submitList(imageList + listOf())
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        binding.viewModel = addProductViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.productSpecsRecyclerView.adapter = specificationAdapter
        binding.productSpecsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.imagesRecyclerView.adapter = imageAdapter
        binding.imagesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.addSpecsBtn.setOnClickListener {
            specificationAdapter.specificationList.add(Specs("", ""))
            specificationAdapter.notifyDataSetChanged()
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.productDescRecyclerView.adapter = descriptionAdapter
        binding.productDescRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.addHeadingBtn.setOnClickListener {
            descriptionAdapter.descriptionList.add(Desc("", ""))
            descriptionAdapter.notifyDataSetChanged()
        }

        if(arguments?.getInt("categoryId") != null){
            addProductViewModel.categoryId = arguments?.getInt("categoryId")!!
        }



        binding.addProductBtn.setOnClickListener {
            upload()
        }

        binding.addImagesBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startForResult.launch(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedItem = specificationAdapter.specificationList[position]
                specificationAdapter.specificationList.removeAt(position)
                specificationAdapter.notifyItemRemoved(position)
                Snackbar.make(binding.productSpecsRecyclerView, "Deleted", Snackbar.LENGTH_LONG).setAction("Undo"
                ) {
                    specificationAdapter.specificationList.add(position, deletedItem)
                    specificationAdapter.notifyItemInserted(position)
                }.show()
            }

        }).attachToRecyclerView(binding.productSpecsRecyclerView)



        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedItem = descriptionAdapter.descriptionList[position]
                descriptionAdapter.descriptionList.removeAt(position)
                descriptionAdapter.notifyItemRemoved(position)
                Snackbar.make(binding.productDescRecyclerView, "Deleted", Snackbar.LENGTH_LONG).setAction("Undo"
                ) {
                    descriptionAdapter.descriptionList.add(position, deletedItem)
                    descriptionAdapter.notifyItemInserted(position)
                }.show()
            }
        }).attachToRecyclerView(binding.productDescRecyclerView)

        return binding.root
    }

    private fun upload() {
        val uriPathHelper = URIPathHelper()
        val images = mutableListOf<MultipartBody.Part>()
        imageList.forEach { uri ->
            val path = uriPathHelper.getPath(requireContext(), uri)
            val file = File(path!!)
            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData("images", file.name, requestBody)
            images.add(imageMultipart)
        }
        val addProductRequest = AddProductRequest(
            addProductViewModel.productName.value!!,
            addProductViewModel.originalPrice.value!!,
            addProductViewModel.offerPercentage.value!!,
            addProductViewModel.quantity.value!!,
            listOf(),
            addProductViewModel.warranty.value!!,
            "",
            "",
            listOf(SpecificationX("", specificationAdapter.specificationList)),
            descriptionAdapter.descriptionList,
            listOf()
        )
        Log.d("Ashu", addProductRequest.toString())
        val addProductJson = Gson().toJson(addProductRequest)
        val addProductRequestBody = addProductJson.toString().toRequestBody("application/json".toMediaTypeOrNull())
        addProductViewModel.addProduct(images, addProductRequestBody)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addProductViewModel.addProductResponse.observe(viewLifecycleOwner){
            when(it){
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {}
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}