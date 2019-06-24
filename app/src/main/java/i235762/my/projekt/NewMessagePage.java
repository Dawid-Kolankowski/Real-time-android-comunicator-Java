package i235762.my.projekt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewMessagePage extends AppCompatActivity {
    DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<User> usersList;
    NewMessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message_page);
        recyclerView =  (RecyclerView) findViewById(R.id.newMessageRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersList = new ArrayList<User>();
        ref = FirebaseDatabase.getInstance().getReference().child("uzytkownicy");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(usersList.size()>0){
                    usersList.clear();
                }
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                   User u = dataSnapshot1.getValue(User.class);
                   usersList.add(u);

                }

                adapter = new NewMessageAdapter(NewMessagePage.this,usersList,FirebaseAuth.getInstance().getCurrentUser().getUid(),FirebaseAuth.getInstance().getCurrentUser().getEmail());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }


}
