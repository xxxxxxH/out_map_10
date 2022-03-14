package com.cctv.xh.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cctv.libbbbbb.Harry
import com.cctv.xh.R
import com.cctv.xh.http.getCameraPosition
import com.cctv.xh.http.getLocation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sevenheaven.segmentcontrol.SegmentControl
import kotlinx.android.synthetic.main.activity_main.*

class MapsActivity : AppCompatActivity() {

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Harry.potter(this)
        initMap()
        segment_control.setDirection(SegmentControl.Direction.HORIZONTAL)
        segment_control.setOnSegmentControlClickListener { index->
            when(index){
                0->{
                    map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                }
                1->{
                    map!!.mapType = GoogleMap.MAP_TYPE_HYBRID
                }
                2->{
                    map!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
                }
                3->{
                    map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
                }
            }
        }
        segment_control2.setDirection(SegmentControl.Direction.VERTICAL)
        segment_control2.setOnSegmentControlClickListener { index->
            when(index){
                1->{
                    startActivity(Intent(this,Near::class.java))
                }
                2->{
                    startActivity(Intent(this,Interactive::class.java))
                }
                3->{
                    startActivity(Intent(this,Setting::class.java))
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapview) as SupportMapFragment?
        mapFragment!!.getMapAsync {
            map = it
            map!!.uiSettings.isZoomControlsEnabled = true
            map!!.isMyLocationEnabled = true
            map!!.uiSettings.isMyLocationButtonEnabled = true
            map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            val location = getLocation(this)
            val lat = location.latitude //纬度30
            val lgt = location.longitude //经度104
            if (lat != 0.0 && lgt != 0.0) {
                map?.let { it1 ->
                    it1.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            getCameraPosition(
                                lat,
                                lgt
                            )
                        ), 1000, null
                    )
                    it1.addMarker(
                        MarkerOptions()
                            .position(LatLng(lat, lgt)).title("On Your Location")
                    )
                    it1.setOnMyLocationButtonClickListener {
                        it1.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                getCameraPosition(
                                    lat,
                                    lgt
                                )
                            ), 1000, null
                        )
                        false
                    }
                }
            }
        }
    }
}