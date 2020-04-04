package it.alexm.dogsexample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.alexm.dogsexample.R
import it.alexm.dogsexample.setVisible
import it.alexm.dogsexample.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val mAdapter = DogListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        viewModel.refresh()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }

        refreshLayout.setOnRefreshListener {
            recyclerView.setVisible(false)
            listError.setVisible(false)
            loadingView.setVisible(true)
            viewModel.refresh()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.apply {
            dogs.observe(viewLifecycleOwner, Observer {
                recyclerView.setVisible(true)
                mAdapter.updateDogsList(it)
            })
            error.observe(viewLifecycleOwner, Observer {
                listError.setVisible(it)
            })
            loading.observe(viewLifecycleOwner, Observer {
                loadingView.setVisible(it)
                if (it) {
                    listError.setVisible(false)
                    recyclerView.setVisible(false)
                }
            })
        }
    }
}
