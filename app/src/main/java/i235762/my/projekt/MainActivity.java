package i235762.my.projekt;

import android.content.Intent;
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
    private Button loginButton, newUserButton;
    private TextView errorTextView;
    public FirebaseAuth mAuth;



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

        if (mAuth.getCurrentUser() != null) {
            logIn();
        }

    }

    private void loginClicked() {
        if (!TextUtils.isEmpty(loginForm.getText().toString()) && !TextUtils.isEmpty(passwordForm.getText().toString())) {
            mAuth.signInWithEmailAndPassword(loginForm.getText().toString(), passwordForm.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("aaa",mAuth.getCurrentUser().getUid());
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
                    errorTextView.setText("Zarejerstrowano");
                } else {
                    errorTextView.setText("Błąd");
                }
            }
        });
    }

    private void logIn() {
        Intent intent = new Intent(this, NewMessagePage.class);
        intent.putExtra("uid",mAuth.getCurrentUser().getUid());
        intent.putExtra("email",mAuth.getCurrentUser().getEmail());
        startActivity(intent);
    }

    private void saveUserInDatabase(String userid, String email) {
        User user = new User(userid, email);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/uzytkownicy/" + userid);
        myRef.setValue(user);
    }
}


