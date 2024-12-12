package app.c14220170.roomdb.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class historyBarang(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    val id: Int = 0,

    @ColumnInfo(name = "tanggal")
    val tanggal: String? = null,

    @ColumnInfo(name = "item")
    val item: String? = null,

    @ColumnInfo(name = "jumlah")
    val jumlah: String? = null
)
