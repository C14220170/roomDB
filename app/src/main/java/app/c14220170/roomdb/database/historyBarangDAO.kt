package app.c14220170.roomdb.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBarangDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: historyBarang)

    @Query("SELECT * FROM historyBarang ORDER BY id asc")
    fun selectAll(): MutableList<historyBarang>

}