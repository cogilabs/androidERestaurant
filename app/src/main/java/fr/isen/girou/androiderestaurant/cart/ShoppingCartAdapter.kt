package fr.isen.girou.androiderestaurant.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.girou.androiderestaurant.R
import fr.isen.girou.androiderestaurant.cart.CartItem
import fr.isen.girou.androiderestaurant.databinding.CellShoppingCartBinding
import fr.isen.girou.androiderestaurant.model.Item

class ShoppingCartAdapter(private val items: List<CartItem>,
                    val deleteClickListener: (CartItem)-> Unit): RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {
    lateinit var context: Context
    class ShoppingCartViewHolder(binding: CellShoppingCartBinding): RecyclerView.ViewHolder(binding.root) {
        val itemName : TextView = binding.name
        val price : TextView = binding.price
        val quantity : TextView = binding.quantity
        val delete : ImageButton = binding.deleteButton
        val imageView : ImageView = binding.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        context = parent.context
        return ShoppingCartViewHolder(CellShoppingCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }



    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val cartItem = items[position]
        holder.itemName.text = cartItem.item.name_fr
        holder.quantity.text = "${context?.getString(R.string.quantity)} ${cartItem.quantity.toString()}"

        holder.price.text = "${cartItem.item.prices.first().price} €"

        holder.delete.setOnClickListener{
            deleteClickListener.invoke(cartItem)
        }

        Picasso.get()
            .load(cartItem.item.getThumbnailURL())
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}