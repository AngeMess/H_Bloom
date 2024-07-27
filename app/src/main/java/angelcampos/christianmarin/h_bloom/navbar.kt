package angelcampos.christianmarin.h_bloom

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class navbar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_navbar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Configura los listeners para los ImageButton
        findViewById<ImageButton>(R.id.btnMenu).setOnClickListener {
            val intent = Intent(this, PantallaInicio::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btnAgregarPaciente).setOnClickListener {
            val intent = Intent(this, AnadirPaciente::class.java)
            startActivity(intent)
        }

        findViewById<ImageButton>(R.id.btnAgregarMedicamento).setOnClickListener {
            val intent = Intent(this, AnadirMedicamento::class.java)
            startActivity(intent)
        }
    }
}