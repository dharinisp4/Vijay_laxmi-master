package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Model.ProductVariantModel;
import trolley.tcc.R;

public class VarientsAdapter extends RecyclerView.Adapter<VarientsAdapter.ViewHolder> {
    Context context;
    List<ProductVariantModel> varientList ;

    int row_index=-1;
    public VarientsAdapter(Context context, List<ProductVariantModel> varientList) {
        this.context = context;
        this.varientList = varientList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate( R.layout.row_weight_layout,parent,false );
    ViewHolder viewHolder = new ViewHolder( view );
    return  viewHolder ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ProductVariantModel model=varientList.get(position);
        holder.txt_weight.setText(model.getAttribute_name());
        holder.rel_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
            }
        });
        if(row_index==position){
            holder.rel_click.setCardBackgroundColor(Color.parseColor("#fc6b03"));
            holder.txt_weight.setTextColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.rel_click.setCardBackgroundColor(Color.parseColor("#ffffff"));
            holder.txt_weight.setTextColor(Color.parseColor("#fc6b03"));
        }
    }

    @Override
    public int getItemCount() {
        return varientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView rel_click;
        TextView txt_weight ;

        public ViewHolder(View itemView) {
            super( itemView );
            rel_click=(CardView) itemView.findViewById(R.id.rel_click);
            txt_weight=(TextView) itemView.findViewById( R.id.txt_weight);
        }
    }
}
