package com.damar.submitinter2.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.damar.submitinter2.R
import com.damar.submitinter2.databinding.ActivityDetailStoryBinding

private var binding: ActivityDetailStoryBinding? = null

class DetailStoryActivity : AppCompatActivity() {

    companion object {
        const val NAMA = "NAMA"
        const val DESKRIPSI = "DESKRIPSI"
        const val FOTO = "FOTO"
        const val CREATED_AT = "CREATED_AT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.title = getString(R.string.story_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra(NAMA)
        val desc = intent.getStringExtra(DESKRIPSI)
        val photo = intent.getStringExtra(FOTO)
        val createdAt = intent.getStringExtra(CREATED_AT)

        Glide.with(this)
            .load(photo)
            .into(binding!!.ivDetailPhoto)

        binding!!.tvDetailName.text = name
        binding!!.tvDetailDescription.text = desc
        binding!!.tvDetailCreatedAt.text = getString(R.string.created_at_prefix) + createdAt
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}