package com.sun.qakhadelivery.screens.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sun.qakhadelivery.R
import com.sun.qakhadelivery.data.model.Refresh
import com.sun.qakhadelivery.data.model.User
import com.sun.qakhadelivery.data.repository.CloudRepositoryImpl
import com.sun.qakhadelivery.data.repository.TokenRepositoryImpl
import com.sun.qakhadelivery.data.repository.UserRepositoryImpl
import com.sun.qakhadelivery.data.source.local.sharedprefs.SharedPrefsImpl
import com.sun.qakhadelivery.data.source.remote.schema.request.UpdateImage
import com.sun.qakhadelivery.extensions.*
import com.sun.qakhadelivery.screens.profile.update.email.UpdateEmailFragment
import com.sun.qakhadelivery.screens.profile.update.name.UpdateNameFragment
import com.sun.qakhadelivery.screens.profile.update.phone.UpdatePhoneFragment
import com.sun.qakhadelivery.screens.signedin.SignedInFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ProfileFragment : Fragment(), ProfileContract.View, View.OnClickListener {

    private val presenter by lazy {
        ProfilePresenter(
            TokenRepositoryImpl.getInstance(
                SharedPrefsImpl.getInstance(requireContext())
            ),
            UserRepositoryImpl.getInstance(),
            CloudRepositoryImpl.getInstance(requireContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
    }

    override fun onStart() {
        super.onStart()
        presenter.run {
            setView(this@ProfileFragment)
            getUser()
            disableInteraction()
        }
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onSuccessUser(user: User) {
        coinNavigateView.setDescribe(user.coin.toString())
        avatarCircleImageView.loadUrl(user.image.imageUrl)
        nameNavigateView.setDescribe(user.name)
        phoneNumberNavigateView.setDescribe(user.phoneNumber)
        emailNavigateView.setDescribe(user.email)
        arguments = Bundle().apply {
            putParcelable(BUNDLE_USER, user)
        }
        enableInteraction()
    }

    override fun onSuccessUploadImage(url: String) {
        presenter.updateImage(UpdateImage(url))
    }

    override fun onSuccessUpdateImage(user: User) {
        avatarCircleImageView.loadAvatarUrl(user.image.imageUrl)
        EventBus.getDefault().post(Refresh(this::class.java, SignedInFragment::class.java).apply {
            data = Bundle().apply { putString(DATA_AVATAR, user.image.imageUrl) }
        })
        enableInteraction()
        makeText(getString(R.string.update_image_success))
    }

    override fun onErrorUser(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorUploadImage(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onErrorUpdateImage(exception: String) {
        enableInteraction()
        makeText(exception)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val filePath = data?.data?.let { getRealPathFromUri(it) }
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                filePath?.let {
                    presenter.uploadImage(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessTheGallery()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.permission_denied),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(refresh: Refresh) {
        refresh.message(this::class.java) {
            presenter.getUser()
            disableInteraction()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.avatarLayout -> {
                requestPermission()
            }
            R.id.nameNavigateView -> {
                addFragmentSlideAnim(
                    UpdateNameFragment.newInstance(arguments?.getParcelable(BUNDLE_USER)),
                    R.id.containerView
                )
            }
            R.id.phoneNumberNavigateView -> {
                addFragmentSlideAnim(
                    UpdatePhoneFragment.newInstance(arguments?.getParcelable(BUNDLE_USER)),
                    R.id.containerView
                )
            }
            R.id.emailNavigateView -> {
                addFragmentSlideAnim(
                    UpdateEmailFragment.newInstance(arguments?.getParcelable(BUNDLE_USER)),
                    R.id.containerView
                )
            }
        }
    }

    private fun handleEvent() {
        avatarLayout.setOnSafeClickListener(listener = this)
        nameNavigateView.setOnSafeClickListener(listener = this)
        phoneNumberNavigateView.setOnSafeClickListener(listener = this)
        emailNavigateView.setOnSafeClickListener(listener = this)
        imageViewBack.setOnClickListener {
            back()
        }
    }

    private fun disableInteraction() {
        loadingProgress.show()
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun enableInteraction() {
        loadingProgress.gone()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            accessTheGallery()
        } else {
            ActivityCompat.requestPermissions(
                activity as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        }
    }

    private fun accessTheGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_PICK
            type = "image/*"
        }
        startActivityForResult(intent, PICK_IMAGE)
    }

    private fun getRealPathFromUri(imageUri: Uri): String? {
        val cursor: Cursor? =
            requireContext().contentResolver?.query(imageUri, null, null, null, null)
        return if (cursor == null) {
            imageUri.path
        } else {
            cursor.moveToFirst()
            val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            cursor.getString(idx)
        }
    }

    companion object {
        const val DATA_AVATAR = "AVATAR"
        private const val BUNDLE_USER = "BUNDLE_USER"
        private const val PERMISSION_CODE = 1
        private const val PICK_IMAGE = 1

        fun newInstance() = ProfileFragment()
    }
}
