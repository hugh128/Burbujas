package gt.com.burbujas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.cardview.widget.CardView
import android.widget.CheckBox

class ServiceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_service, container, false)

        // Lista de pares CardView-CheckBox con sus IDs correspondientes
        val cardCheckboxPairs = listOf(
            view.findViewById<CardView>(R.id.card1) to view.findViewById<CheckBox>(R.id.cardCheckBox1),
            view.findViewById<CardView>(R.id.card2) to view.findViewById<CheckBox>(R.id.cardCheckBox2),
            view.findViewById<CardView>(R.id.card3) to view.findViewById<CheckBox>(R.id.cardCheckBox3),
            view.findViewById<CardView>(R.id.card4) to view.findViewById<CheckBox>(R.id.cardCheckBox4),
            view.findViewById<CardView>(R.id.card5) to view.findViewById<CheckBox>(R.id.cardCheckBox5)
        )

        // Configurar el evento de clic para cada par de CardView y CheckBox
        for ((card, checkbox) in cardCheckboxPairs) {
            card.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
            }
        }

        return view
    }
}
