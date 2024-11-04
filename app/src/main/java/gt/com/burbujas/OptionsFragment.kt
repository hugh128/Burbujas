package gt.com.burbujas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class OptionsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_options, container, false)

        val btnAgregarUsuario: Button = view.findViewById(R.id.btnAgregarUsuario)

        btnAgregarUsuario.setOnClickListener {
            val intent = Intent(activity, AgregarUsuarioActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
