package org.fcitx.fcitx5.android.plugin.clipboard_sync.network

import java.security.MessageDigest

object HashUtils {
    fun sha256(text: String): String = sha256(text.toByteArray(Charsets.UTF_8))

    fun sha256(bytes: ByteArray): String =
        MessageDigest.getInstance("SHA-256")
            .digest(bytes)
            .toHex()

    fun calculateFileHash(fileName: String, bytes: ByteArray): String {
        val nameBytes = fileName.toByteArray(Charsets.UTF_8)
        val combined = ByteArray(nameBytes.size + 1 + bytes.size)
        System.arraycopy(nameBytes, 0, combined, 0, nameBytes.size)
        combined[nameBytes.size] = 0
        System.arraycopy(bytes, 0, combined, nameBytes.size + 1, bytes.size)
        return sha256(combined)
    }

    private fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }
}

