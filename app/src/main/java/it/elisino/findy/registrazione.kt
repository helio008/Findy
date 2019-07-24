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
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_registrazione.*
import kotlinx.android.synthetic.main.fragment_scelta_worker.*
import android.app.ProgressDialog
import android.content.Context
import android.view.inputmethod.InputMethodManager


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var auth: FirebaseAuth
// ...



/**
 * A simple [Fragment] subclass.
 *
 */
class registrazione : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        return inflater.inflate(R.layout.fragment_registrazione, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupButton.setOnClickListener( View.OnClickListener() {
            if(emailEditTextReg.text.isNotBlank() && emailEditTextReg.text.isNotEmpty() && passwordEditTextReg.text.isNotBlank() && passwordEditTextReg.text.isNotEmpty() && passwordEditTextReg2.text.isNotBlank() && passwordEditTextReg2.text.isNotEmpty()){
                if(passwordEditTextReg2.text.toString()==passwordEditTextReg.text.toString()){
                    //LE PASSWORD COINCIDONO

                    auth.createUserWithEmailAndPassword(emailEditTextReg.text.toString(), passwordEditTextReg.text.toString())
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(activity, "UTENTE CREATO.",
                                    Toast.LENGTH_SHORT).show()
                                val user = auth.currentUser
                                chiudoTastiera()
                                Navigation.findNavController(view).navigate(R.id.action_registrazione_to_modificaDati)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("MESSAGE", "createUserWithEmail:failure", task.exception)
                                if(passwordEditTextReg.text.toString().length<6){
                                    passwordEditTextReg.setError("Almeno 6 Caratteri")

                                }else{
                                    Toast.makeText(activity, "CREAZIONE FALLITA",
                                        Toast.LENGTH_SHORT).show()
                                }
                            }

                            // ...
                        }
                }else{
                    //PASSWORD NON COINCIDENTI
                    passwordEditTextReg2.setError("Le pasword non coincidono")
                }
            }else{
                //c'è qualche campo vuoto
                Toast.makeText(activity, "RIEMPI TUTTI I CAMPI",
                    Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun chiudoTastiera(){
        val inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager!!.hideSoftInputFromWindow(
            view!!.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }


}
