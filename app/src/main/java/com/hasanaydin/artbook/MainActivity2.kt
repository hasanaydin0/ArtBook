package com.hasanaydin.artbook

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hasanaydin.artbook.databinding.ActivityMain2Binding
import java.util.jar.Manifest

class MainActivity2 : AppCompatActivity() {

    var selectedPicture : Uri? = null

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun save(view: View) {

    }

    fun selectImage(view: View){

        // USES PERMISSION
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery,2)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == 1){

            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentToGallery,2)

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){

            selectedPicture =  data.data

            if (selectedPicture != null){
                if (Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(this.contentResolver,selectedPicture!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.imageView.setImageBitmap(bitmap)
                }else{
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPicture)
                    binding.imageView.setImageBitmap(bitmap)
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}