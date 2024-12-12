package app.c14220170.roomdb

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.c14220170.roomdb.database.daftarBelanjaDB
import app.c14220170.roomdb.database.historyBarang
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class history : AppCompatActivity() {
    //Hubungin db
    private lateinit var DB: daftarBelanjaDB

    //Hubungin arHistory dengan recyclerview
    private lateinit var adapterHistory: adapterHistory

    //Penyimpanan sementara data historyBarang
    private var arHistory: MutableList<historyBarang> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = daftarBelanjaDB.getDatabase(this)
        adapterHistory = adapterHistory(arHistory)
        var _rvhistory = findViewById<RecyclerView>(R.id.rvHistory)

        _rvhistory.layoutManager = LinearLayoutManager(this)
        _rvhistory.adapter = adapterHistory

        val _btnBack = findViewById<Button>(R.id.btnBack)

        _btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBarang = DB.fundhistoryBarangDAO().selectAll()
            adapterHistory.isiData(historyBarang)
            Log.d("data ROOM", historyBarang.toString())
        }
    }
}