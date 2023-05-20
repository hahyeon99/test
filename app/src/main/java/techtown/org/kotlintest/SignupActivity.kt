package techtown.org.kotlintest

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import techtown.org.kotlintest.databinding.ActivitySignupBinding

@Suppress("DEPRECATION")
class SignupActivity : AppCompatActivity() {
    private lateinit var storage: FirebaseStorage
    lateinit var binding: ActivitySignupBinding

    private lateinit var mAuth: FirebaseAuth

    private lateinit var mDbRef: DatabaseReference

    val CAMERA = arrayOf(Manifest.permission.CAMERA)
    val CAMERA_CODE = 98

    companion object {
        lateinit var imageUri : Uri
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.signupBtn.isEnabled = false

        // 인증 초기화
        mAuth = Firebase.auth

        // 데베 초기화
        mDbRef = Firebase.database.reference

        binding.galleryBtn.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activityResult.launch(intent)
        }

        /* camera */

        fun checkPermission(permissions: Array<out String>): Boolean
        {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissions, CAMERA_CODE)
                    return false
                }
            }
            return true
        }


        binding.newPicBtn.setOnClickListener() {
            if (checkPermission(CAMERA)) {
                val itt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(itt, CAMERA_CODE)
            }
        }
        // sign up
        binding.signupBtn.setOnClickListener {
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()
            val passwordCheck = binding.passwordCheckEdit.text.toString().trim()
            val id = binding.idEdit.text.toString().trim()
            val nickname = binding.nicknameEdit.text.toString().trim()
            /*if(password == passwordCheck) {
                binding.signupBtn.isEnabled = true
            }
            else
            {
                val repassword = binding.passwordEdit.text.toString().trim()
                val repasswordCheck = binding.passwordCheckEdit.text.toString().trim()
                if(repassword == repasswordCheck)
                    binding.signupBtn.isEnabled = true
            }*/
            signUp(email, password, passwordCheck, id, nickname)
        }

        setSupportActionBar(binding.topBar)
        //툴바에 타이틀 없애기
        supportActionBar?.setDisplayShowTitleEnabled(false)
        /*toggle = ActionBarDrawerToggle(this, binding.btnSave, R.string.drawer_opened,
            R.string.drawer_closed
        )*/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private val activityResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== RESULT_OK && it.data != null) {
            imageUri = it.data!!.data!!
            Glide.with(this)
                .load(imageUri)
                .into(binding.profileView)

        }
    }

    //회원가입
    private fun signUp(email: String, password: String, passwordCheck:String, id: String, nickname: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    storage = Firebase.storage
                    val profileName = "${id}.png"
                    storage.reference.child("profile/photo").child(profileName)
                        .putFile(imageUri)
                    // 성공 시 실행
                    Toast.makeText(this, "SignUp SUCCESS", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    startActivity(intent)
                    addUserToDatabase(email, mAuth.currentUser?.uid!!, id, nickname, password, imageUri)
                } else {
                    // 실패 시 실행
                    Toast.makeText(this, "SignUp FAIL", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(email: String, uId: String, id: String, nickname: String, password:String, imageUri:Uri){
        mDbRef.child("user").child(uId).setValue(User(email, uId, id, nickname, password, imageUri.toString()))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = intent
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            CAMERA_CODE -> {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Please accept permission for camera", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_CODE -> {
                    imageUri = data?.extras?.get("data") as Uri
                    if (imageUri != null) {
                        val img = data.extras?.get("data") as Bitmap
                        binding.profileView.setImageBitmap(img)
                    }
                }
            }
        }
    }
}
