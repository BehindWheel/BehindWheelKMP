package com.egoriku.grodnoroads

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.CameraType.Stationary
import com.egoriku.grodnoroads.ui.SpeedLimitSign
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

private const val TAG = "MapSampleActivity"

enum class CameraType {
    Stationary,
    Temporary
}

data class Camera(
    val type: CameraType,
    val message: String,
    val speed: Int,
    val position: LatLng
)

private val stationaryCameras = listOf(
    Camera(
        type = Stationary,
        message = "Стационарная камера",
        speed = 70,
        position = LatLng(53.647136, 23.811177)
    )
)

val grodnoPosition = LatLng(53.6687765, 23.8212226)
val defaultCameraPosition = CameraPosition.fromLatLngZoom(grodnoPosition, 13f)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GrodnoRoadsTheme {
                val cameraPositionState = rememberCameraPositionState {
                    position = defaultCameraPosition
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    GoogleMapView(
                        modifier = Modifier.matchParentSize(),
                        cameraPositionState = cameraPositionState,
                        markers = stationaryCameras
                    )

                    IconButton(
                        onClick = {
                            //start navigation
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .size(80.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_car),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    markers: List<Camera>
) {
    val context = LocalContext.current

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(compassEnabled = true)
        )
    }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
            )
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onPOIClick = {
            Log.d(TAG, "POI clicked: ${it.name}")
        }
    ) {
        val markerClick: (Marker) -> Boolean = {
            Log.d(TAG, "${it.title} was clicked")
            cameraPositionState.projection?.let { projection ->
                Log.d(TAG, "The current projection is: $projection")
            }
            false
        }

        markers.forEach { camera ->
            MarkerInfoWindow(
                state = rememberMarkerState(position = camera.position),
                icon = generateHomeMarker(context),
                onClick = markerClick
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(text = camera.message, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    SpeedLimitSign(limit = camera.speed)
                }
            }
        }
    }

    DebugView(cameraPositionState)
}

fun smallIcon(context: Context): Bitmap {
    val height = 80
    val width = 80
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_speed_camera)
    return Bitmap.createScaledBitmap(bitmap, width, height, false)
}

fun generateHomeMarker(context: Context): BitmapDescriptor {
    return BitmapDescriptorFactory.fromBitmap(smallIcon(context))
}

@Composable
private fun DebugView(cameraPositionState: CameraPositionState) {
    Column(
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        val moving =
            if (cameraPositionState.isMoving) "moving" else "not moving"
        Text(text = "Camera is $moving")
        Text(text = "Camera position is ${cameraPositionState.position}")
        Spacer(modifier = Modifier.height(4.dp))
    }
}
