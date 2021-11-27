package com.example.examen1t

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.example.examen1t.databinding.ActivityMainBinding
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var passwordVisible = false

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
                Toast.makeText(this, R.string.empty_user, Toast.LENGTH_SHORT).show()
            } catch (e: InvalidPasswordException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvToRegisto.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

    }


    private fun changePasswordVisibility() { // TODO: Hacerlo bien
        if (passwordVisible) {
            binding.editTextPassMostrado.visibility = INVISIBLE
            binding.editTextTextPassword.text = binding.editTextPassMostrado.text
            binding.editTextTextPassword.visibility = VISIBLE
            binding.tvMuestraContrasena.text = getString(R.string.showPassword)
            passwordVisible = false
        } else {
            binding.editTextTextPassword.visibility = INVISIBLE
            binding.editTextPassMostrado.text = binding.editTextTextPassword.text
            binding.editTextPassMostrado.visibility = VISIBLE
            binding.tvMuestraContrasena.text = getString(R.string.hidePassword)
            passwordVisible = true

        }
    }

    class InvalidEmailException : RuntimeException()
    class InvalidPasswordException(message: String) : RuntimeException(message)

    private fun checkEmail() {
        if (binding.editTextTextEmailAddress.text.isEmpty())
            throw InvalidEmailException()
    }


    private fun checkPassword() {
        val password =
            if (passwordVisible) binding.editTextPassMostrado.text.toString()
            else binding.editTextTextPassword.text.toString()

        if (password.isEmpty()) throw InvalidPasswordException(getString(R.string.empty_password))
        if (password.length <= 8) throw InvalidPasswordException(getString(R.string.invalid_password))
    }

}