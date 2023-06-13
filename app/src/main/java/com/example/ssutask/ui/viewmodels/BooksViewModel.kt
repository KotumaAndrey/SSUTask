package com.example.ssutask.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ssutask.data.local.MyLibraryDatabaseClass
import com.example.ssutask.data.local.entity.BookDAOEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor (
    private val db: MyLibraryDatabaseClass
) : ViewModel() {
    val welcomeText: String = "Here you can see the list of previously noted books:";
    val booksList = db.booksDao.getAllBooks();

    fun addBook(entity: BookDAOEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            db.booksDao.createBook(entity);
        }
    }

    suspend fun getBookById(bookId: Long): BookDAOEntity {
        return viewModelScope.async (Dispatchers.IO) {
            return@async db.booksDao.getBookById(bookId)
        }.await()
    }

    fun deleteBookById(bookId: Long){
        viewModelScope.launch(Dispatchers.IO) {
            db.booksDao.deleteBookById(bookId)
        }
    }

    suspend fun updateBook(book: BookDAOEntity) {
        viewModelScope.launch (Dispatchers.IO) {
            db.booksDao.updateBook(book)
        }
    }
}