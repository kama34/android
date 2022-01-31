package ru.kinopoisk.fishermanhandbook

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import ru.kinopoisk.fishermanhandbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    var adapter: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.navView.setNavigationItemSelectedListener(this)

        // создаём пустой адаптер
        binding.mainContent.rcView.hasFixedSize()
        binding.mainContent.rcView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(ArrayList<ListItem>(), this)
        binding.mainContent.rcView.adapter = adapter

        // отмечаем домашний элемент в меню выделенным
        binding.navView.menu.getItem(0).isChecked = true
        // вызываем домашний элемент из меню
        onNavigationItemSelected(binding.navView.menu.getItem(0))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.id_fish -> {
                adapter?.updateAdapter(
                    fillArrays(
                        resources.getStringArray(R.array.fish_name),
                        resources.getStringArray(R.array.fish_content),
                        getImageId(R.array.fish_image)
                    )
                )
            }
            R.id.id_na -> {
                adapter?.updateAdapter(
                    fillArrays(
                        resources.getStringArray(R.array.na_name),
                        resources.getStringArray(R.array.na_content),
                        getImageId(R.array.na_image)
                    )
                )
            }
            R.id.id_sna -> {
                adapter?.updateAdapter(
                    fillArrays(
                        resources.getStringArray(R.array.sna_name),
                        resources.getStringArray(R.array.sna_content),
                        getImageId(R.array.sna_image)
                    )
                )
            }
            R.id.id_history -> {
                adapter?.updateAdapter(
                    fillArrays(
                        resources.getStringArray(R.array.history_name),
                        resources.getStringArray(R.array.history_content),
                        getImageId(R.array.history_image)
                    )
                )
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun fillArrays(
        titleArray: Array<String>,
        contentArray: Array<String>,
        imageArray: IntArray
    ): List<ListItem> {
        var listItemArray = ArrayList<ListItem>()

        for (n in titleArray.indices) {
            var listItem = ListItem(imageArray[n], titleArray[n], contentArray[n])
            listItemArray.add(listItem)
        }

        return listItemArray
    }

    private fun getImageId(imageId: Int): IntArray {
        var tArray: TypedArray = resources.obtainTypedArray(imageId)
        val count = tArray.length()
        val ids = IntArray(count)
        for (i in ids.indices) {
            ids[i] = tArray.getResourceId(i, 0)
        }
        tArray.recycle()
        return ids
    }
}