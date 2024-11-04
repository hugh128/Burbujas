package gt.com.burbujas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import data.models.BaseDatos

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsuario: EditText
    private lateinit var editTextContraseña: EditText
    private lateinit var buttonLogin: Button
    private lateinit var baseDatos: BaseDatos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsuario = findViewById(R.id.editTextText)
        editTextContraseña = findViewById(R.id.editTextText2)
        buttonLogin = findViewById(R.id.button3)

        baseDatos = BaseDatos(this)

        buttonLogin.setOnClickListener {
            val usuario = editTextUsuario.text.toString().trim()
            val contraseña = editTextContraseña.text.toString().trim()

            if (usuario.isNotEmpty() && contraseña.isNotEmpty()) {
                try {
                    if (validarCredenciales(usuario, contraseña)) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al acceder a la base de datos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validarCredenciales(usuario: String, contraseña: String): Boolean {
        return baseDatos.validarUsuario(usuario, contraseña)
    }
}
