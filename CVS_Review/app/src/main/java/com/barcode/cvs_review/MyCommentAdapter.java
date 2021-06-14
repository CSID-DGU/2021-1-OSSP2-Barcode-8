package com.barcode.cvs_review;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.CustomViewHolder> {

    private ArrayList<Database> mList = null;
    private Activity context = null;


    public MyCommentAdapter(Activity context, ArrayList<Database> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView product;
        protected TextView product_point;
        protected TextView comments;


        public CustomViewHolder(View view) {
            super(view);
            this.product = (TextView) view.findViewById(R.id.textView_product);
            this.product_point = (TextView) view.findViewById(R.id.textView_product_point);
            this.comments = (TextView) view.findViewById(R.id.textView_comments);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycomment_recycler_view, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.product.setText(mList.get(position).getPRODUCT_NAME());
        viewholder.product_point.setText(mList.get(position).getPRODUCT_POINT());
        viewholder.comments.setText(mList.get(position).getCOMMENTS());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
