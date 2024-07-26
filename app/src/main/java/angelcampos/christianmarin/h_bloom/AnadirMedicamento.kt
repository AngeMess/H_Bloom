package angelcampos.christianmarin.h_bloom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class AnadirMedicamento : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_medicamento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombreMed = findViewById<EditText>(R.id.txtNombreMed)
        val txtHoraApli = findViewById<EditText>(R.id.txtHoraApli)
        val btnAgragarMedicamento = findViewById<Button>(R.id.btnAgragarMedicamento)

        btnAgragarMedicamento.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()

                val addMedicamento =objConexion?.prepareStatement("insert into tbMedicamentosA (UUID_Medicamento, Nombre_medicamento, Hora_aplicacion) values (?,?,?)")!!
                addMedicamento.setString(1, UUID.randomUUID().toString())
                addMedicamento.setString(2, txtNombreMed.text.toString())
                addMedicamento.setString(3, txtHoraApli.text.toString())
                addMedicamento.executeUpdate()

                withContext(Dispatchers.Main){
                    Toast.makeText(this@AnadirMedicamento, "Medicamento añadido", Toast.LENGTH_SHORT).show()
                    txtNombreMed.setText("")
                    txtHoraApli.setText("")
                }
            }
        }
    }
}