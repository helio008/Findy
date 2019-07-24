package it.elisino.findy


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_scelta_worker.*
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var auth: FirebaseAuth


/**
 * A simple [Fragment] subclass.
 *
 */
class login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth= FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // UTENTE GIà LOGGATO
            //APRO LA SCHERMATA PROFILO PERSONALE
            Navigation.findNavController(view).navigate(R.id.action_login_to_profilo)
        } else {
            // UTENTE NON LOGGATO


            loginButton.setOnClickListener( View.OnClickListener() {
                if(emailEditText.text.isNotBlank() && emailEditText.text.isNotEmpty() && passwordEditText.text.isNotBlank() && passwordEditText.text.isNotEmpty()){
                    auth.signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Message:", "signInWithEmail:success")
                                val user = auth.currentUser
                                Toast.makeText(activity, "LOGIN SUCCESS.",
                                    Toast.LENGTH_SHORT).show()

                                chiudoTastiera()

                                //APRO LA SCHERMATA PROFILO PERSONALE
                                Navigation.findNavController(view).navigate(R.id.action_login_to_profilo)
                                /////updateUI(user)
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Message:", "signInWithEmail:failure", task.exception)
                                Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                                /////updateUI(null)
                            }

                            // ...
                        }
                }else{
                    //CAMPI MAIL E PASSWORD SONO VUOTI
                    Toast.makeText(activity, "Controlla i dati inseriti.", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    fun chiudoTastiera(){
        val inputManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager!!.hideSoftInputFromWindow(
            view!!.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }


}
