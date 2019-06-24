package i235762.my.projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    private EditText loginForm, passwordForm;
    private Button loginButton, newUserButton,deleteData;
    private TextView errorTextView;
    public FirebaseAuth mAuth;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginForm = findViewById(R.id.loginForm);
        passwordForm = findViewById(R.id.passwordForm);
        loginButton = findViewById(R.id.loginButton);
        errorTextView = findViewById(R.id.errorTextView);
        mAuth = FirebaseAuth.getInstance();
        newUserButton = findViewById(R.id.newUserButton);
        deleteData = findViewById(R.id.deleteData);


        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        Log.d("aaa",sharedPref.getString("email",""));
        loginForm.setText(sharedPref.getString("email",""));
        passwordForm.setText(sharedPref.getString("haslo",""));

        FirebaseAuth.getInstance().signOut();
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            deleteData();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClicked();
            }
        });
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUserToBase();
            }
        });



    }

    private void loginClicked() {
        if (!TextUtils.isEmpty(loginForm.getText().toString()) && !TextUtils.isEmpty(passwordForm.getText().toString())) {
            mAuth.signInWithEmailAndPassword(loginForm.getText().toString(), passwordForm.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                logIn();
                            } else {
                                errorTextView.setText("Zły login lub hasło");
                            }
                        }
                    });
        }
    }

    private void newUserToBase() {
        mAuth.createUserWithEmailAndPassword(loginForm.getText().toString(), passwordForm.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener < AuthResult > () {
            @Override
            public void onComplete (@NonNull Task < AuthResult > task) {
                if (task.isSuccessful()) {
                    loginClicked();
                    saveUserInDatabase(task.getResult().getUser().getUid(), task.getResult().getUser().getEmail());
                    errorTextView.setText("Zarejestrowano");
                } else {
                    errorTextView.setText("Błąd");
                }
            }
        });
    }

    private void logIn() {
        editor.putString("email",loginForm.getText().toString());
        editor.putString("haslo",passwordForm.getText().toString());
        editor.commit();
        Intent intent = new Intent(this, NewMessagePage.class);

        startActivity(intent);
    }

    private void saveUserInDatabase(String userid, String email) {
        User user = new User(userid, email);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/uzytkownicy/" + userid);
        myRef.setValue(user);
    }
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth.getInstance().signOut();

    }
    private void deleteData(){
        loginForm.setText("");
        passwordForm.setText("");
        editor.putString("email","");
        editor.putString("haslo","");
        editor.commit();
    }

}


