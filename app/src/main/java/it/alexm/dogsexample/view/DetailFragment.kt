package it.alexm.dogsexample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import it.alexm.dogsexample.R
import it.alexm.dogsexample.databinding.FragmentDetailBinding
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.Result
import it.alexm.dogsexample.viewmodel.DetailViewModel

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var dogId = 0
    private lateinit var detailBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_detail, container, false
        )
        return detailBinding.root
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
                        detailBinding.dog = it
                    }
                    /* (res.value as? DogBreed)?.let {
                         dogName.text = it.dogBreed
                         dogPurpose.text = it.bredFor
                         dogTemperament.text = it.temperament
                         dogLifespan.text = it.lifeSpan
                         imageView.loadImage(it.imageUrl)
                     }*/
                }
            }
        })
    }
}
