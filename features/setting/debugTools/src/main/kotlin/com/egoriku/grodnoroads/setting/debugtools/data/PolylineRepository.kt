package com.egoriku.grodnoroads.setting.debugtools.data

import com.egoriku.grodnoroads.setting.debugtools.Polyline
import com.egoriku.grodnoroads.setting.debugtools.State
import com.egoriku.grodnoroads.setting.debugtools.State.Loaded
import com.egoriku.grodnoroads.setting.debugtools.State.None
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class PolylineRepository {

    private val _polylines = MutableStateFlow<State>(None)
    val polylines = _polylines.asStateFlow()

    init {
        _polylines.tryEmit(
            Loaded(
                polyline = persistentListOf(
                    Polyline(
                        name = "grodno",
                        points = persistentListOf(
                            LatLng(53.677865, 23.7364759),
                            LatLng(53.677258, 23.7517825),
                            LatLng(53.6958691, 23.7523566),
                            LatLng(53.7137621, 23.7905214),
                            LatLng(53.7418072, 23.8205625),
                            LatLng(53.7228865, 23.9034438),
                            LatLng(53.6988061, 23.8997554),
                            LatLng(53.6840719, 23.8965026),
                            LatLng(53.6676634, 23.8967543),
                            LatLng(53.6483532, 23.8946692),
                            LatLng(53.6165424, 23.8885688),
                            LatLng(53.5998739, 23.8418484),
                            LatLng(53.6110952, 23.7947563),
                            LatLng(53.6160009, 23.7492421),
                            LatLng(53.6308808, 23.741084),
                            LatLng(53.6543211, 23.7315001),
                            LatLng(53.6689345, 23.7294389),
                            LatLng(53.6777983, 23.7256919),
                            LatLng(53.677865, 23.7364759)
                        )
                    ),
                    Polyline(
                        name = "berestovitca",
                        points = persistentListOf(
                            LatLng(53.1951594, 24.0030663),
                            LatLng(53.1884433, 24.0055632),
                            LatLng(53.1815879, 24.008108),
                            LatLng(53.1794024, 24.0166032),
                            LatLng(53.1845447, 24.0302469),
                            LatLng(53.1924883, 24.0363394),
                            LatLng(53.2057499, 24.0254405),
                            LatLng(53.2051506, 24.0132852),
                            LatLng(53.2024523, 24.0061201),
                            LatLng(53.1951594, 24.0030663),
                        )
                    ),
                    Polyline(
                        name = "skidel",
                        points = persistentListOf(
                            LatLng(53.5987627, 24.2287032),
                            LatLng(53.6009458, 24.2445713),
                            LatLng(53.6021933, 24.2643075),
                            LatLng(53.5940372, 24.2696849),
                            LatLng(53.5853207, 24.2788109),
                            LatLng(53.574621, 24.2673954),
                            LatLng(53.5645626, 24.2452236),
                            LatLng(53.5586105, 24.233416),
                            LatLng(53.5614393, 24.1987775),
                            LatLng(53.5639876, 24.189982),
                            LatLng(53.5768031, 24.1869358),
                            LatLng(53.5857289, 24.1879764),
                            LatLng(53.5988326, 24.228768),
                            LatLng(53.5987627, 24.2287032),
                        )
                    ),
                    Polyline(
                        name = "ozery",
                        points = persistentListOf(
                            LatLng(53.7325917, 24.1361288),
                            LatLng(53.7083211, 24.1629872),
                            LatLng(53.7031261, 24.1780764),
                            LatLng(53.7110637, 24.1976543),
                            LatLng(53.7219041, 24.2006492),
                            LatLng(53.7271092, 24.198684),
                            LatLng(53.7375152, 24.1789478),
                            LatLng(53.7325917, 24.1361288)
                        )
                    ),
                    Polyline(
                        name = "porechye",
                        points = persistentListOf(
                            LatLng(53.8724632, 24.1232477),
                            LatLng(53.8676351, 24.1401893),
                            LatLng(53.8736557, 24.1598826),
                            LatLng(53.8885988, 24.150621),
                            LatLng(53.8964367, 24.1341456),
                            LatLng(53.8826815, 24.1146668),
                            LatLng(53.8724632, 24.1232477)
                        )
                    ),
                    Polyline(
                        name = "volkovysk",
                        points = persistentListOf(
                            LatLng(53.1762187, 24.4098844),
                            LatLng(53.1501803, 24.3906566),
                            LatLng(53.1213524, 24.391343),
                            LatLng(53.1192238, 24.4249232),
                            LatLng(53.1277888, 24.465883),
                            LatLng(53.1369013, 24.4754937),
                            LatLng(53.1496657, 24.4985479),
                            LatLng(53.1689081, 24.4968317),
                            LatLng(53.1799215, 24.4569081),
                            LatLng(53.1762187, 24.4098844)
                        )
                    ),
                )
            )
        )
    }

    fun onPointsChanged(name: String, points: PersistentList<LatLng>) {
        _polylines.tryEmit(
            when (val t = _polylines.value) {
                is Loaded -> {
                    Loaded(
                        t.polyline.map { polyline ->
                            if (polyline.name == name) {
                                polyline.copy(points = points)
                            } else {
                                polyline
                            }
                        }.toPersistentList()
                    )
                }
                None -> t
            }
        )
    }
}