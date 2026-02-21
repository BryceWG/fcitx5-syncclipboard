package org.fcitx.fcitx5.android.plugin.clipboard_sync.service

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.preference.PreferenceManager

class QuickSyncTileService : TileService() {
    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val enabled = prefs.getBoolean("quick_sync", true)
        prefs.edit().putBoolean("quick_sync", !enabled).apply()
        updateTileState()
    }

    private fun updateTileState() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val enabled = prefs.getBoolean("quick_sync", true)
        qsTile?.let { tile ->
            tile.state = if (enabled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            tile.updateTile()
        }
    }
}

