package angelcampos.christianmarin.h_bloom

import RecyclerViewHolder.AdaptadorPaciente
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.tbPacienteA

class PantallaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rcvPacientes = findViewById<RecyclerView>(R.id.rcvPacientes)
        rcvPacientes.layoutManager = LinearLayoutManager(this)

        fun obtenerPacientes(): List<tbPacienteA>{
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbPacienteA")!!
            val listaPacientes = mutableListOf<tbPacienteA>()

            while(resultSet.next()){
                val uuid = resultSet.getString("UUID_Paciente")
                val nombres = resultSet.getString("Nombres")
                val apellidos = resultSet.getString("Apellidos")
                val edad = resultSet.getInt("Edad")
                val enfermedad = resultSet.getString("Enfermedad")
                val numeroHabitacion = resultSet.getInt("Número_habitacion")
                val numeroCama = resultSet.getInt("Número_cama")
                val fechaNacimiento = resultSet.getString("Fecha_nacimiento")
                val medicamento = resultSet.getString("UUID_Medicamento")

                val unPaciente = tbPacienteA(uuid, nombres, apellidos, edad, enfermedad, numeroHabitacion, numeroCama, fechaNacimiento, medicamento)
                listaPacientes.add(unPaciente)
            }
            return listaPacientes
        }

        CoroutineScope(Dispatchers.IO).launch {
            val pacientesBD = obtenerPacientes()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorPaciente(pacientesBD)
                rcvPacientes.adapter = adapter
            }
        }
    }
}