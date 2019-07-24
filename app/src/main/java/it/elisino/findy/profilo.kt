package it.elisino.findy


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.fotoProfilo
import kotlinx.android.synthetic.main.activity_profile.nomeUtenteProfilo
import kotlinx.android.synthetic.main.activity_profile.pulsanteModifica
import kotlinx.android.synthetic.main.activity_profile.tagAvellino
import kotlinx.android.synthetic.main.activity_profile.tagBarman
import kotlinx.android.synthetic.main.activity_profile.tagBenevento
import kotlinx.android.synthetic.main.activity_profile.tagCameriere
import kotlinx.android.synthetic.main.activity_profile.tagCaserta
import kotlinx.android.synthetic.main.activity_profile.tagCuoco
import kotlinx.android.synthetic.main.activity_profile.tagMaitre
import kotlinx.android.synthetic.main.activity_profile.tagNapoli
import kotlinx.android.synthetic.main.activity_profile.tagSalerno
import kotlinx.android.synthetic.main.activity_profile.testoDescrizione
import kotlinx.android.synthetic.main.fragment_profilo.*
import kotlinx.android.synthetic.main.fragment_scelta_worker.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var auth: FirebaseAuth

/**
 * A simple [Fragment] subclass.
 *
 */
class profilo : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        variabileBack=true
        return inflater.inflate(R.layout.fragment_profilo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        //val testoNome: TextView = findViewById(R.id.nomeUtenteProfilo)
        //val testoDescrizione: TextView =findViewById(R.id.testoDescrizione)
        //val imgProfilo: ImageView =findViewById(R.id.fotoProfilo)

        //DEVO LEGGERE DAL DATABASE
        val docRef = db.collection("utenti").document(auth.currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("MESSAGGIO:", "DocumentSnapshot data: ${document.data}")
                    nomeUtenteProfilo.setText(document.getString("nome"))
                    testoDescrizione.setText(document.getString("descrizione"))
                    val vettoreProvince = document.get("province") as List<Boolean>
                    val vettoreProfessioni = document.get("professioni") as List<Boolean>

                    //VISUALIZZO LE PROVINCE DISPONIBILI
                    if(vettoreProvince[0]==true){
                        tagAvellino.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProvince[1]==true){
                        tagBenevento.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProvince[2]==true){
                        tagCaserta.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProvince[3]==true){
                        tagNapoli.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProvince[4]==true){
                        tagSalerno.setVisibility(View.VISIBLE)
                    }

                    //VISUALIZZO LE PROFESSIONI DISPONIBILI
                    if(vettoreProfessioni[0]==true){
                        tagCameriere.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProfessioni[1]==true){
                        tagBarman.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProfessioni[2]==true){
                        tagMaitre.setVisibility(View.VISIBLE)
                    }
                    if(vettoreProfessioni[3]==true){
                        tagCuoco.setVisibility(View.VISIBLE)
                    }

                    //IMPOSTO LA FOTO
                    Picasso.get().load(document.getString("foto")).into(fotoProfilo);
                } else {
                    Log.d("MESSAGGIO", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("MESSAGGIO", "get failed with ", exception)
            }



        val pulsanteModifica: FloatingActionButton =pulsanteModifica
        pulsanteModifica.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_profilo_to_modificaDati)
        })

        val pulsanteEsci: FloatingActionButton =pulsanteEsci
        pulsanteEsci.setOnClickListener(View.OnClickListener {
            auth.signOut()
            Navigation.findNavController(view).navigate(R.id.action_profilo_to_login)
        })


        //PER AGGIUNGERE
        /*

        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("utenti")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("OH", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("OH", "Error adding document", e)
            }
        */
    }


}
