package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import Fragment.*;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Config.BaseURL;
import Model.ProductVariantModel;
import Model.Wish_model;
import Module.Module;
import trolley.tcc.R;
import util.DatabaseCartHandler;
import util.WishlistHandler;

import static android.content.Context.MODE_PRIVATE;

public class Wishlist_Adapter extends RecyclerView.Adapter<Wishlist_Adapter.WishHolder> {

    String atr_id="";
    String atr_product_id="";
    String attribute_name="";
    String attribute_value="";
    String attribute_mrp="";
    Module module=new Module();
    ArrayList<ProductVariantModel> variantList;
    ArrayList<ProductVariantModel> attributeList;
    ProductVariantAdapter productVariantAdapter;
   ArrayList<HashMap<String, String>> list;
    private List<Wish_model> wishList;
    Activity activity;
    String Reward;
    int status=0;

    Double price ,reward ;
    SharedPreferences preferences;
    String language;
    float qty = 1;
   WishlistHandler db_wish ;
    DatabaseCartHandler db_cart;
    String product_id ;
  String cat_id,product_images,details_product_name,details_product_desc,details_product_inStock,details_product_attribute;
    String details_product_price,details_product_mrp,details_product_unit_value,details_product_unit_price,details_product_unit,details_product_rewards,details_product_increament,details_product_title;

    public Wishlist_Adapter(ArrayList<HashMap<String, String>> list, Activity activity) {
        this.list = list;
        this.activity = activity;
        db_cart = new DatabaseCartHandler(activity);
    }



    @Override
    public WishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate( R.layout.row_wishlist, parent, false);
        return new Wishlist_Adapter.WishHolder( view );
    }

    @Override
    public void onBindViewHolder(final WishHolder holder, final int position) {
//        final Product_model mList = modelList.get(position);
        final HashMap<String, String> map = list.get(position);

        String img_array=map.get("product_image");
        String img_name = null;
        try {
            JSONArray array=new JSONArray(img_array);
            img_name=array.get(0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Glide.with(activity)
                .load( BaseURL.IMG_PRODUCT_URL + img_name)
                .centerCrop()
                .placeholder(R.drawable.icon)
                .crossFade()
                .diskCacheStrategy( DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.iv_icon);
        preferences = activity.getSharedPreferences("lan", MODE_PRIVATE);
        language=preferences.getString("language","");
        holder.product_name.setText( map.get( "product_name" ));
        holder.txtdesc.setText( map.get( "product_description" ) );
        String p=String.valueOf(map.get("price"));
        String m=String.valueOf(map.get("mrp"));
        int discount=getDiscount(p,m);
        //Toast.makeText(getActivity(),""+atr,Toast.LENGTH_LONG).show();
        holder.discount.setText(""+discount+"% OFF");


        holder.product_mrp.setPaintFlags( holder.product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
      //  holder.discount.setText(map.get( "discount" ));
//        int items = Integer.parseInt(db_wish.getInWishtableItemQty( map.get("product_id")));

        String atr=String.valueOf(map.get("product_attribute"));


            status=1;
//
            holder.product_price.setText(activity.getResources().getString(R.string.currency)+ map.get("price"));
            holder.product_mrp.setText(activity.getResources().getString(R.string.currency)+map.get("mrp"));

            holder.txtrate.setText(map.get("unit_value")+" "+map.get("unit"));
             discount=getDiscount(p,m);
            //Toast.makeText(getActivity(),""+atr,Toast.LENGTH_LONG).show();
            holder.discount.setText(""+Math.round(discount)+"% OFF");

//        else
//        {
//           holder.rel_variant.setVisibility(View.VISIBLE);
//            status=2;
//            attributeList.clear();
//       //     String atr=String.valueOf(mList.getProduct_attribute());
//            JSONArray jsonArr = null;
//            try {
//
//                jsonArr = new JSONArray(atr);
//                for (int i = 0; i < jsonArr.length(); i++)
//                {
//                    ProductVariantModel model=new ProductVariantModel();
//                    JSONObject jsonObj = jsonArr.getJSONObject(i);
//                    String atrid=jsonObj.getString("id");
//                    String atrproductid=jsonObj.getString("product_id");
//                    String attributename=jsonObj.getString("attribute_name");
//                    String attributevalue=jsonObj.getString("attribute_value");
//                    String attributemrp=jsonObj.getString("attribute_mrp");
//
//
//                    model.setId(atrid);
//                    model.setProduct_id(atrproductid);
//                    model.setAttribute_value(attributevalue);
//                    model.setAttribute_name(attributename);
//                    model.setAttribute_mrp(attributemrp);
//
//                    attributeList.add(model);
//
//                    //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));
//
//                    //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();
//                }
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            try
//            {
//
//
//
//
//                atr_id=attributeList.get(0).getId();
//                atr_product_id=attributeList.get(0).getProduct_id();
//                attribute_name=attributeList.get(0).getAttribute_name();
//                attribute_value=attributeList.get(0).getAttribute_value();
//                attribute_mrp=attributeList.get(0).getAttribute_mrp();
//
//
//
//                //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));
//
//                //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();
//
//
//
//                String atr_price=String.valueOf(attribute_value);
//                String atr_mrp=String.valueOf(attribute_mrp);
//                int atr_dis=getDiscount(atr_price,atr_mrp);
//                holder.product_price.setText("\u20B9"+attribute_value.toString());
//                holder.product_mrp.setText("\u20B9"+attribute_mrp.toString());
//                holder.dialog_txtId.setText(atr_id.toString()+"@"+"0");
//                holder.dialog_unit_type.setText("\u20B9"+attribute_value+"/"+attribute_name);
//                holder.dialog_txtVar.setText(attribute_value+"@"+attribute_name+"@"+attribute_mrp);
//                //  holder.txtTotal.setText("\u20B9"+String.valueOf(list_atr_value.get(0).toString()));
//                holder.discount.setText(""+atr_dis+"% OFF");
//            }
//            catch (Exception ex)
//            {
//                ex.printStackTrace();
//            }
//
//
//        }

         String product_id=String.valueOf(map.get("product_id"));
        if(atr.equals("[]"))
        {
            boolean st=db_cart.isInCart(product_id);
            if(st==true)
            {
//                holder.add.setVisibility(View.GONE);
//                holder.elegantNumberButton.setNumber(db_cart.getCartItemQty(product_id));
//                holder.elegantNumberButton.setVisibility(View.VISIBLE);
            }

        }
        else
        {
//            String str_id=holder.dialog_txtId.getText().toString();
//            String[] str=str_id.split("@");
//            String at_id=String.valueOf(str[0]);
//            boolean st=db_cart.isInCart(at_id);
//            if(st==true)
//            {
//                holder.add.setVisibility(View.GONE);
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


                final HashMap<String, String> map = list.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(activity);
                LayoutInflater layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row=layoutInflater.inflate(R.layout.dialog_vairant_layout,null);
                variantList.clear();
                String atr=String.valueOf(map.get("product_attribute"));
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
                productVariantAdapter=new ProductVariantAdapter(activity,variantList);
                //productVariantAdapter.notifyDataSetChanged();
                l1.setAdapter(productVariantAdapter);


                builder.setView(row);
                final AlertDialog ddlg=builder.create();
                ddlg.show();
                 l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                        holder.dialog_unit_type.setText("\u20B9"+variantList.get(i).getAttribute_value()+"/"+variantList.get(i).getAttribute_name());
                        holder.dialog_txtId.setText(variantList.get(i).getId()+"@"+i);
                        holder.dialog_txtVar.setText(variantList.get(i).getAttribute_value()+"@"+variantList.get(i).getAttribute_name()+"@"+variantList.get(i).getAttribute_mrp());
                        //    txtPer.setText(String.valueOf(df)+"% off");

                        holder.product_price.setText("\u20B9"+variantList.get(i).getAttribute_value().toString());
                        holder.product_mrp.setText("\u20B9"+variantList.get(i).getAttribute_mrp().toString());
                        String pr=String.valueOf(variantList.get(i).getAttribute_value());
                        String mr=String.valueOf(variantList.get(i).getAttribute_mrp());
                        int atr_dis=getDiscount(pr,mr);
                        holder.discount.setText(""+atr_dis+"% OFF");
                        String atr=String.valueOf(map.get("product_attribute"));
                        String product_id=String.valueOf(map.get("product_id"));
                        if(atr.equals("[]"))
                        {
                            boolean st=db_cart.isInCart(product_id);
                            if(st==true)
                            {
                                holder.add.setVisibility(View.GONE);
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
                                holder.add.setVisibility(View.GONE);
                                holder.elegantNumberButton.setNumber(db_cart.getCartItemQty(at_id));
                                holder.elegantNumberButton.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                holder.add.setVisibility(View.VISIBLE);

                                holder.elegantNumberButton.setVisibility(View.GONE);
                            }
                        }

                        ddlg.dismiss();
                    }
                });


            }
        });



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.db_wish.removeItemFromWishtable(map.get("product_id"));
                list.remove(position);
                notifyDataSetChanged();

                // db_cart.getCartAll()
                updateintent();
            }
        });
        holder.rel_wishlist.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();


               // details_product_attribute=bundle.getString("product_attribute");


                //args.putString("product_id",mList.getProduct_id());
                args.putString("cat_id",map.get( "cat_id" ));
                args.putString("product_id",map.get( "product_id" ));
                args.putString("product_image",map.get( "product_image" ));
                args.putString("product_name",map.get("product_name"));
                args.putString("product_description",map.get( "product_description" ));
                args.putString("product_attribute",map.get( "product_attribute" ));
                args.putString("stock",map.get("stock"));
                args.putString("price",map.get("price"));
                args.putString("mrp",map.get("mrp"));
                args.putString( "unit_price",map.get("unit_price") );
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
        holder.icon_cart.setOnClickListener( new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String product_id ;

                final HashMap<String, String> map = list.get(position);
                holder.add.setVisibility( View.INVISIBLE );
                cat_id=map.get("cat_id");
                product_id =map.get("product_id");
                product_images=map.get("product_image");
                details_product_name=map.get("product_name");
                details_product_desc=map.get("product_description");
                //     details_product_color=bundle.getString("product_color");
                details_product_inStock=map.get("stock");
                details_product_attribute=map.get("product_attribute");

                //   details_product_size=bundle.getString("product_size");
                details_product_unit_price =map.get("unit_price");
                details_product_price=map.get("price");
                details_product_mrp=map.get("mrp");
                details_product_unit_value=map.get("unit_value");
                details_product_unit=map.get("unit");
                details_product_rewards=map.get("rewards");
                details_product_increament=map.get("increment");
                details_product_title=map.get("title");
                float qty = 1;
                String atr = String.valueOf(details_product_attribute);
                if (atr.equals("[]")) {

//                    Module.setIntoCart(activity,product_id,product_id,product_images,cat_id,details_product_name,details_product_price,
//                            details_product_desc,details_product_rewards,details_product_price,details_product_unit_value,details_product_unit,details_product_increament,
//                            details_product_inStock,details_product_title,details_product_mrp,details_product_attribute,"p",qty);
                  //  txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

                }
                else {
                    //ProductVariantModel model=variantList.get(position);

//                    String str_id=holder.dialog_txtId.getText().toString();
//                    String s=dialog_txtVar.getText().toString();
//                    String[] st=s.split("@");
//                    String st0=String.valueOf(st[0]);
//                    String st1=String.valueOf(st[1]);
//                    String st2=String.valueOf(st[2]);
//                    String[] str=str_id.split("@");
//                    String at_id=String.valueOf(str[0]);
//                    int j=Integer.parseInt(String.valueOf(str[1]));
//
//                    Module.setIntoCart(getActivity(),at_id,product_id,product_images,cat_id,details_product_name,st0,
//                            details_product_desc,details_product_rewards,st0,details_product_unit_value,st1,details_product_increament,
//                            details_product_inStock,details_product_title,st2,details_product_attribute,"a",qty);
//                    txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

                }


//                    HashMap<String, String> mapProduct = new HashMap<String, String>();
//                    String unt=String.valueOf( map.get("unit_value")+" "+map.get("unit"));
//                    mapProduct.put("cat_id",map.get( "cat_id" ));
//                    mapProduct.put("product_id",map.get( "product_id" ));
//                    mapProduct.put("product_image",map.get( "product_image" ));
//                    mapProduct.put("product_name",map.get("product_name"));
//                    mapProduct.put("product_description",map.get( "product_description" ));
//                    mapProduct.put("product_attribute",map.get( "product_attribute" ));
//                    mapProduct.put("stock",map.get("stock"));
//                    mapProduct.put("price",map.get("price"));
//                    mapProduct.put("mrp",map.get("mrp"));;
//                    mapProduct.put( "unit_price",map.get("unit_price") );
//                    mapProduct.put("unit_value",map.get( "unit_value" ));
//                    mapProduct.put("unit",map.get("unit"));
//                    mapProduct.put("rewards",map.get("rewards"));
//                    mapProduct.put("increment",map.get("increment"));
//                    mapProduct.put("title",map.get( "title" ));
//
//                    try {
//
//                        boolean tr = db_cart.setCart(mapProduct, (float) 1 );
//                        if (tr == true) {
//                            MainActivity mainActivity = new MainActivity();
//                            mainActivity.setCartCounter("" + db_cart.getCartCount());
//
//                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
//                            Toast.makeText(activity, "Added to Cart", Toast.LENGTH_LONG).show();
//                            int n= db_cart.getCartCount();
//                            updateintent();
//
//
//                        }
//                        else if(tr==false)
//                        {
//                            Toast.makeText(activity,"cart updated",Toast.LENGTH_LONG).show();
//                        }
//
//                    } catch (Exception ex) {
//                        Toast.makeText(activity, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//
//                    //Toast.makeText(context,"1\n"+status+"\n"+modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    //ProductVariantModel model=variantList.get(position);
//
//                    String str_id=holder.dialog_txtId.getText().toString();
//                    String s=holder.dialog_txtVar.getText().toString();
//                    String[] st=s.split("@");
//                    String st0=String.valueOf(st[0]);
//                    String st1=String.valueOf(st[1]);
//                    String st2=String.valueOf(st[2]);
//                    String[] str=str_id.split("@");
//                    String at_id=String.valueOf(str[0]);
//                    int j=Integer.parseInt(String.valueOf(str[1]));
//                    //       Toast.makeText(context,""+str[0].toString()+"\n"+str[1].toString(),Toast.LENGTH_LONG).show();
//                    HashMap<String, String> mapProduct = new HashMap<String, String>();
//                    mapProduct.put("cart_id",at_id);
//                    mapProduct.put("product_id",map.get("product_id"));
//                    mapProduct.put("product_image",map.get("product_image"));
//                    mapProduct.put("cat_id",map.get("cat_id"));
//                    mapProduct.put("product_name",map.get("product_name"));
//                   // mapProduct.put("price", st0);
//                    mapProduct.put("unit_price",st0);
//                    mapProduct.put("unit",st1);
//                 //   mapProduct.put("mrp",st2);
//                    mapProduct.put("type","a");
//                   mapProduct.put("product_description",map.get( "product_description" ));
//                    mapProduct.put("product_attribute",map.get( "product_attribute" ));
//                    mapProduct.put("stock",map.get("stock"));
//                    mapProduct.put("price",map.get("price"));
//                    mapProduct.put("mrp",map.get("mrp"));;
//                    mapProduct.put( "unit_price",map.get("unit_price") );
//                    mapProduct.put("unit_value",map.get( "unit_value" ));
//                    mapProduct.put("unit",map.get("unit"));
//                    mapProduct.put("rewards",map.get("rewards"));
//                    mapProduct.put("increment",map.get("increment"));
//                    mapProduct.put("title",map.get( "title" ));
                    //  Toast.makeText(context,""+attributeList.get(j).getId()+"\n"+mapProduct,Toast.LENGTH_LONG).show();
//                    try {
//
//                        boolean tr = db_cart.setCart(mapProduct, (float) 1 );
//                        if (tr == true) {
//                            MainActivity mainActivity = new MainActivity();
//                            mainActivity.setCartCounter("" + db_cart.getCartCount());
//
//                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
//                            Toast.makeText(activity, "Added to Cart" +db_cart.getCartCount(), Toast.LENGTH_LONG).show();
//                            int n= db_cart.getCartCount();
//                            updateintent();
//
//
//                        }
//                        else if(tr==false)
//                        {
//                            Toast.makeText(activity,"cart updated",Toast.LENGTH_LONG).show();
//                        }
//
//                    } catch (Exception ex) {
//                        Toast.makeText(activity, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//

                holder.icon_cart.setColorFilter( ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

//                holder.add.setVisibility(View.GONE);
//                holder.elegantNumberButton.setVisibility(View.VISIBLE);
//                holder.elegantNumberButton.setNumber("1");
            }
        } );

//        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//            @Override
//            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                final HashMap<String, String> map = list.get(position);
//                String atr = String.valueOf(map.get("product_attribute"));
//                if(newValue<=0)
//                {
//                    String p_id=String.valueOf(map.get("product_id"));
//                    boolean st=checkAttributeStatus(atr);
//                    if(st==false)
//                    {
//                        db_cart.removeItemFromCart(p_id);
//                    }
//                    else if(st==true)
//                    {
//
//                        String str_id = holder.dialog_txtId.getText().toString();
//                        String[] str = str_id.split("@");
//                        String at_id = String.valueOf(str[0]);
//                        db_cart.removeItemFromCart(at_id);
//                    }
//
//                    holder.elegantNumberButton.setVisibility(View.GONE);
//                    holder.add.setVisibility(View.VISIBLE);
//                }
//                else {
//
//
//
//                    float qty = Float.parseFloat(String.valueOf(newValue));
//
//                    //String atr=String.valueOf(modelList.get(position).getProduct_attribute());
//                    if (atr.equals("[]")) {
//                        double pr = Double.parseDouble(map.get("price"));
//                        double amt = pr * qty;
//                        HashMap<String, String> mapProduct = new HashMap<String, String>();
//
//                        String unt = String.valueOf(map.get("unit_value") + " " + map.get("unit"));
//                        mapProduct.put("cart_id", map.get("product_id"));
//                        mapProduct.put("product_id", map.get("product_id"));
//                        mapProduct.put("product_image", map.get("product_images"));
//                        mapProduct.put("cat_id", map.get("cat_id"));
//                        mapProduct.put("product_name", map.get("product_name"));
//                        mapProduct.put("price", map.get("price"));
//                        mapProduct.put("unit_price", map.get("price"));
//                        mapProduct.put("unit", unt);
//                        mapProduct.put("mrp", map.get("mrp"));
//                        mapProduct.put("type", "p");
//                        try {
//
//                            boolean tr = db_cart.setCart(mapProduct, qty);
//                            if (tr == true) {
//                                MainActivity mainActivity = new MainActivity();
//                                mainActivity.setCartCounter("" + db_cart.getCartCount());
//
//                                //   context.setCartCounter("" + holder.db_cart.getCartCount());
//                                Toast.makeText(activity, "Added to Cart", Toast.LENGTH_LONG).show();
//                                int n = db_cart.getCartCount();
//                                updateintent();
//
//
//                            } else if (tr == false) {
//                                Toast.makeText(activity, "cart updated", Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (Exception ex) {
//                            Toast.makeText(activity, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//
//                        //Toast.makeText(context,"1\n"+status+"\n"+modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
//                    } else {
//                        //ProductVariantModel model=variantList.get(position);
//
//                        String str_id = holder.dialog_txtId.getText().toString();
//
//
//                        String s = holder.dialog_txtVar.getText().toString();
//                        String[] st = s.split("@");
//                        String st0 = String.valueOf(st[0]);
//                        String st1 = String.valueOf(st[1]);
//                        String st2 = String.valueOf(st[2]);
//                        String[] str = str_id.split("@");
//                        String at_id = String.valueOf(str[0]);
//                        int k = Integer.parseInt(String.valueOf(str[1]));
//                        double pr = Double.parseDouble(st0);
//                        double amt = pr * qty;
//                        //       Toast.makeText(context,""+str[0].toString()+"\n"+str[1].toString(),Toast.LENGTH_LONG).show();
//                        HashMap<String, String> mapProduct = new HashMap<String, String>();
//                        mapProduct.put("cart_id", at_id);
//                        mapProduct.put("product_id", map.get("product_id"));
//                        mapProduct.put("product_image", map.get("product_images"));
//                        mapProduct.put("cat_id", map.get("cat_id"));
//                        mapProduct.put("product_name", map.get("product_name"));
//                        mapProduct.put("price", st0);
//                        mapProduct.put("unit_price", st0);
//                        mapProduct.put("unit", st1);
//                        mapProduct.put("mrp", st2);
//                        mapProduct.put("type", "a");
//                        //  Toast.makeText(context,""+attributeList.get(j).getId()+"\n"+mapProduct,Toast.LENGTH_LONG).show();
//                        try {
//
//                            boolean tr = db_cart.setCart(mapProduct, qty);
//                            if (tr == true) {
//                                MainActivity mainActivity = new MainActivity();
//                                mainActivity.setCartCounter("" + db_cart.getCartCount());
//
//                                //   context.setCartCounter("" + holder.db_cart.getCartCount());
//                                Toast.makeText(activity, "Added to Cart", Toast.LENGTH_LONG).show();
//                                int n = db_cart.getCartCount();
//                                updateintent();
//
//
//                            } else if (tr == false) {
//                                Toast.makeText(activity, "cart updated", Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (Exception ex) {
//                            Toast.makeText(activity, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//                }
//
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WishHolder extends RecyclerView.ViewHolder {
        public WishlistHandler db_wish;
        TextView product_name ,product_price ,product_mrp ,unit_type ,discount;
        RelativeLayout varient , rel_wishlist ;
        ImageView iv_icon , delete ,icon_cart ;
        CardView card_wishlist ;
        Button add ;
        private TextView dialog_unit_type,dialog_txtId,dialog_txtVar;
        TextView txtrate ,txtdesc;
        RelativeLayout rel_variant;
        DatabaseCartHandler db_cart ;
        ElegantNumberButton elegantNumberButton ;

        public WishHolder(View itemView) {
            super( itemView );
            product_name = (TextView)itemView.findViewById( R.id.product_name );
            product_price=(TextView)itemView.findViewById( R.id.product_prize );
            rel_wishlist=(RelativeLayout)itemView.findViewById( R.id.rel_wish );
            product_mrp=(TextView)itemView.findViewById( R.id.product_mrp );
            unit_type=(TextView)itemView.findViewById( R.id.unit_type );
            add=itemView.findViewById( R.id.btn_add );
            iv_icon=(ImageView)itemView.findViewById( R.id.iv_icon );
            delete=(ImageView)itemView.findViewById( R.id.delete );
            varient= itemView.findViewById( R.id.varient );
            txtrate=(TextView)itemView.findViewById(R.id.single_varient);
            db_cart=new DatabaseCartHandler(activity);
            rel_variant=(RelativeLayout)itemView.findViewById(R.id.rel_variant);
            discount=itemView.findViewById( R.id.dis );
            dialog_unit_type=(TextView)itemView.findViewById(R.id.unit_type);
            dialog_txtId=(TextView)itemView.findViewById(R.id.txtId);
            dialog_txtVar=(TextView)itemView.findViewById(R.id.txtVar);
            elegantNumberButton =(ElegantNumberButton)itemView.findViewById( R.id.elegantButton );
            txtdesc = (TextView)itemView.findViewById( R.id.txtDesc );
            icon_cart =(ImageView)itemView.findViewById( R.id.icon_cart );
            db_wish = new WishlistHandler( activity );

            attributeList=new ArrayList<>();
            variantList=new ArrayList<>();
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
