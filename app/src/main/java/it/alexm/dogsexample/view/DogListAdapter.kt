package it.alexm.dogsexample.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import it.alexm.dogsexample.R
import it.alexm.dogsexample.databinding.ItemDogBinding
import it.alexm.dogsexample.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*


/**
 * Created by alexm on 04/04/2020
 */
class DogListAdapter(private val dogsList: ArrayList<DogBreed> = arrayListOf()) :
    RecyclerView.Adapter<DogListAdapter.DogViewHolder>(), DogClickListener {

    fun updateDogsList(newList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return LayoutInflater.from(parent.context)
            .let {
                DataBindingUtil
                    .inflate<ItemDogBinding>(it, R.layout.item_dog, parent, false)
            }
//            .inflate(R.layout.item_dog, parent, false)
            .let(::DogViewHolder)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
//        holder.view.name.text = dogsList[position].dogBreed
//        holder.view.lifeSpan.text = dogsList[position].lifeSpan
//        holder.view.itemImage.loadImage(dogsList[position].imageUrl)
//        holder.view.setOnClickListener {
//            val action = ListFragmentDirections.toDetail()
//            action.dogId = dogsList[position].uuid
//            Navigation.findNavController(it).navigate(action)
//        }
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    override fun onDogClick(v: View) {
        val action = ListFragmentDirections.toDetail()
        action.dogId = v.dogId.text.toString().toInt()
        Navigation.findNavController(v).navigate(action)
    }

    inner class DogViewHolder(val view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)

}