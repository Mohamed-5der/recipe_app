package com.example.recipeapp.presention.ui
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.recipeapp.R

class ContactUsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_us, container, false)

        val khederIn: ImageView = view.findViewById(R.id.kheder_in)
        val morganIn: ImageView = view.findViewById(R.id.morgan_in)
        val emadIn: ImageView = view.findViewById(R.id.emad_in)
        val shazaIn: ImageView = view.findViewById(R.id.shaza_in)
        val contactNumberIcon: ImageView = view.findViewById(R.id.contact_number)
        val emailAddressIcon: ImageView = view.findViewById(R.id.email_address)

        khederIn.setOnClickListener { openLinkedInProfile("https://www.linkedin.com/in/mohamed-khedr-186861244/") }
        morganIn.setOnClickListener { openLinkedInProfile("https://www.linkedin.com/in/ahmed-morgan-808241309") }
        emadIn.setOnClickListener { openLinkedInProfile("https://www.linkedin.com/in/ahmed-emad-%F0%9F%87%B5%F0%9F%87%B8-010a52262/") }
        shazaIn.setOnClickListener { openLinkedInProfile("https://www.linkedin.com/in/shaza-ali-38122a323") }

        contactNumberIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$01203898109")
            }
            startActivity(intent)
            Toast.makeText(context, "Contact Number: +201203898109", Toast.LENGTH_LONG).show()
        }

        emailAddressIcon.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:elmorg6666@gmail.com") // Only email apps should handle this
            }
            startActivity(intent)
            Toast.makeText(context, "Email Address: elmorg6666@gmail.com", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun openLinkedInProfile(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}