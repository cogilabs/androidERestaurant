package fr.isen.girou.androiderestaurant.cart

import java.io.Serializable

data class ItemShoppingCart(var cartItem: CartItem, var quantity:Int) : Serializable
