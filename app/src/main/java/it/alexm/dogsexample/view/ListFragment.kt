package it.alexm.dogsexample.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import it.alexm.dogsexample.R
import kotlinx.android.synthetic.main.fragment_list.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_show_detail.setOnClickListener {
            val action = ListFragmentDirections.toDetail()
            Navigation.findNavController(it).navigate(action)
        }
    }
}
