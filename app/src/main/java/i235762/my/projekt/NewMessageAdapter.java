package i235762.my.projekt;

import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;




import java.util.ArrayList;

public class NewMessageAdapter extends RecyclerView.Adapter<NewMessageAdapter.MyViewHolder> {


    Context context;
    String currentUserId,emails;
    ArrayList<User> users;


    public NewMessageAdapter(Context c,ArrayList<User> u,String uid,String e){
        context = c;
        users = u;
        currentUserId = uid;
        emails = e;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.usercard,viewGroup,false));
    }

    @Override
   public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

            myViewHolder.email.setText(users.get(i).getEmail());
            myViewHolder.onClick(i);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email;
        Button button;

        public MyViewHolder(View itemView){
            super(itemView);

            email = (TextView) itemView.findViewById(R.id.email);
            button = (Button) itemView.findViewById(R.id.sendMessage);


        }
        public void onClick(final int position){
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    newChat(currentUserId,users.get(position).getUid());


                    Intent intent = new Intent(v.getContext(),ChatPage.class);

                    intent.putExtra("reciverUid",users.get(position).getUid());
                    intent.putExtra("reciverEmail",email.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

//    private void newChat(String senderID,String reciverID) {
//        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("wiadomosci").child(senderID).child(reciverID).child("0").setValue("0");
//        mDatabase.child("wiadomosci").child(reciverID).child(senderID).child("0").setValue("0");
//
//    }

}
