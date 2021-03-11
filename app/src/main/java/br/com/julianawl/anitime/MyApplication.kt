package br.com.julianawl.anitime

import android.app.Application
import br.com.julianawl.anitime.database.AppDatabase
import br.com.julianawl.anitime.repository.AnimesRepository

open class MyApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AnimesRepository(database.animeDAO()) }
}