package com.example.consulta_facil
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PrescricaoAdapter(private var list:List<Prescricao>):RecyclerView.Adapter<PrescricaoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescricaoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_atestados,parent,false)
        return PrescricaoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescricaoViewHolder, position: Int) {
        val context = holder.item.context
        val prescricao = list[position]
        //print list
        Log.d("ATESTADOS", prescricao.toString())
        holder.nomeMedico.text = prescricao.nome
        //holder.especialidade.text = atestado.specialty
        holder.dataAtestado.text = (prescricao.dias + "/n" + prescricao.validade)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetalhesPrescricao::class.java)
            intent.putExtra("prescricao", prescricao)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        Log.d("PrescricaoAdapter", "Tamanho da lista: ${list.size}")
        return list.size
    }

}

class PrescricaoViewHolder (var item: View) :RecyclerView.ViewHolder(item){
    var nomeMedico: TextView = item.findViewById(R.id.nomeMedico_atestado)
    //var especialidade: TextView = item.findViewById(R.id.tipo_atestado)
    var dataAtestado: TextView = item.findViewById(R.id.data_atestado)
}