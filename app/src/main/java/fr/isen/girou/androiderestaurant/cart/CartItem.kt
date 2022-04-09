package fr.isen.girou.androiderestaurant.cart

import fr.isen.girou.androiderestaurant.model.Price
import java.io.Serializable

data class CartItem(val id:String, val name_fr: String, val images: ArrayList<String>, val prices: ArrayList<Price>) :
    Serializable
