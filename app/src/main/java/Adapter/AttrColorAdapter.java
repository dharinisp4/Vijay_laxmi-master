package Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Fragment.Details_Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import trolley.tcc.R;

public class AttrColorAdapter extends RecyclerView.Adapter<AttrColorAdapter.ViewHolder> {
    List<String> list;
    Activity activity;

    public AttrColorAdapter(List<String> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(activity).inflate(R.layout.row_atr_col_layout,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       // holder.txt.setBackgroundColor(Color.parseColor(list.get(position).toString()));
    //  holder.txt.setVisibility(View.GONE);
        holder.img_color.setColorFilter(Color.parseColor(list.get(position).toString()));
       holder.txt.setText("a");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img_color;
     TextView txt;
        public ViewHolder(View itemView) {

            super(itemView);
            img_color=(CircleImageView) itemView.findViewById(R.id.img_color);
            txt=(TextView) itemView.findViewById(R.id.txt);

        }
    }
}
