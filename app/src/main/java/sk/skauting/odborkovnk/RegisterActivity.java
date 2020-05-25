package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import sk.skauting.odborkovnk.Model.Challenge;
import sk.skauting.odborkovnk.Model.ChallengeTask;
import sk.skauting.odborkovnk.Model.User;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextFullname;
    private EditText editTextScoutNickname;
    private EditText editTextScoutUnit;
    private EditText editTextpassword;
    private EditText editTextConfpassword;
    private TextView textViewRegistered;

    private ProgressDialog progressDialog;

    private FirebaseDatabase database;
    private DatabaseReference refDatabase;
    private FirebaseAuth fireBaseAuth;

    private static final String USER = "user";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        buttonRegister =  (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.regTextEmail);
        editTextFullname = (EditText) findViewById(R.id.regTextFullName);
        editTextScoutNickname = (EditText) findViewById(R.id.regTextScoutNickName);
        editTextScoutUnit = (EditText) findViewById(R.id.regTextUnit);
        editTextpassword = (EditText) findViewById(R.id.regTextPassword);
        editTextConfpassword = (EditText) findViewById(R.id.regConfTextPassword);
        textViewRegistered = (TextView)  findViewById(R.id.textViewRegistered);

        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        refDatabase = database.getReference("users");
        fireBaseAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(this);
        textViewRegistered.setOnClickListener(this);

    }

    public void registerUser() {

        String email = editTextEmail.getText().toString();
        String fullname = editTextFullname.getText().toString();
        String scoutNickname = editTextScoutNickname.getText().toString();
        String scoutUnit = editTextScoutUnit.getText().toString();
        String password = editTextpassword.getText().toString();
        String confPassword = editTextConfpassword.getText().toString();

        if (TextUtils.isEmpty(email) ) {
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password) ) {
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( !password.equals(confPassword) ) {
            Toast.makeText(this,"passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(fullname) ) {
            Toast.makeText(this,"Please enter your fullname", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(scoutNickname) ) {
            Toast.makeText(this,"Please enter your scout nickname", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(scoutNickname) ) {
            Toast.makeText(this,"Please enter your scout unit", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Create account...");
        progressDialog.show();

        String uloha = "Počas jednej sezóny som šiel aspoň 10-krát na hubačku.";
        String uloha2 = "Družine som vysvetlil rozdiel medzi nejedlými a jedovatými hubami.";

        String uloha3 = "Zostavil som malý herbár z nižších (machy) a vyšších rastlín (byliny a dreviny).";
        String uloha4 = "Viem pracovať s rôznymi druhmi atlasov a kľúčov na určovanie rastlín.";

        ChallengeTask tas = new ChallengeTask("false",uloha);
        ChallengeTask tas2 = new ChallengeTask("false",uloha2);

        ChallengeTask tas3 = new ChallengeTask("false",uloha3);
        ChallengeTask tas4 = new ChallengeTask("false",uloha4);

        Map<String,ChallengeTask> tasks = new HashMap<>();
        Map<String,ChallengeTask> tasks2 = new HashMap<>();

        String key = refDatabase.push().getKey();

        tasks.put(key,tas);
        key = refDatabase.push().getKey();
        tasks.put(key,tas2);
        key = refDatabase.push().getKey();
        tasks2.put(key,tas4);
        key = refDatabase.push().getKey();
        tasks2.put(key,tas3);

        Challenge ch = new Challenge("Botanik","Botanik je odborka o pestovani odborie","https://firebasestorage.googleapis.com/v0/b/odborkovnik.appspot.com/o/Botanik.png?alt=media&token=502466ef-4e88-4f9d-b86d-7e601432af0c",tasks);
        Challenge ch1 = new Challenge("Hubar","Hubar je odborka o hubach a znalostiach","https://firebasestorage.googleapis.com/v0/b/odborkovnik.appspot.com/o/Hubar.png?alt=media&token=531c4d96-951d-4453-9015-1abc734a6a60",tasks2);

        Map<String,Challenge> challenges = new HashMap<>();

        key = refDatabase.push().getKey();
        challenges.put(key,ch);
        key = refDatabase.push().getKey();
        challenges.put(key,ch1);

        user = new User(email,fullname,scoutNickname,password,scoutUnit,challenges);

        fireBaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account successfully created",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fireBaseAuth.getCurrentUser();
                            updateUI();
                        } else {
                            String msg = task.getException().getMessage();
                            if ( task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(RegisterActivity.this, "Password should be at least 6 characters ",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, msg,Toast.LENGTH_SHORT).show();
                            }
                        progressDialog.cancel();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewRegistered) {
            finish();
        }
    }

    public void updateUI() {
        String keyId = refDatabase.push().getKey();
        refDatabase.child(keyId).setValue(user);

        finish();
    }

}

