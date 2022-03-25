package fr.isen.girou.androiderestaurant

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class CarouselAdapter(activity: AppCompatActivity, val images: ArrayList<String>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = images.size

    override fun createFragment(position: Int): Fragment {
        return PictureFragment.newInstance(images[position])
    }

}