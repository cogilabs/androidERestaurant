package fr.isen.girou.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.girou.androiderestaurant.model.Item

internal class CategorieAdapter(private var itemsList: ArrayList<Item>,
                                val clickListener: (Item) -> Unit) :
    RecyclerView.Adapter<CategorieAdapter.MyViewHolder>() {

        internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var itemTextView: TextView = view.findViewById(R.id.itemTextView)
            var priceTextView: TextView = view.findViewById(R.id.priceTextView)
            var itemLogo: ImageView = view.findViewById(R.id.itemLogo)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemTextView.text = item.name_fr
        val prix: String = item.prices[0].price + "€"
        holder.priceTextView.text = prix

        Picasso
            .get()
            .load(item.images[0].ifEmpty { null })
            .placeholder(R.drawable.previewfailed)
            .into(holder.itemLogo)

        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}