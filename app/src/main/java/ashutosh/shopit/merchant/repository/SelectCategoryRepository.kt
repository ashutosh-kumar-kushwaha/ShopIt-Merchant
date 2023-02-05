package ashutosh.shopit.merchant.repository

import ashutosh.shopit.merchant.SingleLiveEvent
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.models.CategoryResponse
import javax.inject.Inject

class SelectCategoryRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val categoriesResponse = SingleLiveEvent<NetworkResult<CategoryResponse>>()

    suspend fun getCategories(){
        categoriesResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getCategory()
            if(response.code() == 200){
                if(response.body() != null){
                    categoriesResponse.value = NetworkResult.Success(200, response.body()!!)
                }
                else{
                    categoriesResponse.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                }
            }
            else{
                categoriesResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch(e: Exception){
            categoriesResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}