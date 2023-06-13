package com.example.ssutask.domain.entity

import com.example.ssutask.data.local.entity.BookDAOEntity


data class BookEntity(
    var id: Long,
    var title: String?,
    var description: String?
) {
    companion object {
        fun ToDomain(source: BookDAOEntity): BookEntity {
            return BookEntity(source.id, source.title, source.description)
        }

        fun ToDAO(source: BookEntity): BookDAOEntity {
            return BookDAOEntity(source.id, source.title, source.description)
        }
    }
}
