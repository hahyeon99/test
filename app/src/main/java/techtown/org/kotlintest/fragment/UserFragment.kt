package techtown.org.kotlintest.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import techtown.org.kotlintest.LoginActivity
import techtown.org.kotlintest.R
import techtown.org.kotlintest.SignupActivity
import techtown.org.kotlintest.account.*
import techtown.org.kotlintest.databinding.FragmentUserBinding

class UserFragment: Fragment() {
    private lateinit var binding : FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserBinding.inflate(inflater, container, false)
        lateinit var mAuth: FirebaseAuth

        binding.infoBtn.setOnClickListener(({
            val intent = Intent(context, InformationActivity::class.java)
            startActivity(intent)
        }))

        binding.friendsBtn.setOnClickListener(({
            val intent = Intent(context, FriendsActivity::class.java)
            startActivity(intent)
        }))

        binding.myPostBtn.setOnClickListener(({
            val intent = Intent(context, myPostsActivity::class.java)
            startActivity(intent)
        }))

        binding.savedBtn.setOnClickListener(({
            val intent = Intent(context, SavedActivity::class.java)
            startActivity(intent)
        }))

        binding.likedBtn.setOnClickListener(({
            val intent = Intent(context, LikedActivity::class.java)
            startActivity(intent)
        }))


        binding.logOutBtn.setOnClickListener(({

            mAuth = Firebase.auth
            mAuth.signOut()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }))

        binding.deleteAccountBtn.setOnClickListener(({
            val user = Firebase.auth.currentUser!!

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User account deleted.")
                    }
                }
            val intent = Intent(context, SignupActivity::class.java)
            startActivity(intent)
        }))

        return binding.root
    }

    /*fun onItemClicked() {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.vertical_enter,
                R.anim.none,
                R.anim.none,
                R.anim.vertical_exit)
            .replace(R.id.fragmentContainerView,DetailFragment())
            .addToBackStack(null)
            .commit()
    }*/

}