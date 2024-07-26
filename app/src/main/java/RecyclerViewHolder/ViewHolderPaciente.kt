package RecyclerViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import angelcampos.christianmarin.h_bloom.R

class ViewHolderPaciente(view: View): RecyclerView.ViewHolder(view) {
    val txtNombrePaciente = view.findViewById<TextView>(R.id.txtNombrePaciente)
    val imgEditarPaciente = view.findViewById<ImageView>(R.id.imgEditarPaciente)
    val imgEliminarPaciente = view.findViewById<ImageView>(R.id.imgEliminarPaciente)
}