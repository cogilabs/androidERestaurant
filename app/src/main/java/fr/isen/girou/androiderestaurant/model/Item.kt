package fr.isen.girou.androiderestaurant.model

import java.io.Serializable

data class Item(val name_fr: String, val images: ArrayList<String>, val prices: ArrayList<Price>, val ingredients: ArrayList<Ingredient>) :Serializable {
    fun getThumbnailURL(): String? {
        return if(images.isNotEmpty() && images.first().isNotEmpty()){
            images.first()
        }else
            null
    }
}
