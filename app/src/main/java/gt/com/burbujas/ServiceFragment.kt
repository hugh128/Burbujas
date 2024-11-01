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
import data.models.Calculos

class ServiceFragment : Fragment() {
    private lateinit var subTotalTextView: TextView
    private lateinit var ivaTextView: TextView
    private lateinit var comisionTextView: TextView
    private lateinit var totalPrecioTextView: TextView
    private var subTotal: Double = 0.0
    private var ivaTotal: Double = 0.0
    private var comisionTotal: Double = 0.0
    private var totalPrecio: Double = 0.0

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

        val preciosServicios = listOf(10.00, 10.00, 5.00, 50.00, 100.00)
        val textViewsPrecios = listOf(
            view.findViewById<TextView>(R.id.precioLavado),
            view.findViewById<TextView>(R.id.precioAspirado),
            view.findViewById<TextView>(R.id.precioSilicon),
            view.findViewById<TextView>(R.id.precioPulido),
            view.findViewById<TextView>(R.id.precioLavadoMotor),
        )

        val cardCheckboxPairs = listOf(
            view.findViewById<CardView>(R.id.card1) to view.findViewById<CheckBox>(R.id.cardCheckBox1),
            view.findViewById<CardView>(R.id.card2) to view.findViewById<CheckBox>(R.id.cardCheckBox2),
            view.findViewById<CardView>(R.id.card3) to view.findViewById<CheckBox>(R.id.cardCheckBox3),
            view.findViewById<CardView>(R.id.card4) to view.findViewById<CheckBox>(R.id.cardCheckBox4),
            view.findViewById<CardView>(R.id.card5) to view.findViewById<CheckBox>(R.id.cardCheckBox5)
        )

        for ((index, pair) in cardCheckboxPairs.withIndex()) {
            val (card, checkbox) = pair
            val precioServicio = preciosServicios[index]
            val textViewPrecio = textViewsPrecios[index]

            card.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
                actualizarPrecio(precioServicio, textViewPrecio, checkbox.isChecked)
            }
        }

        val calculos = Calculos()

        btnCalcular.setOnClickListener {
            if(subTotal == 0.0) {
                Toast.makeText(requireContext(), "Selecciona al menos un servicio", Toast.LENGTH_SHORT).show()
            } else {
                ivaTotal = calculos.calcularIVA(subTotal.toFloat()).toDouble()
                ivaTextView.text = formatearPrecio(ivaTotal)
                actualizarTotal()
            }
        }

        btnComision.setOnClickListener {
            if(subTotal == 0.0) {
                Toast.makeText(requireContext(), "Selecciona al menos un servicio", Toast.LENGTH_SHORT).show()
            } else {
                comisionTotal = calculos.calcularComision(subTotal.toFloat()).toDouble()
                comisionTextView.text = formatearPrecio(comisionTotal)
                actualizarTotal()
            }
        }

        btnRegistrar.setOnClickListener {
            if(ivaTotal == 0.0 || comisionTotal == 0.0) {
                Toast.makeText(requireContext(), "Calcula el valor del IVA y de la comision", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Registro almacenado en la base de datos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
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
        totalPrecio = subTotal + ivaTotal + comisionTotal
        totalPrecioTextView.text = formatearPrecio(totalPrecio)
    }

    private fun formatearPrecio(precio: Double): String {
        return "Q${"%.2f".format(precio)}"
    }
}
