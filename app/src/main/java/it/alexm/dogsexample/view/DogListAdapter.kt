package it.alexm.dogsexample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.alexm.dogsexample.R
import it.alexm.dogsexample.loadImage
import it.alexm.dogsexample.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*


/**
 * Created by alexm on 04/04/2020
 */
class DogListAdapter(private val dogsList: ArrayList<DogBreed> = arrayListOf()) :
    RecyclerView.Adapter<DogListAdapter.DogViewHolder>() {

    fun updateDogsList(newList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dog, parent, false).let(::DogViewHolder)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.name.text = dogsList[position].dogBreed
        holder.view.lifeSpan.text = dogsList[position].lifeSpan
        holder.view.itemImage.loadImage(dogsList[position].imageUrl)
        holder.view.setOnClickListener {
            val action = ListFragmentDirections.toDetail()
            action.dogId = dogsList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }
    }

    inner class DogViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}