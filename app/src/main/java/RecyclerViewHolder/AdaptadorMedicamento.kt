package RecyclerViewHolder

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import angelcampos.christianmarin.h_bloom.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import modelo.tbMedicamenosA

class AdaptadorMedicamento(var Datos: List<tbMedicamenosA>): RecyclerView.Adapter<ViewHolderMedicamento>() {

    fun eleminarDatos(Nombre_medicamento: String, posicion: Int){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            val objConexion = ClaseConexion().cadenaConexion()

            val deleteMedicamento = objConexion?.prepareStatement("delete tbMedicamentosA where Nombre_medicamento =?")!!
            deleteMedicamento.setString(1, Nombre_medicamento)
            deleteMedicamento.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit?.executeUpdate()
        }
        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMedicamento {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card_medicamento, parent, false)
        return ViewHolderMedicamento(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolderMedicamento, position: Int) {
        val item = Datos[position]
        holder.txtNombreMedicamento.text = item.Nombre_medicamento

        holder.imgEliminarMedicamento.setOnClickListener {
            val context = holder.itemView.context
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Eliminar Medicamento")
            builder.setMessage("¿Desea eliminar el Medicamento?")

            builder.setPositiveButton("Si"){
                    dialog, wich -> eleminarDatos(item.Nombre_medicamento, position)
            }
            builder.setNegativeButton("No"){
                    dialog, wich -> dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

}