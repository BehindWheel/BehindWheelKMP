package com.egoriku.grodnoroads.screen.root

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.eventreporting.EventReportingScreen
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.resources.R.drawable
import com.egoriku.grodnoroads.resources.R.string
import com.egoriku.grodnoroads.screen.main.MainUi
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.MigrationModel
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.ui.HeadLampDialog
import com.egoriku.grodnoroads.setting.alerts.AlertsScreen
import com.egoriku.grodnoroads.setting.appearance.screen.AppearanceScreen
import com.egoriku.grodnoroads.setting.changelog.screen.ChangelogScreen
import com.egoriku.grodnoroads.setting.faq.screen.FaqScreen
import com.egoriku.grodnoroads.setting.map.MapSettingsScreen
import com.egoriku.grodnoroads.setting.screen.ui.foundation.SocialNetwork

@OptIn(FaultyDecomposeApi::class, ExperimentalDecomposeApi::class)
@Composable
fun RootContent(rootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val dialogState by rootComponent.headlampDialogState.collectAsState(initial = HeadLampType.None)

        val migrationModel by rootComponent.migrationModel.collectAsState(MigrationModel())

        if (migrationModel.enabled) {
            MigrationScreen(migrationModel)
        } else {
            if (dialogState != HeadLampType.None) {
                HeadLampDialog(
                    headlampType = dialogState,
                    onClose = rootComponent::closeHeadlampDialog
                )
            }
            Box {
                Children(
                    stack = rootComponent.childStack,
                    animation = predictiveBackAnimation(
                        backHandler = rootComponent.backHandler,
                        animation = stackAnimation { _, _, direction ->
                            if (direction.isFront) {
                                slide() + fade()
                            } else {
                                scale(frontFactor = 1F, backFactor = 0.7F) + fade()
                            }
                        },
                        onBack = rootComponent::onBack,
                    ),

                    ) {
                    when (val child = it.instance) {
                        is Child.Main -> MainUi(component = child.component)
                        is Child.Appearance -> AppearanceScreen(
                            appearanceComponent = child.appearanceComponent,
                            onBack = rootComponent::onBack
                        )

                        is Child.Map -> MapSettingsScreen(
                            mapSettingsComponent = child.mapSettingsComponent,
                            onBack = rootComponent::onBack
                        )

                        is Child.Alerts -> AlertsScreen(
                            alertsComponent = child.alertsComponent,
                            onBack = rootComponent::onBack
                        )

                        is Child.Changelog -> ChangelogScreen(
                            changelogComponent = child.changelogComponent,
                            onBack = rootComponent::onBack,
                        )

                        is Child.NextFeatures -> TODO()
                        is Child.BetaFeatures -> TODO()
                        is Child.FAQ -> FaqScreen(
                            faqComponent = child.faqComponent,
                            onBack = rootComponent::onBack
                        )
                    }
                }

                val childSlot by rootComponent.childSlot.subscribeAsState()
                childSlot.onChild {
                    EventReportingScreen(
                        onClose = rootComponent::closeReporting,
                        onReport = rootComponent::processReporting
                    )
                }
            }
        }
    }
}

@Composable
fun MigrationScreen(migrationModel: MigrationModel) {
    val context = LocalContext.current

    val onOpenBrowser = { url: String ->
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = url.toUri()
        }
        context.startActivity(intent)
    }

    val openPlayStore = { newPackage: String ->
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "market://details?id=$newPackage".toUri()
                )
            )
        } catch (exception: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    "https://play.google.com/store/apps/details?id=$newPackage".toUri()
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(80.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_old),
                    contentDescription = null
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_new),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = """
                    Уважаемые пользователи!

                    К сожалению, из-за санкций и нововведений Google, приложение было безвозвратно удалено.
                    
                    Текущее приложение больше не поддерживается, а новое уже доступно для скачивания ниже. 
                                        
                    Присоединяйтесь к каналу в Telegram, чтобы быть в курсе всех новостей и обновлений.

                    С уважением,
                    команда За Рулем Гродно
                """.trimIndent()
            )

            val channelUrl = stringResource(string.tg_channel_link)

            SocialNetwork(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                title = "Канал в Telegram",
                onClick = { onOpenBrowser(channelUrl) }
            ) {
                Icon(
                    painter = painterResource(drawable.ic_telegram),
                    contentDescription = stringResource(string.social_telegram_channel)
                )
            }

        }
        PrimaryButton(
            onClick = {
                if (migrationModel.newPackage.isNotEmpty()) {
                    openPlayStore(migrationModel.newPackage)
                } else {
                    onOpenBrowser(migrationModel.link)
                }
            }
        ) {
            Text("Скачать новое приложение")
        }
    }
}

inline fun <T : Any> ChildSlot<*, T>?.onChild(action: (T) -> Unit) {
    val instance = this?.child?.instance
    if (instance != null) {
        action(instance)
    }
}