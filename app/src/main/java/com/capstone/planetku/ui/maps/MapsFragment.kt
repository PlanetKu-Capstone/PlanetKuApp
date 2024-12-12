package com.capstone.planetku.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.capstone.planetku.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        setupMap()

        val markers = listOf(
            LatLng(-6.122802, 106.899351) to Pair("Tempat Sampah 1", "Area Jakarta Utara"),
            LatLng(-6.256597, 106.817287) to Pair("Tempat Sampah 2", "Area Jakarta Selatan"),
            LatLng(-6.404243, 106.813408) to Pair("Tempat Sampah 3", "Area Depok"),
            LatLng(-6.185777, 106.831073) to Pair("Tempat Sampah 4", "Area Jakarta Pusat")
        )

        val boundsBuilder = LatLngBounds.Builder()

        for (markerData in markers) {
            val location = markerData.first
            val title = markerData.second.first
            val snippet = markerData.second.second

            addCustomMarker(location, title, snippet)

            boundsBuilder.include(location)
        }

        val bounds = boundsBuilder.build()
        val padding = 100
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))

        mMap.setOnMapClickListener { latLng ->
            addCustomMarker(latLng, "Marker Baru", "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}")
        }

        enableUserLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setupMap() {
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isTiltGesturesEnabled = true
        mMap.uiSettings.isRotateGesturesEnabled = true

        // Menyesuaikan InfoWindow untuk marker
        mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null // Gunakan default
            }

            override fun getInfoContents(marker: Marker): View {
                val view = layoutInflater.inflate(R.layout.custom_info_window, null)
                val title = view.findViewById<TextView>(R.id.title)
                val snippet = view.findViewById<TextView>(R.id.snippet)

                title.text = marker.title
                snippet.text = marker.snippet
                return view
            }
        })
    }

    private fun addCustomMarker(latLng: LatLng, title: String, snippet: String) {
        val markerOptions = MarkerOptions()
            .position(latLng)
            .title(title)
            .snippet(snippet)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        mMap.addMarker(markerOptions)
    }

    private fun enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
