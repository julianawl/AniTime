package br.com.julianawl.anitime.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class AnimeMigration() {
    companion object{
        val migration_1_2 = object: Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE 'Anime_Novo' " +
                        "('id' INTEGER PRIMARY KEY NOT NULL, " +
                        "'imageUrl' TEXT NOT NULL, " +
                        "'title' TEXT NOT NULL," +
                        "'episodes' INTEGER," +
                        "'startDate' TEXT," +
                        "'endDate' TEXT," +
                        "'score' REAL NOT NULL)" )
                database.execSQL("INSERT INTO Anime_Novo (" +
                        "id, imageUrl, title, episodes, startDate, endDate, score)" +
                        "SELECT id, imageUrl, title, episodes, startDate, endDate, score FROM AnimeItem")
                database.execSQL("DROP TABLE AnimeItem")
                database.execSQL("ALTER TABLE Anime_Novo RENAME TO AnimeItem")
            }
        }
        val migration_2_3 = object: Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE AnimeItem ADD COLUMN status INTEGER")
            }
        }
    }




}