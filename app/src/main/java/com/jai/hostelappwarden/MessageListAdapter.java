package com.jai.hostelappwarden;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.text.BoringLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int FOOTER_ITEM=1;

    private  static final int NORMAL_ITEM=0;


    static Context context22;

    static ArrayList<TextView> msgtime=new ArrayList<>();

    static ArrayList<TextView> seenmsg=new ArrayList<>();

    static ArrayList<View> listpos=new ArrayList<>();

    static ArrayList<TextView> listtext=new ArrayList<>();




    Context context;
    ArrayList<MessageData> arrayList;

    public MessageListAdapter(Context context, ArrayList<MessageData> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
        context22=context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());

        if(viewType==FOOTER_ITEM)
        {
            FooterViewHolder footerViewHolder=new FooterViewHolder(inflater.inflate(R.layout.footerlayout,parent,false));

            return footerViewHolder;
        }
        else{

            ImageViewHolder imageViewHolder=new ImageViewHolder(inflater.inflate(R.layout.messagecustomlist,parent,false));

            return imageViewHolder;
        }




    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ImageViewHolder)
        {
            ImageViewHolder holder1=(ImageViewHolder)holder;


            final MessageData messageData=arrayList.get(position);

            holder1.message.setText(messageData.getMessage());
            holder1.dateandtime.setText(messageData.getTime());
            holder1.idofmsg.setText(messageData.getId());



            holder1.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

                    DatabaseReference databaseReference=firebaseDatabase.getReference("allbroadcasts").child(messageData.getId());
                    databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                            Toast.makeText(context,"message deleted",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });






        }
        else if(holder instanceof  FooterViewHolder)
        {

        }




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder
    {


        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        TextView message,dateandtime;

        TextView idofmsg;

        ImageView delete;

        ConstraintLayout constraintLayout;

        public ImageViewHolder(@NonNull final View itemView) {
            super(itemView);

            message=itemView.findViewById(R.id.messagetext);
            dateandtime=itemView.findViewById(R.id.messagedatetime);

            constraintLayout=itemView.findViewById(R.id.messagecontainer);

            delete=itemView.findViewById(R.id.deletemsg);

            idofmsg=itemView.findViewById(R.id.idofmsg);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    BroadcastActivity.textcpy.setVisible(true);
                    BroadcastActivity.seenmenu.setVisible(true);

                   for(View view:listpos)
                   {
                       view.setBackground(view.getResources().getDrawable(R.drawable.customchatui));
                   }

                   listpos.clear();
                   listtext.clear();
                   seenmsg.clear();
                   msgtime.clear();

                    itemView.setBackground(itemView.getResources().getDrawable(R.drawable.selectchatui));
                   listpos.add(itemView);

                   listtext.add((TextView) itemView.findViewById(R.id.messagetext));
                   seenmsg.add((TextView)itemView.findViewById(R.id.idofmsg));
                   msgtime.add((TextView)itemView.findViewById(R.id.messagedatetime));

                   BroadcastActivity.check=true;

                    return true;
                }
            });





        }
    }



    @Override
    public int getItemViewType(int position) {

        if(arrayList.get(position).getId().equalsIgnoreCase("footer"))
        {
            return FOOTER_ITEM;
        }
        else {
            return NORMAL_ITEM;
        }
    }


    static void resetallandcopy(int val)
    {


        if(val==1)
        {
            for(View view:listpos)
            {

                view.setBackground(view.getResources().getDrawable(R.drawable.customchatui));
            }

            for(TextView textView:listtext)
            {
                ClipboardManager manager=(ClipboardManager) context22.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("message",textView.getText().toString());

                manager.setPrimaryClip(clipData);

                Toast.makeText(context22, "copied to clipboard", Toast.LENGTH_SHORT).show();
                BroadcastActivity.textcpy.setVisible(false);
                BroadcastActivity.seenmenu.setVisible(false);
                BroadcastActivity.check=false;
            }
        }
        else if(val==0)
        {
            for(View view:listpos)
            {

                view.setBackground(view.getResources().getDrawable(R.drawable.customchatui));
                BroadcastActivity.textcpy.setVisible(false);
                BroadcastActivity.seenmenu.setVisible(false);
            }

        }




    }

    static void startseen()
    {


        BroadcastActivity.check=false;
        BroadcastActivity.textcpy.setVisible(false);
        BroadcastActivity.seenmenu.setVisible(false);

        Intent intent=new Intent(context22,SeenActivity.class);
        for(TextView textView:seenmsg)
        {
            intent.putExtra("msgid",textView.getText().toString());
        }
        for(TextView textView:msgtime)
        {
            intent.putExtra("msgtime",textView.getText().toString());
        }
        for(TextView textView:listtext)
        {
            intent.putExtra("msg",textView.getText().toString());
        }
        context22.startActivity(intent);

    }



}
