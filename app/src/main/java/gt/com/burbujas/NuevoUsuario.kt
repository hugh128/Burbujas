package gt.com.burbujas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import data.models.BaseDatos
import java.util.*

class NuevoUsuario : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_nuevo_usuario, container, false)

        val nombreEditText = view.findViewById<EditText>(R.id.txtNombre)
        val usuarioEditText = view.findViewById<EditText>(R.id.txtUsuario)
        val passwordEditText = view.findViewById<EditText>(R.id.txtPassword)
        val tipoSpinner = view.findViewById<Spinner>(R.id.spinner)
        val agregarButton = view.findViewById<Button>(R.id.btnAgregar)

        // Inicializa el adaptador para el spinner
        val opciones = arrayOf("Administrador", "Usuario")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipoSpinner.adapter = adapter

        val baseDatos = BaseDatos(requireContext())

        agregarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString().trim()
            val usuario = usuarioEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val tipo = tipoSpinner.selectedItem.toString()

            if (nombre.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fechaCreacion = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val id = baseDatos.insertarUsuario(nombre, usuario, password, tipo, fechaCreacion)

            if (id != -1L) {
                Toast.makeText(requireContext(), "Usuario agregado con ID: $id", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Error al agregar usuario", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}

