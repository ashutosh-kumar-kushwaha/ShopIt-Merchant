package ashutosh.shopit.merchant.ui.selectCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.shopit.merchant.repository.SelectCategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectCategoryViewModel @Inject constructor(private val selectCategoryRepository: SelectCategoryRepository) : ViewModel() {

    val categoriesResponse get() = selectCategoryRepository.categoriesResponse

    fun getCategories(){
        viewModelScope.launch {
            selectCategoryRepository.getCategories()
        }
    }
}