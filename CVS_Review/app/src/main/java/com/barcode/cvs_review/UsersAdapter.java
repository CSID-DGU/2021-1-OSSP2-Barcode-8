package com.barcode.cvs_review;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.barcode.cvs_review.activity.ProductSpecActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

    private ArrayList<Database> mList = null;
    private Activity context = null;


    public UsersAdapter(Activity context, ArrayList<Database> list) {
        this.context = context;
        this.mList = list;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView CVS_NAME;
        protected TextView PRODUCT_NAME;
        protected TextView BARCODE;
        protected ImageView PRODUCT_IMAGE;
        protected RatingBar AVE_GRADE;


        public CustomViewHolder(View view) {
            super(view);
            // this.BARCODE = (TextView) view.findViewById(R.id.textView_list_BARCODE);
            this.CVS_NAME = (TextView) view.findViewById(R.id.textView_product_point);
            this.PRODUCT_NAME = (TextView) view.findViewById(R.id.textView_comments);
            this.PRODUCT_IMAGE = (ImageView) view.findViewById(R.id.imageView_product_image);
        }
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_recycler_view, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {
        // viewholder.BARCODE.setText(mList.get(position).getBARCODE());
        viewholder.CVS_NAME.setText(mList.get(position).getCVS_NAME());
        viewholder.PRODUCT_NAME.setText(mList.get(position).getPRODUCT_NAME());
        Glide.with(context).load(mList.get(position).getPRODUCT_IMAGE_URL()).into(viewholder.PRODUCT_IMAGE);

        viewholder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewholder.itemView.getContext(), ProductSpecActivity.class);
                intent.putExtra("CVS_NAME", mList.get(position).getCVS_NAME());
                intent.putExtra("PRODUCT_NAME", mList.get(position).getPRODUCT_NAME());
                intent.putExtra("PRODUCT_IMAGE", mList.get(position).getPRODUCT_IMAGE_URL());
                intent.putExtra("AVE_GRADE", mList.get(position).getAVE_GRADE());
                intent.putExtra("BARCODE", mList.get(position).getBARCODE());
                ContextCompat.startActivity(viewholder.itemView.getContext(), intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}