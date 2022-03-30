package com.valentine.permissionexample

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var buttonLoadingImage: Button
    private lateinit var requestpermission: Button
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        buttonLoadingImage = findViewById(R.id.load_image_button)
        requestpermission = findViewById(R.id.request_permission_button)
        imageView = findViewById(R.id.image_view)

        buttonLoadingImage.setOnClickListener {
            loadImage()
            //snackbar
            // Snackbar.make(it, "Ini Snackbar Long", Snackbar.LENGTH_LONG).show()

            //no 2
//            Snackbar.make(it, "Ini snackbar Long dengan Action", Snackbar.LENGTH_LONG).setAction("Text Action"){
//                Toast.makeText(this, "Toast dari Action Snackbar", Toast.LENGTH_LONG).show()}.show()
            //no 3 Membuat Snackbar dengan Tombol Aksi Dismiss!
            val snackBar = Snackbar.make(it, "Snackbar Indefinite", Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction("Dismiss") {
                snackBar.dismiss()
            }
            snackBar.show()


        }

        requestpermission.setOnClickListener {
            requestPermissionLocation()
        }
    }

    //no 1 function load Image
    fun loadImage() {
        Glide.with(this)
            .load("https://img.icons8.com/plasticine/2x/flower.png")
            .circleCrop()
            .into(imageView)
    }

    //check permission
    //no 2 function request permission
    fun requestPermissionLocation() {
        val permissionCheck = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Location DIIZINKAN", Toast.LENGTH_LONG).show()
            getLongLat()
        } else {
            Toast.makeText(this, "Permission Location DITOLAK", Toast.LENGTH_LONG).show()
            requestLocationPermission()
        }
    }

    //konfirmasi user untuk mendapatkan izin.
    private fun requestLocationPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 201)
    }

    //mendapatkan data lokasi dalam bentuk longitude dan latitude
    @SuppressLint("MissingPermission")
    fun getLongLat() {
        val locationManager =
            applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val location: Location? =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        val latLongText = "Lat: ${location?.latitude} Long : ${location?.longitude}"
        Log.d(MainActivity::class.simpleName, latLongText)
        Toast.makeText(
            this,
            latLongText,
            Toast.LENGTH_LONG
        ).show()
    }

    //Mendapatkan Keputusan User mengenai
    //Permintaan Permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            201 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                ) {
                    Toast.makeText(this, "Permissions for Location Permitted", Toast.LENGTH_LONG)
                        .show()
                    getLongLat()
                } else {
                    Toast.makeText(this, "Permissions for Location Denied", Toast.LENGTH_LONG)
                        .show()
                }
            }
            else -> {
                Toast.makeText(this, "The request code doesn't match", Toast.LENGTH_LONG).show()
            }
        }
    }
}
