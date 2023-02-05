package ashutosh.shopit.merchant.repository

import ashutosh.shopit.merchant.SingleLiveEvent
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.models.*
import javax.inject.Inject

class HomePageRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {

    val productsResponse = SingleLiveEvent<NetworkResult<ProductsParentContent>>()
    val categoriesResponse = SingleLiveEvent<NetworkResult<CategoryResponse>>()
    val getAdvertisementResponseLiveData = SingleLiveEvent<NetworkResult<AdvertisementResponse>>()

    suspend fun getProductsByCategory(category : Category){
        productsResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getProductsByCategory(category.categoryId)
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        productsResponse.value = NetworkResult.Success(200, ProductsParentContent(category, response.body()!!.content))
                    }
                    else{
                        productsResponse.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                    }
                }
                else -> productsResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            productsResponse.value = NetworkResult.Error(-1, e.message)
        }
    }

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

    suspend fun getAdvertisements(){
        getAdvertisementResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.getAdvertisements()
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        getAdvertisementResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        getAdvertisementResponseLiveData.value = NetworkResult.Error(200, "Something went wrong!\nResponse is null")
                    }
                }
                else -> {
                    getAdvertisementResponseLiveData.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
                }
            }
        }
        catch(e: Exception){
            getAdvertisementResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }

}