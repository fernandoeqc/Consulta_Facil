package com.example.consulta_facil
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VacinaAdapter(private var list:List<Vacina>):RecyclerView.Adapter<VacinasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacinasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_vacinas,parent,false)
        return VacinasViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacinasViewHolder, position: Int) {
        val context = holder.item.context
        val vacina = list[position]
        //print list
        Log.d("VACINAS", vacina.toString())
        holder.nomeMedico.text = vacina.nomeMedico
        holder.especialidade.text = vacina.specialty
        holder.dataVacina.text = vacina.data

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalhesVacina::class.java)
            intent.putExtra("vacina", vacina)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        Log.d("VacinaAdapter", "Tamanho da lista: ${list.size}")
        return list.size
    }

}

class VacinasViewHolder (var item: View) :RecyclerView.ViewHolder(item){
    var nomeMedico: TextView = item.findViewById(R.id.nomeMedico_vacina)
    var especialidade: TextView = item.findViewById(R.id.tipo_vacina)
    var dataVacina: TextView = item.findViewById(R.id.data_vacina)
}