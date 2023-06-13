package com.example.ssutask.ui.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.ssutask.domain.entity.BookEntity
import com.example.ssutask.ui.viewmodels.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteBookDialogFragment(
    private val book: BookEntity
) : DialogFragment() {
    private val booksViewModel: BooksViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Delete this book")
        builder.setMessage("Are you sure to delete ${book.title} ?")

        builder.setPositiveButton("Yes") { dialog, _ ->
            booksViewModel.deleteBookById(book.id);
            dialog.dismiss()

            var navController = Navigation.findNavController(requireParentFragment().requireView())
            navController?.navigate("home")
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.cancel() }
        return builder.create()
    }
}