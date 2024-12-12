package app.c14220170.roomdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.c14220170.roomdb.database.daftarBelanja
import app.c14220170.roomdb.database.daftarBelanjaDB
import app.c14220170.roomdb.database.historyBarang
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    //Hubungin db
    private lateinit var DB : daftarBelanjaDB
    //Hubungin arDaftar dengan recyclerview
    private lateinit var adapterDaftar : adapterDaftar
    //Penyimpanan sementara data daftarBelanja
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = daftarBelanjaDB.getDatabase(this)
        adapterDaftar = adapterDaftar(arDaftar)
        var _rvDaftar = findViewById<RecyclerView>(R.id.rvDaftarBelanja)

        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        val _btnHistory = findViewById<Button>(R.id.btnHistory)
        _btnHistory.setOnClickListener {
            startActivity(Intent(this, history::class.java))
        }

        adapterDaftar.setOnItemClickCallback(
            object : adapterDaftar.OnItemClickCallback {
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftar = DB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftar)
                        }
                    }
                }
                override fun doneData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.fundhistoryBarangDAO().insert(
                            historyBarang(
                                tanggal = dtBelanja.tanggal,
                                item = dtBelanja.item,
                                jumlah = dtBelanja.jumlah
                            )
                        )

                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftar = DB.fundaftarBelanjaDAO().selectAll()
                        withContext(Dispatchers.Main) {
                            adapterDaftar.isiData(daftar)
                        }
                    }
                }
            })
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("data ROOM", daftarBelanja.toString())
        }
    }


}