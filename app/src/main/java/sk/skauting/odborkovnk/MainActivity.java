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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewRegister;

    private ProgressDialog progressDialog;

    private FirebaseAuth fireBaseAuth;

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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
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
                            Toast.makeText(MainActivity.this, "Authentication Succes.",Toast.LENGTH_SHORT).show();
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
        Intent profileActivity = new Intent(this, ProfileActivity.class);
        profileActivity.putExtra("email",currentUser.getEmail());
        startActivity(profileActivity);
    }

}
