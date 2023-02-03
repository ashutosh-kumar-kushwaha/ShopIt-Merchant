package ashutosh.shopit.merchant.repository

import ashutosh.shopit.merchant.SingleLiveEvent
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.models.Profile
import javax.inject.Inject

class AccountRepository @Inject constructor(private val retrofitAPI: RetrofitAPI) {
    val profileResponse = SingleLiveEvent<NetworkResult<Profile>>()

    suspend fun getProfile(){
        profileResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getProfile()
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        profileResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        profileResponse.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                401 -> {
                    profileResponse.value = NetworkResult.Error(401, "Session expired")
                }
                else -> {
                    profileResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        }
        catch (e: Exception){
            profileResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}