package com.labactivity.gakumate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.labactivity.gakumate.databinding.ActivityMainBinding
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val add = binding.btnAdd

        add.setOnClickListener{
            val dialogBuilder = DialogPlus.newDialog(this)
                .setContentHolder(ViewHolder(R.layout.show_add))
                .setExpanded(false) // This is optional, set it as per your requirement
                .create()

            // Show the dialog
            dialogBuilder.show()        }

        if(auth.currentUser == null){

            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }





    }

}