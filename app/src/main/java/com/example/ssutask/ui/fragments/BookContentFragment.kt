package com.example.ssutask.ui.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.ssutask.databinding.FragmentBookContentBinding
import com.example.ssutask.domain.entity.BookEntity
import com.example.ssutask.ui.fragments.dialogs.DeleteBookDialogFragment
import com.example.ssutask.ui.util.addBookIdArg
import com.example.ssutask.ui.util.getBookIdFromArgs
import com.example.ssutask.ui.viewmodels.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookContentFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentBookContentBinding? = null;
    private val binding get() = _binding!!
    private val booksViewModel: BooksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookContentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            val bookEntity = BookEntity
                .ToDomain(booksViewModel.getBookById(getBookIdFromArgs(arguments)))

            binding.name.text = bookEntity?.title
            binding.description.text = bookEntity?.description

            binding.deleteBook.setOnClickListener{
                DeleteBookDialogFragment(bookEntity)
                    .show(childFragmentManager, null)
            }
        }

        binding.editBook.setOnClickListener{
            var navController = Navigation.findNavController(requireView())
            navController.navigate(
                com.example.ssutask.R.id.navigation_add_book,
                addBookIdArg(getBookIdFromArgs(arguments))
            )
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
}