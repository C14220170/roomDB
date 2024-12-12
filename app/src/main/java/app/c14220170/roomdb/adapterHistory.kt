package app.c14220170.roomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.c14220170.roomdb.database.daftarBelanja
import app.c14220170.roomdb.database.historyBarang

class adapterHistory (private val historyBarang: MutableList<historyBarang>) : RecyclerView.Adapter<adapterHistory.ListViewHolder>() {

    //Untuk menyimpan elemen ui yang akan ditampilkan di recyclerview
    inner class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tanggal)
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.itemBarang)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlahBarang)

    }

    //Untuk membuat viewholder baru, menghubungkan file xml item_list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterHistory.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterHistory.ListViewHolder, position: Int) {
        val history = historyBarang[position]
        holder._tvTanggal.setText(history.tanggal)
        holder._tvItemBarang.setText(history.item)
        holder._tvJumlahBarang.setText(history.jumlah)
    }

    override fun getItemCount(): Int {
        return historyBarang.size
    }

    //Untuk memperbarui data dalam adapter dengan data baru
    fun isiData(daftar: List<historyBarang>) {
        historyBarang.clear()
        historyBarang.addAll(daftar)
        notifyDataSetChanged()

    }
}