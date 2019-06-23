package i235762.my.projekt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder>  {
    Context context;
    ArrayList<Message> messages;

    public ChatAdapter(Context c, ArrayList<Message> m){
        context =c;
        messages = m;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.messagecard,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.email.setText(messages.get(i).getUser());
            myViewHolder.messageText.setText(messages.get(i).getMessage());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView email,messageText;
        public MyViewHolder(View itemView){
            super(itemView);

            email = (TextView) itemView.findViewById(R.id.messageCardEmail);
            messageText=(TextView) itemView.findViewById(R.id.messageCardText);
        }
    }
}
