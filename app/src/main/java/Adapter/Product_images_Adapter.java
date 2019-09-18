package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import Config.BaseURL;
import trolley.tcc.R;

public class Product_images_Adapter extends BaseAdapter {
    Context context;
    private List<String> list;

    public Product_images_Adapter(Context context, List<String> list) {
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
        View view1= LayoutInflater.from(context).inflate(R.layout.row_product_imges_rv,null);

        ImageView img=(ImageView)view1.findViewById(R.id.img_pd_dt);

        //Toast.makeText(context,""+list.get(i),Toast.LENGTH_LONG).show();

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + list.get(i))
                .centerCrop()
                .crossFade()
                .into(img);
        return view1;
    }
}
