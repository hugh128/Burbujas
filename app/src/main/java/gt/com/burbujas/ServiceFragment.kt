package gt.com.burbujas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import data.models.BaseDatos
import data.models.Calculos

class ServiceFragment : Fragment() {
    private lateinit var subTotalTextView: TextView
    private lateinit var ivaTextView: TextView
    private lateinit var comisionTextView: TextView
    private lateinit var totalPrecioTextView: TextView
    private var subTotal = 0.0
    private var ivaTotal = 0.0
    private var comisionTotal = 0.0
    private lateinit var baseDatos: BaseDatos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_service, container, false)

        subTotalTextView = view.findViewById(R.id.subtotal)
        totalPrecioTextView = view.findViewById(R.id.total)
        ivaTextView = view.findViewById(R.id.iva)
        comisionTextView = view.findViewById(R.id.comision)

        val btnCalcular = view.findViewById<Button>(R.id.btnCalcular)
        val btnComision = view.findViewById<Button>(R.id.btnComision)
        val btnRegistrar = view.findViewById<Button>(R.id.btnRegistrar)

        baseDatos = BaseDatos(requireContext())

        val preciosServicios = listOf(10.00, 10.00, 5.00, 50.00, 100.00)
        val textViewsPrecios = listOf(
            R.id.precioLavado,
            R.id.precioAspirado,
            R.id.precioSilicon,
            R.id.precioPulido,
            R.id.precioLavadoMotor
        ).map { view.findViewById<TextView>(it) }

        val cardCheckboxPairs = listOf(
            Triple(view.findViewById<CardView>(R.id.card1), view.findViewById<CheckBox>(R.id.cardCheckBox1), view.findViewById<TextView>(R.id.titleCard1)),
            Triple(view.findViewById<CardView>(R.id.card2), view.findViewById<CheckBox>(R.id.cardCheckBox2), view.findViewById<TextView>(R.id.titleCard2)),
            Triple(view.findViewById<CardView>(R.id.card3), view.findViewById<CheckBox>(R.id.cardCheckBox3), view.findViewById<TextView>(R.id.titleCard3)),
            Triple(view.findViewById<CardView>(R.id.card4), view.findViewById<CheckBox>(R.id.cardCheckBox4), view.findViewById<TextView>(R.id.titleCard4)),
            Triple(view.findViewById<CardView>(R.id.card5), view.findViewById<CheckBox>(R.id.cardCheckBox5), view.findViewById<TextView>(R.id.titleCard5))
        )

        cardCheckboxPairs.forEachIndexed { index, (card, checkbox, _) ->
            val precioServicio = preciosServicios[index]
            val textViewPrecio = textViewsPrecios[index]

            card.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
                actualizarPrecio(precioServicio, textViewPrecio, checkbox.isChecked)
            }
        }

        val calculos = Calculos()

        btnCalcular.setOnClickListener {
            if (subTotal == 0.0) {
                Toast.makeText(requireContext(), "Selecciona al menos un servicio", Toast.LENGTH_SHORT).show()
            } else {
                ivaTotal = calculos.calcularIVA(subTotal.toFloat()).toDouble()
                ivaTextView.text = formatearPrecio(ivaTotal)
                actualizarTotal()
            }
        }

        btnComision.setOnClickListener {
            if (subTotal == 0.0) {
                Toast.makeText(requireContext(), "Selecciona al menos un servicio", Toast.LENGTH_SHORT).show()
            } else {
                comisionTotal = calculos.calcularComision(subTotal.toFloat()).toDouble()
                comisionTextView.text = formatearPrecio(comisionTotal)
                actualizarTotal()
            }
        }

        btnRegistrar.setOnClickListener {
            if (ivaTotal == 0.0 || comisionTotal == 0.0) {
                Toast.makeText(requireContext(), "Calcula el valor del IVA y de la comision", Toast.LENGTH_SHORT).show()
            } else {
                val fechaRegistro = obtenerFechaActual()
                val serviciosSeleccionados = obtenerServiciosSeleccionados(cardCheckboxPairs)
                val costoIva = subTotal + ivaTotal

                val id = baseDatos.insertarRegistros(costoIva.toFloat(), comisionTotal.toFloat(), fechaRegistro, serviciosSeleccionados)

                if (id != -1L) {
                    Toast.makeText(requireContext(), "Registro almacenado con éxito", Toast.LENGTH_SHORT).show()
                    reiniciarEstado(cardCheckboxPairs, textViewsPrecios)
                } else {
                    Toast.makeText(requireContext(), "Error al almacenar el registro", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    private fun reiniciarEstado(cardCheckboxPairs: List<Triple<CardView, CheckBox, TextView>>, textViewsPrecios: List<TextView>) {
        cardCheckboxPairs.forEach { (_, checkbox, _) -> checkbox.isChecked = false }
        textViewsPrecios.forEach { it.apply { text = "0.00" } }

        subTotal = 0.0
        subTotalTextView.text = formatearPrecio(subTotal)

        ivaTotal = 0.0
        comisionTotal = 0.0
        ivaTextView.text = formatearPrecio(ivaTotal)
        comisionTextView.text = formatearPrecio(comisionTotal)

        actualizarTotal()
    }

    private fun actualizarPrecio(precio: Double, textViewPrecio: TextView, agregar: Boolean) {
        if (agregar) {
            textViewPrecio.text = formatearPrecio(precio)
            subTotal += precio
        } else {
            textViewPrecio.text = formatearPrecio(0.0)
            subTotal -= precio
        }

        subTotalTextView.text = formatearPrecio(subTotal)
        ivaTotal = 0.0
        comisionTotal = 0.0
        ivaTextView.text = formatearPrecio(ivaTotal)
        comisionTextView.text = formatearPrecio(comisionTotal)

        actualizarTotal()
    }

    private fun actualizarTotal() {
        totalPrecioTextView.text = formatearPrecio(subTotal + ivaTotal + comisionTotal)
    }

    private fun formatearPrecio(precio: Double): String {
        return "Q${"%.2f".format(precio)}"
    }

    private fun obtenerFechaActual(): String {
        val formatoFecha = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return formatoFecha.format(System.currentTimeMillis())
    }

    private fun obtenerServiciosSeleccionados(cardCheckboxPairs: List<Triple<CardView, CheckBox, TextView>>): String {
        return cardCheckboxPairs.filter { it.second.isChecked }
            .joinToString(", ") { it.third.text.toString() }
    }
}
