package com.damar.submitinter2.addstory

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.damar.submitinter2.MainActivity
import com.damar.submitinter2.R
import com.damar.submitinter2.databinding.FragmentAddStoryBinding
import com.damar.submitinter2.etc.createCustomTempFile
import com.damar.submitinter2.etc.reduceFileImage
import com.damar.submitinter2.home.HomeFragment
import com.damar.submitinter2.login.LoginActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class AddStoryFragment : Fragment() {

    private lateinit var binding: FragmentAddStoryBinding
    private var getImgFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.add_story)
        setHasOptionsMenu(true)

        binding.buttonCamera.setOnClickListener {
            startTakePhoto()
        }

        binding.buttonGallery.setOnClickListener {
            startGallery()
        }

        binding.buttonAdd.setOnClickListener {
            val desc = binding.edAddDescription.text.toString()

            when {
                getImgFile != null -> {
                    if (desc.isNotEmpty()) {
                        val description = desc.toRequestBody("text/plain".toMediaTypeOrNull())
                        val reducedImage = reduceFileImage(getImgFile!!)
                        val requestImageFile = reducedImage.asRequestBody("image/jpeg".toMediaTypeOrNull())
                        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                            "photo",
                            getImgFile!!.name,
                            requestImageFile
                        )

                        val addViewModel = ViewModelProvider(this).get(AddViewModel::class.java)

                        addViewModel.postStory(HomeFragment.USER_TOKEN!!, description, imageMultipart)
                        showLoading(true)

                        addViewModel.isInfoError.observe(viewLifecycleOwner) { isError ->
                            showLoading(false)
                            if (isError) {
                                Toast.makeText(
                                    requireContext(),
                                    addViewModel.errorMessage.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.upload_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                navigateToListStoryFragment()
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.description_warning),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                else -> {
                    Toast.makeText(requireContext(), getString(R.string.photo_warning), Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun navigateToListStoryFragment() {
        val intentList = Intent(requireContext(), MainActivity::class.java)
        startActivity(intentList)
    }
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)
        createCustomTempFile(requireContext().applicationContext).also { file ->
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().getString(R.string.authority),
                file
            )
            getImgFile = file
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.choose_pic))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val bitmap = BitmapFactory.decodeFile(getImgFile?.path)
                val exif = ExifInterface(getImgFile?.path!!)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
                )
                val rotatedBitmap = rotateBitmap(bitmap, orientation)
                binding.ivCreatePhoto.setImageBitmap(rotatedBitmap)
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            Activity.RESULT_OK -> {
                val selectedImg = result.data?.data as Uri
                selectedImg.let { uri ->
                    getImgFile = uriToFile(uri, requireContext())
                    binding.ivCreatePhoto.setImageURI(uri)
                }
            }
        }
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context.applicationContext)

        val inputStream = contentResolver.openInputStream(selectedImg)
        val outputStream: OutputStream = FileOutputStream(myFile)

        inputStream?.copyTo(outputStream, bufferSize = DEFAULT_BUFFER_SIZE)

        outputStream.close()
        inputStream?.close()

        return myFile
    }

    private fun rotateBitmap(bitmap: Bitmap, orientation: Int): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> return bitmap
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(-90f)
            else -> return bitmap
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_language_setting -> {
                val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(languageIntent)
                return true
            }
            R.id.btn_logout -> {
                val sharedPref =
                    requireContext().getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(getString(R.string.user_token), "")
                editor.commit()

                val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(logoutIntent)
                requireActivity().finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarCreate.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}