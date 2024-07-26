package angelcampos.christianmarin.h_bloom

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VerPaciente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val UUIDRecibido = intent.getStringExtra("UUID_Paciente")
        val NombresRecibido = intent.getStringExtra("Nombres")
        val ApellidosRecibido = intent.getStringExtra("Apellidos")
        val EdadRecibido = intent.getIntExtra("Edad", 0)
        val EnfermedadRecibido = intent.getStringExtra("Enfermedad")
        val NumeroHabitacionRecibido = intent.getIntExtra("Número_habitacion", 0)
        val NumeroCamaRecibido = intent.getIntExtra("Número_cama", 0)
        val FechaNacimientoRecibido = intent.getStringExtra("Fecha_nacimiento")
        val textViewNombres = findViewById<TextView>(R.id.textViewNombres)
        val textViewApellidos = findViewById<TextView>(R.id.textViewApellidos)
        val textViewEdad = findViewById<TextView>(R.id.textViewEdad)
        val textViewFechaNaci = findViewById<TextView>(R.id.textViewFechaNaci)
        val textViewNumeroHab = findViewById<TextView>(R.id.textViewNumeroHab)
        val textViewEnfermedad = findViewById<TextView>(R.id.textViewEnfermedad)
        val textViewNumeroCam = findViewById<TextView>(R.id.textViewNumeroCam)
        textViewNombres.text = NombresRecibido
        textViewApellidos.text = ApellidosRecibido
        textViewEdad.text = EdadRecibido.toString()
        textViewFechaNaci.text = FechaNacimientoRecibido
        textViewNumeroHab.text = NumeroHabitacionRecibido.toString()
        textViewEnfermedad.text = EnfermedadRecibido
        textViewNumeroCam.text = NumeroCamaRecibido.toString()
    }
}