package trolley.tcc;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.SelectCityListViewAdapter;
import Config.BaseURL;
import Config.SharedPref;
import trolley.tcc.networkconnectivity.NetworkConnection;

public class SelectCity extends AppCompatActivity {

    int Selected_City = 0;
    String SelectCity = "";
    ArrayList<String> City_List = new ArrayList<>();
    String City_Id;
    ArrayList<String> CITY_LIST_ID = new ArrayList<>();
    LinearLayout City_Selected;
    final Context context = this;
    TextView textView;
    private JsonObject Json;
    String text;
    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_city);


        City_Selected = (LinearLayout) findViewById(R.id.edit_city);
        textView = (TextView) findViewById(R.id.city);
        City_Selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_City();

            }
        });


        RelativeLayout Next = (RelativeLayout) findViewById(R.id.btn_next);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectCity.equals("")) {
                    Toast.makeText(context, "Please Select City", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(context, SelectStore.class);
                    intent.putExtra("city_id", City_Id);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);


                }
            }
        });
    }


//
//

    //City Dialog
    private void SelectCityDialog() {

        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_city_dialog);
        final ListView listView = (ListView) dialog.findViewById(R.id.list_city);
        SelectCityListViewAdapter sec = new SelectCityListViewAdapter(SelectCity.this, City_List);
        listView.setAdapter(sec);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SelectCity = (String) adapterView.getItemAtPosition(position);
                textView.setText(StringUtils.capitalize(City_List.get(position).toLowerCase().trim()));
                SelectCity = textView.getText().toString();
                Selected_City = position + 1;
                City_Id = ("" + CITY_LIST_ID.get(position));
                SharedPref.putString(SelectCity.this, BaseURL.CITY_ID, City_Id);
                dialog.dismiss();
            }
        });
        dialog.getWindow().getDecorView().setTop(100);
        dialog.getWindow().getDecorView().setLeft(100);
        dialog.show();

    }

    private void Get_City() {
        if (NetworkConnection.connectionChecking(getApplicationContext())) {
            Json = new JsonObject();
            Ion.with(SelectCity.this).load(BaseURL.BASE_URL+"index.php/api/city")
                    .setTimeout(15000).setJsonObjectBody(Json).asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (e == null) {
                        Log.e("result", result);
                        try {
                            JSONObject js = new JSONObject(result);
                            {
                                JSONArray obj = js.getJSONArray("city");
                                City_List.clear();
                                CITY_LIST_ID.clear();
                                for (int i = 0; i < obj.length(); i++) {
                                    City_List.add("" + obj.getJSONObject(i).optString("city_name"));
                                    CITY_LIST_ID.add("" + obj.getJSONObject(i).optString("city_id"));

                                }
                                Log.e("Size", "" + City_List.size());
                                Log.e("result", js.toString() + "\n" + js.getJSONArray("city") + "\n"
                                        + obj.getJSONObject(0).optString("city_name"));
                            }
                            SelectCityDialog();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connnection", Toast.LENGTH_SHORT).show();
        }
    }
}
