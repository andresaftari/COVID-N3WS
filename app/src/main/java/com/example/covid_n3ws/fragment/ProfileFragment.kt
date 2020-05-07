package com.example.covid_n3ws.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.covid_n3ws.EditActivity
import com.example.covid_n3ws.MainActivity
import com.example.covid_n3ws.R
import com.example.covid_n3ws.account.LoginActivity
import com.example.covid_n3ws.account.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var fAuthorization: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var userID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true

        fAuthorization = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()

        userID = fAuthorization.currentUser?.uid.toString()
        val docRef = fStore.collection("Users").document(userID)

        docRef.addSnapshotListener { documentSnapshot, _ ->
            if (fAuthorization.currentUser == null) {
                tv_profileName?.text = "Member"
                tv_profileRole?.text = "Member role"

                username?.visibility = View.GONE
                tv_profileUsername?.visibility = View.GONE
                email?.visibility = View.GONE
                tv_profileEmail?.visibility = View.GONE
                btn_editProfile?.visibility = View.GONE

                btn_logout?.visibility = View.GONE
                member_subs?.visibility = View.VISIBLE

            } else if (fAuthorization.currentUser != null) {
                tv_profileName.text = documentSnapshot?.getString("fullname")
                val role = documentSnapshot?.getString("role")
                tv_profileUsername.text = documentSnapshot?.getString("username")
                tv_profileEmail.text = documentSnapshot?.getString("email")
                tv_profileRole.text = role

                profile_data?.visibility = View.VISIBLE

                btn_logout?.visibility = View.VISIBLE

                joinUs?.visibility = View.GONE
                btn_toRegister?.visibility = View.GONE
                btn_toLogin?.visibility = View.GONE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_toRegister.setOnClickListener {
            startActivity(Intent(activity, RegisterActivity::class.java))
        }

        btn_toLogin.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        btn_editProfile.setOnClickListener {
            startActivity(Intent(activity, EditActivity::class.java))
        }

        btn_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, MainActivity::class.java))
            Toast.makeText(activity, "Logout Successful!", Toast.LENGTH_SHORT).show()
        }
    }
}
