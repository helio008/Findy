package it.elisino.findy


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home_page.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

private lateinit var auth: FirebaseAuth

class homePage : Fragment() {


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
            //LO PORTO DIRETTAMENTE AL PROFILO
            Toast.makeText(activity, "UTENTE LOGGATO.",
                Toast.LENGTH_SHORT).show()
            /*
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
            */
        }
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonWorker.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homePage_to_sceltaWorker) }
        buttonRicerca.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_homePage_to_ricerca) }
    }

    /*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        if(user!=null){
            //LO PORTO DIRETTAMENTE AL PROFILO
            Toast.makeText(activity, "UTENTE LOGGATO.",
                Toast.LENGTH_SHORT).show()
            /*
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
            */
        }else{
            //DEVE SCEGLIERE COSA FARE
            Toast.makeText(activity, "UTENTE NON LOGGATO.",
                Toast.LENGTH_SHORT).show()
            val myView:View = inflater.inflate(R.layout.fragment_home_page, container, false)
            val button:Button= myView.findViewById(R.id.buttonWorker)
            button.setOnClickListener(View.OnClickListener {
                Toast.makeText(activity, "CLICK.",
                    Toast.LENGTH_SHORT).show()
                myView.findNavController().navigate(R.id.action_homePage_to_sceltaWorker)
            })

        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }
    */


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        //updateUI(currentUser)
    }



}
