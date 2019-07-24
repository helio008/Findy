package it.elisino.findy


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_home_page.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_scelta_worker.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class sceltaWorker : Fragment() {

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scelta_worker, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // UTENTE GIà LOGGATO
            //APRO LA SCHERMATA PROFILO PERSONALE
            Navigation.findNavController(view).navigate(R.id.action_sceltaWorker_to_profilo)
        } else {
            // UTENTE NON LOGGATO
            buttonAccedi.setOnClickListener{
                Navigation.findNavController(view).navigate(R.id.action_sceltaWorker_to_login)
                //val intent = Intent(activity, LoginActivity::class.java)
                //startActivity(intent)
            }
            buttonRegistrati.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_sceltaWorker_to_registrazione) }
        }
    }

    //Navigation.findNavController(view).navigate(R.id.action_homePage_to_sceltaWorker)

}
