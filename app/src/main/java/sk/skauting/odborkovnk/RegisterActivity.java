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

