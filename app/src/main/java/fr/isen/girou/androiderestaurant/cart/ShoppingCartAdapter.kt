package fr.isen.girou.androiderestaurant.erestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.girou.androiderestaurant.R
import fr.isen.girou.androiderestaurant.cart.ItemShoppingCart
import fr.isen.girou.androiderestaurant.databinding.ActivityShoppingCartAdapterBinding

class ShoppingCartAdapter(val data: ArrayList<ItemShoppingCart>, val clickListener: (ItemShoppingCart) -> Unit)
    :RecyclerView.Adapter<ShoppingCartAdapter.CartViewHolder>(){
    private lateinit var binding: ActivityShoppingCartAdapterBinding

            inner class CartViewHolder (binding: ActivityShoppingCartAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        val productImage: ImageView = binding.productImage
        val productName: TextView = binding.productName
        val productQuantity: TextView = binding.productQuantity
        val productPrice: TextView = binding.productPrice
        //val deleteItem: ImageView = binding.deleteBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        binding = ActivityShoppingCartAdapterBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = data[position]
        if (item.cartItem.name_fr.length > 13) {
            holder.productName.text = item.cartItem.name_fr.subSequence(0,13)
        }
        else {
            holder.productName.text = item.cartItem.name_fr
        }
        holder.productQuantity.text = item.quantity.toString()
        var price = item.cartItem.prices[0].price.toFloat() * item.quantity
        holder.productPrice.text = price.toString()

        val url = item.cartItem.images[0]
        Picasso.get().load(url.ifEmpty { null }).fit().centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.productImage);

        /*holder.deleteItem.setOnClickListener {
            data.remove(data[position])
            notifyItemRemoved(position)
            clickListener(item)
        }*/
    }

    override fun getItemCount(): Int {
        return data.size
    }
}