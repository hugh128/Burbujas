package gt.com.burbujas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import data.models.BaseDatos

class OptionsFragment : Fragment() {

    private lateinit var baseDatos: BaseDatos
    private lateinit var tablaUsuarios: TableLayout
    private lateinit var btnMostrarUsuarios: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_options, container, false)

        baseDatos = BaseDatos(requireContext())
        tablaUsuarios = view.findViewById(R.id.tablaUsuarios)
        btnMostrarUsuarios = view.findViewById(R.id.btnMostrarUsuarios)

        val btnAgregarUsuario: Button = view.findViewById(R.id.btnAgregarUsuario)
        btnAgregarUsuario.setOnClickListener {
            val intent = Intent(activity, AgregarUsuarioActivity::class.java)
            startActivity(intent)
        }

        btnMostrarUsuarios.setOnClickListener {
            mostrarUsuarios()
        }

        return view
    }

    private fun mostrarUsuarios() {
        tablaUsuarios.removeAllViews()

        val headerRow = TableRow(context)
        headerRow.addView(createTextView("ID", isHeader = true))
        headerRow.addView(createTextView("Nombre", isHeader = true))
        headerRow.addView(createTextView("Usuario", isHeader = true))
        headerRow.addView(createTextView("Tipo", isHeader = true))
        headerRow.addView(createTextView("Fecha Creación", isHeader = true))
        tablaUsuarios.addView(headerRow)

        val usuarios = baseDatos.obtenerUsuarios()
        for (usuario in usuarios) {
            val row = TableRow(context)
            row.addView(createTextView(usuario.id.toString()))
            row.addView(createTextView(usuario.nombre))
            row.addView(createTextView(usuario.usuario))
            row.addView(createTextView(usuario.tipo))
            row.addView(createTextView(usuario.fechaCreacion))
            tablaUsuarios.addView(row)
        }
    }

    private fun createTextView(text: String, isHeader: Boolean = false): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.setPadding(16, 16, 16, 16)
        textView.textSize = 14f
        textView.setTextColor(
            ContextCompat.getColor(requireContext(), if (isHeader) R.color.black else R.color.header_text_color)
        )
        return textView
    }
}

