package RecyclerViewHolder

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import angelcampos.christianmarin.h_bloom.R
import angelcampos.christianmarin.h_bloom.VerPaciente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.tbPacienteA

class AdaptadorPaciente(var Datos: List<tbPacienteA>): RecyclerView.Adapter<ViewHolderPaciente>() {

    fun eliminarDatos(Nombres: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val deletePaciente = objConexion?.prepareStatement("delete tbPacienteA where Nombres =?")!!
            deletePaciente.setString(1, Nombres)
            deletePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit?.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun editarDatos(NuevoNombre: String, NuevoApellido: String, NuevaEdad: Int, NuevaEnfermedad: String, NuevoNumeroHabitacion: Int, NuevoNumeroCama: Int, NuevaFechaNacimiento: String, UUID_Paciente: String){
        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val updatePaciente = objConexion?.prepareStatement("update tbPacientesA set Nombres =?, Apellidos =?, Edad =?, Enfermedad =?, Número_Habitacion =?, Número_Cama =?, Fecha_nacimiento =? where UUID_Paciente =?")!!
            updatePaciente.setString(1, NuevoNombre)
            updatePaciente.setString(2, NuevoApellido)
            updatePaciente.setInt(3, NuevaEdad)
            updatePaciente.setString(4, NuevaEnfermedad)
            updatePaciente.setInt(5, NuevoNumeroHabitacion)
            updatePaciente.setInt(6, NuevoNumeroCama)
            updatePaciente.setString(7, NuevaFechaNacimiento)
            updatePaciente.setString(8, UUID_Paciente)
            updatePaciente.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit?.executeUpdate()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPaciente {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_paciente, parent, false)
        return ViewHolderPaciente(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderPaciente, position: Int) {
        val item = Datos[position]
        holder.txtNombrePaciente.text = item.Nombres

        holder.imgEliminarPaciente.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Eliminar Paciente")
            builder.setMessage("¿Desea eliminar el Paciente?")

            builder.setPositiveButton("Si"){
                    dialog, wich -> eliminarDatos(item.Nombres, position)
            }
            builder.setNegativeButton("No"){
                    dialog, wich -> dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        holder.imgEditarPaciente.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Editar Paciente")

            val txtNuevoNombre = EditText(context).apply {
                setText(item.Nombres)
            }
            val txtNuevoApellido = EditText(context).apply {
                setText(item.Apellidos)
            }
            val txtNuevaEdad = EditText(context).apply {
                setText(item.Edad.toString())
            }
            val txtNuevaEnfermedad = EditText(context).apply {
                setText(item.Enfermedad)
            }
            val txtNuevoNumeroHabitacion = EditText(context).apply {
                setText(item.Número_habitacion.toString())
            }
            val txtNuevoNumeroCama = EditText(context).apply {
                setText(item.Número_cama.toString())
            }
            val txtNuevaFechaNacimiento = EditText(context).apply {
                setText(item.Fecha_nacimiento)
            }

            val layout = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                addView(txtNuevoNombre)
                addView(txtNuevoApellido)
                addView(txtNuevaEdad)
                addView(txtNuevaEnfermedad)
                addView(txtNuevoNumeroHabitacion)
                addView(txtNuevoNumeroCama)
                addView(txtNuevaFechaNacimiento)
            }
            builder.setView(layout)

            builder.setPositiveButton("Guardar"){
                    dialog, which -> editarDatos(txtNuevoNombre.text.toString(), txtNuevoApellido.text.toString(), txtNuevaEdad.text.toString().toInt(), txtNuevaEnfermedad.text.toString(), txtNuevoNumeroHabitacion.text.toString().toInt(), txtNuevoNumeroCama.text.toString().toInt(), txtNuevaFechaNacimiento.text.toString(), item.UUID_Paciente)
            }
            builder.setNegativeButton("Cancelar"){
                    dialog, wich -> dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        holder.itemView.setOnClickListener{
            val context = holder.itemView.context

            val pantallaVer = Intent(context, VerPaciente::class.java)
            pantallaVer.putExtra("UUID_Paciente", item.UUID_Paciente)
            pantallaVer.putExtra("Nombres", item.Nombres)
            pantallaVer.putExtra("Apellidos", item.Apellidos)
            pantallaVer.putExtra("Edad", item.Edad)
            pantallaVer.putExtra("Enfermedad", item.Enfermedad)
            pantallaVer.putExtra("Número_habitacion", item.Número_habitacion)
            pantallaVer.putExtra("Número_cama", item.Número_cama)
            pantallaVer.putExtra("Fecha_nacimiento", item.Fecha_nacimiento)
            context.startActivity(pantallaVer)
        }
    }

}