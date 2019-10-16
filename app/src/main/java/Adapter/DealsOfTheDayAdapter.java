package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Config.BaseURL;
import Fragment.Details_Fragment;
import Model.Deal_Of_Day_model;
import Model.ProductVariantModel;
import trolley.tcc.R;
import util.DatabaseCartHandler;
import util.WishlistHandler;

import static android.content.Context.MODE_PRIVATE;

public class DealsOfTheDayAdapter extends RecyclerView.Adapter<DealsOfTheDayAdapter.ViewHolder> {

    String atr_id="";
    String atr_product_id="";
    String attribute_name="";
    String attribute_value="";
    String attribute_mrp="";
    ArrayList<ProductVariantModel> variantList;
    ProductVariantAdapter productVariantAdapter;
    int status=0;
    private List<Deal_Of_Day_model> modelList;
    private Context context;
    public int counter;
    public WishlistHandler db_wish;
    DatabaseCartHandler db_cart;
    // Activity activity;
    SharedPreferences preferences;
    public DealsOfTheDayAdapter(List<Deal_Of_Day_model> modelList, Context context) {
        db_cart=new DatabaseCartHandler(context);
        db_wish=new WishlistHandler(context);
        this.modelList = modelList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.row_deal_of_the_day, parent, false);

        context = parent.getContext();

        return new DealsOfTheDayAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Deal_Of_Day_model mList = modelList.get(position);
        final String getid = mList.getProduct_id();
        String product_dsecription = mList.getProduct_description();
//        if(db_wish.isInWishtable( getid ))
//        {
//            holder.wish_after.setVisibility( View.VISIBLE );
//            holder.wish_before.setVisibility( View.GONE );
//        }
        preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
        final String language=preferences.getString("language","");

        String img_array= mList.getProduct_image();
        String img_name = null;
        try {
            JSONArray array=new JSONArray(img_array);
            img_name=array.get(0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Glide.with(context)
                .load( BaseURL.IMG_PRODUCT_URL + img_name)
                .placeholder(R.drawable.icon)
                .crossFade()
                .diskCacheStrategy( DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.product_img);
        //holder.product_prize.setText(context.getResources().getString(R.string.tv_toolbar_price) + context.getResources().getString(R.string.currency) + mList.getPrice());
        // holder.product_prize.setText( context.getResources().getString(R.string.currency) + mList.getMrp());
        holder.product_price.setPaintFlags( holder.product_offer_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.product_desc.setText(product_dsecription );

        final String atr=String.valueOf(mList.getProduct_attribute());
        if(atr.equals("[]"))
        {


            status=1;
            String p=String.valueOf(mList.getPrice());
            String m=String.valueOf(mList.getMrp());
            holder.product_price.setText(context.getResources().getString(R.string.currency)+ mList.getMrp());
            holder.product_offer_price.setText(context.getResources().getString(R.string.currency)+mList.getPrice());
            holder.txtrate.setVisibility(View.VISIBLE);
            holder.txtrate.setText(mList.getUnit_value()+" "+mList.getUnit());
            int discount=getDiscount(p,m);
            holder.product_discount.setText( String.valueOf( discount ) + "%" +"off" );
            //Toast.makeText(getActivity(),""+atr,Toast.LENGTH_LONG).show();
          //  holder.product_discount.setText(""+discount+"% OFF");

        }

        else
        {

            holder.rel_variant.setVisibility(View.VISIBLE);
            status=2;
            JSONArray jsonArr = null;
            try {

                jsonArr = new JSONArray(atr);

                ProductVariantModel model=new ProductVariantModel();
                JSONObject jsonObj = jsonArr.getJSONObject(0);
                atr_id=jsonObj.getString("id");
                atr_product_id=jsonObj.getString("product_id");
                attribute_name=jsonObj.getString("attribute_name");
                attribute_value=jsonObj.getString("attribute_value");
                attribute_mrp=jsonObj.getString("attribute_mrp");



                //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));

                //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();



                String atr_price=String.valueOf(attribute_value);
                String atr_mrp=String.valueOf(attribute_mrp);
                int atr_dis=getDiscount(atr_price,atr_mrp);
                holder.product_price.setText("\u20B9"+attribute_value.toString());
               holder.product_offer_price.setText("\u20B9"+attribute_mrp.toString());
               holder.product_discount.setText( String.valueOf( atr_dis ) + "%" +"off" );

               // holder.dialog_txtId.setText(atr_id.toString()+"@"+"0");
                //dialog_unit_type.setText("\u20B9"+variantList.get(i).getAttribute_value()+"/"+variantList.get(i).getAttribute_name());
                //dialog_txtId.setText(variantList.get(i).getId()+"@"+i);
              //  holder.dialog_txtVar.setText(attribute_value+"@"+attribute_name+"@"+attribute_mrp);
              //  holder.dialog_unit_type.setText("\u20B9"+attribute_value+"/"+attribute_name);
                //  holder.txtTotal.setText("\u20B9"+String.valueOf(list_atr_value.get(0).toString()));
                //holder.product_discount.setText(""+atr_dis+"% OFF");
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }

        final String product_id=String.valueOf(mList.getProduct_id());
        if(atr.equals("[]"))
        {
            boolean st=db_cart.isInCart(product_id);
            if(st==true)
            {
//                holder.add_Button.setVisibility(View.GONE);
//                holder.elegantNumberButton.setNumber(db_cart.getCartItemQty(product_id));
//                holder.elegantNumberButton.setVisibility(View.VISIBLE);
            }

        }
        else
        {
//            String str_id=holder.dialog_txtId.getText().toString();
//           String[] str=str_id.split("@");
//            String at_id=String.valueOf(str[0]);
//            boolean st=db_cart.isInCart(at_id);
//            if(st==true)
//            {
//                holder.add_Button.setVisibility(View.GONE);
//                holder.elegantNumberButton.setNumber(db_cart.getCartItemQty(at_id));
//                holder.elegantNumberButton.setVisibility(View.VISIBLE);
//            }
//            else {
//
//            }
        }
        holder.rel_variant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final Deal_Of_Day_model mList = modelList.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row=layoutInflater.inflate(R.layout.dialog_vairant_layout,null);
                variantList.clear();
                String atr=String.valueOf(mList.getProduct_attribute());
                JSONArray jsonArr = null;
                try {

                    jsonArr = new JSONArray(atr);
                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        ProductVariantModel model=new ProductVariantModel();
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        String atr_id=jsonObj.getString("id");
                        String atr_product_id=jsonObj.getString("product_id");
                        String attribute_name=jsonObj.getString("attribute_name");
                        String attribute_value=jsonObj.getString("attribute_value");
                        String attribute_mrp=jsonObj.getString("attribute_mrp");


                        model.setId(atr_id);
                        model.setProduct_id(atr_product_id);
                        model.setAttribute_value(attribute_value);
                        model.setAttribute_name(attribute_name);
                        model.setAttribute_mrp(attribute_mrp);

                        variantList.add(model);

                        //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));

                        //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListView l1=(ListView)row.findViewById(R.id.list_view_varaint);
                productVariantAdapter=new ProductVariantAdapter(context,variantList);
                //productVariantAdapter.notifyDataSetChanged();
                l1.setAdapter(productVariantAdapter);


                builder.setView(row);
                final AlertDialog ddlg=builder.create();
                ddlg.show();
                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        atr_id=String.valueOf(variantList.get(i).getId());
                        atr_product_id=String.valueOf(variantList.get(i).getProduct_id());
                        attribute_name=String.valueOf(variantList.get(i).getAttribute_name());
                        attribute_value=String.valueOf(variantList.get(i).getAttribute_value());
                        attribute_mrp=String.valueOf(variantList.get(i).getAttribute_mrp());

                        holder.dialog_unit_type.setText("\u20B9"+attribute_value+"/"+attribute_name);
                        //   holder.dialog_txtId.setText(variantList.get(i).getId());
                        holder.dialog_txtId.setText(variantList.get(i).getId()+"@"+i);
                        holder.dialog_txtVar.setText(variantList.get(i).getAttribute_value()+"@"+variantList.get(i).getAttribute_name()+"@"+variantList.get(i).getAttribute_mrp());

                        //    txtPer.setText(String.valueOf(df)+"% off");

                        holder.product_price.setText("\u20B9"+attribute_value.toString());
                        holder.product_offer_price.setText("\u20B9"+attribute_mrp.toString());
                        String pr=String.valueOf(attribute_value);
                        String mr=String.valueOf(attribute_mrp);
                        int atr_dis=getDiscount(pr,mr);
                       // holder.product_discount.setText(""+atr_dis+"% OFF");
                        String atr=String.valueOf(modelList.get(position).getProduct_attribute());
                        String product_id=String.valueOf(modelList.get(position).getProduct_id());
                        if(atr.equals("[]"))
                        {
                            boolean st=db_cart.isInCart(product_id);
                            if(st==true)
                            {
                                holder.add_Button.setVisibility(View.GONE);
                                holder.elegantNumberButton.setNumber(db_cart.getCartItemQty(product_id));
                                holder.elegantNumberButton.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                           String str_id=holder.dialog_txtId.getText().toString();
                            String[] str=str_id.split("@");
                            String at_id=String.valueOf(str[0]);
                            boolean st=db_cart.isInCart(at_id);
                            if(st==true)
                            {
                                holder.add_Button.setVisibility(View.GONE);
                                holder.elegantNumberButton.setNumber(db_cart.getCartItemQty(at_id));
                                holder.elegantNumberButton.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                holder.add_Button.setVisibility(View.VISIBLE);

                                holder.elegantNumberButton.setVisibility(View.GONE);
                            }
                        }

                        // holder.elegantNumberButton.setNumber("1");


                        ddlg.dismiss();
                    }
                });

            }
        });

        holder.card_deals.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Deal_Of_Day_model mList = modelList.get(position);
                Bundle args = new Bundle();
//                String cl=String.valueOf( mList.getColor());
//                String sz=String.valueOf(mList.getSize());
//                String c="";
//                String s="";
//
//                if(cl.isEmpty())
//                {
//
//                    c="col";
//
//                }
//                else if(cl.equals( "null" ))
//                {
//                    c="col";
//                }
//                else if(sz.isEmpty())
//                {
//                    s="size";
//                }
//                else if(sz.equals( "null" ))
//                {
//                    s="size";
//                }
//


                //args.putString("product_id",mList.getProduct_id());
                //args.putString("product_id",mList.getProduct_id());
                args.putString("cat_id", modelList.get(position).getCategory_id());
                args.putString("product_id",modelList.get(position).getProduct_id());
                args.putString("product_image",modelList.get(position).getProduct_image());
                args.putString("product_name",modelList.get(position).getProduct_name());
                args.putString("product_description",modelList.get(position).getProduct_description());
                args.putString("stock",modelList.get(position).getIn_stock());
//                args.putString("product_size",modelList.get(position).getSize());
//                args.putString("product_color",modelList.get( position).getColor());
                args.putString("unit_price",modelList.get( position ).getUnit_price());
                args.putString("price",modelList.get(position).getPrice());
                args.putString("mrp",modelList.get(position).getMrp());
                args.putString("unit_value",modelList.get(position).getUnit_value());
                args.putString("unit",modelList.get(position).getUnit());
                args.putString("product_attribute",modelList.get(position).getProduct_attribute());
                args.putString("rewards",modelList.get(position).getRewards());
                args.putString("increment",modelList.get(position).getIncreament());
                args.putString("title",modelList.get(position).getTitle());
                // Toast.makeText(getActivity(),""+getid,Toast.LENGTH_LONG).show();
                Details_Fragment fm = new Details_Fragment();
                fm.setArguments(args);
                 AppCompatActivity activity=(AppCompatActivity) view.getContext();
                activity.getFragmentManager().beginTransaction().replace(R.id.contentPanel,fm)
                        .addToBackStack(null)
                        .commit();

            }
        } );


        if (language.contains("english")) {
            holder.product_name.setText(mList.getProduct_name());
        }
        else {
            holder.product_name.setText(mList.getProduct_name());

        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_img ;
        TextView product_name,product_desc , product_price ,product_offer_price ,dialog_unit_type ,dialog_txtId,dialog_txtVar,txtrate ,product_discount ;
        CardView card_deals ;
        RelativeLayout rel_variant ;
        Button add_Button ;
        ElegantNumberButton elegantNumberButton;


        public ViewHolder(View itemView) {
            super( itemView );

            product_img = (ImageView) itemView.findViewById( R.id.iv_icon );
            product_name=(TextView)itemView.findViewById( R.id.product_name );
            product_price=(TextView)itemView.findViewById( R.id.product_prize );
            product_offer_price=(TextView)itemView.findViewById( R.id.product_offer_prize );
            product_discount =(TextView)itemView.findViewById( R.id.product_discount );
            variantList=new ArrayList<>();
            card_deals=(CardView) itemView.findViewById(R.id.card_view_deals);
            dialog_unit_type=(TextView)itemView.findViewById(R.id.unit_type);
            dialog_txtId=(TextView)itemView.findViewById(R.id.txtId);
            txtrate=(TextView)itemView.findViewById(R.id.single_varient);
            dialog_txtVar=(TextView)itemView.findViewById(R.id.txtVar);
            rel_variant=(RelativeLayout)itemView.findViewById(R.id.rel_variant);
            product_desc=(TextView)itemView.findViewById( R.id.product_desc );
            add_Button=(Button)itemView.findViewById( R.id.btn_add );
            elegantNumberButton=(ElegantNumberButton) itemView.findViewById( R.id.product_qty );
        }
    }
    public int getDiscount(String price, String mrp)
    {
        double mrp_d=Double.parseDouble(mrp);
        double price_d=Double.parseDouble(price);
        double per=((mrp_d-price_d)/mrp_d)*100;
        double df=Math.round(per);
        int d=(int)df;
        return d;
    }
    private void updateintent() {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "update");
        context.sendBroadcast(updates);
    }

    public boolean checkAttributeStatus(String atr)
    {
        boolean sts=false;
        if(atr.equals("[]"))
        {
            sts=false;
        }
        else
        {
            sts=true;
        }
        return sts;
    }
}
