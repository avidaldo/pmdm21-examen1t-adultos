package com.example.examen1t

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.util.Patterns
import android.widget.Toast
import com.example.examen1t.databinding.ActivityMainBinding
import java.lang.RuntimeException
import java.util.regex.Pattern
import android.text.method.PasswordTransformationMethod


// https://www.baeldung.com/java-email-validation-regex#regular-expression-by-rfc-5322-for-email-validation
// const val REGEX_EMAIL_STRING_RFC5322 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"

class MainActivity : AppCompatActivity() {

    //val EMAIL_ADDRESS = Pattern.compile(REGEX_EMAIL_STRING_RFC5322)

    private lateinit var binding: ActivityMainBinding

    private var passwordVisible = false
        set(value) {
            binding.tvMuestraContrasena.text =
                if (value) getString(R.string.hidePassword)
                else getString(R.string.showPassword)
            //Toast.makeText(this, binding.editTextTextPassword.inputType.toString(), Toast.LENGTH_SHORT).show()
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMuestraContrasena.setOnClickListener {
            changePasswordVisibility()
        }

        binding.buttonEntrar.setOnClickListener {
            try {
                checkEmail()
                checkPassword()
                startActivity(Intent(this, ContadorActivity::class.java))
            } catch (e: InvalidEmailException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            } catch (e: InvalidPasswordException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvToRegisto.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

    }


    private fun changePasswordVisibility() {
        changePasswordVisibility1()
        //changePasswordVisibility2()
    }

    private fun changePasswordVisibility1() {
        /* Solución de bajo nivel (sacada de leer el código) */
        binding.editTextTextPassword.inputType =
            if (passwordVisible) InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD  // 129
            else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD // 145
        passwordVisible = !passwordVisible
    }

    private fun changePasswordVisibility2() {
        /* https://developer.android.com/reference/android/text/method/TransformationMethod */
        binding.editTextTextPassword.transformationMethod =
            if (passwordVisible) PasswordTransformationMethod()
            else HideReturnsTransformationMethod.getInstance() // null
        passwordVisible = !passwordVisible
    }

    class InvalidEmailException(message: String) : RuntimeException(message)
    class InvalidPasswordException(message: String) : RuntimeException(message)

    private fun checkEmail() {
        val emailString = binding.editTextTextEmailAddress.text.toString()

        if (emailString.isEmpty())
            throw InvalidEmailException(getString(R.string.emptyString))
        //if (!EMAIL_ADDRESS.matcher(emailString).matches())  // Con patrón RFC5322
        if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) // Con patrón en librería de Android
            throw InvalidEmailException(getString(R.string.email_dont_match_pattern))
    }


    private fun checkPassword() {
        val password = binding.editTextTextPassword.text.toString()

        if (password.isEmpty()) throw InvalidPasswordException(getString(R.string.empty_password))
        if (password.length <= 8) throw InvalidPasswordException(getString(R.string.invalid_password))
    }


}