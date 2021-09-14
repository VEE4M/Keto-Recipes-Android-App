package com.gmail.appverstas.ketorecipes.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navArgs
import com.gmail.appverstas.ketorecipes.R
import com.gmail.appverstas.ketorecipes.adapters.PagerAdapter
import com.gmail.appverstas.ketorecipes.data.local.entities.FavouritesEntity
import com.gmail.appverstas.ketorecipes.databinding.ActivityDetailsBinding
import com.gmail.appverstas.ketorecipes.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*


@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val args: DetailsActivityArgs by navArgs()
    private lateinit var binding: ActivityDetailsBinding

    private var favouriteId = 0
    private var isFavourite = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val fragments = arrayListOf<Fragment>(
            OverviewFragment(),
            IngredientsFragment(),
            InstructionsFragment()
        )

        val listOfTitles = arrayListOf<String>(
            "Overview",
            "Ingredients",
            "Instructions"
        )

        val recipeBundle = Bundle()
        recipeBundle.putParcelable("recipeBundle", args.result)

        val pagerAdapter = PagerAdapter(recipeBundle, fragments, this)

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = listOfTitles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.details_menu_star)
        menuItem?.let { menuItem ->
            mainViewModel.favourites.observe(this, Observer { favourites ->
                for (entity in favourites) {
                    if (entity.recipe.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        isFavourite = true
                        favouriteId = entity.id
                        break
                    }else{
                        changeMenuItemColor(menuItem, R.color.lightGray)
                        isFavourite = false
                    }
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.details_menu_star -> favouriteButtonClick(item)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun showSnackbar(message: String){
        val rootLayout = findViewById<View>(android.R.id.content)
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.snackbar_action_ok)){}
            .show()
    }

    private fun favouriteButtonClick(menuItem: MenuItem){
        if(!isFavourite){
            saveToFavourites()
            changeMenuItemColor(menuItem, R.color.yellow)
            showSnackbar(getString(R.string.snackbar_recipe_added_to_favourites))
        }else{
            deleteFromFavourites()
            changeMenuItemColor(menuItem, R.color.white)
            showSnackbar(getString(R.string.snackbar_recipe_removed_from_favourites))
        }
    }

    private fun saveToFavourites() {
        val favouritesEntity = FavouritesEntity(
            0,
            args.result
        )
        mainViewModel.insertFavourite(favouritesEntity)
        isFavourite = true
    }

    private fun deleteFromFavourites(){
        val favouritesEntity = FavouritesEntity(
            favouriteId,
            args.result
        )
        mainViewModel.deleteFavourite(favouritesEntity)
        isFavourite = false
    }

    private fun changeMenuItemColor(menuItem: MenuItem, color: Int) {
        menuItem.icon.setTint(getColor(color))
    }


}