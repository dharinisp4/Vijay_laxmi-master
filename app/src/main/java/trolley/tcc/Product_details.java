package trolley.tcc;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
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
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.Produccts_images_adapter;
import Config.BaseURL;
import trolley.tcc.R;
import util.CartHandler;
import util.CustomVolleyJsonRequest;
import util.DatabaseCartHandler;
import util.DatabaseHandler;

public class Product_details extends AppCompatActivity {

    //Product_images_Adapter product_images_adapter;

    DatabaseHandler dbCart;
   DatabaseCartHandler db_cart;
    TextView txtColor,txtSize;
    Button btn_buy_now,btn_add_to_cart;
    Dialog dialog;
  public static   ProgressBar progressBar,pgb,pbg1;
    RelativeLayout relativeLayout_spinner,relativeLayout_size,relativeLayout_color;
    Produccts_images_adapter imagesAdapter;
    String cat_id,product_id,product_images,details_product_name,details_product_desc,details_product_inStock,details_product_size,details_product_color;
    String details_product_price,details_product_mrp,details_product_unit_value,details_product_unit,details_product_rewards,details_product_increament,details_product_title;


   public static ImageView btn,img2;
   private TextView txtrate,txtTotal,txtBack;
   ListView listView,listView1;
   CardView varient ;
   // ListView listView;
    List<String> image_list;
    private TextView txtName,txtDesc,txtPrice,txtMrp;
//Spinner spinner_size,spinner_color;
    RecyclerView recyclerView;

    private ElegantNumberButton numberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        dbCart=new DatabaseHandler(Product_details.this);
        db_cart=new DatabaseCartHandler(Product_details.this);
        //relativeLayout_spinner=(RelativeLayout)findViewById(R.id.relative_layout_spin);
        /*intent.putExtra("cat_id",mList.getCategory_id());
        intent.putExtra("product_id",mList.getProduct_id());
        intent.putExtra("product_name",mList.getProduct_name());
        intent.putExtra("product_description",mList.getProduct_description());
        intent.putExtra("product_in_stock",mList.getIn_stock());
        intent.putExtra("product_size",mList.getUnit_value());
        intent.putExtra("product_color",mList.getProduct_id());
        intent.putExtra("product_price",mList.getDeal_price());
        intent.putExtra("product_mrp",mList.getIncreament());
        intent.putExtra("product_unit_value",mList.getUnit_value());
        intent.putExtra("product_unit",mList.getUnit());
        intent.putExtra("product_rewards",mList.getRewards());*/
        cat_id=getIntent().getStringExtra("cat_id");
        product_id=getIntent().getStringExtra("product_id");
        product_images=getIntent().getStringExtra("images");
        details_product_name=getIntent().getStringExtra("product_name");
        details_product_desc=getIntent().getStringExtra("product_description");
        details_product_color=getIntent().getStringExtra("product_color");
        details_product_inStock=getIntent().getStringExtra("product_in_stock");
        details_product_size=getIntent().getStringExtra("product_size");
        details_product_price=getIntent().getStringExtra("product_price");
        details_product_mrp=getIntent().getStringExtra("product_mrp");
        details_product_unit_value=getIntent().getStringExtra("product_unit_value");
        details_product_unit=getIntent().getStringExtra("product_unit");
        details_product_rewards=getIntent().getStringExtra("product_rewards");
        details_product_increament=getIntent().getStringExtra("product_increament");
        details_product_title=getIntent().getStringExtra("product_title");
      //  details_product_name=getIntent().getStringExtra("product_name");
        btn=(ImageView)findViewById(R.id.img_product);
        recyclerView=findViewById(R.id.recylerView);
     //   listView=findViewById(R.id.lstView);
        txtBack=(TextView)findViewById(R.id.txtBack);
       // varient =(CardView) findViewById( R.id.card_view2 );
        image_list=new ArrayList<>();
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);

        txtColor=(TextView)findViewById(R.id.txtColor);
        txtSize=(TextView)findViewById(R.id.txtSize);
     //   Glide.with(this).load(R.raw.basicloader).into(btn);
        txtName=(TextView)findViewById(R.id.details_product_name);
        txtDesc=(TextView)findViewById(R.id.details_product_description);
        txtPrice=(TextView)findViewById(R.id.details_product_price);
        txtMrp=(TextView)findViewById(R.id.details_product_mrp);
        txtMrp.setPaintFlags(txtMrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        btn_add_to_cart=(Button)findViewById(R.id.btnAdd_to_cart);


        txtrate=(TextView)findViewById(R.id.product_rate);
        txtTotal=(TextView)findViewById(R.id.product_total);
        numberButton=(ElegantNumberButton)findViewById(R.id.product_qty);



        txtName.setText(details_product_name);
        txtDesc.setText(details_product_desc);
        txtPrice.setText("\u20B9"+details_product_price);
        txtMrp.setText(details_product_mrp);
        txtrate.setText("\u20B9"+details_product_price+"/"+details_product_unit_value+details_product_unit);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Product_details.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        txtSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        dialog=new Dialog(Product_details.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_product_size_layout);

                listView=(ListView)dialog.findViewById(R.id.list_view_size);
                pgb=(ProgressBar)dialog.findViewById(R.id.pgb);
                //final List lis=makeGetProductColorRequest(cat_id,product_id,listView1,pbg1,dialog);
                final List lis= makeGetProductRequest(cat_id,product_id,listView,pgb,dialog);


                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        txtSize.setText(lis.get(i).toString());
                        dialog.dismiss();


                    }
                });

            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        txtTotal.setText("\u20B9"+String.valueOf(details_product_price));
        numberButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                float number=Float.parseFloat(numberButton.getNumber());

                float price=Float.parseFloat(details_product_price);
                float tot=price*number;
                txtTotal.setText("\u20B9"+String.valueOf(tot));

            }
        });


        txtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(Product_details.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_product_color_layout);

                listView1=(ListView)dialog.findViewById(R.id.list_view_color);
                pbg1=(ProgressBar)dialog.findViewById(R.id.pgbr);
 //List<String> lis=new ArrayList<>();
                 final List lis=makeGetProductColorRequest(cat_id,product_id,listView1,pbg1,dialog);


                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   txtColor.setText(lis.get(i).toString());
                   dialog.dismiss();

                      //  Toast.makeText(Product_Frag_details.this,""+lis.get(i).toString(),Toast.LENGTH_LONG).show();


                    }
                });

            }
        });



        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sd1=btn_add_to_cart.getText().toString();
                if(sd1.equals("Go To Cart"))
                {
                    Intent intent=new Intent(Product_details.this,MainActivity.class);
                    intent.putExtra("frag","frag_cart");
                    startActivity(intent);
                }
                else
                {
                    String tot=txtTotal.getText().toString().trim();

                    String tot_amount=tot.substring(1,tot.length());

                    String sz=txtSize.getText().toString();
                    String col=txtColor.getText().toString();

                    String size="";
                    String color="";

                    int sd=txtColor.getVisibility();
                    int ds=txtSize.getVisibility();
                    if(sd==4)
                    {
//                 sz="";
                        col="";

                    }
                    if(ds==4)
                    {
                        sz="";
                    }

                    if(sz.equals("Select Size")&& col.equals("Select Color"))
                    {
                        Toast.makeText(Product_details.this,"Select Color and Size",Toast.LENGTH_LONG).show();

                    }
                    else
                    {

                        float qty=Float.parseFloat(numberButton.getNumber().toString());
                        HashMap<String,String> mapProduct=new HashMap<>();
                        mapProduct.put("product_id",product_id);
                        mapProduct.put("product_image",product_images);
                        mapProduct.put("category_id",cat_id);
                        mapProduct.put("product_name",details_product_name);
                        mapProduct.put("price",tot_amount);
                        mapProduct.put("size",sz.toString());
                        mapProduct.put("color",col.toString());
                        mapProduct.put("rewards",details_product_rewards);
                        mapProduct.put("unit_value",details_product_unit_value);
                        mapProduct.put("unit",details_product_unit);
                        mapProduct.put("increament",details_product_increament);
                        mapProduct.put("stock",details_product_inStock);
                        mapProduct.put("title",details_product_title);

                        try
                        {
                            boolean tr= db_cart.setCart(mapProduct,qty);
                            if(tr==true)
                            {
                                MainActivity mainActivity=new MainActivity();
                                mainActivity.setCartCounter("" + db_cart.getCartCount());

                                //((MainActivity)getApplicationContext()).setCartCounter("" + db_cart.getCartCount());
                                Toast.makeText(Product_details.this,"Added to Cart",Toast.LENGTH_LONG).show();
                                btn_add_to_cart.setText("Go To Cart");
                            }
                            else
                            {
                                Toast.makeText(Product_details.this,"Update Cart",Toast.LENGTH_LONG).show();
                            }
                            updateintent();
                        }
                        catch(Exception ex)
                        {
                            Toast.makeText(Product_details.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }





                    }

                }




            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        try
        {
            image_list.clear();
            JSONArray array=new JSONArray(product_images);
           //Toast.makeText(this,""+product_images,Toast.LENGTH_LONG).show();
            if(product_images.equals(null))
            {
                Toast.makeText(this,"There is no image for this product",Toast.LENGTH_LONG).show();
            }
            else
            {
                for(int i=0; i<=array.length()-1;i++)
                {
                    image_list.add(array.get(i).toString());

                }

            }

            makeGetProductColorSizeRequest(cat_id,product_id);

           // Toast.makeText(Product_Frag_details.this,""+image_list.toString(),Toast.LENGTH_LONG).show();

        }
        catch (Exception ex)
        {
           // Toast.makeText(Product_Frag_details.this,""+ex.getMessage(),Toast.LENGTH_LONG).show();
        }
//        imagesAdapter.notifyDataSetChanged();
        // makeGetProductRequest(cat_id,product_id);
       // product_images_adapter=new Product_images_Adapter(Product_Frag_details.this,image_list);
        imagesAdapter=new Produccts_images_adapter(Product_details.this,image_list);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setAdapter(imagesAdapter);

//        listView.setAdapter(product_images_adapter);

    }

    //Get Shop By Catogary Products
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
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();
                            Toast.makeText(Product_details.this, "There are no other size", Toast.LENGTH_LONG).show();
                        } else if (sdf.equals(null)) {
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();
                            Toast.makeText(Product_details.this, "There are no other size", Toast.LENGTH_LONG).show();
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

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Product_details.this, android.R.layout.simple_list_item_2, android.R.id.text1, str);

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
                Toast.makeText(Product_details.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
               // VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Product_details.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        return list;
    }

    //For More color function

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
                    String status = response.getString("response");
                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();

                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();

                    if(status.equals("true")) {


                        JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        // List list1=new ArrayList();

                       // String sdf = jsonObject.getString("size");
                        String sdf = jsonObject.getString("color");

                        if (sdf.isEmpty()) {
                          //  varient.setVisibility( View.GONE );
                            pg.setVisibility(View.GONE);
                            dialog.dismiss();

                            Toast.makeText(Product_details.this, "There are no other color", Toast.LENGTH_LONG).show();
                        } else if (sdf.equals(null)) {
                          //  varient.setVisibility( View.GONE );
                            pg.setVisibility(View.GONE);

                            dialog.dismiss();
                            Toast.makeText(Product_details.this, "There are no other color", Toast.LENGTH_LONG).show();
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

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Product_details.this, android.R.layout.simple_list_item_2, android.R.id.text1, str);

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
                Toast.makeText(Product_details.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Product_details.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // return list;
        return list;
    }


    private void updateintent() {
        Intent updates = new Intent("Grocery_cart");
        updates.putExtra("type", "update");
        Product_details.this.sendBroadcast(updates);
    }


    private void makeGetProductColorSizeRequest(String cat_id, String product_id) {

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



                try {
                    String status = response.getString("responce");
                    //    Toast.makeText(Product_Frag_details.this,"asssssssssj"+response,Toast.LENGTH_LONG).show();

                    //    Toast.makeText(Product_Frag_details.this,""+status.toString()+"\n ",Toast.LENGTH_LONG).show();

                    if(status.equals("true")) {


                        JSONArray jsonArray = response.getJSONArray("data");

                        JSONObject jsonObject = jsonArray.getJSONObject(0);



                        // String sdf = jsonObject.getString("size");
                        String sdf = jsonObject.getString("color");
                        String size=jsonObject.getString("size");

                      if(sdf.equals("") )
                      {

                              txtColor.setVisibility(View.INVISIBLE);


                      }
                      else if( sdf.equals(null))
                      {
                          txtColor.setVisibility(View.INVISIBLE);
                      }

                        if(size.equals("") )
                        {

                            txtSize.setVisibility(View.INVISIBLE);


                        }
                        else if( size.equals(null))
                        {
                            txtSize.setVisibility(View.INVISIBLE);
                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product_details.this,"Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                // VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(Product_details.this, getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        // return list;


    }

}
