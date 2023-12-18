    package com.example.padelbook.ui.map

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import com.example.padelbook.R
    import org.osmdroid.config.Configuration
    import org.osmdroid.views.MapView
    import org.osmdroid.tileprovider.tilesource.TileSourceFactory
    import org.osmdroid.util.GeoPoint
    import org.osmdroid.views.overlay.Marker
    import java.io.File

    class MapFragment : Fragment() {
        private lateinit var mapView: MapView

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_map, container, false)
            mapView = view.findViewById(R.id.map)

            // Get the package name
            val packageName = context?.packageName

            // Get the cache directory
            val cacheDir = context?.cacheDir

            val osmConfig = Configuration.getInstance()
            osmConfig.userAgentValue = packageName
            val basePath = File(cacheDir?.absolutePath, "osmdroid")
            osmConfig.osmdroidBasePath = basePath
            val tileCache = File(osmConfig.osmdroidBasePath, "tile")
            osmConfig.osmdroidTileCache = tileCache

            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Set the tile source
            mapView.setTileSource(TileSourceFactory.MAPNIK)

            // Set the map center and zoom level
            val mapController = mapView.controller
            mapController.setZoom(12.0) // Set the zoom level
            mapController.setCenter(GeoPoint(51.2195, 4.4024)) // Set the center of the map

            val marker1 = Marker(mapView)
            marker1.position = GeoPoint(51.185824, 4.436123) // Antwerp
            marker1.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker1.title = "Antwerp padelclub Berchem"

            val marker2 = Marker(mapView)
            marker2.position = GeoPoint(51.195916,4.364045) // Another point near Antwerp
            marker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker2.title = "The Box Antwerp"

            val marker3 = Marker(mapView)
            marker3.position = GeoPoint(51.141062,4.432324) // Another point near Antwerp
            marker3.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker3.title = "Antwerp padelclub Kontich"

            val marker4 = Marker(mapView)
            marker4.position = GeoPoint(51.188819,4.485935) // Another point near Antwerp
            marker4.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker4.title = "El Citadel Antwerp"

            mapView.overlays.add(marker1)
            mapView.overlays.add(marker2)
            mapView.overlays.add(marker3)
            mapView.overlays.add(marker4)

            // Force the map to redraw itself
            mapView.invalidate()
        }
    }