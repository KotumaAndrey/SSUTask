package com.example.ssutask.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.ssutask.databinding.FragmentAddBookBinding
import com.example.ssutask.domain.entity.BookEntity
import com.example.ssutask.ui.util.getBookIdFromArgs
import com.example.ssutask.ui.util.hasKeyInArgs
import com.example.ssutask.ui.viewmodels.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AddBookFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentAddBookBinding? = null;
    private val binding get() = _binding!!
    private val booksViewModel: BooksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (hasKeyInArgs(arguments)) {
            binding.headerTitle.text = "Edit book";

            viewLifecycleOwner.lifecycleScope.launch {
                val bookEntity = BookEntity
                    .ToDomain(booksViewModel.getBookById(getBookIdFromArgs(arguments)))

                binding.bookTitle.editText?.setText(bookEntity.title)
                binding.bookDescription.editText?.setText(bookEntity.description)
            }
        }

        binding.createProject.setOnClickListener {
            if (binding.bookTitle.editText?.text!!.isEmpty()) {
                Toast.makeText(context, "Add book title", Toast.LENGTH_SHORT).show()
            } else if (binding.bookDescription.editText?.text!!.isEmpty()) {
                Toast.makeText(context, "Add book text", Toast.LENGTH_SHORT).show()
            } else {
                val entity = BookEntity.ToDAO(
                    BookEntity(
                        0,
                        binding.bookTitle.editText?.text!!.toString(),
                        binding.bookDescription.editText?.text!!.toString()
                    )
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    if (hasKeyInArgs(arguments)) {
                        entity.id = getBookIdFromArgs(arguments)
                        booksViewModel.updateBook(entity)
                    } else {
                        booksViewModel.addBook(entity)
                    }
                    goBack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun goBack() {
        var navController = Navigation.findNavController(requireView())
        navController?.navigateUp()
    }
}