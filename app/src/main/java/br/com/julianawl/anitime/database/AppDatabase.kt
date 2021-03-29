package br.com.julianawl.anitime.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.julianawl.anitime.database.AnimeMigration.Companion.migration_1_2
import br.com.julianawl.anitime.database.AnimeMigration.Companion.migration_2_3
import br.com.julianawl.anitime.database.dao.AnimeDAO
import br.com.julianawl.anitime.model.AnimeItem

const val dbName = "anime-db"
@Database(entities = [AnimeItem::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun animeDAO(): AnimeDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    dbName
                ).addMigrations(migration_1_2, migration_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}