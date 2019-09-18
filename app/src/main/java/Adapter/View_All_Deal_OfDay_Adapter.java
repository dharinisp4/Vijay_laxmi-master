package Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import Config.BaseURL;
import Model.View_All_Deal_Of_Day_model;
import trolley.tcc.R;



public class View_All_Deal_OfDay_Adapter extends RecyclerView.Adapter<View_All_Deal_OfDay_Adapter.MyViewHolder> {

    private List<View_All_Deal_Of_Day_model> modelList;
    private Context context;
    public int counter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView product_nmae, product_prize, offer_product_prize, start_time, end_time;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            product_nmae = (TextView) view.findViewById(R.id.product_name);
            product_prize = (TextView) view.findViewById(R.id.product_prize);
            offer_product_prize = (TextView) view.findViewById(R.id.offer_product_prize);
            start_time = (TextView) view.findViewById(R.id.start_time);
            end_time = (TextView) view.findViewById(R.id.end_time);

            product_prize.setPaintFlags(product_prize.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            image = (ImageView) view.findViewById(R.id.iv_icon);
        }
    }

    public View_All_Deal_OfDay_Adapter(List<View_All_Deal_Of_Day_model> modelList) {
        this.modelList = modelList;
    }

    @Override
    public View_All_Deal_OfDay_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_deal_of_the_day, parent, false);

        context = parent.getContext();

        return new View_All_Deal_OfDay_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final View_All_Deal_OfDay_Adapter.MyViewHolder holder, int position) {
        View_All_Deal_Of_Day_model mList = modelList.get(position);
        if (mList.getStatus().equals("1")) {
            holder.offer_product_prize.setText(context.getResources().getString(R.string.currency) + mList.getDeal_price());
        } else if (mList.getStatus().equals("0")) {
            holder.offer_product_prize.setText("Expire");
            holder.offer_product_prize.setTextColor(context.getResources().getColor(R.color.color_3));
        }

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + mList.getProduct_image())
                .placeholder(R.drawable.icon)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.image);
        holder.product_prize.setText(context.getResources().getString(R.string.currency) + mList.getPrice());
        holder.product_nmae.setText(mList.getProduct_name());
        holder.start_time.setText(mList.getStart_time());
        holder.end_time.setText(mList.getEnd_time());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


}

