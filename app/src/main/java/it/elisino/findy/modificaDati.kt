package it.elisino.findy


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_modifica_dati.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val db = FirebaseFirestore.getInstance()

/**
 * A simple [Fragment] subclass.
 *
 */
class modificaDati : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modifica_dati, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val user = FirebaseAuth.getInstance().currentUser

        //PER RIPORTARE I DATI GIà INSERITI
        val docRef = db.collection("utenti").document(user!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    nomeEditText.setText(document.getString("nome"))
                    descrizioneEditText.setText(document.getString("descrizione"))
                    fotoEditText.setText(document.getString("foto"))
                    telefonoEditText.setText(document.getString("telefono"))
                    val vettoreProvince = document.get("province") as List<Boolean>
                    val vettoreProfessioni = document.get("professioni") as List<Boolean>

                    //VISUALIZZO LE PROVINCE DISPONIBILI
                    if(vettoreProvince[0]==true){
                        checkBoxAvellino.isChecked=true
                    }
                    if(vettoreProvince[1]==true){
                        checkBoxBenevento.isChecked=true
                    }
                    if(vettoreProvince[2]==true){
                        checkBoxCaserta.isChecked=true
                    }
                    if(vettoreProvince[3]==true){
                        checkBoxNapoli.isChecked=true
                    }
                    if(vettoreProvince[4]==true){
                        checkBoxSalerno.isChecked=true
                    }

                    //VISUALIZZO LE PROFESSIONI DISPONIBILI
                    if(vettoreProfessioni[0]==true){
                        checkBoxCameriere.isChecked=true
                    }
                    if(vettoreProfessioni[1]==true){
                        checkBoxBarman.isChecked=true
                    }
                    if(vettoreProfessioni[2]==true){
                        checkBoxMaitre.isChecked=true
                    }
                    if(vettoreProfessioni[3]==true){
                        checkBoxCuoco.isChecked=true
                    }

                } else {
                    Log.d("MESSAGGIO", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("MESSAGGIO", "get failed with ", exception)
            }

        //PER SALVARE LE MODIFICHE
        avantiButton.setOnClickListener (View.OnClickListener() {
            if(nomeEditText.text.isNotEmpty() && nomeEditText.text.isNotBlank()){
                //NOME INSERITO
                if(descrizioneEditText.text.isNotBlank() && descrizioneEditText.text.isNotEmpty()){
                    //DESCRIZIONE INSERITA

                    //DEVO INSERIRE TUTTO NEL DATABASE
                    // Create a new user with a first and last name
                    val user2 = hashMapOf(
                        "nome" to nomeEditText.text.toString(),
                        "descrizione" to descrizioneEditText.text.toString(),
                        "foto" to fotoEditText.text.toString(),
                        "telefono" to telefonoEditText.text.toString(),
                        "province" to arrayListOf(checkBoxAvellino.isChecked, checkBoxBenevento.isChecked, checkBoxCaserta.isChecked, checkBoxNapoli.isChecked, checkBoxNapoli.isChecked),
                        "professioni" to arrayListOf(checkBoxCameriere.isChecked, checkBoxBarman.isChecked, checkBoxMaitre.isChecked, checkBoxCuoco.isChecked)
                    )

                    // Add a new document with a generated ID
                    db.collection("utenti").document(user!!.uid)
                        .set(user2)
                        .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }

                    //AGGIUNGO ALLA LISTA CAMERIERI
                    if(checkBoxCameriere.isChecked){
                        val temp = hashMapOf(
                            "uid" to user.uid
                        )
                        if(checkBoxAvellino.isChecked){
                            db.collection("professioni").document("camerieri").collection("avellino").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("camerieri").collection("avellino").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxBenevento.isChecked){
                            db.collection("professioni").document("camerieri").collection("benevento").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("camerieri").collection("benevento").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxCaserta.isChecked){
                            db.collection("professioni").document("camerieri").collection("caserta").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("camerieri").collection("caserta").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxNapoli.isChecked){
                            db.collection("professioni").document("camerieri").collection("napoli").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("camerieri").collection("napoli").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxSalerno.isChecked){
                            db.collection("professioni").document("camerieri").collection("salerno").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("camerieri").collection("salerno").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }
                    }else{
                        //CAMERIERE NON CHECCATO
                        //DEVO CANCELLARE DAI CAMERIERI
                        db.collection("professioni").document("camerieri").collection("avellino").document(user.uid).delete()
                        db.collection("professioni").document("camerieri").collection("benevento").document(user.uid).delete()
                        db.collection("professioni").document("camerieri").collection("caserta").document(user.uid).delete()
                        db.collection("professioni").document("camerieri").collection("napoli").document(user.uid).delete()
                        db.collection("professioni").document("camerieri").collection("salerno").document(user.uid).delete()
                    }

                    //AGGIUNGO ALLA LISTA BARMAN
                    if(checkBoxBarman.isChecked){
                        val temp = hashMapOf(
                            "uid" to user.uid
                        )
                        if(checkBoxAvellino.isChecked){
                            db.collection("professioni").document("barman").collection("avellino").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("barman").collection("avellino").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxBenevento.isChecked){
                            db.collection("professioni").document("barman").collection("benevento").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("barman").collection("benevento").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxCaserta.isChecked){
                            db.collection("professioni").document("barman").collection("caserta").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            db.collection("professioni").document("barman").collection("caserta").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxNapoli.isChecked){
                            db.collection("professioni").document("barman").collection("napoli").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("barman").collection("napoli").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxSalerno.isChecked){
                            db.collection("professioni").document("barman").collection("salerno").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("barman").collection("salerno").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }
                    }else{
                        //BARMAN NON CHECCATO
                        //DEVO CANCELLARE DAI BARMAN
                        db.collection("professioni").document("barman").collection("avellino").document(user.uid).delete()
                        db.collection("professioni").document("barman").collection("benevento").document(user.uid).delete()
                        db.collection("professioni").document("barman").collection("caserta").document(user.uid).delete()
                        db.collection("professioni").document("barman").collection("napoli").document(user.uid).delete()
                        db.collection("professioni").document("barman").collection("salerno").document(user.uid).delete()
                    }

                    //AGGIUNGO ALLA LISTA MAITRE
                    if(checkBoxMaitre.isChecked){
                        val temp = hashMapOf(
                            "uid" to user.uid
                        )
                        if(checkBoxAvellino.isChecked){
                            db.collection("professioni").document("maitre").collection("avellino").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("maitre").collection("avellino").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxBenevento.isChecked){
                            db.collection("professioni").document("maitre").collection("benevento").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("maitre").collection("benevento").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxCaserta.isChecked){
                            db.collection("professioni").document("maitre").collection("caserta").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("maitre").collection("caserta").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxNapoli.isChecked){
                            db.collection("professioni").document("maitre").collection("napoli").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("maitre").collection("napoli").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxSalerno.isChecked){
                            db.collection("professioni").document("maitre").collection("salerno").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("maitre").collection("salerno").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }
                    }else{
                        //MAITRE NON CHECCATO
                        //DEVO CANCELLARE DAI MAITRE
                        db.collection("professioni").document("maitre").collection("avellino").document(user.uid).delete()
                        db.collection("professioni").document("maitre").collection("benevento").document(user.uid).delete()
                        db.collection("professioni").document("maitre").collection("caserta").document(user.uid).delete()
                        db.collection("professioni").document("maitre").collection("napoli").document(user.uid).delete()
                        db.collection("professioni").document("maitre").collection("salerno").document(user.uid).delete()
                    }

                    //AGGIUNGO ALLA LISTA cuochi
                    if(checkBoxCuoco.isChecked){
                        val temp = hashMapOf(
                            "uid" to user.uid
                        )
                        if(checkBoxAvellino.isChecked){
                            db.collection("professioni").document("cuochi").collection("avellino").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("cuochi").collection("avellino").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxBenevento.isChecked){
                            db.collection("professioni").document("cuochi").collection("benevento").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("cuochi").collection("benevento").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxCaserta.isChecked){
                            db.collection("professioni").document("cuochi").collection("caserta").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("cuochi").collection("caserta").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxNapoli.isChecked){
                            db.collection("professioni").document("cuochi").collection("napoli").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("cuochi").collection("napoli").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                        if(checkBoxSalerno.isChecked){
                            db.collection("professioni").document("cuochi").collection("salerno").document(user.uid).set(temp)
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }else{
                            //DEVO CANCELLARE
                            db.collection("professioni").document("cuochi").collection("salerno").document(user.uid).delete()
                                .addOnSuccessListener { Log.d("MESSAGE", "DocumentSnapshot successfully written!") }
                                .addOnFailureListener { e -> Log.w("MESSAGE", "Error writing document", e) }
                        }

                    }else{
                        //CUOCO NON CHECCATO
                        //DEVO CANCELLARE DAI CUOCHI
                        db.collection("professioni").document("cuochi").collection("avellino").document(user.uid).delete()
                        db.collection("professioni").document("cuochi").collection("benevento").document(user.uid).delete()
                        db.collection("professioni").document("cuochi").collection("caserta").document(user.uid).delete()
                        db.collection("professioni").document("cuochi").collection("napoli").document(user.uid).delete()
                        db.collection("professioni").document("cuochi").collection("salerno").document(user.uid).delete()
                    }

                    //VAI AL PROFILO
                    Navigation.findNavController(view).navigate(R.id.action_modificaDati_to_profilo)

                }else{
                    //DESCRIZIONE NON INSERITA
                    descrizioneEditText.setError("Campo Vuoto")
                }
            }else{
                //NOME NON INSERITO
                nomeEditText.setError("Campo Vuoto")
            }
        })


    }





}
