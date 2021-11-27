package com.example.examen1t

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.examen1t.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegistrar.setOnClickListener {
            if (binding.editTextTextEmailAddress.text.isEmpty())
                errorEmailInvalido()
            else if (binding.editTextTextPassword.text.length <8)
                errorNoPassword()
            else if(binding.editTextTextPasswordRepeat.text.toString() !=
                    binding.editTextTextPassword.text.toString())
                        errorNoMatchPassword()

            Toast.makeText(this, R.string.registro_correcto, Toast.LENGTH_SHORT).show()
        }

    }


    private fun errorEmailInvalido() {
        Toast.makeText(this, R.string.empty_user, Toast.LENGTH_SHORT).show()
    }

    private fun errorNoPassword() {
        Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_SHORT).show()
    }

    private fun errorNoMatchPassword() {
        Toast.makeText(this, R.string.no_match_password, Toast.LENGTH_SHORT).show()
    }

}