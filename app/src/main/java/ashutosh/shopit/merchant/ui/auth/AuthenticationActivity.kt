package ashutosh.shopit.merchant.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ashutosh.shopit.merchant.R
import ashutosh.shopit.merchant.ui.auth.forgotPasswordOtpVerification.ForgotPasswordOtpVerificationFragment
import ashutosh.shopit.merchant.ui.auth.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    var email = ""
    var otp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

//        val fm = supportFragmentManager
//        val ft = fm.beginTransaction()
//        ft.add(R.id.fragmentContainer, ForgotPasswordOtpVerificationFragment())
//        ft.commit()
    }
}