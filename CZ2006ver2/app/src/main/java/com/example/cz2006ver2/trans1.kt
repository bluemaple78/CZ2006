package com.example.cz2006ver2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.cz2006ver2.databinding.ActivityTrans1Binding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_home_page1.*
import kotlinx.android.synthetic.main.activity_trans1.*

class trans1 : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityTrans1Binding
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrans1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        homeicon_page2.setOnClickListener{
            val hmpage = Intent(this, HomePage1::class.java)
            startActivity(hmpage)
        }

        calendaricon_page2.setOnClickListener{
            val calst = Intent(this, CalendarMainActivity::class.java)
            startActivity(calst)
        }

        transporticon_page2.setOnClickListener{
            val transp1 = Intent(this, trans1::class.java)
            startActivity(transp1)
        }

        accounticon_page2.setOnClickListener{
            val accnt = Intent(this, AccountMainActivity::class.java)
            startActivity(accnt)
        }
        imageView6.setOnClickListener{
            val searchpage = Intent(this, transport2::class.java)
            startActivity(searchpage)
        }
        imageView10.setOnClickListener{
            val searchpage1 = Intent(this, transport2::class.java)
            startActivity(searchpage1)
        }
        imageView9.setOnClickListener{
            val favpage = Intent(this, transport4::class.java)
            startActivity(favpage)
        }
        imageView11.setOnClickListener{
            val favpage1 = Intent(this, transport4::class.java)
            startActivity(favpage1)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
            }
            else {
                val place = LatLng(1.3521, 103.8198)
                mMap.addMarker(MarkerOptions().position(place).title("Singapore"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15f))
            }
        }

    }
    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false
}