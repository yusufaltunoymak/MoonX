import android.media.MediaPlayer
object MusicPlayerManager {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    private fun stopPreviousMusic() {
        if (isPlaying) {
            mediaPlayer?.apply {
                stop()
                reset()
                release()
                this@MusicPlayerManager.isPlaying = false
            }
        }
    }

    fun startMusic(url: String) {
        stopPreviousMusic()

        mediaPlayer = MediaPlayer()
        mediaPlayer?.apply {
            try {
                setDataSource(url)
                prepareAsync()
                setOnPreparedListener {
                    it.start()
                    this@MusicPlayerManager.isPlaying = true
                }
                setOnCompletionListener {
                    it.reset()
                    it.release()
                    this@MusicPlayerManager.isPlaying = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun stopMusic() {
        if (isPlaying) {
            mediaPlayer?.apply {
                stop()
                reset()
                release()
                this@MusicPlayerManager.isPlaying = false
            }
        }
    }
}

