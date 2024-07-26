package angelcampos.christianmarin.h_bloom

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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
import modelo.tbMedicamenosA
import java.util.Calendar
import java.util.UUID

class AnadirPaciente : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_anadir_paciente)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spMedicamentos = findViewById<Spinner>(R.id.spMedicamentos)
        val txtNombresPaciente = findViewById<EditText>(R.id.txtNombresPaciente)
        val txtApellido = findViewById<EditText>(R.id.txtApellido)
        val txtEdad = findViewById<EditText>(R.id.txtEdad)
        val txtEnfermedad = findViewById<EditText>(R.id.txtEnfermedad)
        val txtFechaNaci = findViewById<EditText>(R.id.txtFechaNaci)
        val txtNumeroHab = findViewById<EditText>(R.id.txtNumeroHab)
        val txtNumeroCam = findViewById<EditText>(R.id.txtNumeroCam)
        val btnGuardarPaciente = findViewById<Button>(R.id.btnGruardarPaciente)

        txtFechaNaci.setOnClickListener{
            val calendario = Calendar.getInstance()
            val anio = calendario.get(Calendar.YEAR)
            val mes = calendario.get(Calendar.MONTH)
            val dia = calendario.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { view, anioSeleccionado, mesSeleccionado, diaSeleccionado ->
                    val fechaSeleccionada =
                        "$diaSeleccionado/${mesSeleccionado + 1}/$anioSeleccionado"
                    txtFechaNaci.setText(fechaSeleccionada)
                },
                anio, mes, dia
            )
            datePickerDialog.show()
        }


        fun obtenerMedicamentos(): List<tbMedicamenosA> {
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbMedicamentosA")!!

            val listaMedicamentos = mutableListOf<tbMedicamenosA>()

            while (resultSet.next()) {
                val uuid = resultSet.getString("UUID_Medicamento")
                val nombre = resultSet.getString("Nombre_medicamento")
                val hora = resultSet.getString("Hora_aplicacion")
                val unMedicamento = tbMedicamenosA(uuid, nombre, hora)
                listaMedicamentos.add(unMedicamento)
            }
            return listaMedicamentos
        }

        CoroutineScope(Dispatchers.IO).launch {
            val listaMedicamentos = obtenerMedicamentos()
            val nombreMedic = listaMedicamentos.map { it.Nombre_medicamento }

            withContext(Dispatchers.Main){
                val miAdaptador = ArrayAdapter(this@AnadirPaciente, android.R.layout.simple_spinner_dropdown_item, nombreMedic)
                spMedicamentos.adapter = miAdaptador
            }
        }

        btnGuardarPaciente.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val objConexion = ClaseConexion().cadenaConexion()
                val medicamento = obtenerMedicamentos()

                val addPaciente = objConexion?.prepareStatement("insert into tbPacienteA (UUID_Paciente, Nombres, Apellidos, Edad, Enfermedad, Número_habitacion, Número_cama, Fecha_nacimiento, UUID_Medicamento) values (?,?,?,?,?,?,?,?,?)")!!
                addPaciente.setString(1, UUID.randomUUID().toString())
                addPaciente.setString(2, txtNombresPaciente.text.toString())
                addPaciente.setString(3, txtApellido.text.toString())
                addPaciente.setInt(4, txtEdad.text.toString().toInt())
                addPaciente.setString(5, txtEnfermedad.text.toString())
                addPaciente.setInt(6, txtNumeroHab.text.toString().toInt())
                addPaciente.setInt(7, txtNumeroCam.text.toString().toInt())
                addPaciente.setString(8, txtFechaNaci.text.toString())
                addPaciente.setString(9, medicamento[spMedicamentos.selectedItemPosition].UUID_Medicamento)
                addPaciente.executeUpdate()

                withContext(Dispatchers.Main){
                    txtNombresPaciente.setText("")
                    txtApellido.setText("")
                    txtEdad.setText("")
                    txtEnfermedad.setText("")
                    txtFechaNaci.setText("")
                    txtNumeroHab.setText("")
                    txtNumeroCam.setText("")
                    Toast.makeText(this@AnadirPaciente, "Paciente añadido", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}