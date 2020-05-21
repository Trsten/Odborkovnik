package sk.skauting.odborkovnk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextFullname;
    private EditText editTextScoutNickname;
    private EditText editTextScoutUnit;
    private EditText editTextpassword;
    private EditText editTextConfpassword;
    private TextView textViewRegistered;

    private FirebaseDatabase database;
    private DatabaseReference mDataBase;
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

        database = FirebaseDatabase.getInstance();
        mDataBase = database.getReference("user");
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
        if (TextUtils.isEmpty(scoutNickname) ) {
            Toast.makeText(this,"Please enter your scout nickname", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(scoutNickname) ) {
            Toast.makeText(this,"Please enter your scout unit", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( !password.equals(confPassword) ) {
            Toast.makeText(this,"passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        user = new User(email,fullname,scoutNickname,scoutUnit,password);

        fireBaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser user = fireBaseAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewRegistered) {
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        }
    }

    public void updateUI() {
        String keyId = mDataBase.push().getKey();
        mDataBase.child(keyId).setValue(user);

        Intent loginIntent = new Intent(this,MainActivity.class);
        startActivity(loginIntent);
    }

}

