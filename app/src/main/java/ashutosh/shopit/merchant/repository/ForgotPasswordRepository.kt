package ashutosh.shopit.merchant.repository

import ashutosh.shopit.merchant.SingleLiveEvent
import ashutosh.shopit.merchant.api.RetrofitAPI
import ashutosh.shopit.merchant.api.NetworkResult
import ashutosh.shopit.merchant.models.Email
import ashutosh.shopit.merchant.models.DefaultResponse
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(private val retrofitAPI: RetrofitAPI)  {

    val forgotPasswordResponseLiveData =
        SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun forgotPassword(email : String){
        forgotPasswordResponseLiveData.value = NetworkResult.Loading()
        try{
            val response = retrofitAPI.forgotPassword(Email(email))
            when(response.code()){
                200 -> {
                    if(response.body() != null){
                        forgotPasswordResponseLiveData.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        forgotPasswordResponseLiveData.value = NetworkResult.Error(200, "Something went wrong\nError: Response is null")
                    }
                }
                400 -> forgotPasswordResponseLiveData.value = NetworkResult.Error(400, "Invalid Action")
                404 -> forgotPasswordResponseLiveData.value = NetworkResult.Error(404, "No user with this email is found")
                else -> forgotPasswordResponseLiveData.value = NetworkResult.Error(response.code(),"Something went wrong\nError code : ${response.code()}")
            }
        }
        catch (e : Exception){
            forgotPasswordResponseLiveData.value = NetworkResult.Error(-1, e.message)
        }
    }


}