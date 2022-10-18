package com.example.startflutter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.startflutter.databinding.ActivityMainBinding
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prewarmFlutter()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            startActivity(
//                Without a pre-cached Flutter engine everything works properly, but really slow
//                FlutterActivity.createDefaultIntent(this)

//                When adding a pre-cached engine,
//                status bar color becomes darker on a second Flutter activity launch
                FlutterActivity.withCachedEngine("my_engine_id").build(this)
            )
        }
    }

    private fun prewarmFlutter() {
        val flutterEngine = FlutterEngine(this)

        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache
            .getInstance()
            .put("my_engine_id", flutterEngine)
    }

}