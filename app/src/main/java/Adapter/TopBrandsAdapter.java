package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import Model.Category_model;
import trolley.tcc.R;

import static android.content.Context.MODE_PRIVATE;

public class TopBrandsAdapter extends RecyclerView.Adapter<TopBrandsAdapter.Holder> {
    private List<Category_model> modelList;
    private Context context;
    String language;
    SharedPreferences preferences;

    public TopBrandsAdapter(List<Category_model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.row_topbrands, parent, false);

        context = parent.getContext();

        return new TopBrandsAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Category_model mList = modelList.get(position);

        Glide.with(context)
                .load( BaseURL.IMG_CATEGORY_URL + mList.getImage())
                .placeholder(R.drawable.icon)
                .crossFade()
                .diskCacheStrategy( DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.brand_icon);
        preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
        language=preferences.getString("language","");
//        if (language.contains("english")) {
//            holder.title.setText(mList.getTitle());
//        }
//        else {
//            holder.title.setText(mList.getTitle());
//
//        }

    }




    @Override
    public int getItemCount() {
        return  modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView brand_icon ;
        TextView brand_name;

        public Holder(View itemView) {
            super( itemView );
            brand_icon = itemView.findViewById( R.id.brandlogo );
            brand_name=itemView.findViewById( R.id.brandname );

        }
    }
}
