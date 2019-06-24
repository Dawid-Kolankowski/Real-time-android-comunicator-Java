package i235762.my.projekt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatPage extends AppCompatActivity {
    EditText newMessageSpace;
    Button sendMessasgeButton;
    int messageNumber;
    ArrayList<Message> messageList;
    DatabaseReference ref,invRef,adapterRef;
    ChatAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);
        messageNumber =0;
        newMessageSpace = findViewById(R.id.newMessageTextEdit);
        sendMessasgeButton = findViewById(R.id.sendNewMessageButton);
        messageList = new ArrayList<Message>();
        recyclerView = (RecyclerView) findViewById(R.id.chatRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sendMessasgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            sendMessage();
            newMessageSpace.setText("");
            }
        });
        adapterRef = FirebaseDatabase.getInstance().getReference().child("messages").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(getIntent().getExtras().getString("reciverUid"));
        adapterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(messageList.size()>0){
                    messageList.clear();
                }
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Message m = dataSnapshot1.getValue(Message.class);
                    messageList.add(m);
                }
                adapter = new ChatAdapter(ChatPage.this,messageList);
                recyclerView.setAdapter(adapter);
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
                messageNumber=messageList.size()+1;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void sendMessage(){
    Message message = new Message(newMessageSpace.getText().toString(),FirebaseAuth.getInstance().getCurrentUser().getEmail(),messageNumber);
        ref = FirebaseDatabase.getInstance().getReference("/messages/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+getIntent().getExtras().getString("reciverUid")+"/"+messageNumber);
        invRef = FirebaseDatabase.getInstance().getReference("/messages/"+getIntent().getExtras().getString("reciverUid")+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+messageNumber);

        ref.setValue(message);
        invRef.setValue(message);

    }
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        Log.d("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
        Log.d("email",FirebaseAuth.getInstance().getCurrentUser().getEmail());}
}
