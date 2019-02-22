package com.vitafresh.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdaptor extends BaseAdapter {
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;

    public ChatListAdaptor(Activity activity, DatabaseReference dbRef, String userName){
        mActivity = activity;
        mDatabaseReference = dbRef.child("messages");
        mDisplayName = userName;
        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder{
        TextView authorName;
        TextView messageBody;
        LinearLayout.LayoutParams params;
        
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public InstantMessage getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_msg_row, viewGroup, false);
            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) view.findViewById(R.id.author);
            holder.messageBody = (TextView) view.findViewById(R.id.messageInput);
            holder.params = (LinearLayout.LayoutParams)holder.authorName.getLayoutParams();
            view.setTag(holder);
        }
        final  InstantMessage message = getItem(i);
        final ViewHolder holder = (ViewHolder)view.getTag();
        String author = message.getAuthor();
        holder.authorName.setText(author);
        String msg = message.getMessage();
        holder.messageBody.setText(msg);
        

        return view;
    }
}
