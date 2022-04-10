package fr.isen.girou.androiderestaurant.cart

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.girou.androiderestaurant.model.Item
import java.io.File
import java.io.Serializable

class Cart(val items: MutableList<CartItem>): Serializable {
    var itemsCount: Int = 0
        get() {

            val count = items.map {
                it.quantity
            }.reduceOrNull { acc, i -> acc +i } ?: 0
            return count ?:0

        }

    var totalPrice: Float = 0f
        get() {

            return items.map { item ->
                item.quantity * item.item.prices.first().price.toFloat()
            }.reduceOrNull { acc, fl -> acc + fl } ?: 0f
        }

    fun addItem(item: Item, quantity: Int){
        val existingItem = items.firstOrNull{ it.item.name_fr == item.name_fr }
        existingItem?.let {
            existingItem.quantity += quantity
        } ?: run {
            val cartItem = CartItem(item, quantity)
            items.add(cartItem)
        }
    }

    fun removeItem(cartItem: CartItem){
        items.remove(cartItem)
    }

    fun clear(){
        items.removeAll { true }
    }

    fun save(context: Context){
        val jsonFile =  File(context.cacheDir.absolutePath + CART_FILE)

        jsonFile.writeText(toJson())
        updateCounter(context)
    }


    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }

    private fun updateCounter(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()
    }


    companion object{
        fun getCart(context: Context): Cart {
            val count = Cart(mutableListOf()).itemsCount

            val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
            return if (jsonFile.exists()) {
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Cart::class.java)
            } else {
                Cart(mutableListOf())
            }
        }

        const val CART_FILE = "cart.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}

class CartItem(val item: Item,var quantity: Int ): Serializable {

}