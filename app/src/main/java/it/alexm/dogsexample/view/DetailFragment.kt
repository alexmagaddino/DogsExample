package it.alexm.dogsexample.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import it.alexm.dogsexample.R
import it.alexm.dogsexample.databinding.FragmentDetailBinding
import it.alexm.dogsexample.model.DogBreed
import it.alexm.dogsexample.model.DogPalette
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
                        it.imageUrl?.let(::setupBackgroundColor)
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

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate { palette ->
                        val myPalette = DogPalette(
                            palette?.vibrantSwatch?.rgb ?: 0
                        )
                        detailBinding.palette = myPalette
                    }
                }
            })
    }
}
