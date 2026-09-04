package com.operator.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.operator.app.permissions.MicrophonePermission
import com.operator.app.ui.OperatorActions
import com.operator.app.ui.OperatorScreen
import com.operator.app.ui.OperatorViewModel
import com.operator.app.ui.theme.OperatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as OperatorApplication).container
        setContent {
            OperatorTheme {
                val viewModel: OperatorViewModel = viewModel(factory = OperatorViewModel.factory(container))
                OperatorRoot(viewModel)
            }
        }
    }
}

@Composable
private fun OperatorRoot(viewModel: OperatorViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        viewModel.refreshPermission()
    }

    // Re-check permission and routes whenever the screen comes back (e.g. from system settings).
    LifecycleResumeEffect(Unit) {
        viewModel.refreshPermission()
        onPauseOrDispose { viewModel.stopAudio() }
    }

    val actions = remember(viewModel) {
        OperatorActions(
            onActivate = viewModel::activate,
            onStandby = viewModel::standby,
            onCommentNow = viewModel::commentNow,
            onToggleMute = viewModel::toggleMute,
            onSelectMode = viewModel::setMode,
            onSelectWit = viewModel::setWit,
            onRequestMicrophone = { permissionLauncher.launch(MicrophonePermission.PERMISSION) },
            onRecordTest = viewModel::recordTest,
            onPlayTest = viewModel::playTest,
            onStopAudio = viewModel::stopAudio,
            onDiscardClip = viewModel::discardClip,
        )
    }

    OperatorScreen(state = state, actions = actions)
}
