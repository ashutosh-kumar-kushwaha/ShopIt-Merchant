package ashutosh.shopit.merchant.repository

import android.util.Log
import ashutosh.shopit.merchant.SingleLiveEvent
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.models.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AddProductRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val addProductResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun addProduct(categoryId : Int, images: List<MultipartBody.Part>, productDetails: RequestBody){
        addProductResponse.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.addProduct(categoryId, images, productDetails)
            when(response.code()){
                201 -> {
                    if(response.body() != null){
                        addProductResponse.value = NetworkResult.Success(200, DefaultResponse("Product Added", true))
                    }
                }
                else -> addProductResponse.value = NetworkResult.Error(response.code(), "Something went wrong!\nError code: ${response.code()}")
            }
        }
        catch (e: Exception){
            addProductResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}