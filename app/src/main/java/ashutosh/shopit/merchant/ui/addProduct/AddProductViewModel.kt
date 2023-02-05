package ashutosh.shopit.merchant.ui.addProduct

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.merchant.api.RetrofitAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val retrofitAPI: RetrofitAPI): ViewModel() {
    val productName = MutableLiveData("")
    val originalPrice = MutableLiveData("")
    val offerPercentage = MutableLiveData("")
    val quantity = MutableLiveData("")
    val warranty = MutableLiveData("")

    fun addProduct(){
        viewModelScope.launch {

        }
    }

}