package app.c14220170.roomdb

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.c14220170.roomdb.database.daftarBelanja

class adapterDaftar(private val daftarBelanja: MutableList<daftarBelanja>) : RecyclerView.Adapter<adapterDaftar.ListViewHolder>() {

    private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tanggal)
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.itemBarang)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlahBarang)

        var _btnDelete = itemView.findViewById<ImageButton>(R.id.btnDelete)
        var _btnEdit = itemView.findViewById<ImageButton>(R.id.btnEdit)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]

        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)

        holder._btnEdit.setOnClickListener {
            val intent = Intent(it.context, TambahDaftar::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit", 1)
            it.context.startActivity(intent)
        }

        holder._btnDelete.setOnClickListener {
            onItemClickCallback.delData(daftar)
        }
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    fun isiData(daftar: List<daftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()

    }

}