package fr.isen.girou.androiderestaurant.model

import java.io.Serializable

data class Item(val name_fr: String, val images: ArrayList<String>, val prices: ArrayList<Price>) :Serializable
