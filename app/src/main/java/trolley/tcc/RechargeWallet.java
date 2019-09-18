package trolley.tcc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Config.BaseURL;
import Config.SharedPref;

import trolley.tcc.networkconnectivity.NetworkConnection;
import trolley.tcc.networkconnectivity.NetworkError;
import util.Session_management;

import static com.android.volley.VolleyLog.TAG;

public class RechargeWallet extends AppCompatActivity implements PaymentResultListener {
    private Session_management sessionManagement;
    EditText Wallet_Ammount;
    RelativeLayout Recharge_Button;
    String ammount;
    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_wallet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.rech_wallet));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RechargeWallet.this, MainActivity.class);
                startActivity(intent);
            }
        });
        sessionManagement = new Session_management(RechargeWallet.this);
        final String email = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
        final String mobile = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
        final String name = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
        Wallet_Ammount = (EditText) findViewById(R.id.et_wallet_ammount);
        Recharge_Button = (RelativeLayout) findViewById(R.id.recharge_button);

        Recharge_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ammount = Wallet_Ammount.getText().toString();
                //Recharge_wallet();
                startPayment(name,ammount,email,mobile);

            }
        });
    }
    public void startPayment(String name,String amount,String email,String phone) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */


        final Activity activity = this;

        Checkout co = new Checkout();

        try {

            JSONObject options = new JSONObject();

            options.put("name", name);
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");

            options.put("amount", Integer.parseInt(amount)*100);

            JSONObject preFill = new JSONObject();

            preFill.put("email", email);

            preFill.put("contact", phone);

            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Recharge_wallet();
            Intent intent = new Intent(RechargeWallet.this, ThanksOrder.class);
            startActivity(intent);
            overridePendingTransition(0, 0);

            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent intent = new Intent(RechargeWallet.this, OrderFail.class);
        startActivity(intent);
        overridePendingTransition(0, 0);

    }

    private void Recharge_wallet() {
        final String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
        if (NetworkConnection.connectionChecking(this)) {
            RequestQueue rq = Volley.newRequestQueue(this);
            StringRequest postReq = new StringRequest(Request.Method.POST, BaseURL.BASE_URL+"index.php/api/recharge_wallet",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("eclipse", "Response=" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                if (object.optString("success").equalsIgnoreCase("success")) {
                                    String wallet_amount = object.getString("wallet_amount");
                                    SharedPref.putString(RechargeWallet.this, BaseURL.KEY_WALLET_Ammount, wallet_amount);
                                } else {
                                    // Toast.makeText(DashboardPage.this, "" + jObj.optString("msg"), Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error [" + error + "]");
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", user_id);
                    params.put("wallet_amount", ammount);
                    return params;
                }
            };
            rq.add(postReq);
        } else {
            Intent intent = new Intent(RechargeWallet.this, NetworkError.class);
            startActivity(intent);
        }

    }

}
