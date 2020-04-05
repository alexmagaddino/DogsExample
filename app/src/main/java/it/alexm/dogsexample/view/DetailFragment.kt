package it.alexm.dogsexample.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import it.alexm.dogsexample.R
import it.alexm.dogsexample.loadImage
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.Result
import it.alexm.dogsexample.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var dogId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        arguments?.also {
            dogId = DetailFragmentArgs.fromBundle(it).dogId
        }

        observeViewModel(dogId)
    }

    private fun observeViewModel(dogId: Int) {
        detailViewModel.getDetail(dogId).observe(viewLifecycleOwner, Observer { res ->
            when (res) {
                is Result.Loading -> {
                }
                is Result.Error<*> -> {
                }
                is Result.Success<*> -> {
                    (res.value as? DogBreed)?.let {
                        dogName.text = it.dogBreed
                        dogPurpose.text = it.bredFor
                        dogTemperament.text = it.temperament
                        dogLifespan.text = it.lifeSpan
                        imageView.loadImage(it.imageUrl)
                    }
                }
            }
        })
    }
}
