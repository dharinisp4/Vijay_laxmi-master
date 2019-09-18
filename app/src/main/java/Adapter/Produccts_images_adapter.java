package Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import Config.BaseURL;
import Fragment.Details_Fragment;
import trolley.tcc.R;

public class Produccts_images_adapter extends RecyclerView.Adapter<Produccts_images_adapter.MyViewHolder>{

    Activity activity;
    private List<String> list;

    public Produccts_images_adapter(Activity activity, List<String> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(activity).inflate(R.layout.row_product_imges_rv,null);

     MyViewHolder myViewHolder=new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {



//        Picasso.get().load(BaseURL.IMG_PRODUCT_URL + list.get(position))
//                .centerCrop()
//                .into(holder.img);

        Glide.with(activity)

                        .load(BaseURL.IMG_PRODUCT_URL + list.get(position))
                .override(120,120)

                        .into(holder.img);

        Glide.with(activity)
                .load(BaseURL.IMG_PRODUCT_URL + list.get(0))


                .crossFade()
                .into(Details_Fragment.btn);

        final int i=position;


        holder.img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Glide.with(activity)
                        .load(BaseURL.IMG_PRODUCT_URL + list.get(i))

                        .crossFade()
                        .into(Details_Fragment.btn);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);

            img=(ImageView)itemView.findViewById(R.id.img_pd_dt);
        }
    }
}
