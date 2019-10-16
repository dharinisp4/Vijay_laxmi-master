package Fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.AttrColorAdapter;
import Adapter.Produccts_images_adapter;
import Adapter.ProductVariantAdapter;
import Adapter.RelatedProductAdapter;
import Adapter.VarientsAdapter;
import Config.BaseURL;
import Model.ProductVariantModel;
import Model.RelatedProductModel;
import Module.Module;
import trolley.tcc.AppController;
import trolley.tcc.LoginActivity;
import trolley.tcc.MainActivity;
import trolley.tcc.R;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;
import util.DatabaseCartHandler;
import util.RecyclerTouchListener;
import util.Session_management;
import util.WishlistHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class Details_Fragment extends Fragment {
    String atr_id="";
    String atr_product_id="";
    String attribute_name="";
    String attribute_value="";
    String attribute_mrp="";
    Context context;
    Button btn_add;
    ArrayList<ProductVariantModel> variantList;
    ProductVariantAdapter productVariantAdapter;
  VarientsAdapter varientsAdapter;
    List<ProductVariantModel> v_list;
    AttrColorAdapter colorAdapter;
    private static String TAG = Details_Fragment.class.getSimpleName();
    private RecyclerView rv_cat,rv_color,rv_weight;
    int index;
    double tot_amt=0;
    ProgressDialog loadingBar;
    double tot=0;
    RelativeLayout rel_variant;
    private List<RelatedProductModel> product_modelList = new ArrayList<>();
    private RelatedProductAdapter adapter_product;
   // VarientsAdapter varientsAdapter ;
    Activity activity;
    Button btn_add_to_cart;
    DatabaseCartHandler db_cart;
   WishlistHandler db_wish ;
    //TextView txtColor,txtSize;
    TextView txtPer;
    Button btn_buy_now;

    ImageView wish_before ,wish_after ;
    int status=0;

    private TextView dialog_unit_type,dialog_txtId,dialog_txtVar;
    String color ,size ;
    Dialog dialog;

    public static ProgressBar progressBar,pgb,pbg1;
    RelativeLayout relativeLayout_spinner,relativeLayout_size,relativeLayout_color;
    Produccts_images_adapter imagesAdapter;
    String cat_id,product_id,product_images,details_product_name,details_product_desc,details_product_inStock,details_product_attribute;
    String details_product_price,details_product_mrp,details_product_unit_value,details_product_unit,details_product_rewards,details_product_increament,details_product_title;
    //String details_product_size,details_product_color;

    public static ImageView btn,img2;
    private TextView txtrate,txtTotal,txtBack;
    ListView listView,listView1;
//    ArrayList<String> list;
//    ArrayList<String> list_id;
//    ArrayList<String> list_atr_value;
//    ArrayList<String> list_atr_name;
//    ArrayList<String> list_atr_mrp;


    // ListView listView;
    List<String> image_list;
    private TextView txtName,txtDesc,txtPrice,txtMrp;
    //Spinner spinner_size,spinner_color;
    RecyclerView recyclerView ,varient_recycler;
    CardView cardView;

    private ElegantNumberButton numberButton;
 Module module=new Module();
    private Session_management sessionManagement;
    private String details_product_unit_price;
    List<String> list_color=new ArrayList<>();
    List<String> list_images=new ArrayList<>();

    Button btn_desc,btn_info;
    RelativeLayout rel_desc,rel_info;
    RecyclerView.LayoutManager layoutManager;
    public Details_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details_, container, false);

        sessionManagement = new Session_management(getActivity());
        sessionManagement.cleardatetime();
        loadingBar=new ProgressDialog(getActivity());
        loadingBar.setMessage("Loading...");
        loadingBar.setCanceledOnTouchOutside(false);

        rv_cat = (RecyclerView) view.findViewById(R.id.top_selling_recycler);
        varient_recycler=(RecyclerView)view.findViewById( R.id.varient_recycler );
        rv_color=(RecyclerView)view.findViewById( R.id.rv_color);
        rv_weight=(RecyclerView)view.findViewById( R.id.rv_weight);

        // gifImageView=(ImageView) view.findViewById(R.id.gifImageView);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);

        rv_cat.setLayoutManager(linearLayoutManager1);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        varient_recycler.setLayoutManager( linearLayoutManager2 );
        db_wish = new WishlistHandler( getActivity() );
        db_cart=new DatabaseCartHandler(getActivity());

        Bundle bundle=getArguments();
        variantList=new ArrayList<>();
        v_list=new ArrayList<>();

        cat_id=bundle.getString("cat_id");
        product_id=bundle.getString("product_id");
        product_images=bundle.getString("product_image");
        details_product_name=bundle.getString("product_name");
        details_product_desc=bundle.getString("product_description");
   //     details_product_color=bundle.getString("product_color");
        details_product_inStock=bundle.getString("stock");
        details_product_attribute=bundle.getString("product_attribute");

     //   details_product_size=bundle.getString("product_size");
        details_product_unit_price =bundle.getString("unit_price");
        details_product_price=bundle.getString("price");
        details_product_mrp=bundle.getString("mrp");
        details_product_unit_value=bundle.getString("unit_value");
        details_product_unit=bundle.getString("unit");
        details_product_rewards=bundle.getString("rewards");
        details_product_increament=bundle.getString("increment");
        details_product_title=bundle.getString("title");


    btn_add=(Button)view.findViewById(R.id.btn_add);
        dialog_unit_type=(TextView)view.findViewById(R.id.unit_type);
        dialog_txtId=(TextView)view.findViewById(R.id.txtId);
        dialog_txtVar=(TextView)view.findViewById(R.id.txtVar);
        btn_add_to_cart=(Button)view.findViewById(R.id.btn_f_Add_to_cart);
       // cardView=(CardView)view.findViewById(R.id.card_view2);
        txtPer=(TextView)view.findViewById(R.id.details_product_per);

        rel_variant=(RelativeLayout)view.findViewById(R.id.rel_variant);
        btn=(ImageView)view.findViewById(R.id.img_product);
        recyclerView=view.findViewById(R.id.recylerView);
        //   listView=findViewById(R.id.lstView);
        txtBack=(TextView)view.findViewById(R.id.txtBack);
            wish_after=(ImageView)view.findViewById(R.id.wish_after );
            wish_before = (ImageView)view.findViewById( R.id.wish_before );

        image_list=new ArrayList<>();
        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);
        //   Glide.with(this).load(R.raw.basicloader).into(btn);
        txtName=(TextView)view.findViewById(R.id.details_product_name);
        txtDesc=(TextView)view.findViewById(R.id.details_product_description);
        txtPrice=(TextView)view.findViewById(R.id.details_product_price);
        txtMrp=(TextView)view.findViewById(R.id.details_product_mrp);
        txtMrp.setPaintFlags(txtMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
      //  btn_add_to_cart=(Button)view.findViewById(R.id.btnAdd_to_cart);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(details_product_name);
       // txtrate=(TextView)view.findViewById(R.id.product_rate);
        txtTotal=(TextView)view.findViewById(R.id.product_total);
        numberButton=(ElegantNumberButton)view.findViewById(R.id.product_qty);
        txtDesc.setText(details_product_desc);
        Double mrp=Double.parseDouble(details_product_mrp);
       final Double price=Double.parseDouble(details_product_price);
       Double discount = Double.valueOf(getDiscount(details_product_price,details_product_mrp));
      if (mrp > price)
      {

          txtPer.setText(String.valueOf(Math.round(discount)+"%"+"off"));
      }
      else  txtPer.setVisibility(View.GONE);

        txtName.setText(details_product_name);


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        makeGetAttributeRequest(product_id);

        layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rv_color.setLayoutManager(layoutManager);
        colorAdapter=new AttrColorAdapter(list_color,getActivity());
        rv_color.setAdapter(colorAdapter);

        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rv_weight.setLayoutManager(layoutManager1);
        varientsAdapter=new VarientsAdapter(getActivity(),v_list);
        rv_weight.setAdapter(varientsAdapter);




        if(db_wish.isInWishtable( product_id ))
        {
            wish_after.setVisibility( View.VISIBLE );
            wish_before.setVisibility( View.GONE );
        }


        rel_variant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          //      final Product_model mList = modelList.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                LayoutInflater layoutInflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row=layoutInflater.inflate(R.layout.dialog_vairant_layout,null);
                variantList.clear();
                String atr=String.valueOf(details_product_attribute);
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
                productVariantAdapter=new ProductVariantAdapter(getActivity(),variantList);
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

                        dialog_unit_type.setText("\u20B9"+attribute_value+"/"+attribute_name);
                        dialog_txtId.setText(variantList.get(i).getId()+"@"+i);
                        dialog_txtVar.setText(attribute_value+"@"+attribute_name+"@"+attribute_mrp);
                        //    txtPer.setText(String.valueOf(df)+"% off");

                        txtPrice.setText("\u20B9"+attribute_value.toString());
                        txtMrp.setText("\u20B9"+attribute_mrp.toString());
                        String pr=String.valueOf(attribute_value);
                        String mr=String.valueOf(attribute_mrp);
                        int atr_dis=getDiscount(pr,mr);
                        txtPer.setText(""+atr_dis+"% OFF");
                        String atr=String.valueOf(details_product_attribute);
                        if(atr.equals("[]"))
                        {
                            boolean st=db_cart.isInCart(product_id);
                            if(st==true)
                            {
                                btn_add.setVisibility(View.GONE);
                                numberButton.setNumber(db_cart.getCartItemQty(product_id));
                                numberButton.setVisibility(View.VISIBLE);
                            }
                        }
                        else
                        {
                            String str_id=dialog_txtId.getText().toString();
                            String[] str=str_id.split("@");
                            String at_id=String.valueOf(str[0]);
                            boolean st=db_cart.isInCart(at_id);
                            if(st==true)
                            {
                                btn_add.setVisibility(View.GONE);
                                numberButton.setNumber(db_cart.getCartItemQty(at_id));
                                numberButton.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                btn_add.setVisibility(View.VISIBLE);

                                numberButton.setVisibility(View.GONE);
                            }
                        }

     numberButton.setNumber("1");

                        txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));
                        ddlg.dismiss();
                    }
                });

            }
        });
        //Toast.makeText(getActivity(),"tot_amount"+atr.toString(),Toast.LENGTH_LONG).show();



//       if (details_product_color.isEmpty())
//       {
//           txtColor.setVisibility( View.GONE );
//       }
//       if (details_product_color.equals( "null" ))
//       {
//           txtColor.setVisibility( View.GONE );
//       }
//       if(details_product_size.isEmpty())
//       {
//           txtSize.setVisibility( View.GONE );
//       }
//       if(details_product_size.equals( "null" ))
//       {
//           txtSize.setVisibility( View.GONE );
//       }

//if (txtSize.getVisibility()== View.VISIBLE) {
//    txtSize.setOnClickListener( new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            dialog = new Dialog( getActivity() );
//            dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
//            dialog.setContentView( R.layout.dialog_product_size_layout );
//
//            listView = (ListView) dialog.findViewById( R.id.list_view_size );
//            pgb = (ProgressBar) dialog.findViewById( R.id.pgb );
//            //final List lis=makeGetProductColorRequest(cat_id,product_id,listView1,pbg1,dialog);
//            final List lis = makeGetProductRequest( cat_id, product_id, listView, pgb, dialog );
//
//
//            dialog.setCanceledOnTouchOutside( false );
//            dialog.show();
//
//            listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    txtSize.setText( lis.get( i ).toString() );
//                    dialog.dismiss();
//
//
//                }
//            } );
//
//        }
//    } );
//}
        wish_before.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // holder.wish_after.setVisibility( View.VISIBLE );
                wish_after.setVisibility( View.VISIBLE );
               wish_before.setVisibility( View.INVISIBLE );



                String tl="";
                String title=String.valueOf(details_product_title);
                if(title.equals("null"))
                {
                    tl="title";
                }
                else
                {
                    tl=String.valueOf(details_product_title);
                }

                HashMap<String, String> mapProduct = new HashMap<String, String>();
                mapProduct.put("product_id", product_id);
                mapProduct.put("product_image",product_images);
                mapProduct.put("cat_id",cat_id);
                mapProduct.put("product_name",details_product_name);
                mapProduct.put("price", details_product_price);
                mapProduct.put("product_description",details_product_desc);
                mapProduct.put("rewards", details_product_rewards);
                mapProduct.put( "unit_price",details_product_price );
                mapProduct.put("unit_value",details_product_unit_value);
                mapProduct.put("unit", details_product_unit);
                mapProduct.put("increment",details_product_increament);
                mapProduct.put("stock",details_product_inStock);
                mapProduct.put("title",tl);
                mapProduct.put("mrp",details_product_mrp);
                mapProduct.put("product_attribute",details_product_attribute);

                // Toast.makeText(context,""+mapProduct,Toast.LENGTH_LONG).show();


                try {

                    boolean tr =db_wish.setwishTable(mapProduct);
                    if (tr == true) {

                        //   context.setCartCounter("" + holder.db_cart.getCartCount());
                        Toast.makeText(context, "Added to Wishlist"  , Toast.LENGTH_LONG).show();



                    }
                    else
                    {
                        Toast.makeText(context, "Something Went Wrong" , Toast.LENGTH_LONG).show();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    //  Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        } );

        wish_after.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wish_after.setVisibility( View.INVISIBLE );
                wish_before.setVisibility( View.VISIBLE );
                db_wish.removeItemFromWishtable(product_id);
                Toast.makeText(getActivity(), "removed from Wishlist" , Toast.LENGTH_LONG).show();
                // list.remove(position);
             //   notifyDataSetChanged();

            }
        } );

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tot1 = txtTotal.getText().toString().trim();

                String tot_amount = tot1.substring(1, tot1.length());
                float qty = 1;
              String atr = String.valueOf(details_product_attribute);
                if (atr.equals("[]")) {

                    Module.setIntoCart(getActivity(),product_id,product_id,product_images,cat_id,details_product_name,details_product_price,
                            details_product_desc,details_product_rewards,details_product_price,details_product_unit_value,details_product_unit,details_product_increament,
                            details_product_inStock,details_product_title,details_product_mrp,details_product_attribute,"p",qty);
                    txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

                }
                else {
                    //ProductVariantModel model=variantList.get(position);

                    String str_id=dialog_txtId.getText().toString();
                    String s=dialog_txtVar.getText().toString();
                    String[] st=s.split("@");
                    String st0=String.valueOf(st[0]);
                    String st1=String.valueOf(st[1]);
                    String st2=String.valueOf(st[2]);
                    String[] str=str_id.split("@");
                    String at_id=String.valueOf(str[0]);
                    int j=Integer.parseInt(String.valueOf(str[1]));

                    Module.setIntoCart(getActivity(),at_id,product_id,product_images,cat_id,details_product_name,st0,
                            details_product_desc,details_product_rewards,st0,details_product_unit_value,st1,details_product_increament,
                            details_product_inStock,details_product_title,st2,details_product_attribute,"a",qty);
                    txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

                }

                updateData();
                btn_add.setVisibility(View.GONE);
                numberButton.setNumber("1");
                numberButton.setVisibility(View.VISIBLE);

            }
        });

        rv_color.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_color, new RecyclerTouchListener.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(View view, int position) {

                Glide.with(getActivity())
                        .load(BaseURL.IMG_PRODUCT_URL +list_images.get(position).toString())
                        .centerCrop()
                        .placeholder(R.drawable.icon)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(btn);
                btn.setColorFilter(Color.parseColor(list_color.get(position).toString()), android.graphics.PorterDuff.Mode.SRC_IN);
                btn.setColorFilter(ContextCompat.getColor(getActivity(), Color.parseColor(list_color.get(position).toString())), android.graphics.PorterDuff.Mode.SRC_IN);
                btn.setColorFilter(Color.parseColor(list_color.get(position).toString()));
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));


        //  txtTotal.setText("\u20B9"+String.valueOf(details_product_price));

        numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

               // final Product_model mList = modelList.get(position);

                if(newValue<=0)
                {
                   boolean st=checkAttributeStatus(details_product_attribute);
                   if(st==false)
                   {
                       db_cart.removeItemFromCart(product_id);
                   }
                   else if(st==true)
                   {

                       String str_id = dialog_txtId.getText().toString();
                       String[] str = str_id.split("@");
                       String at_id = String.valueOf(str[0]);
                       db_cart.removeItemFromCart(at_id);
                   }

                   numberButton.setVisibility(View.GONE);
                   btn_add.setVisibility(View.VISIBLE);
                }
                else {


                    float qty = Float.parseFloat(String.valueOf(newValue));

                    String atr = String.valueOf(details_product_attribute);
                    if (atr.equals("[]")) {
                        double pr = Double.parseDouble(details_product_price);
                        double amt = pr * qty;
                        HashMap<String, String> mapProduct = new HashMap<String, String>();
                        String unt = String.valueOf(details_product_unit_value + " " + details_product_unit);


                        Module.setIntoCart(getActivity(),product_id,product_id,product_images,cat_id,details_product_name,String.valueOf(amt),
                                details_product_desc,details_product_rewards,details_product_price,details_product_unit_value,unt,details_product_increament,
                                details_product_inStock,details_product_title,details_product_mrp,details_product_attribute,"p",qty);




                        //Toast.makeText(context,"1\n"+status+"\n"+modelList.get(position).getProduct_attribute(),Toast.LENGTH_LONG).show();
                    } else {
                        //ProductVariantModel model=variantList.get(position);




                        String s = dialog_txtVar.getText().toString();
                        String[] st = s.split("@");
                        String st0 = String.valueOf(st[0]);
                        String st1 = String.valueOf(st[1]);
                        String st2 = String.valueOf(st[2]);
                        String str_id = dialog_txtId.getText().toString();
                        String[] str = str_id.split("@");
                        String at_id = String.valueOf(str[0]);
                        int k = Integer.parseInt(String.valueOf(str[1]));
                        double pr = Double.parseDouble(st0);


                        double amt = pr * qty;
                        //       Toast.makeText(context,""+str[0].toString()+"\n"+str[1].toString(),Toast.LENGTH_LONG).show();

                        Module.setIntoCart(getActivity(),at_id,product_id,product_images,cat_id,details_product_name,String.valueOf(amt),
                                details_product_desc,details_product_rewards,st0,details_product_unit_value,st1,details_product_increament,
                                details_product_inStock,details_product_title,st2,details_product_attribute,"a",qty);
                        //txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

                        //  Toast.makeText(context,""+attributeList.get(j).getId()+"\n"+mapProduct,Toast.LENGTH_LONG).show();

                    }
                }
                txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));
            }
        });

//        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                double number=Double.parseDouble(numberButton.getNumber());
//
//
//
//                double price=0;
//                if(status==1)
//                {
//                    price=Double.parseDouble(details_product_price);
//                }
//                else if(status==2)
//                {
//                     price=Double.parseDouble(attribute_value);
//
//                }
//
//                tot=price*number;
//
//                txtTotal.setText("\u20B9"+String.valueOf(tot));
//
//            }
//        });





        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectivityReceiver.isConnected()) {
                    makeGetLimiteRequest();
                } else {
                    ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
                }

            }
        });




        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister reciver
        getActivity().unregisterReceiver(mCart);
    }

    @Override
    public void onResume() {
        super.onResume();
        // register reciver
        getActivity().registerReceiver(mCart, new IntentFilter("Grocery_cart"));
    }

    @Override
    public void onStart() {
        super.onStart();


        txtTotal.setText("\u20B9"+String.valueOf(db_cart.getTotalAmount()));

//        String atr=String.valueOf(details_product_attribute);
//        if(atr.equals("[]"))
//        {
//            boolean st=db_cart.isInCart(product_id);
//            if(st==true)
//            {
//             btn_add.setVisibility(View.GONE);
//             numberButton.setNumber(db_cart.getCartItemQty(product_id));
//             numberButton.setVisibility(View.VISIBLE);
//            }
//        }
//        else
//        {
//            String str_id=dialog_txtId.getText().toString();
//            String[] str=str_id.split("@");
//            String at_id=String.valueOf(str[0]);
//            boolean st=db_cart.isInCart(at_id);
//            if(st==true)
//            {
//                btn_add.setVisibility(View.GONE);
//                numberButton.setNumber(db_cart.getCartItemQty(at_id));
//                numberButton.setVisibility(View.VISIBLE);
//            }
//        }

        //Toast.makeText(getActivity(),""+cat_id, Toast.LENGTH_LONG).show();
      //  makeRelatedProductRequest(cat_id);

       try
        {
            image_list.clear();
            JSONArray array=new JSONArray(product_images );
           //Toast.makeText(this,""+product_images,Toast.LENGTH_LONG).show();
            if(product_images.equals(null))
            {
                Toast.makeText(getActivity(),"There is no image for this product",Toast.LENGTH_LONG).show();
            }
            else
            {
                for(int i=0; i<=array.length()-1;i++)
                {
                    image_list.add(array.get(i).toString());

                }

            }

         //   Toast.makeText(getActivity(),""+image_list.get(0).toString(),Toast.LENGTH_LONG).show();
            Glide.with(getActivity())
                    .load(BaseURL.IMG_PRODUCT_URL +image_list.get(0) )
                    .centerCrop()
                    .placeholder(R.drawable.icon)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(btn);

            /*if(details_product_color.equals(null) || details_product_color.equals("null"))
            {
                Toast.makeText(getActivity(),"color :"+ details_product_color,Toast.LENGTH_LONG).show();
            }
            else
            {
                cardView.setVisibility(View.VISIBLE);
            }*/

          //  makeGetProductColorSizeRequest(cat_id,product_id);

           // Toast.makeText(Product_Frag_details.this,""+image_list.toString(),Toast.LENGTH_LONG).show();

        }
        catch (Exception ex)
        {
           // Toast.makeText(Product_Frag_details.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
      // imagesAdapter.notifyDataSetChanged();
        // makeGetProductRequest(cat_id,product_id);
       // product_images_adapter=new Product_images_Adapter(Product_Frag_details.this,image_list);
        //imagesAdapter=new Produccts_images_adapter(getActivity(),image_list);
       // progressBar.setVisibility(View.INVISIBLE);
        //recyclerView.setAdapter(imagesAdapter);


    }


   /* private void makeGetProductColorSizeRequest(String cat_id, String product_id) {

        String tag_json_obj = "json_product_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cat_id", cat_id);
        params.put("product_id", product_id);

        final Object[] arrayObjects=new Object[2];
        final List list1=new ArrayList();
        final List list=new ArrayList();


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.PRODUCT_DETAILS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //  Log.d(TAG, response.toString());



              /*  try {
                    String status = response.getString("responce");
                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();

                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();

                    if(status.equals("true")) {


                        JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);



                        // String sdf = jsonObject.getString("size");
                        String sdf = jsonObject.getString("color");
                        String size=jsonObject.getString("size");

                        if(!(sdf.equals("")) )
                        {

                            txtColor.setVisibility(View.GONE);



                        }
                        else if( !(sdf.equals("null")))
                        {
                            txtColor.setVisibility(View.GONE);
                        }

                        if(size.equals("") )
                        {

                            txtSize.setVisibility(View.GONE);


                        }
                        else if(!( size.equals("null")))
                        {
                            txtSize.setVisibility(View.GONE);
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // return list;


    }*/

    private List<String> makeGetProductRequest(String cat_id, String product_id, final ListView listView, final ProgressBar pg,final Dialog dialog) {
        final List list=new ArrayList();
        String tag_json_obj = "json_product_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cat_id", cat_id);
        params.put("product_id", product_id);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.PRODUCT_DETAILS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //  Log.d(TAG, response.toString());



                try {
                    String status = response.getString("responce");
                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();

                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();

                    if(status.equals("true")) {


                        JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        // List list1=new ArrayList();

                        String sdf = jsonObject.getString("size");
                        String sdf1 = jsonObject.getString("color");

                        if (sdf.isEmpty()) {
                           // txtSize.setVisibility( View.GONE );
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();
                           // Toast.makeText(getActivity(), "There are no other size", Toast.LENGTH_LONG).show();
                        } else if (sdf.equals("null")) {
                          //  txtSize.setVisibility( View.GONE );
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();
                           // Toast.makeText(getActivity(), "There are no other size", Toast.LENGTH_LONG).show();
                        } else {

                            list.add("Select Size");
                            JSONArray array = new JSONArray(sdf);
                            for (int i = 0; i < array.length(); i++) {

                                list.add(array.get(i));
                            }
                            String str[] = new String[list.size()];
                            for (int l = 0; l < list.size(); l++) {
                                str[l] = list.get(l).toString();
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, str);

                            listView.setAdapter(arrayAdapter);

                            pg.setVisibility(View.GONE);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        return list;
    }

    private List<String> makeGetProductColorRequest(String cat_id, String product_id, final ListView listView, final ProgressBar pg,final Dialog dialog) {
        final List list=new ArrayList();
        String tag_json_obj = "json_product_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cat_id", cat_id);
        params.put("product_id", product_id);


        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.PRODUCT_DETAILS, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //  Log.d(TAG, response.toString());



                try {
                    String status = response.getString("responce");
                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();

                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();

                    if(status.equals("true")) {


                        JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        // List list1=new ArrayList();

                        // String sdf = jsonObject.getString("size");
                        String sdf = jsonObject.getString("color");

                        if (sdf.isEmpty()) {
                            //txtColor.setVisibility( View.GONE );
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();
                            //Toast.makeText(getActivity(), "There are no other color", Toast.LENGTH_LONG).show();
                        } else if (sdf.equals("null")) {
                           // txtColor.setVisibility( View.GONE );
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();
                          //  Toast.makeText(getActivity(), "There are no other color", Toast.LENGTH_LONG).show();
                        } else {

                            list.add("Select color");
                            JSONArray array = new JSONArray(sdf);
                            for (int i = 0; i < array.length(); i++) {

                                list.add(array.get(i));
                            }
                            String str[] = new String[list.size()];
                            for (int l = 0; l < list.size(); l++) {
                                str[l] = list.get(l).toString();
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, str);

                            listView.setAdapter(arrayAdapter);

                            pg.setVisibility(View.GONE);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
             //   Toast.makeText(getContext().getApplicationContext(),"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                 VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // return list;
        return list;
    }


    private void makeGetAttributeRequest(String product_id)
    {
        loadingBar.show();
        String json_tag="json_attr_tag";
        HashMap<String,String> params=new HashMap<>();
        params.put("product_id",product_id);

        CustomVolleyJsonRequest customVolleyJsonRequest=new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.GET_ATTR_URL, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                v_list.clear();
                Log.d("attrm",response.toString());
                loadingBar.dismiss();
                try
                {
                    JSONArray jsonArr=response.getJSONArray("product_attribute");

                    List<ProductVariantModel> varientlist = new ArrayList<>();


                        //JSONArray jsonArr = new JSONArray(attribute);

                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            ProductVariantModel model=new ProductVariantModel();


                            if (jsonObj.has("id") && !jsonObj.isNull("id"))
                                model.setId(jsonObj.getString("id"));
                            else
                                model.setId(jsonObj.getString(""));
                            if (jsonObj.has("product_id") && !jsonObj.isNull("product_id"))
                                model.setProduct_id(jsonObj.getString("product_id"));
                            else
                                model.setProduct_id("");

                            if (jsonObj.has("attribute_name") && !jsonObj.isNull("attribute_name"))
                                model.setAttribute_name(jsonObj.getString("attribute_name"));
                            else
                                model.setAttribute_name("");

                            if (jsonObj.has("attribute_value") && !jsonObj.isNull("attribute_value"))
                                model.setAttribute_value(jsonObj.getString("attribute_value"));
                            else
                                model.setAttribute_value("");

                            if (jsonObj.has("attribute_mrp") && !jsonObj.isNull("attribute_mrp"))
                                model.setAttribute_mrp(jsonObj.getString("attribute_mrp"));
                            else
                                model.setAttribute_mrp("");

                            if (jsonObj.has("attribute_image") && !jsonObj.isNull("attribute_image"))
                                model.setAttribute_image(jsonObj.getString("attribute_image"));
                            else
                                model.setAttribute_image("");
                            if (jsonObj.has("attribute_color") && !jsonObj.isNull("attribute_color"))
                                model.setAttribute_color(jsonObj.getString("attribute_color"));
                            else
                                model.setAttribute_color("");


                            varientlist.add(model);
                            v_list.add(model);
                         varientsAdapter.notifyDataSetChanged();
                        }
              list_color.clear();
                        String str_col=varientlist.get(0).getAttribute_color();
                        if(str_col.isEmpty() || str_col.equals(null))
                        {
                            Toast.makeText(getActivity(),"empty ",Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            JSONArray col_array=new JSONArray(str_col);
                            for(int j=0; j<col_array.length();j++)
                            {
                                    list_color.add(col_array.getString(j).toString());
                            }
                            colorAdapter.notifyDataSetChanged();
                            //Toast.makeText(getActivity(),""+col_array+"\n "+col_array.length(),Toast.LENGTH_LONG).show();
                        }

                    list_images.clear();
                    String str_img=varientlist.get(0).getAttribute_image();
                    if(str_img.isEmpty() || str_img.equals(null))
                    {
                        Toast.makeText(getActivity(),"empty ",Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        JSONArray col_array=new JSONArray(str_img);
                        for(int j=0; j<col_array.length();j++)
                        {
                            list_images.add(col_array.getString(j).toString());
                        }
                        //colorAdapter.notifyDataSetChanged();
                        //Toast.makeText(getActivity(),""+col_array+"\n "+col_array.length(),Toast.LENGTH_LONG).show();
                    }


                }
                catch (Exception ex)
                {
                    Toast.makeText(getActivity(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
              //  Toast.makeText(getActivity(),""+response,Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loadingBar.dismiss();
                Toast.makeText(getActivity(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(customVolleyJsonRequest,json_tag);
     }


    private void makeRelatedProductRequest(String cat_id) {
        loadingBar.show();
        String tag_json_obj = "json_product_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cat_id", cat_id);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                BaseURL.GET_PRODUCT_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("rett" +
                        "", response.toString());

                try {

                    Boolean status = response.getBoolean("responce");

                    if (status) {
                        ///         Toast.makeText(getActivity(),""+response.getString("data"),Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<RelatedProductModel>>() {
                        }.getType();
                        product_modelList = gson.fromJson(response.getString("data"), listType);
                        loadingBar.dismiss();
                        adapter_product = new RelatedProductAdapter( getActivity(),product_modelList,product_id);

                        rv_cat.setAdapter(adapter_product);
                        adapter_product.notifyDataSetChanged();
                        if (getActivity() != null) {
                            if (product_modelList.isEmpty()) {

                                loadingBar.dismiss();
                                //  Toast.makeText(getActivity(), getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                } catch (JSONException e) {
                    loadingBar.dismiss();
                    //   e.printStackTrace();
                    String ex=e.getMessage();




                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //loadingBar.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    loadingBar.dismiss();
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
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


    private void makeGetLimiteRequest() {

        JsonArrayRequest req = new JsonArrayRequest(BaseURL.GET_LIMITE_SETTING_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        Double total_amount = Double.parseDouble(db_cart.getTotalAmount());


                        try {
                            // Parsing json array response
                            // loop through each json object

                            boolean issmall = false;
                            boolean isbig = false;

                            // arraylist list variable for store data;
                            ArrayList<HashMap<String, String>> listarray = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonObject = (JSONObject) response
                                        .get(i);
                                int value;

                                if (jsonObject.getString("id").equals("1")) {
                                    value = Integer.parseInt(jsonObject.getString("value"));

                                    if (total_amount < value) {
                                        issmall = true;
                                        Toast.makeText(getActivity(), "" + jsonObject.getString("title") + " : " + value, Toast.LENGTH_SHORT).show();
                                    }
                                } else if (jsonObject.getString("id").equals("2")) {
                                    value = Integer.parseInt(jsonObject.getString("value"));

                                    if (total_amount > value) {
                                        isbig = true;
                                        Toast.makeText(getActivity(), "" + jsonObject.getString("title") + " : " + value, Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            if (!issmall && !isbig) {
                                if (sessionManagement.isLoggedIn()) {
                                    Bundle args = new Bundle();
                                    Fragment fm = new Delivery_fragment();
                                    fm.setArguments(args);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                            .addToBackStack(null).commit();
                                } else {
                                    //Toast.makeText(getActivity(), "Please login or regiter.\ncontinue", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(i);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Connection Time out", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }




    private BroadcastReceiver mCart = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String type = intent.getStringExtra("type");

            if (type.contentEquals("update")) {
                updateData();
            }
        }
    };

    private void updateData() {

        ((MainActivity) getActivity()).setCartCounter("" + db_cart.getCartCount());
    }
}
