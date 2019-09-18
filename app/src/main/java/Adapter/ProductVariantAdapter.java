package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Model.ProductVariantModel;
import trolley.tcc.R;

public class ProductVariantAdapter extends BaseAdapter {

    Context context;
    ArrayList<ProductVariantModel> list;

    public ProductVariantAdapter(Context context, ArrayList<ProductVariantModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=layoutInflater.inflate(R.layout.row_vairant_layout,viewGroup,false);

        TextView txtVariant=(TextView)row.findViewById(R.id.txtVarient);
        TextView txtVariant_id=(TextView)row.findViewById(R.id.txtVariantId);

        ProductVariantModel model=list.get(i);

        txtVariant.setText("\u20B9"+model.getAttribute_value()+"/"+model.getAttribute_name());
        txtVariant_id.setText(model.getId());

        return row;
    }
}
