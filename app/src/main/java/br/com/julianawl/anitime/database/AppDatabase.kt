package br.com.julianawl.anitime.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.julianawl.anitime.model.AnimeItem

@Database(entities = [AnimeItem::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun animeDAO(): AnimeDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anime-db"
                ).addMigrations(AnimeMigration().migration_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}