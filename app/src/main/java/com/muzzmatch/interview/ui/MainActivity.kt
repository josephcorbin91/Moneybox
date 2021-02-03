package com.muzzmatch.interview.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import com.muzzmatch.interview.R
import com.muzzmatch.interview.data.entities.User
import com.muzzmatch.interview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding.user = User()
        setContentView(binding.root)
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.my_toolbar)
        toolbar.apply {
            setSupportActionBar(this)
            val navigationIconBitmap = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24, null).toBitmap().addGradient(context)
            navigationIcon = BitmapDrawable(resources, navigationIconBitmap)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.chat_menu, menu)
        return true
    }
}


fun Bitmap.addGradient(context: Context): Bitmap? {
    val width = width
    val height = height
    val updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(updatedBitmap)
    canvas.drawBitmap(this, 0F, 0F, null)
    val paint = Paint()
    val shader = LinearGradient(0F, 0F, 0F, height.toFloat(), context.resources.getColor(R.color.start_gradient, null), context.resources.getColor(R.color.end_gradient, null), Shader.TileMode.CLAMP)
    paint.shader = shader
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
    return updatedBitmap
}