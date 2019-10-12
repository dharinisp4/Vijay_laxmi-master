package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Model.ProductVariantModel;
import trolley.tcc.R;

public class VarientsAdapter extends RecyclerView.Adapter<VarientsAdapter.ViewHolder> {
    Context context;
    ArrayList<ProductVariantModel> varientList ;

    public VarientsAdapter(Context context, ArrayList<ProductVariantModel> varientList) {
        this.context = context;
        this.varientList = varientList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate( R.layout.row_varients,parent,false );
    ViewHolder viewHolder = new ViewHolder( view );
    return  viewHolder ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return varientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView varient_img ;
        TextView varient_name ;

        public ViewHolder(View itemView) {
            super( itemView );
            varient_img = (ImageView)itemView.findViewById( R.id.varient_img );
            varient_name=(TextView) itemView.findViewById( R.id.varient_code );
        }
    }
}
