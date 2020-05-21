package sk.skauting.odborkovnk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextFullname;
    private EditText editTextScoutNickname;
    private EditText editTextScoutUnit;
    private EditText editTextpassword;
    private EditText editTextConfpassword;
    private TextView textViewRegistered;

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

     //   buttonRegister.setOnClickListener(this);
      //  textViewRegistered.setOnClickListener(this);
    }

    //
//
//
//        fireBaseAuth.createUserWithEmailAndPassword(nickname, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Toast.makeText(MainActivity.this, "Authentication Succes.",Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(MainActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//
//                });
//


}

