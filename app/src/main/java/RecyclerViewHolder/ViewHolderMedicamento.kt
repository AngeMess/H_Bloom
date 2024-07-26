package RecyclerViewHolder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import angelcampos.christianmarin.h_bloom.R

class ViewHolderMedicamento(view: View): RecyclerView.ViewHolder(view) {
    val txtNombreMedicamento = view.findViewById<TextView>(R.id.txtNombreMedicamento)
    val imgEliminarMedicamento = view.findViewById<ImageView>(R.id.imgEliminarMedicamento)
}