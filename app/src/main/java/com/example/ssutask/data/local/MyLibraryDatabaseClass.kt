package com.example.ssutask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ssutask.data.local.dao.BooksDao
import com.example.ssutask.data.local.entity.BookDAOEntity

@Database(
    entities = [
        BookDAOEntity::class],
    version = 1, exportSchema = false
)
abstract class MyLibraryDatabaseClass : RoomDatabase() {
    abstract val booksDao: BooksDao

    companion object {
        const val DATABASE_NAME = "book_db"

        @Volatile
        private var instance: MyLibraryDatabaseClass? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MyLibraryDatabaseClass::class.java,
            DATABASE_NAME
        ).build()
    }
}