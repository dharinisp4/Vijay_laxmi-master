package trolley.tcc;

import android.app.Dialog;
import android.app.ProgressDialog;
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

public class SelectStore extends AppCompatActivity {

    TextView textStore;
    LinearLayout Store_Selected;
    final Context context = this;
    String SelectStore = "";
    int Selected_Store = 0;
    ArrayList<String> Store_List = new ArrayList<>();
    String Store_Id;
    ArrayList<String> Store_LIST_ID = new ArrayList<>();
    private JsonObject Json;
    String GetCityId;
    ListView listView;
    int count;

    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_store);
        Store_Selected = (LinearLayout) findViewById(R.id.edit_store);
        textStore = (TextView) findViewById(R.id.store);
        Store_Selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_City();


            }
        });

        RelativeLayout Next = (RelativeLayout) findViewById(R.id.go_button);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectStore.equals("")) {
                    Toast.makeText(context, "Please Select Store", Toast.LENGTH_SHORT).show();
                } else {
                    count = listView.getAdapter().getCount();
                    Toast.makeText(getApplicationContext(), "Total number of Items are:" + count, Toast.LENGTH_LONG).show();
                    SharedPref.putString(SelectStore.this, BaseURL.KEY_STORE_COUNT, String.valueOf(count));
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);


                }

            }
        });
    }

    private void SelectCityDialog() {
        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_city_dialog);
        listView = (ListView) dialog.findViewById(R.id.list_city);
        SelectCityListViewAdapter sec = new SelectCityListViewAdapter(SelectStore.this, Store_List);
        listView.setAdapter(sec);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SelectStore = (String) adapterView.getItemAtPosition(position);
                textStore.setText(StringUtils.capitalize(Store_List.get(position).toLowerCase().trim()));
                SelectStore = textStore.getText().toString();
                Selected_Store = position + 1;
                Store_Id = ("" + Store_LIST_ID.get(position));
                SharedPref.putString(SelectStore.this, BaseURL.STORE_ID, Store_Id);
                dialog.dismiss();
            }
        });
        dialog.getWindow().getDecorView().setTop(100);
        dialog.getWindow().getDecorView().setLeft(100);
        dialog.show();
    }

    private void Get_City() {
        final String state_id = SharedPref.getString(SelectStore.this, BaseURL.CITY_ID);
        final ProgressDialog dialog = new ProgressDialog(SelectStore.this);
        Json = new JsonObject();
        Ion.with(SelectStore.this).load(BaseURL.BASE_URL+"index.php/api/store?city_id=" + state_id)
                .setTimeout(15000).setJsonObjectBody(Json).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                if (e == null) {
                    Log.e("result", result);
                    try {
                        JSONObject js = new JSONObject(result);
                        {
                            JSONArray obj = js.getJSONArray("data");
                            Store_List.clear();
                            Store_LIST_ID.clear();
                            if (js.getString("data").equals("")) {
                                Toast.makeText(getApplicationContext(), "Store Not Found", Toast.LENGTH_SHORT).show();
                            } else {
                                for (int i = 0; i < obj.length(); i++) {
                                    Store_List.add("" + obj.getJSONObject(i).optString("user_fullname"));
                                    Store_LIST_ID.add("" + obj.getJSONObject(i).optString("user_id"));

                                }
                                Log.e("Size", "" + Store_List.size());
                                Log.e("result", js.toString() + "\n" + js.getJSONArray("data") + "\n"
                                        + obj.getJSONObject(0).optString("user_fullname"));
                            }
                        }
                        SelectCityDialog();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }


}
