package com.barcode.cvs_review;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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


        public CustomViewHolder(View view) {
            super(view);
            // this.BARCODE = (TextView) view.findViewById(R.id.textView_list_BARCODE);
            this.CVS_NAME = (TextView) view.findViewById(R.id.textView_list_CVS_NAME);
            this.PRODUCT_NAME = (TextView) view.findViewById(R.id.textView_list_PRODUCT_NAME);
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
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}