package com.capstone.planetku.ui.classification

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.capstone.planetku.CameraActivity
import com.capstone.planetku.databinding.ActivityWasteClassificationBinding
import com.capstone.planetku.ui.priceprediction.PricePredictionActivity

class WasteClassificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWasteClassificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWasteClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCapture.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            cameraLauncher.launch(intent)
        }

        val galleryLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                if (uri != null) {
                    binding.ivResult.setImageURI(uri)
                    binding.ivResult.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this, "Gagal memilih gambar dari galeri", Toast.LENGTH_SHORT).show()
                }
            }

        binding.btnGallery.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.btnPredict.setOnClickListener {
            val classifiedWaste = "Plastik" // Contoh hasil klasifikasi sementara
            navigateToPricePrediction(classifiedWaste)
        }
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val photoPath = result.data?.getStringExtra("PHOTO_PATH")
                if (!photoPath.isNullOrEmpty()) {
                    val bitmap = BitmapFactory.decodeFile(photoPath)
                    binding.ivResult.setImageBitmap(bitmap)
                    binding.ivResult.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(this, "Gagal mengambil gambar", Toast.LENGTH_SHORT).show()
            }
        }

    private fun navigateToPricePrediction(classifiedWaste: String) {
        val intent = Intent(this, PricePredictionActivity::class.java)
        intent.putExtra("CLASSIFIED_WASTE", classifiedWaste)
        startActivity(intent)
    }
}

