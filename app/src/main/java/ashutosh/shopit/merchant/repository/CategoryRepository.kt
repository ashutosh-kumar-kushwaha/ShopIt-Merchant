package ashutosh.shopit.merchant.repository

import ashutosh.shopit.merchant.SingleLiveEvent
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.models.CategoryResponse
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val getCategoriesResponseLiveData = SingleLiveEvent<NetworkResult<CategoryResponse>>()

    suspend fun getCategories(){
        getCategoriesResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getCategory()
            if(response.code() == 200){
                if(response.body() != null){
                    getCategoriesResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                }
                else{
                    getCategoriesResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                }
            }
            else{
                getCategoriesResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch(e: Exception){
            getCategoriesResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }
}