    package com.example.padelbook.ui.map

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.activityViewModels
    import com.example.padelbook.R
    import com.example.padelbook.models.SharedViewModel
    import com.example.padelbook.service.PadelService
    import com.google.firebase.firestore.FirebaseFirestore
    import org.osmdroid.config.Configuration
    import org.osmdroid.views.MapView
    import org.osmdroid.tileprovider.tilesource.TileSourceFactory
    import org.osmdroid.util.GeoPoint
    import org.osmdroid.views.overlay.Marker
    import java.io.File


    class MapFragment : Fragment() {
        private lateinit var mapView: MapView
        private val sharedViewModel: SharedViewModel by activityViewModels()

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

            val db = FirebaseFirestore.getInstance();
            val service: PadelService = PadelService();
            service.getAllFields(db, sharedViewModel)

            val fields = sharedViewModel.fields
            mapView.overlays.clear()

            // Observe the fields list in the SharedViewModel
            for (field in fields) {
                // Create a new Marker
                val marker = Marker(mapView)

                // Get the latitude and longitude from the GeoPoint object
                val latitude = field.location.value?.latitude
                val longitude = field.location.value?.longitude

                // Check if the latitude and longitude are not null
                if (latitude != null && longitude != null) {
                    // Create a new GeoPoint object
                    val geoPoint = GeoPoint(latitude, longitude)

                    // Set the position of the Marker based on the GeoPoint object
                    marker.position = geoPoint
                }

                // Set the title of the Marker based on the name of the field
                marker.title = field.name.value

                // Add the Marker to the map overlays
                mapView.overlays.add(marker)
            }

            // Force the map to redraw itself
            mapView.invalidate()
        }
    }