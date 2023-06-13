package com.example.ssutask.ui.fragments

import RecyclerItemClickListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.ssutask.R
import com.example.ssutask.databinding.FragmentBookListBinding
import com.example.ssutask.domain.entity.BookEntity
import com.example.ssutask.ui.adapters.BooksAdapter
import com.example.ssutask.ui.util.addBookIdArg
import com.example.ssutask.ui.util.launchWhenStarted
import com.example.ssutask.ui.viewmodels.BooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class BooksFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentBookListBinding? = null;
    private val binding get() = _binding!!
    private val booksViewModel: BooksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcomeText.text = booksViewModel.welcomeText

        booksViewModel.booksList.onEach {
            binding.recyclerViewProjects.adapter = BooksAdapter(
                projectEntities = it.map { item -> BookEntity.ToDomain(item) }
            )
        }.launchWhenStarted(lifecycleScope)

        binding.recyclerViewProjects.addOnItemTouchListener(
            RecyclerItemClickListener(
                context,
                view.findViewById(R.id.recyclerViewProjects),
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        var navController = Navigation.findNavController(requireView())
                        navController.navigate(R.id.navigation_book_content,
                            addBookIdArg(binding.recyclerViewProjects.adapter?.getItemId(position)!!)
                        )
                    }

                    override fun onLongItemClick(view: View?, position: Int) {

                    }
                })
        )

        binding.addProject.setOnClickListener {
            var navController = Navigation.findNavController(requireView())
            navController?.navigate("addBook")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}