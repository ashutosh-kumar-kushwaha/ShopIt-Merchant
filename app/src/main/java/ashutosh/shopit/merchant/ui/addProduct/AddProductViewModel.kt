package ashutosh.shopit.merchant.ui.addProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.repository.AddProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val addProductRepository: AddProductRepository): ViewModel() {
    val productName = MutableLiveData("")
    val originalPrice = MutableLiveData("")
    val offerPercentage = MutableLiveData("")
    val quantity = MutableLiveData("")
    val warranty = MutableLiveData("")

    val addProductResponse get() = addProductRepository.addProductResponse

    var categoryId = -1

    fun addProduct(images: List<MultipartBody.Part>, productDetails: RequestBody){
        viewModelScope.launch {
            addProductRepository.addProduct(categoryId, images, productDetails)
        }
    }

}