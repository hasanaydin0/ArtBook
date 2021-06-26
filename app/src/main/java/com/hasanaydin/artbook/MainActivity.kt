package com.hasanaydin.artbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.hasanaydin.artbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val artNameList = ArrayList<String>()
        val artIdList = ArrayList<Int>()

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,artNameList)
        binding.listView.adapter = arrayAdapter

        try {

            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM arts",null)
            val artnameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")


            while (cursor.moveToNext()){
                artNameList.add(cursor.getString(artnameIx))
                artIdList.add(cursor.getInt(idIx))
            }
            arrayAdapter.notifyDataSetChanged()

            cursor.close()

        }catch (e:Exception){
            e.printStackTrace()
        }

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("info","old")
            intent.putExtra("id",artIdList[position])
            startActivity(intent)
        }

    }

    // OPTIONS MENU
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // INFLATE
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_art,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.add_art_item){
            val intent = Intent(this,MainActivity2::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
    // END OPTIONS MENU
}