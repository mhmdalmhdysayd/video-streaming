package com.example.videostreaming

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class MainActivity : AppCompatActivity() {

            var playerView: PlayerView? = null
            var player: SimpleExoPlayer? = null
            var url :String = ""
            private var playWhenReady = true
            private var currentWindow = 0
            private var playPackPosition: Long = 0
            var index: Int = 1

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_main)

                when(index){
                    1-> url = "https://www.youtube.com/watch?v=xILeX-9V-Ys"
                    2-> url = "https://www.youtube.com/watch?v=CkBUnhg6shw"
                    3-> url = "https://www.youtube.com/watch?v=vwckieEiPcA"
                    4-> url = "https://www.youtube.com/watch?v=S1b_Gqp-W_U"
                }

                index = intent.getIntExtra("video", 0)
                playerView = findViewById(R.id.video_view)

            }


            fun initVideo() {
                player = ExoPlayerFactory.newSimpleInstance(this)
                playerView!!.player = player
                val uri = Uri.parse(url)
                val dataSourceFactory: DataSource.Factory =
                        DefaultDataSourceFactory(this, "exoplayer-codelab")
                val mediaSource: MediaSource =
                        ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri)
                player!!.playWhenReady = playWhenReady
                player!!.seekTo(currentWindow, playPackPosition)
                player!!.prepare(mediaSource, false, false)
            }

            fun releaseVideo() {
                if (player != null) {
                    playWhenReady = player!!.playWhenReady
                    playPackPosition = player!!.currentPosition
                    currentWindow = player!!.currentWindowIndex
                    player!!.release()
                    player = null
                }
            }

            override fun onStart() {
                super.onStart()
                initVideo()
            }

            override fun onResume() {
                super.onResume()
                if (player != null) {
                    initVideo()
                }
            }

            override fun onPause() {
                super.onPause()
                releaseVideo()
            }


            override fun onStop() {
                super.onStop()
                releaseVideo()
            }

        }