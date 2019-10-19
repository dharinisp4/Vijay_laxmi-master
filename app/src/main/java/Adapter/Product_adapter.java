
package Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import Fragment.*;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import Model.Product_model;
import Module.Module;
import trolley.tcc.MainActivity;
import trolley.tcc.R;
import util.DatabaseCartHandler;
import util.DatabaseHandler;
import util.WishlistHandler;

import static android.content.Context.MODE_PRIVATE;


public class Product_adapter extends RecyclerView.Adapter<Product_adapter.MyViewHolder> {

    List<String> image_list;
   // Dialog dialog;
    ListView listView1;
    String atr_id="";
    String atr_product_id="";
    String attribute_name="";
    String attribute_value="";
    String attribute_mrp="";
    Module module=new Module();
    ArrayList<ProductVariantModel> variantList;
    ArrayList<ProductVariantModel> attributeList;
    ProductVariantAdapter productVariantAdapter;
    String cat_id,product_id,product_images,details_product_name,details_product_desc,details_product_inStock,details_product_attribute;
    String details_product_price,details_product_mrp,details_product_unit_value,details_product_unit_price,details_product_unit,details_product_rewards,details_product_increament,details_product_title;
    //    ArrayList<String> list;
//    ArrayList<String> list_id;
//    ArrayList<String> list_atr_value;
//    ArrayList<String> list_atr_name;
//    ArrayList<String> list_atr_mrp;

    private List<Product_model> modelList;
    private Context context;
    int status=0;
    private DatabaseHandler dbcart;
    private DatabaseCartHandler db_cart;
    Activity activity ;
    String language;
SharedPreferences preferences;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_title, tv_price,product_mrp ,discount;
        public ImageView iv_logo, wish_before ,wish_after ,icon_cart;
        public ConstraintLayout con_product;
        public RelativeLayout rel_variant;
        TextView txtrate ,txt_desc;
        ElegantNumberButton elegantNumberButton;
        private TextView dialog_unit_type,dialog_txtId,dialog_txtVar;
        Button add ;
        public Double reward;
       WishlistHandler db_wish;

        RelativeLayout rel_click;

        public MyViewHolder(View view) {
            super(view);

            tv_title = (TextView) view.findViewById(R.id.tv_subcat_title);
            tv_price = (TextView) view.findViewById(R.id.tv_subcat_price);
           // tv_reward = (TextView) view.findViewById(R.id.tv_reward_point);
          //  tv_total = (TextView) view.findViewById(R.id.tv_subcat_total);
            product_mrp = (TextView) view.findViewById(R.id.product_mrp);
            discount= view.findViewById( R.id.dis );
            rel_click=view.findViewById(R.id.rel_click);
            add = view.findViewById( R.id.btn_add );
            wish_after=view.findViewById( R.id.wish_after );
            wish_before=view.findViewById( R.id.wish_before );
            icon_cart = view .findViewById( R.id.icon_cart );
            iv_logo = (ImageView) view.findViewById(R.id.iv_subcat_img);
            rel_variant=(RelativeLayout)view.findViewById(R.id.rel_variant);
            dialog_unit_type=(TextView)view.findViewById(R.id.unit_type);
            dialog_txtId=(TextView)view.findViewById(R.id.txtId);
            dialog_txtVar=(TextView)view.findViewById(R.id.txtVar);
            txtrate=(TextView)view.findViewById(R.id.single_varient);
            txt_desc =(TextView)view.findViewById( R.id.txt_desc );
            con_product=(ConstraintLayout)view.findViewById(R.id.con_layout_product);
            elegantNumberButton=view.findViewById( R.id.elegantButton );
            image_list=new ArrayList<>();
           // iv_remove.setVisibility(View.GONE);
       //     iv_minus.setOnClickListener(this);
       //     iv_plus.setOnClickListener(this);
            //tv_add.setOnClickListener(this);
//            iv_logo.setOnClickListener(this);
            variantList=new ArrayList<>();
            attributeList=new ArrayList<>();


            wish_before.setOnClickListener(this);
            wish_after.setOnClickListener(this );
            rel_click.setOnClickListener(this);
          //  add.setOnClickListener( this );
            rel_variant.setOnClickListener(this);
       //     elegantNumberButton.setOnClickListener( this );
            db_wish = new WishlistHandler( context );

            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            final int position = getAdapterPosition();




                if(id==R.id.rel_click)
            {
              //  Toast .makeText(context ,"attribute " + "\n" +modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
                Details_Fragment details_fragment=new Details_Fragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Bundle args = new Bundle();

                args.putString("cat_id", modelList.get(position).getCategory_id());
                args.putString("product_id",modelList.get(position).getProduct_id());
                args.putString("product_image",modelList.get(position).getProduct_image());
                args.putString("product_name",modelList.get(position).getProduct_name());
                args.putString("product_description",modelList.get(position).getProduct_description());
                args.putString("stock",modelList.get(position).getIn_stock());
//                args.putString("product_size",modelList.get(position).getSize());
                args.putString("product_color",modelList.get( position).getColor());
                args.putString("price",modelList.get(position).getPrice());
                args.putString("mrp",modelList.get(position).getMrp());
                args.putString( "unit_price",modelList.get( position ).getPrice());
                args.putString("unit_value",modelList.get(position).getUnit_value());
                args.putString("unit",modelList.get(position).getUnit());
                args.putString("product_attribute",modelList.get(position).getProduct_attribute());
                args.putString("rewards",modelList.get(position).getRewards());
                args.putString("increment",modelList.get(position).getIncreament());
                args.putString("title",modelList.get(position).getTitle());
                details_fragment.setArguments(args);


                FragmentManager fragmentManager=activity.getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel,details_fragment)

                        .addToBackStack(null).commit();

            }



            else if(id==R.id.wish_before) {
                    final Product_model mList = modelList.get(position);
                //    txt_desc.setText(""+);
                    //Toast.makeText(activity,""+mList.getProduct_attribute(),Toast.LENGTH_LONG).show();
                    wish_after.setVisibility( View.VISIBLE );
                    wish_before.setVisibility( View.INVISIBLE );
                    HashMap<String, String> mapProduct = new HashMap<String, String>();
                    mapProduct.put("product_id", mList.getProduct_id());
                    mapProduct.put("product_image",mList.getProduct_image());
                    mapProduct.put("cat_id",mList.getCategory_id());
                    mapProduct.put("product_name",mList.getProduct_name());
                    mapProduct.put("price", mList.getPrice());
                    mapProduct.put("product_description",mList.getProduct_description());
                    mapProduct.put("rewards", mList.getRewards());
                    mapProduct.put("unit_price",mList.getPrice());
                    mapProduct.put("unit_value",mList.getUnit_value());
                    mapProduct.put("unit", mList.getUnit());
                    mapProduct.put("increment",mList.getIncreament());
                    mapProduct.put("stock",mList.getStock());
                    mapProduct.put("title",mList.getTitle());
                    mapProduct.put("mrp",mList.getMrp());
                    mapProduct.put("product_attribute",modelList.get(position).getProduct_attribute());
               //    mapProduct.put("product_attribute",mList.getProduct_attribute());

                       // Toast.makeText(context,""+mapProduct,Toast.LENGTH_LONG).show();


                    try {

                        boolean tr =db_wish.setwishTable(mapProduct);
                        if (tr == true) {

                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
                            Toast.makeText(context, "Added to Wishlist", Toast.LENGTH_LONG).show();



                        }
                        else
                        {
                            Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                      //  Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }


                    //   Toast.makeText(context,"wish",Toast.LENGTH_LONG).show();
                              //  AppCompatActivity activity = (AppCompatActivity) view.getContext();

            }
            else if (id == R.id.wish_after) {
                    final Product_model mList = modelList.get(position);
                wish_after.setVisibility( View.INVISIBLE );
                wish_before.setVisibility( View.VISIBLE );
               db_wish.removeItemFromWishtable(mList.getProduct_id());

               Toast.makeText(context, "removed from Wishlist", Toast.LENGTH_LONG).show();
               // list.remove(position);
              notifyDataSetChanged();

            }


        }
    }

    public Product_adapter(List<Product_model> modelList, Context context) {
        this.modelList = modelList;
        dbcart = new DatabaseHandler(context);
        db_cart=new DatabaseCartHandler(context);
    }

    @Override
    public Product_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_product_rv, parent, false);
        context = parent.getContext();
        return new Product_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Product_adapter.MyViewHolder holder, final int position) {
        final Product_model mList = modelList.get(position);
        final String getid = mList.getProduct_id();


        holder.product_mrp.setPaintFlags( holder.product_mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        String atr=mList.getProduct_attribute();
        if(atr.equals("[]") )
        {
            holder.tv_price.setText("\u20B9" +mList.getPrice());
            holder.product_mrp.setText("\u20B9"+mList.getMrp());
            Double price = Double.valueOf(mList.getPrice());
            Double mrp = Double.valueOf(mList.getMrp());
            if (mrp > price)
            {
                holder.discount.setVisibility(View.VISIBLE);
                int discount = getDiscount(mList.getPrice(),mList.getMrp());
              //  holder.discount.setText( mList.getPrice()+ "&"+mList.getMrp() );
                holder.discount.setText(Math.round(discount)+"%" + "off");
            }
            else
                holder.discount.setVisibility(View.GONE);
        }
        else
        {
            List<ProductVariantModel> variantModels=module.getAttribute(atr);
            holder.tv_price.setText("\u20B9" +variantModels.get(0).getAttribute_value());
            holder.product_mrp.setText("\u20B9" +variantModels.get(0).getAttribute_mrp());
            Double price = Double.valueOf(variantModels.get(0).getAttribute_value());
            Double mrp = Double.valueOf(variantModels.get(0).getAttribute_mrp());
            if (mrp > price)
            {
                holder.discount.setVisibility(View.VISIBLE);
              int discount = getDiscount(variantModels.get(0).getAttribute_value(),variantModels.get(0).getAttribute_mrp());
              //  holder.discount.setText( mList.getPrice()+ "&"+mList.getMrp() );
               holder.discount.setText(Math.round(discount)+"%" + "off");
            }
            else
                holder.discount.setVisibility(View.GONE);
          //  holder.product_mrp.setText(variantModels.get(0).getAttribute_image().get(0).toString());

        }






        if(holder.db_wish.isInWishtable( getid ))
        {
            holder.wish_after.setVisibility( View.VISIBLE );
            holder.wish_before.setVisibility( View.GONE );
        }
        if(db_cart.isInCart( getid ))
        {
            holder.icon_cart.setColorFilter( ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
           holder.icon_cart.setEnabled( false );

            holder.icon_cart.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Product_model mList = modelList.get(position);

                    db_cart.removeItemFromCart(mList.getProduct_id());
                    holder.icon_cart.setColorFilter( ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

                    Toast.makeText(context, "already in cart", Toast.LENGTH_LONG).show();
                    // list.remove(position);
                    notifyDataSetChanged();
                }
            } );
        }
        else
        {
            holder.icon_cart.setEnabled( true );
        }


        try
        {
            image_list.clear();
            JSONArray array=new JSONArray(mList.getProduct_image());
            //Toast.makeText(this,""+product_images,Toast.LENGTH_LONG).show();
            if(mList.getProduct_image().equals(null))
            {
                Glide.with(context)
                        .load(BaseURL.IMG_PRODUCT_URL +mList.getProduct_image() )
                        .centerCrop()
                        .placeholder(R.drawable.icon)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(holder.iv_logo);
            }
            else
            {
                String st_atr=mList.getProduct_attribute().toString();
                if(st_atr.equals("[]"))
                {
                    for(int i=0; i<=array.length()-1;i++)
                    {
                        image_list.add(array.get(i).toString());

                    }


                    Glide.with(context)
                            .load(BaseURL.IMG_PRODUCT_URL +image_list.get(0) )
                            // .centerCrop()
                            .placeholder(R.drawable.icon)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .into(holder.iv_logo);
                }
                else
                {
                    List<ProductVariantModel> variantModels=module.getAttribute(atr);

                    String img=variantModels.get(0).getAttribute_image();

                    String img_f=getAttrFstImage(img);


                    Glide.with(context)
                            .load(BaseURL.IMG_PRODUCT_URL +img_f.toString() )
                             .fitCenter()
                            .placeholder(R.drawable.icon)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .into(holder.iv_logo);
                }

            }


            // Toast.makeText(Product_Frag_details.this,""+image_list.toString(),Toast.LENGTH_LONG).show();

        }
        catch (Exception ex)
        {
            // Toast.makeText(Product_Frag_details.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
//


        preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
        language=preferences.getString("language","");
        if (language.contains("english")) {
            holder.tv_title.setText(mList.getProduct_name());
        }
        else {
            holder.tv_title.setText(mList.getProduct_name());


        }


        holder.txt_desc.setText( mList.getProduct_description() );
      //  holder.txt_desc.setText( mList.getProduct_attribute().get(0).getAttribute_color() );

//        String atr=String.valueOf(mList.getProduct_attribute());
//        if(atr.equals(null))
//        {
//
//
//            status=1;
//            String p=String.valueOf(mList.getPrice());
//            String m=String.valueOf(mList.getMrp());
//            holder.tv_price.setText(context.getResources().getString(R.string.currency)+ mList.getPrice());
//            holder.product_mrp.setText(context.getResources().getString(R.string.currency)+mList.getMrp());
//            holder.txtrate.setVisibility(View.VISIBLE);
//            holder.txtrate.setText(mList.getUnit_value()+" "+mList.getUnit());
//            int discount=getDiscount(p,m);
//            //Toast.makeText(getActivity(),""+atr,Toast.LENGTH_LONG).show();
//            if(discount>0)
//            {
//            holder.discount.setText(""+discount+"% OFF");}
//            else {
//                holder.discount.setVisibility( View.GONE );
//            }
//        }
//
//        else
//        {
//            holder.rel_variant.setVisibility(View.VISIBLE);
//            status=2;
//        attributeList.clear();
////            String atr=String.valueOf(mList.getProduct_attribute());
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
//


                    //     arrayList.add(new AttributeModel(atr_id,product_id,attribute_name,attribute_value));

                    //Toast.makeText(getActivity(),"id "+atr_id+"\n p_id "+product_id+"\n atr_name "+attribute_name+"\n atr_value "+attribute_value,Toast.LENGTH_LONG).show();








        holder.icon_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Product_model mList=modelList.get(position);
                cat_id= mList.getCategory_id();
                product_id=mList.getProduct_id();
                product_images=mList.getProduct_image();
                details_product_name=mList.getProduct_name();
                details_product_desc=mList.getProduct_description();
                //     details_product_color=bundle.getString("product_color");
                details_product_inStock=mList.getIn_stock();
             details_product_attribute=mList.getProduct_attribute();

                //   details_product_size=bundle.getString("product_size");
                details_product_unit_price =mList.getUnit_price();
                details_product_price=mList.getPrice();
                details_product_mrp=mList.getMrp();
                details_product_unit_value=mList.getUnit_value();
                details_product_unit=mList.getUnit();
                details_product_rewards=mList.getRewards();
                details_product_increament=mList.getIncreament();
                details_product_title=mList.getTitle();
                float qty = 1;
                    String atr=String.valueOf(modelList.get(position).getProduct_attribute());
//
                      if (atr.equals("[]")) {

//                           Module.setIntoCart(activity,product_id,product_id,product_images,cat_id,details_product_name,details_product_price,
//                                   details_product_desc,details_product_rewards,details_product_price,details_product_unit_value,details_product_unit,details_product_increament,
//                                   details_product_inStock,details_product_title,details_product_mrp,details_product_attribute,"p",qty);
                          // txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

                       }
                       else {
                           //ProductVariantModel model=variantList.get(position);

                           String str_id=holder.dialog_txtId.getText().toString();
                           String s=holder.dialog_txtVar.getText().toString();
                           String[] st=s.split("@");
                           String st0=String.valueOf(st[0]);
                           String st1=String.valueOf(st[1]);
                           String st2=String.valueOf(st[2]);
                           String[] str=str_id.split("@");
                           String at_id=String.valueOf(str[0]);
                           int j=Integer.parseInt(String.valueOf(str[1]));

//                           Module.setIntoCart(activity,at_id,product_id,product_images,cat_id,details_product_name,st0,
//                                   details_product_desc,details_product_rewards,st0,details_product_unit_value,st1,details_product_increament,
//                                   details_product_inStock,details_product_title,st2,details_product_attribute,"a",qty);
                          // txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));
//
//                       }

//                        HashMap<String, String> mapProduct = new HashMap<String, String>();
//                      String unt=String.valueOf( mList.getUnit_value()+" "+mList.getUnit());
//                        mapProduct.put("product_id", mList.getProduct_id());
//                        mapProduct.put("product_image",mList.getProduct_image());
//                        mapProduct.put("cat_id",mList.getCategory_id());
//                        mapProduct.put("product_name",mList.getProduct_name());
//                        mapProduct.put("price", mList.getPrice());
//                        mapProduct.put("product_description",mList.getProduct_description());
//                        mapProduct.put("rewards", mList.getRewards());
//                        mapProduct.put("unit_price",mList.getUnit_price());
//                        mapProduct.put("unit_value",mList.getUnit_value());
//                        mapProduct.put("unit", mList.getUnit());
//                        mapProduct.put("increment",mList.getIncreament());
//                        mapProduct.put("stock",mList.getStock());
//                        mapProduct.put("title",mList.getTitle());
//                        mapProduct.put("mrp",mList.getMrp());
//                        mapProduct.put("product_attribute",mList.getProduct_attribute());
//
//                        try {
//
//                            boolean tr = db_cart.setCart(mapProduct, (float) 1 );
//                            if (tr == true) {
//                                MainActivity mainActivity = new MainActivity();
//                                mainActivity.setCartCounter("" + db_cart.getCartCount());
//
//                                //   context.setCartCounter("" + holder.db_cart.getCartCount());
//                                Toast.makeText(context, "Added to Cart" +db_cart.getCartCount(), Toast.LENGTH_LONG).show();
//                                int n= db_cart.getCartCount();
//                                updateintent();
//
//
//                            }
//                            else if(tr==false)
//                            {
//                                Toast.makeText(context,"cart updated",Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (Exception ex) {
//                            Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//
//                        //Toast.makeText(context,"1\n"+status+"\n"+modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        //ProductVariantModel model=variantList.get(position);
//
//                        String str_id=holder.dialog_txtId.getText().toString();
//                        String s=holder.dialog_txtVar.getText().toString();
//                        String[] st=s.split("@");
//                        String st0=String.valueOf(st[0]);
//                        String st1=String.valueOf(st[1]);
//                        String st2=String.valueOf(st[2]);
//                        String[] str=str_id.split("@");
//                        String at_id=String.valueOf(str[0]);
//                        int j=Integer.parseInt(String.valueOf(str[1]));
//                 //       Toast.makeText(context,""+str[0].toString()+"\n"+str[1].toString(),Toast.LENGTH_LONG).show();
//                        HashMap<String, String> mapProduct = new HashMap<String, String>();
//                        mapProduct.put("product_id", mList.getProduct_id());
//                        mapProduct.put("product_image",mList.getProduct_image());
//                        mapProduct.put("cat_id",mList.getCategory_id());
//                        mapProduct.put("product_name",mList.getProduct_name());
//                        mapProduct.put("price", mList.getPrice());
//                        mapProduct.put("product_description",mList.getProduct_description());
//                        mapProduct.put("rewards", mList.getRewards());
//                        mapProduct.put("unit_value",mList.getUnit_value());
//                        mapProduct.put("unit_price",mList.getUnit_price());
//                        mapProduct.put("unit", mList.getUnit());
//                        mapProduct.put("increment",mList.getIncreament());
//                        mapProduct.put("stock",mList.getStock());
//                        mapProduct.put("title",mList.getTitle());
//                        mapProduct.put("mrp",mList.getMrp());
//                        mapProduct.put("product_attribute",mList.getProduct_attribute());
//
//                        //  Toast.makeText(context,""+attributeList.get(j).getId()+"\n"+mapProduct,Toast.LENGTH_LONG).show();
//                        try {
//
//                            boolean tr = db_cart.setCart(mapProduct, (float) 1 );
//                            if (tr == true) {
//                                MainActivity mainActivity = new MainActivity();
//                                mainActivity.setCartCounter("" + db_cart.getCartCount());
//
//                                //   context.setCartCounter("" + holder.db_cart.getCartCount());
//                                Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();
//                                int n= db_cart.getCartCount();
//                                updateintent();
//
//
//                            }
//                            else if(tr==false)
//                            {
//                                Toast.makeText(context,"cart updated",Toast.LENGTH_LONG).show();
//                            }
//
//                        } catch (Exception ex) {
//                            Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
//                        }

                    }
                    holder.icon_cart.setColorFilter( ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                  //  holder.add.setVisibility(View.GONE);
                  //  holder.elegantNumberButton.setVisibility(View.VISIBLE);
           }
        });

        holder.elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                final Product_model mList = modelList.get(position);
                float qty = Float.parseFloat( String.valueOf( newValue ) );

                String atr=String.valueOf(modelList.get(position).getProduct_attribute());
                if(atr.equals("[]"))
                {
                    double pr=Double.parseDouble( mList.getPrice());
                    double amt=pr*qty;
                    HashMap<String, String> mapProduct = new HashMap<String, String>();
                    String unt=String.valueOf( mList.getUnit_value()+" "+mList.getUnit());
                    mapProduct.put("product_id", mList.getProduct_id());
                    mapProduct.put("product_image",mList.getProduct_image());
                    mapProduct.put("cat_id",mList.getCategory_id());
                    mapProduct.put("product_name",mList.getProduct_name());
                    mapProduct.put("price", mList.getPrice());
                    mapProduct.put("product_description",mList.getProduct_description());
                    mapProduct.put("rewards", mList.getRewards());
                    mapProduct.put("unit_value",mList.getUnit_value());
                    mapProduct.put("unit_price",mList.getUnit_price());
                    mapProduct.put("unit", mList.getUnit());
                    mapProduct.put("increment",mList.getIncreament());
                    mapProduct.put("stock",mList.getStock());
                    mapProduct.put("title",mList.getTitle());
                    mapProduct.put("mrp",mList.getMrp());
                   // mapProduct.put("product_attribute",mList.getProduct_attribute());

                    try {

                        boolean tr = db_cart.setCart(mapProduct, qty );
                        if (tr == true) {
                            MainActivity mainActivity = new MainActivity();
                            mainActivity.setCartCounter("" + db_cart.getCartCount());

                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
                            Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();
                            int n= db_cart.getCartCount();
                            updateintent();


                        }
                        else if(tr==false)
                        {
                            Toast.makeText(context,"cart updated",Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(context,"1\n"+status+"\n"+modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
                }
                else
                {
                    //ProductVariantModel model=variantList.get(position);

                    String str_id=holder.dialog_txtId.getText().toString();


                    String s=holder.dialog_txtVar.getText().toString();
                    String[] st=s.split("@");
                  String st0=String.valueOf(st[0]);
                  String st1=String.valueOf(st[1]);
                  String st2=String.valueOf(st[2]);
                    String[] str=str_id.split("@");
                    String at_id=String.valueOf(str[0]);
                    int k=Integer.parseInt(String.valueOf(str[1]));
                    double pr=Double.parseDouble(st0);
                    double amt=pr*qty;
                    //       Toast.makeText(context,""+str[0].toString()+"\n"+str[1].toString(),Toast.LENGTH_LONG).show();
                    HashMap<String, String> mapProduct = new HashMap<String, String>();
                    mapProduct.put("product_id", mList.getProduct_id());
                    mapProduct.put("product_image",mList.getProduct_image());
                    mapProduct.put("cat_id",mList.getCategory_id());
                    mapProduct.put("product_name",mList.getProduct_name());
                    mapProduct.put("price", mList.getPrice());
                    mapProduct.put("product_description",mList.getProduct_description());
                    mapProduct.put("rewards", mList.getRewards());
                    mapProduct.put("unit_value",mList.getUnit_value());
                    mapProduct.put("unit_price",mList.getUnit_price());
                    mapProduct.put("unit", mList.getUnit());
                    mapProduct.put("increment",mList.getIncreament());
                    mapProduct.put("stock",mList.getStock());
                    mapProduct.put("title",mList.getTitle());
                    mapProduct.put("mrp",mList.getMrp());
                  //  mapProduct.put("product_attribute",mList.getProduct_attribute());

                    //  Toast.makeText(context,""+attributeList.get(j).getId()+"\n"+mapProduct,Toast.LENGTH_LONG).show();
                    try {

                        boolean tr = db_cart.setCart(mapProduct, qty );
                        if (tr == true) {
                            MainActivity mainActivity = new MainActivity();
                            mainActivity.setCartCounter("" + db_cart.getCartCount());

                            //   context.setCartCounter("" + holder.db_cart.getCartCount());
                            Toast.makeText(context, "Added to Cart", Toast.LENGTH_LONG).show();
                            int n= db_cart.getCartCount();
                            updateintent();


                        }
                        else if(tr==false)
                        {
                            Toast.makeText(context,"cart updated",Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

            }
        });


        //holder.tv_reward.setText(mList.getRewards());
        //holder.tv_price.setText(context.getResources().getString(R.string.currency)+ mList.getPrice()+" / "+ mList.getUnit_value()+" "+mList.getUnit());



//        holder.tv_price.setText(context.getResources().getString(R.string.tv_pro_price) + mList.getUnit_value() + " " +
//                mList.getUnit() +" \n"+ mList.getPrice()+ context.getResources().getString(R.string.currency));
//        Double items = Double.parseDouble(dbcart.getInCartItemQty(mList.getProduct_id()));
//        Double prices = Double.parseDouble(mList.getPrice());
//        Double reward = Double.parseDouble(mList.getRewards());
//        //holder.tv_total.setText("" + price * items);
     //  holder.tv_reward.setText("" + reward * items);




//       holder.tv_add.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//               int sd=db_cart.getCartCount();
//
//               Toast.makeText(context,""+sd,Toast.LENGTH_LONG).show();
//
//           }
//       });

//       holder.con_product.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//         int y=dbcart.getCartCount();
//          //     Toast.makeText(context,""+y,Toast.LENGTH_LONG).show();
//
//
//               Bundle args = new Bundle();
//
//               Intent intent=new Intent(context, Product_details.class);
//               args.putString("cat_id",mList.getCategory_id());
//               args.putString("product_id",mList.getProduct_id());
//               args.putString("product_images",mList.getProduct_image());
//
//               args.putString("product_name",mList.getProduct_name());
//               args.putString("product_description",mList.getProduct_description());
//               args.putString("product_in_stock",mList.getIn_stock());
//               args.putString("product_size",mList.getSize());
//               args.putString("product_color",mList.getColor());
//               args.putString("product_price",mList.getPrice());
//               args.putString("product_mrp",mList.getIncreament());
//               args.putString("product_unit_value",mList.getUnit_value());
//               args.putString("product_unit",mList.getUnit());
//               args.putString("product_rewards",mList.getRewards());
//               args.putString("product_increament",mList.getIncreament());
//               args.putString("product_title",mList.getTitle());
//
//               Product_Frag_details product_frag_details=new Product_Frag_details();
//
//               product_frag_details.setArguments(args);
//               AppCompatActivity activity=(AppCompatActivity) view.getContext();
//               activity.getSupportFragmentManager().beginTransaction().replace(R.id.contentPanel,product_frag_details)
//                       .addToBackStack(null)
//                       .commit();
//
//           }
//      });
    }

    private String getAttrFstImage(String img) {

        String st="";
        try
        {
            List<String> list_img=new ArrayList<>();
            JSONArray array=new JSONArray(img);
            for(int i=0; i<array.length();i++)
            {
                list_img.add(array.getString(i).toString());
            }
            st=list_img.get(0).toString();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return st;
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }


    private void showImage(String image) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.product_image_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView iv_image_cancle = (ImageView) dialog.findViewById(R.id.iv_dialog_cancle);
        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_dialog_img);

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + image)
                .centerCrop()
                .placeholder(R.drawable.icon)
                .crossFade()
                .into(iv_image);

        iv_image_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void showProductDetail(String image, String title, String description, String detail, final int position, String qty) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product_detail);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();

        ImageView iv_image = (ImageView) dialog.findViewById(R.id.iv_product_detail_img);
        final ImageView iv_minus = (ImageView) dialog.findViewById(R.id.iv_subcat_minus);
        final ImageView iv_plus = (ImageView) dialog.findViewById(R.id.iv_subcat_plus);
        TextView tv_title = (TextView) dialog.findViewById(R.id.tv_product_detail_title);
        TextView tv_detail = (TextView) dialog.findViewById(R.id.tv_product_detail);
        final TextView tv_contetiy = (TextView) dialog.findViewById(R.id.tv_subcat_contetiy);
        final TextView tv_add = (TextView) dialog.findViewById(R.id.tv_subcat_add);


        tv_title.setText(title);
        tv_detail.setText(detail);
        tv_contetiy.setText(qty);
        tv_detail.setText(description);

        Glide.with(context)
                .load(BaseURL.IMG_PRODUCT_URL + image)
                .centerCrop()
                .crossFade()
                .into(iv_image);
        if (Integer.valueOf(modelList.get(position).getStock())<=0){
            tv_add.setText(R.string.tv_out);
            tv_add.setTextColor(context.getResources().getColor(R.color.black));
            tv_add.setBackgroundColor(context.getResources().getColor(R.color.gray));
            tv_add.setEnabled(false);
            iv_minus.setEnabled(false);
            iv_plus.setEnabled(false);
        }

        else if (dbcart.isInCart(modelList.get(position).getProduct_id())) {
            tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
            tv_contetiy.setText(dbcart.getCartItemQty(modelList.get(position).getProduct_id()));
        } else {
            tv_add.setText(context.getResources().getString(R.string.tv_pro_add));
        }

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
                language=preferences.getString("language","");

                    map.put("product_id", modelList.get(position).getProduct_id());
                    map.put("product_name", modelList.get(position).getProduct_name());
                    map.put("category_id", modelList.get(position).getCategory_id());
                    map.put("product_description", modelList.get(position).getProduct_description());
                    map.put("deal_price", modelList.get(position).getDeal_price());
                    map.put("start_date", modelList.get(position).getStart_date());
                    map.put("start_time", modelList.get(position).getStart_time());
                    map.put("end_date", modelList.get(position).getEnd_date());
                    map.put("end_time", modelList.get(position).getEnd_time());
                    map.put("price", modelList.get(position).getPrice());
                    map.put("size",modelList.get(position).getSize());
                    map.put("color",modelList.get(position).getColor());
                    map.put("product_image", modelList.get(position).getProduct_image());
                    map.put("status", modelList.get(position).getStatus());
                    map.put("in_stock", modelList.get(position).getIn_stock());
                    map.put("unit_value", modelList.get(position).getUnit_value());
                    map.put("unit", modelList.get(position).getUnit());
                    map.put("increament", modelList.get(position).getIncreament());
                    map.put("rewards", modelList.get(position).getRewards());
                    map.put("stock", modelList.get(position).getStock());
                    map.put("title", modelList.get(position).getTitle());


                if (!tv_contetiy.getText().toString().equalsIgnoreCase("0")) {
                    if (dbcart.isInCart(map.get("product_id"))) {
                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                        updateintent();

                        tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    } else {
                        dbcart.setCart(map, Float.valueOf(tv_contetiy.getText().toString()));
                        tv_add.setText(context.getResources().getString(R.string.tv_pro_update));
                    }
                } else {
                    dbcart.removeItemFromCart(map.get("product_id"));
                    tv_add.setText(context.getResources().getString(R.string.tv_pro_add));
                }

                Double items = Double.parseDouble(dbcart.getInCartItemQty(map.get("product_id")));
                Double price = Double.parseDouble(map.get("price"));
                ((MainActivity) context).setCartCounter("" + dbcart.getCartCount());

                notifyItemChanged(position);

            }
        });

        iv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = Integer.valueOf(tv_contetiy.getText().toString());
                qty = qty + 1;

                tv_contetiy.setText(String.valueOf(qty));
            }
        });

        iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty = 0;
                if (!tv_contetiy.getText().toString().equalsIgnoreCase(""))
                    qty = Integer.valueOf(tv_contetiy.getText().toString());

                if (qty > 0) {
                    qty = qty - 1;
                    tv_contetiy.setText(String.valueOf(qty));
                }
            }
        });

    }

    public int getDiscount(String price, String mrp)
    {
        double mrp_d=Double.parseDouble(mrp);
        double price_d=Double.parseDouble(price);
        double diff =mrp_d-price_d;
        double per=(diff/mrp_d)*100;
        double df=Math.round(per);
        int d=(int)df;
       return d;
    }

    private void updateintent() {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "update");
        context.sendBroadcast(updates);
    }
}