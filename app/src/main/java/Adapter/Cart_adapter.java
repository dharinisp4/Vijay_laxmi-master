package Adapter;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

import Config.BaseURL;
import Fragment.Cart_fragment;
import Fragment.Details_Fragment;
import Module.Module;
import trolley.tcc.R;
import util.CartHandler;
import util.DatabaseCartHandler;

import static android.content.Context.MODE_PRIVATE;


public class Cart_adapter extends RecyclerView.Adapter<Cart_adapter.ProductHolder> {
    ArrayList<HashMap<String, String>> list;
    Activity activity;
    String Reward;
    Double price ,reward ;
    SharedPreferences preferences;
    String language;
    int qty = 0;
    Module module=new Module();
    int lastpostion;
   // DatabaseHandler dbHandler;
    DatabaseCartHandler db_cart;

    public Cart_adapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        this.list = list;
        this.activity = activity;

        db_cart=new DatabaseCartHandler(activity);

    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_rv, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {
        final HashMap<String, String> map = list.get(position);
        String img_name = null;

        String st_atr=map.get("product_attribute");

        if(st_atr.equals("[]"))
        {
            String img_array=map.get("product_image");
            try {
                JSONArray array=new JSONArray(img_array);
                img_name=array.get(0).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else
        {
            img_name=map.get("attr_img").toString();
        }



        Glide.with(activity)
                .load(BaseURL.IMG_PRODUCT_URL + img_name)
              //  .centerCrop()
                .placeholder(R.drawable.icon)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.iv_logo);
        preferences = activity.getSharedPreferences("lan", MODE_PRIVATE);
        language=preferences.getString("language","");
            holder.tv_title.setText(map.get("product_name"));

        holder.tv_mrp.setText( activity.getResources().getString(R.string.currency)+ map.get( "mrp" ) );
       holder.tv_mrp.setPaintFlags( holder.tv_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tv_price.setText(activity.getResources().getString(R.string.currency)+map.get("unit_price"));
        holder.tv_contetiy.setText(map.get("qty"));
        String p=String.valueOf(map.get("price"));
        String m=String.valueOf(map.get("mrp"));
        int pp =Integer.parseInt(p);
        int mm =Integer.parseInt(m);
        if (mm>pp) {
            int discount = getDiscount(p, m);
            //Toast.makeText(getActivity(),""+atr,Toast.LENGTH_LONG).show();
            holder.tv_product_discout.setText("" + Math.round(discount) + "% OFF");
        }
        else
            holder.tv_product_discout.setVisibility(View.GONE);

     //   int items = Integer.parseInt(db_cart.getInCartItemQty(map.get("cart_id")));
         price = Double.parseDouble(map.get("unit_price"));
        // holder.tv_subcat_weight.setText("Weight : "+map.get("unit"));
        //holder.tv_total.setText("" + price * items);
     //   holder.tv_reward.setText("" + reward * items);
       // holder.btnQty.setNumber(String.valueOf(items));

//        holder.btnQty.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//            @Override
//            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//
//                double tot_cart_amount=Double.parseDouble(db_cart.getTotalAmount());
//                float qt=Float.valueOf(newValue);
//                float price=Float.parseFloat(map.get("price"));
//                float unit_price=Float.parseFloat(map.get("unit_price"));
//                float tot_amt=price+unit_price;
//                holder.tv_total.setText(activity.getResources().getString(R.string.currency)+" "+tot_amt);
//                HashMap<String, String> mapProduct = new HashMap<String, String>();
//                mapProduct.put("product_id", map.get("product_id"));
//                mapProduct.put("product_image", map.get("product_image"));
//                mapProduct.put("category_id", map.get("category_id"));
//                mapProduct.put("product_name",map.get("product_name"));
//                mapProduct.put("price",String.valueOf(tot_amt));
//                mapProduct.put("unit_price",map.get("unit_price"));
//                mapProduct.put("size", map.get("size"));
//                mapProduct.put("color", map.get("color"));
//                mapProduct.put("rewards", map.get("rewards"));
//                mapProduct.put("unit_value", map.get("unit_value"));
//                mapProduct.put("unit", map.get("unit"));
//                mapProduct.put("increament", map.get("increament"));
//                mapProduct.put("stock", map.get("stock"));
//                mapProduct.put("title", map.get("title"));
//
//                boolean u=db_cart.updateCart(mapProduct,qt);
//                if(u)
//                {
//                  //  Toast.makeText(activity,"updated",Toast.LENGTH_LONG).show();
//                    Cart_fragment.tv_total.setText(activity.getResources().getString(R.string.currency)+" "+db_cart.getTotalAmount());
//                }
//                else
//                {
//                    //Toast.makeText(activity," not updated",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });
        holder.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int id=Integer.parseInt(map.get("cart_id"));

                ArrayList<HashMap<String, String>> mapP=db_cart.getCartProduct(id);

                HashMap<String,String> m=mapP.get(0);



               // Toast.makeText(activity,"Count"+m.get("price"),Toast.LENGTH_LONG).show();

                if (!holder.tv_contetiy.getText().toString().equalsIgnoreCase(""))
                    qty = Integer.valueOf(holder.tv_contetiy.getText().toString());

                if (qty > 0) {
                    qty = qty - 1;
                    holder.tv_contetiy.setText(String.valueOf(qty));
                    double t = Double.parseDouble(m.get("price"));
                    double p = Double.parseDouble(m.get("unit_price"));
                    holder.tv_total.setText("" + t * qty);
                    String pr = String.valueOf(t - p);
                    float qt = Float.valueOf(qty);


                    boolean b = db_cart.updateCartWithQty(map.get("cart_id"), pr, qt);
                    if (b) {
                        Toast.makeText(activity, "Qty Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(activity, "Qty not Updated", Toast.LENGTH_LONG).show();
                    }
                }
                    Cart_fragment.tv_total.setText(activity.getResources().getString(R.string.currency)+" "+db_cart.getTotalAmount());

                    if (holder.tv_contetiy.getText().toString().equalsIgnoreCase("0")) {

                        db_cart.removeItemFromCart(map.get("cart_id"));
                    list.remove(position);
                    notifyDataSetChanged();
                    updateintent();
                }
            }
        });

        holder.iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int qty = Integer.parseInt(holder.tv_contetiy.getText().toString());
                qty = qty + 1;

                holder.tv_contetiy.setText(String.valueOf(qty));

            //    holder.tv_reward.setText("" + reward * qty);
                int id=Integer.parseInt(map.get("cart_id"));

                ArrayList<HashMap<String, String>> mapP=db_cart.getCartProduct(id);

                HashMap<String,String> m=mapP.get(0);

                double t=Double.parseDouble(m.get("price"));
                double p=Double.parseDouble(m.get("unit_price"));
                holder.tv_total.setText("" + p * qty);
                String pr=String.valueOf(t+p);
                float qt=Float.valueOf(qty);

                String st_atr=map.get("product_attribute").toString();

                boolean b=db_cart.updateCartWithQty(map.get("cart_id"),pr,qt);
               if(b)
               {
                   Toast.makeText(activity,"Qty Updated",Toast.LENGTH_LONG).show();
               }
               else
               {
                   Toast.makeText(activity,"Qty not Updated",Toast.LENGTH_LONG).show();
               }

                Cart_fragment.tv_total.setText(activity.getResources().getString(R.string.currency)+" "+db_cart.getTotalAmount());


                // Toast.makeText(activity,""+map.get("product_attribute").toString(),Toast.LENGTH_LONG).show();



//             // Toast.makeText(activity,"\npri "+map.get("unit_value")+"\n am "+pr,Toast.LENGTH_LONG ).show();
//                HashMap<String, String> mapProduct = new HashMap<String, String>();
//                mapProduct.put("cat_id",map.get( "cat_id" ));
//                mapProduct.put("qty", String.valueOf( qt ) );
//                mapProduct.put( "cart_id",map.get( "cart_id" ) );
//                mapProduct.put("product_id",map.get( "product_id" ));
//                mapProduct.put("product_image",map.get( "product_image" ));
//                mapProduct.put("product_name",map.get("product_name"));
//                mapProduct.put("product_description",map.get( "product_description" ));
//                mapProduct.put("product_attribute",map.get( "product_attribute" ));
//                mapProduct.put("stock",map.get("stock"));
//                mapProduct.put("price",pr);
//                mapProduct.put("mrp",map.get("mrp"));;
//                mapProduct.put( "unit_price",map.get("unit_price") );
//                mapProduct.put("unit_value",map.get( "unit_value" ));
//                mapProduct.put("unit",map.get("unit"));
//                mapProduct.put("rewards",map.get("rewards"));
//                mapProduct.put("increment",map.get("increment"));
//                mapProduct.put("title",map.get( "title" ));
//
////
////                Toast.makeText(activity,"id- "+map.get("product_id")+"\n img- "+map.get("product_image")+"\n cat_id- "+map.get("category_id")+"\n" +
////                        "\n name- "+map.get("product_name")+"\n price- "+pr+"\n unit_price- "+map.get("unit_price")+
////                        "\n size- "+ map.get("size")+"\n col- "+ map.get("color")+"rew- "+ map.get("rewards")+"unit_value- "+ map.get("unit_value")+
////                        "unit- "+map.get("unit")+"\n inc- "+map.get("increament")+"stock- "+map.get("stock")+"title- "+map.get("title"),Toast.LENGTH_LONG).show();
//
//                boolean update_cart=db_cart.setCart(mapProduct,qt);
//                if(update_cart==true)
//                {
//                    Toast.makeText(activity,"Qty Not Updated",Toast.LENGTH_LONG).show();
//
//                }
//                else
//                {
//                    Toast.makeText(activity,"Qty Updated",Toast.LENGTH_LONG).show();
//                    Cart_fragment.tv_total.setText(activity.getResources().getString(R.string.currency)+" "+db_cart.getTotalAmount());
//                }
             //  holder.tv_total.setText(""+db_cart.getTotalAmount());

            }
        });
//
//


        holder.tv_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cnt=db_cart.getCartCount();
                Toast.makeText(activity,"id- "+map.get("product_id")+"\n img- "+map.get("product_image")+"\n cat_id- "+map.get("category_id")+"\n" +
                        "\n name- "+map.get("product_name")+"\n price- "+map.get("price")+"\n unit_price- "+map.get("unit_price")+
                        "\n size- "+ map.get("size")+"\n col- "+ map.get("color")+"rew- "+ map.get("rewards")+"unit_value- "+ map.get("unit_value")+
                   "unit- "+map.get("unit")+"\n inc- "+map.get("increament")+"stock- "+map.get("stock")+"title- "+map.get("title")+"cnt- "+cnt,Toast.LENGTH_LONG).show();


            }
        });


//       holder.tv_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                db_cart.setCart(map, Float.valueOf(holder.tv_contetiy.getText().toString()));
//
//                Double items = Double.parseDouble(db_cart.getInCartItemQty(map.get("product_id")));
//                Double price = Double.parseDouble(map.get("price"));
//                Double reward = Double.parseDouble(map.get("rewards"));
//                holder.tv_total.setText("" + price * qty);
//                holder.tv_reward.setText("" + reward * qty);
//             // holder.tv_total.setText(activity.getResources().getString(R.string.tv_cart_total) + price * items + " " + activity.getResources().getString(R.string.currency));
//                updateintent();
//            }
//        });
        holder.card_cart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();


                // details_product_attribute=bundle.getString("product_attribute");



                args.putString("cat_id",map.get( "cat_id" ));
                args.putString("product_id",map.get( "product_id" ));
                args.putString("product_image",map.get( "product_image" ));
                args.putString("product_name",map.get("product_name"));
                args.putString("product_description",map.get( "product_description" ));
                args.putString("product_attribute",map.get( "product_attribute" ));
                args.putString("product_price",map.get("product_price"));
                args.putString("stock",map.get("stock"));
                args.putString("price",map.get("price"));
                args.putString("mrp",map.get("mrp"));
                args.putString("unit_value",map.get( "unit_value" ));
                args.putString("unit",map.get("unit"));
                args.putString("rewards",map.get("rewards"));
                args.putString("increment",map.get("increment"));
                args.putString("title",map.get( "title" ));
                // Toast.makeText(getActivity(),""+getid,Toast.LENGTH_LONG).show();
                Details_Fragment fm = new Details_Fragment();
                fm.setArguments(args);
//                FragmentManager fragmentManager = .beginTransaction().replace(R.id.contentPanel, fm)
//                        .addToBackStack(null).commit();

                AppCompatActivity activity=(AppCompatActivity) view.getContext();
                activity.getFragmentManager().beginTransaction().replace(R.id.contentPanel,fm)
                        .addToBackStack(null)
                        .commit();
            }
        } );

        holder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    db_cart.removeItemFromCart(map.get("cart_id"));

                list.remove(position);
                notifyDataSetChanged();

                updateintent();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        public TextView tv_title, tv_price,  tv_total,tv_contetiy,
                tv_unit, tv_unit_value,tv_product_discout ,tv_mrp;
        CardView card_cart ;
        //tv_reward,
       // ElegantNumberButton btnQty;
        public ImageView iv_logo,iv_plus,iv_minus, iv_remove;

        public ProductHolder(View view) {
            super(view);

            tv_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            tv_price = (TextView) view.findViewById(R.id.tv_subcat_price);
            tv_total = (TextView) view.findViewById(R.id.tv_subcat_total);
           // tv_reward = (TextView) view.findViewById(R.id.tv_reward_point);
            tv_contetiy = (TextView) view.findViewById(R.id.tv_subcat_contetiy);
            tv_product_discout = (TextView) view.findViewById(R.id.product_dis);
            tv_mrp =(TextView)view.findViewById( R.id.product_mrp );
            //tv_add = (TextView) view.findViewById(R.id.tv_subcat_add);
          //  btnQty=(ElegantNumberButton)view.findViewById(R.id.product_qty);
            iv_logo = (ImageView) view.findViewById(R.id.iv_subcat_img);
            iv_plus = (ImageView) view.findViewById(R.id.iv_subcat_plus);
            iv_minus = (ImageView) view.findViewById(R.id.iv_subcat_minus);
            iv_remove = (ImageView) view.findViewById(R.id.iv_subcat_remove);
            card_cart =(CardView)view.findViewById( R.id.card_view );

            //tv_add.setText(R.string.tv_pro_update);

        }
    }

    private void updateintent() {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "update");
        activity.sendBroadcast(updates);
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

}

