package ashutosh.shopit.merchant.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.merchant.models.Category
import ashutosh.shopit.merchant.models.ProductsParentContent
import ashutosh.shopit.merchant.repository.HomePageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homePageRepository: HomePageRepository) : ViewModel() {

    val categoriesResponse get() = homePageRepository.categoriesResponse
    val productsResponse get() = homePageRepository.productsResponse

    val productsByCategory = mutableListOf<ProductsParentContent>()

    fun getCategories(){
        viewModelScope.launch {
            homePageRepository.getCategories()
        }
    }

    fun getProductsByCategory(category: Category){
        viewModelScope.launch {
            homePageRepository.getProductsByCategory(category)
        }
    }
}