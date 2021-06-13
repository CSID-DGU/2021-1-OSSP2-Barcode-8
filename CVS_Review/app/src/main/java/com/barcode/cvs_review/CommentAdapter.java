package com.barcode.cvs_review;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {

    private ArrayList<Database> mList = null;
    private Activity context = null;


    public CommentAdapter(Activity context, ArrayList<Database> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView user_id;
        protected TextView product_point;
        protected TextView comments;


        public CustomViewHolder(View view) {
            super(view);
            this.user_id = (TextView) view.findViewById(R.id.textView_user_id);
            this.product_point = (TextView) view.findViewById(R.id.textView_product_point);
            this.comments = (TextView) view.findViewById(R.id.textView_comments);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_recycler_view, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.user_id.setText(mList.get(position).getUSER_ID());
        viewholder.product_point.setText(mList.get(position).getPRODUCT_POINT());
        viewholder.comments.setText(mList.get(position).getCOMMENTS());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
