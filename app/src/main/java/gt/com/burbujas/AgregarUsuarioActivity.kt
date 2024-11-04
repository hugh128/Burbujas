package gt.com.burbujas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import data.models.BaseDatos

class AgregarUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_nuevo_usuario)

        val nombreEditText = findViewById<EditText>(R.id.txtNombre)
        val usuarioEditText = findViewById<EditText>(R.id.txtUsuario)
        val passwordEditText = findViewById<EditText>(R.id.txtPassword)
        val tipoSpinner = findViewById<Spinner>(R.id.spinner)
        val agregarButton = findViewById<Button>(R.id.btnAgregar)
        val btnSalir = findViewById<Button>(R.id.btnSalir)

        val opciones = arrayOf("", "Administrador", "Usuario")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoSpinner.adapter = adapter

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val usuario = usuarioEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val tipo = tipoSpinner.selectedItem.toString()

            if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fechaCreacion = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val baseDatos = BaseDatos(this)
            val id = baseDatos.insertarUsuario(nombre, usuario, password, tipo, fechaCreacion)

            if (id != -1L) {
                Toast.makeText(this, "Usuario agregado con ID: $id", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al agregar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        btnSalir.setOnClickListener {
            finish()
        }
    }
}
