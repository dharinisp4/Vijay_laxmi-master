package Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Config.BaseURL;
import trolley.tcc.AppController;
import trolley.tcc.LoginActivity;
import trolley.tcc.MainActivity;
import trolley.tcc.R;
import trolley.tcc.RegisterActivity;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonRequest;
import util.Session_management;

import static trolley.tcc.AppController.TAG;

public class Help_Fragment extends Fragment {
    ProgressDialog loadingBar;
    String language;
    SharedPreferences preferences;
    EditText et_name , et_phone , et_email , et_message ;
    Session_management sessionManagement;
    Button submit ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_help, container, false );
        loadingBar=new ProgressDialog(getActivity());
        loadingBar.setMessage("Loading...");
        loadingBar.setCanceledOnTouchOutside(false);
        ((MainActivity) getActivity()).setTitle("Help");
        sessionManagement = new Session_management(getActivity());
        Button submit = view.findViewById( R.id.submit_button );
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_email=(EditText)view.findViewById( R.id.et_email );
        et_message=(EditText)view.findViewById( R.id.et_mesg );
        String getemail = sessionManagement.getUserDetails().get( BaseURL.KEY_EMAIL);
        String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        String getphone = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
        String getmessage= et_message.getText().toString();
        et_name.setText(getname);
        et_name.setEnabled( false );
        et_phone.setText(getphone);
        et_phone.setEnabled( false );
        et_email.setText( getemail );
        et_email.setEnabled( false );

        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();

                 //   Toast.makeText( getActivity(),"you response is accepted :" +names +""+emails+""+msg+""+phones,Toast.LENGTH_LONG ).show();

               // fetchEnquiry();
            }
        } );




        return view;
    }

    private void attemptRegister() {
        if (ConnectivityReceiver.isConnected()) {

            String getphone = et_phone.getText().toString();
            String getname = et_name.getText().toString();
            String getmessage = et_message.getText().toString();
            String getemail = et_email.getText().toString();

            if(getmessage.isEmpty())
            {
                et_message.setError("please enter some suggestion");
            }
            else
            {
                makeRegisterRequest(getname, getphone, getemail, getmessage);
            }
  //          Toast.makeText( getActivity(),"you response is accepted :" +getname +""+getemail+""+getmessage+""+getphone,Toast.LENGTH_LONG ).show();



        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }
    private void makeRegisterRequest(String name, String mobile,
                                     String email, String message) {

        loadingBar.show();

       String getUsserId=sessionManagement.getUserDetails().get( BaseURL.KEY_ID );
        // Tag used to cancel the request
        String tag_json_obj = "json_suggest_req";

        Map<String, String> params = new HashMap<String, String>();

        params.put("user_id", getUsserId );
        params.put("user_name", name);

        params.put("user_phone", mobile);
        params.put("user_email", email);
        params.put("message", message);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST, BaseURL.PUT_SUGGESTION_URL, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    boolean status = response.getBoolean("responce");
                    if (status) {
                     loadingBar.dismiss();
                        String msg = response.getString("message");
                        Toast.makeText( getActivity(), "" + msg, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(getActivity(),MainActivity.class);
                        startActivity(i);
                   //  finish();
                       // submit.setEnabled(false);

                    } else {
                        loadingBar.dismiss();
                        String error = response.getString("error");
                        submit.setEnabled(true);
                        Toast.makeText(getActivity(), "error" + error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    loadingBar.dismiss();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                loadingBar.dismiss();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


    public void fetchEnquiry() {
        String Url ="https://www.trolleyxpress.com/index.php/api/put_suggestion";
        final String names = et_name.getText().toString();
        final String emails = et_email.getText().toString();
        final String phones = et_phone.getText().toString();
        final String msg = et_message.getText().toString();

        //Toast.makeText( ContactUs.this, "name" + names+"\n email "+emails, Toast.LENGTH_LONG ).show();
        Toast.makeText( getActivity(),"you response is accepted :" +names +""+emails+""+msg+""+phones,Toast.LENGTH_LONG ).show();

        StringRequest stringRequest =  new StringRequest( Request.Method.POST, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject( response );
                        String status = jsonObject.getString("responce");
                        if(status.equals( ("true") )) {
                            Toast.makeText( getActivity(), "response saved" + response, Toast.LENGTH_LONG ).show();
                        }
                        else
                        {
                            Toast.makeText( getActivity(), "response failed" + response, Toast.LENGTH_LONG ).show();
                        }



                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( getActivity() ,"error"+ error.getMessage(),Toast.LENGTH_LONG ).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String ,String> map = new HashMap<>(  );
                map.put( "user_name" ,names );
                map.put("user_email" ,emails);
                map.put("user_phone",phones);
                map.put("message" ,msg);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue( getActivity() );
        requestQueue.add( stringRequest );
    }

}
