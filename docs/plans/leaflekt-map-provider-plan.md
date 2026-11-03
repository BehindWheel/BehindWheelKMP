# Поддержка альтернативного провайдера карт (Leaflekt/Leaflet) — план

> Статус: план одобрен, реализация отложена. Дата: 2026-11-03.
> Кандидат: [Leaflekt-Maps](https://github.com/binayshaw7777/Leaflekt-Maps) — `io.github.binayshaw7777:leaflekt-compose:1.1.1`, Apache 2.0.

## Context

Идея: добавить в приложение поддержку разных провайдеров карт. Leaflekt — Leaflet.js в WebView за декларативным Compose API, без API-ключей, ~100 KB.

**Вердикт: реально**, потому что в проекте уже есть собственная провайдер-нейтральная абстракция `:kmp:compose:maps-compose` (expect/actual `GoogleMap`, `MapUpdater`, `Marker`, `Projection`, `MarkerImage`, `MapProperties`), и единственный экран с картой — `GuidanceScreen.kt` — не использует gms-типы напрямую. Но библиотека очень молодая (v1.1.1, 16 звёзд, один автор), и в её публичном API есть дыры, важные для приложения: **нет long-click события карты** (нужно для репорта событий), **нет projection API** (нужно для ChooseLocation), **нет anchor у маркеров**. Эти дыры закрываются через JS-инъекцию в WebView (в README заявлен execute JS через контроллер) — это первый спайк с fallback-планом.

Решения пользователя: **Android-first** (iOS остаётся на Google), **деградация принимается** (Leaflet: карта north-up, без tilt/трафика), **включение через скрытый флаг в Debug Tools**.

## Ограничения Leaflet-провайдера (осознанная деградация)

- Bearing/tilt игнорируются → в Drive-режиме карта north-up, навигационная стрелка вращается сама (`rotationDegrees` у LeaflektMarker есть).
- Слой трафика недоступен → `isTrafficEnabled` игнорируется.
- `Hybrid`/`Satellite` → Esri Imagery; `Normal` → OSM/Carto; Light/Dark → Carto Light/Dark.
- Marker anchor отсутствует → либо паддинг в битмапе (bake anchor), либо центр (решить по спайку).

## Stage 0 — Спайк совместимости (первым шагом, go/no-go)

1. Добавить `leaflekt-compose:1.1.1` во временный модуль/сэмпл, проверить сборку под Kotlin 2.4.21 / CMP 1.12.2 / AGP 9.5.0.
2. Проверить публичность `LeaflektController.addMarker/updateMarker/removeMarker` и наличие `executeJs`/eval → критично для:
   - **Projection** (ChooseLocation): `map.containerPointToLatLng([x,y])` через JS.
   - **Long-click**: инъекция leaflet `contextmenu` listener → мост наружу (или свой `pointerInput`-longpress оверлей + JS-конверсия точки).
3. Fallback, если JS-моста нет: форкнуть библиотеку (Apache 2.0) и добавить 2 метода в контроллер/`map.html`, либо upstream PR. Если оба неприемлемы — стоп, репортим пользователю.

## Stage 1 — Нейтрализация типов в `:kmp:compose:maps-compose` (~34 файла + guidance)

Цель: сделать `Marker`, `Projection`, handle карты провайдер-нейтральными, не трогая домен.

- `core/Marker.kt`: `expect class Marker` → `interface MapMarker { remove(); updatePosition(LatLng); setIcon(MarkerImage); currentPosition }`. Android: `GmsMapMarker(gms.Marker)`-адаптер в `core/Marker.android.kt`; iOS — зеркально в `Marker.ios.kt`.
- `core/Projection.kt`: `expect class Projection` → `interface Projection { fun toScreenLatLng(point: Point): LatLng }`. GMS-адаптер поверх `gms.Projection`.
- `extension/MapExpect.kt` + `GoogleMap.kt`: параметр `onMapLoad: (GoogleMap) -> Unit` → `onMapLoad: (Projection) -> Unit` (в GuidanceScreen handle используется только ради `map.projection`). `expect class GoogleMap`/`MarkerImage` в `extension/` остаются как платформенные типы.
- `core/CameraPosition.kt`: `expect class` → общий data class `(target, zoom, bearing, tilt)` — платформенные типы не нужны (Google/iOS actual-конструкторы выпиливаются, `MapUpdaterAndroid/Ios` правятся под это).
- `MarkerImage` (android actual = `BitmapDescriptor`): сменить payload на `Bitmap` с lazy `BitmapDescriptor` и lazy base64-PNG (нужно для Leaflet-иконок). `MarkerCacheAndroid` кэширует `Bitmap` вместо `BitmapDescriptor`; `MarkerGenerator.android.kt` (IconGenerator) возвращает обёртку над Bitmap.
- `updater/MapUpdaterAndroid.kt` возвращает `MapMarker`-адаптеры; `MapPaddingDecorator`, `marker/compose/*` — минимальные правки сигнатур.
- Guidance (`GuidanceScreen.kt`, `screen/cache/`, `ui/google/`): только правка `onMapLoad = { projection = it }` и типов кэша. Домен-модели не трогаем.

## Stage 2 — Новый модуль `:kmp:compose:maps-leaflekt`

- KMP-модуль: commonMain — API (композабл + реализации нейтральных интерфейсов), androidMain — реализация через Leaflekt, iosMain — заглушка (флаг на iOS никогда не включается).
- `LeafletMap.kt`: композабл с тем же контрактом параметров, что у expect `GoogleMap` (чтобы вызов в GuidanceScreen был симметричен), внутри `LeaflektMap(...)` + `rememberLeaflektCameraPositionState`.
- `MapUpdaterLeaflet: MapUpdater`: маркеры — state-backed список `MarkerSpec` + декларативные `LeaflektMarker` (публичный API: add через добавление spec, remove/updatePosition/setIcon через мутацию spec); `clickedMarker` — из `onClick` маркеров; камера — `state.animate/move` (bearing игнорируется); `CameraMoveState` — эмуляция: флаг «программное движение» + `onCameraMoveStarted`; `rotateToNorth()`/zoom-анимации — по возможностям; `MapPaddingDecorator` — no-op.
- `LeafletProjection`: JS-мост из спайка (или no-op + деградация ChooseLocation, если спайк провалился).
- Long-click: оверлей `pointerInput` long-press + JS-конверсия точки (или инъекция `contextmenu`).
- Маппинг `MapProperties`/`MapColor` → `LeaflektMapProperties`/`LeaflektMapStyle` (Normal→OSM, Satellite→Esri Imagery, Light/Dark→Carto Light/Dark).
- Иконки: `Bitmap → PNG → base64` для `LeaflektMarkerIcon`; anchor — паддинг битмапа при необходимости. `isMyLocationEnabled` → своя точка через обычный addMarker.

## Stage 3 — Переключатель в Debug Tools

- `kmp/shared/persistent/.../debug/DebugPreferences.kt`: ключ `use_leaflet_map` (boolean) + `Preferences.useLeafletMap` / `updateUseLeafletMap` — по образцу `SHOW_MAP_DEBUG_OVERLAY`.
- `kmp/features/settings/debugTools/.../DataStoreEdit.kt`: `SwitchListItem("Use Leaflet map (experimental)")`; accessor/setter в `DataStoreEditComponentImpl`.
- `kmp/features/guidance/.../MapConfigStoreFactory.kt`: прокинуть флаг в `MapInternalConfig` → `MapConfig.mapProvider` (по образцу `showMapDebugOverlay`).
- `GuidanceScreen.kt`: `if (mapConfig.useLeafletMap) LeafletMap(...) else GoogleMap(...)`.

## Stage 4 — Верификация (Android emulator, debug build)

1. Оба провайдера: маркеры камер/репортов рисуются и удаляются, клик по маркеру → `MarkerInfoBottomSheet`, размер маркеров меняется с зумом.
2. Leaflet: зум-кнопки, locate-me, тёмная/светлая темы (Carto), переключение Normal/Satellite, Drive-режим — камера следует, карта north-up, стрелка вращается.
3. Спайк-зависимые: long-press → репорт события; ChooseLocation → `startReporting` с корректными координатами.
4. Регресс Google: зум сохраняется в DataStore, лонг-клик, `showMapDebugOverlay` работает.
5. iOS сборка не ломается (iosMain-адаптеры + заглушка).

## Риски

- Библиотека v1.1.1, один мейнтейнер — держим её за адаптером, Google остаётся дефолтом; откат = флаг выключен.
- CMP-версия либы может конфликтовать с 1.12.2 → проверяется в Stage 0.
- JS-мост (projection/long-click) — главный технический риск, решается спайком с fallback на форк.
