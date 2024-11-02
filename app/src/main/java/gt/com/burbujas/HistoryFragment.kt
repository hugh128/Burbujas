package gt.com.burbujas

import android.graphics.Color
import android.graphics.Typeface
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
import java.text.DecimalFormat

class HistoryFragment : Fragment() {

    private lateinit var baseDatos: BaseDatos
    private lateinit var tableLayout: TableLayout
    private lateinit var buttonShowRecords: Button
    private lateinit var totalRegistrosTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        baseDatos = BaseDatos(requireContext())
        tableLayout = view.findViewById(R.id.table_layout_records)
        buttonShowRecords = view.findViewById(R.id.btnMostrar)
        totalRegistrosTextView = view.findViewById(R.id.totalRegistros)

        buttonShowRecords.setOnClickListener {
            mostrarRegistros()
        }

        return view
    }

    private fun mostrarRegistros() {
        tableLayout.removeAllViews()

        val headerRow = createTableRow()
        headerRow.addView(createTextView("ID", true))
        headerRow.addView(createTextView("Costo IVA", true))
        headerRow.addView(createTextView("Comisión", true))
        headerRow.addView(createTextView("Fecha Registro", true))
        headerRow.addView(createTextView("Servicios", true))
        tableLayout.addView(headerRow)

        tableLayout.addView(createDivider())
        val registros = baseDatos.obtenerRegistros()
        totalRegistrosTextView.text = registros.size.toString()
        val decimalFormat = DecimalFormat("#.00")

        for (registro in registros) {
            val row = createTableRow()
            row.addView(createTextView(registro.id.toString()))
            row.addView(createTextView(decimalFormat.format(registro.costoIva), false, true))
            row.addView(createTextView(decimalFormat.format(registro.comision), false, true))
            row.addView(createTextView(registro.fechaRegistro, false, false, true))
            row.addView(createTextView(registro.servicios))
            tableLayout.addView(row)

            tableLayout.addView(createDivider())
        }
    }

    private fun createTextView(text: String, isHeader: Boolean = false, alignRight: Boolean = false, alignCenter: Boolean = false): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.setPadding(16, 16, 16, 16)

        textView.textSize = if (isHeader) 12f else 12f

        if (isHeader) {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            textView.typeface = Typeface.DEFAULT_BOLD
        } else {
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.header_text_color))
        }

        when {
            alignRight -> textView.gravity = android.view.Gravity.END
            alignCenter -> textView.gravity = android.view.Gravity.CENTER
            else -> textView.gravity = android.view.Gravity.START
        }

        return textView
    }

    private fun createTableRow(): TableRow {
        val row = TableRow(context)
        row.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        return row
    }

    private fun createDivider(): View {
        val divider = View(context)
        divider.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,1
        )
        divider.setBackgroundColor(Color.parseColor("#d1d1d1"))
        return divider
    }
}