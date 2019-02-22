package com.vitafresh.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdaptor extends BaseAdapter {
    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;

    private ChildEventListener mListener= new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Log.d("FlashChat", "ChildEventListener onChildAdded: ");
            mSnapshotList.add(dataSnapshot);
            //Refresh List
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public ChatListAdaptor(Activity activity, DatabaseReference dbRef, String userName){
        mActivity = activity;
        mDisplayName = userName;
        mDatabaseReference = dbRef.child("messages");
        mDatabaseReference.addChildEventListener(mListener);


        mSnapshotList = new ArrayList<>();
    }

    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
        
    }

    @Override
    public int getCount() {
        Integer count = mSnapshotList.size();
        return count;
    }

    @Override
    public InstantMessage getItem(int i) {
        DataSnapshot snapshot = mSnapshotList.get(i);
        return snapshot.getValue(InstantMessage.class);
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
            holder.body = (TextView) view.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams)holder.authorName.getLayoutParams();
            view.setTag(holder);
        }
        Log.d("FlashChat","InstantMessage message = getItem(i)");
        final  InstantMessage message = getItem(i);

        Log.d("FlashChat","holder = (ViewHolder)view.getTag()");
        final ViewHolder holder = (ViewHolder)view.getTag();


        String author = message.getAuthor();
        Log.d("FlashChat","String author = message.getAuthor(); author = " + author);
        holder.authorName.setText(author);

        String msg = message.getMessage();
        Log.d("FlashChat","String msg = message.getMessage(); msg = " + msg);

        holder.body.setText(msg);
        Log.d("FlashChat","holder.body.setText(msg); msg= " + msg);

        return view;
    }

    public void cleanup(){
        mDatabaseReference.removeEventListener(mListener);

    }
}
