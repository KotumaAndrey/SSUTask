package com.example.ssutask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ssutask.data.local.entity.BookDAOEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Query("SELECT * FROM Books WHERE id = :bookId")
    fun getBookById(bookId: Long): BookDAOEntity

    @Query("SELECT * FROM Books")
    fun getAllBooks(): Flow<List<BookDAOEntity>>

    @Query("DELETE FROM Books WHERE id = :bookId")
    fun deleteBookById(bookId: Long): Void

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createBook(bookDAOEntity: BookDAOEntity): Void

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateBook(bookDAOEntity: BookDAOEntity): Void
}