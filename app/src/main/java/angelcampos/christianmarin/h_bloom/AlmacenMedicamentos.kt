package angelcampos.christianmarin.h_bloom

import RecyclerViewHolder.AdaptadorMedicamento
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
import modelo.tbMedicamenosA

class AlmacenMedicamentos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_almacen_medicamentos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rcvMedicamentos = findViewById<RecyclerView>(R.id.rcvMedicamentos)
        rcvMedicamentos.layoutManager = LinearLayoutManager(this)

        fun obtenerMedicamen(): List<tbMedicamenosA>{
            val objConexion = ClaseConexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tbMedicamentosA")!!
            val listaMedicamentos = mutableListOf<tbMedicamenosA>()

            while(resultSet.next()){
                val uuid = resultSet.getString("UUID_Medicamento")
                val nombre = resultSet.getString("Nombre_medicamento")
                val hora = resultSet.getString("Hora_aplicacion")

                val unMedicamento = tbMedicamenosA(uuid, nombre, hora)
                listaMedicamentos.add(unMedicamento)
            }
            return listaMedicamentos
        }

        CoroutineScope(Dispatchers.IO).launch {
            val medicamentosBD = obtenerMedicamen()
            withContext(Dispatchers.Main){
                val adapter = AdaptadorMedicamento(medicamentosBD)
                rcvMedicamentos.adapter = adapter
            }
        }
    }
}