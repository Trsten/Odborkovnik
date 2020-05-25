package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import sk.skauting.odborkovnk.Model.Challenge;
import sk.skauting.odborkovnk.Model.ChallengeTask;
import sk.skauting.odborkovnk.Model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth fireBaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireBaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewRegister = (TextView) findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);

        uploadNewChallenge();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            loginUser();
        }

        if (v == textViewRegister) {
            Intent registerActivity = new Intent(this, RegisterActivity.class);
            startActivity(registerActivity);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if ( firebaseUser != null ) {
            FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
            updateUI(currentUser);
        }
    }

    private void loginUser() {
        String nickname = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Login User...");
        progressDialog.show();

        fireBaseAuth.signInWithEmailAndPassword(nickname, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = fireBaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            String msg = task.getException().getMessage();
                            msg = msg.split("\\.", 2)[0];
                            if ( task.getException() instanceof FirebaseAuthInvalidUserException ) {
                                Toast.makeText(MainActivity.this, "Email not in use", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, msg,Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.cancel();
                    }
                });

    }

    public void updateUI(FirebaseUser currentUser) {
        Intent profileActivity = new Intent(this, HomeActivity.class);
        profileActivity.putExtra("email",currentUser.getEmail());
        startActivity(profileActivity);
    }

    private void uploadNewChallenge() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference refDatabase = database.getReference("challenges");
//
//        String uloha = "Vediem si astronomický denník, kde si zaznamenávam moje pozorovania aj s náčrtmi.";
//        String uloha2 = "Družine či oddielu som vysvetlil princíp zatmenia Slnka a Mesiaca a takisto princíp striedania ročných období.";
//        String uloha3 = "Vyrobil som jednoduchú maketu slnečnej sústavy a snažil som sa zachovať proporčné veľkosti. Predviedol som ju družine aj s rozprávaním o vzdialenostiach a zložení jednotlivých hviezd.";
//        String uloha4 = "Urobil som 20 kartičiek súhvezdí (poloha hviezd, významné hviezdy, známe legendy), ak ma niekto požiadal, pomohol som mu ich nájsť na oblohe.";
//        String uloha5 = "Pozoroval som mesiac ďalekohľadom. Podľa mapy som na ňom našiel vyznačené objekty.";
//        String uloha6 = "Prečítal som aspoň jednu knihu o astronómii.";
//
//        ChallengeTask tas = new ChallengeTask("false",uloha);
//        ChallengeTask tas2 = new ChallengeTask("false",uloha2);
//        ChallengeTask tas3 = new ChallengeTask("false",uloha3);
//        ChallengeTask tas4 = new ChallengeTask("false",uloha4);
//        ChallengeTask tas5 = new ChallengeTask("false",uloha5);
//        ChallengeTask tas6 = new ChallengeTask("false",uloha6);
//
//        Map<String,ChallengeTask> tasks = new HashMap<>();
//        String key = refDatabase.push().getKey();
//        tasks.put(key,tas);
//        key = refDatabase.push().getKey();
//        tasks.put(key,tas2);
//        key = refDatabase.push().getKey();
//        tasks.put(key,tas3);
//        key = refDatabase.push().getKey();
//        tasks.put(key,tas4);
//        key = refDatabase.push().getKey();
//        tasks.put(key,tas5);
//        key = refDatabase.push().getKey();
//        tasks.put(key,tas6);
//
//        Challenge ch = new Challenge("Hvezdár","Hvezdar je odborka zamerana na hviezdy","https://firebasestorage.googleapis.com/v0/b/odborkovnik.appspot.com/o/Hvezdar.png?alt=media&token=d9131f3c-d277-407d-b27b-4921854fc64e",tasks);
//
//        String keyId = refDatabase.push().getKey();
//        refDatabase.child(keyId).setValue(ch);
    }

}
