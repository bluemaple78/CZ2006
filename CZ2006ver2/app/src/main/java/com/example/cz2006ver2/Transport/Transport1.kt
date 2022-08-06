package com.example.cz2006ver2.Transport

/**
 * Importing Relevant Dependencies for Google Maps API
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.cz2006ver2.Account.AccountMainActivity
import com.example.cz2006ver2.Calendar.CalendarMainActivity
import com.example.cz2006ver2.HomePage.HomePage1
import com.example.cz2006ver2.R

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
import kotlinx.android.synthetic.main.activity_trans1.*

/**
 * This is the primary Transport Class which shows the User's Current Location.
 * User has option to search for bus routes/carpark and also go to favourites.
 */
class Transport1 : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityTrans1Binding
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    /**
     * Method used to start default activity. Link to other pages.
     * @param savedInstanceState to get prior version. If no data is supplies, then NULL.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrans1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val elderUID = intent.getStringExtra("key").toString()

        homeicon_page2.setOnClickListener{
            val hmpage = Intent(this, HomePage1::class.java)
            hmpage.putExtra("key", elderUID)
            startActivity(hmpage)
        }

        calendaricon_page2.setOnClickListener{
            val calst = Intent(this, CalendarMainActivity::class.java)
            calst.putExtra("key", elderUID)
            startActivity(calst)
        }

        transporticon_page2.setOnClickListener{
            val transp1 = Intent(this, Transport1::class.java)
            transp1.putExtra("key", elderUID)
            startActivity(transp1)
        }

        accounticon_page2.setOnClickListener{
            val accnt = Intent(this, AccountMainActivity::class.java)
            accnt.putExtra("key", elderUID)
            startActivity(accnt)
        }
        imageView6.setOnClickListener{
            val searchpage = Intent(this, Transport2::class.java)
            searchpage.putExtra("key", elderUID)
            startActivity(searchpage)
        }
        imageView10.setOnClickListener{
            val searchpage1 = Intent(this, Transport2::class.java)
            searchpage1.putExtra("key", elderUID)
            startActivity(searchpage1)
        }
        imageView9.setOnClickListener{
            val favpage = Intent(this, Transport4::class.java)
            favpage.putExtra("key", elderUID)
            startActivity(favpage)
        }
        imageView11.setOnClickListener{
            val favpage1 = Intent(this, Transport4::class.java)
            favpage1.putExtra("key", elderUID)
            startActivity(favpage1)
        }
    }

    /**
     * Method to set up Google Maps API. Calls setUpMap() method.
     * @param googleMap
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    /**
     * Method to request permission and get current location.
     * Invokes the placeMarkerOnMap() method.
     */
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 17f))
            }
            else {
                val place = LatLng(1.3471, 103.6808)
                mMap.addMarker(MarkerOptions().position(place).title("North Spine"))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 17f))
                val lwn = LatLng(1.3479980428579894, 103.6806107143307)
                val blk2 = LatLng(1.348021140452294, 103.68026415440096)
                val carparka = LatLng(1.3450051246657206, 103.68081444821753)
                val lwnlib = MarkerOptions().position(lwn)
                val blkmar = MarkerOptions().position(blk2)
                val carmark = MarkerOptions().position(carparka)
                blkmar.title("Buses: 199")
                mMap.addMarker(blkmar)
                lwnlib.title("Buses: 179, 179A")
                mMap.addMarker(lwnlib)
                carmark.title("NTU Carpark A")
                mMap.addMarker(carmark)

            }
        }
    }

    /**
     * Method used to place marker on the passed coordinates in Google Maps.
     * @param currentLatLong
     */
    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        val lwn = LatLng(1.3479980428579894, 103.6806107143307)
        val blk2 = LatLng(1.348021140452294, 103.68026415440096)
        val carparka = LatLng(1.3450051246657206, 103.68081444821753)
        val lwnlib = MarkerOptions().position(lwn)
        val blkmar = MarkerOptions().position(blk2)
        val carmark = MarkerOptions().position(carparka)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
        blkmar.title("Buses: 199")
        mMap.addMarker(blkmar)
        lwnlib.title("Buses: 179, 179A")
        mMap.addMarker(lwnlib)
        carmark.title("NTU Carpark A")
        mMap.addMarker(carmark)
    }

    /**
     * Method for default behaviour.
     */
    override fun onMarkerClick(p0: Marker) = false
}