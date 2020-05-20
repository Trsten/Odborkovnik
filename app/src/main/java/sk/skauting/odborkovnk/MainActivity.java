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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextNickname;
    private EditText editTextPassword;
    private TextView textViewRegistered;

    private ProgressDialog progressDialog;

    private FirebaseAuth fireBaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireBaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        textViewRegistered = (TextView) findViewById(R.id.textViewRegistered);

        buttonRegister.setOnClickListener(this);
        textViewRegistered.setOnClickListener(this);
    }

    private void registerUser() {
        String nickname = editTextNickname.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this,"Please enter nickname", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter nickname", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Register User...");
        progressDialog.show();


        fireBaseAuth.createUserWithEmailAndPassword(nickname, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Authentication Succes.",Toast.LENGTH_SHORT).show();
                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }

                });

//        fireBaseAuth.signInWithEmailAndPassword(nickname, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(MainActivity.this, "Authentication Succes.",Toast.LENGTH_SHORT).show();
//                            // Sign in success, update UI with the signed-in user's information
//
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
//                        }
//                        // ...
//                    }
//                });
//

    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }

        if (v == textViewRegistered) {
            //TODO: open login activity here
        }
    }

}
