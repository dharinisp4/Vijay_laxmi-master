package trolley.tcc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import Config.BaseURL;
import util.Session_management;

/**
 * This is the sample app which will make use of the PG SDK. This activity will
 * show the usage of Paytm PG SDK API’s.
 **/

public class Paytm extends Activity {
    private Session_management sessionManagement;
    String orderId;
    //Test
    TextView order_id_txt;
    EditText order_res;
    EditText edt_email, edt_mobile, edt_amount;
    String url = BaseURL.BASE_URL+"paytm/generateChecksumPost.php";
    Map paramMap = new HashMap();
    String mid = "BISHTT45712521385572", order_id = "ORDER_ID", cust_id = "CUST_ID", callback = "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=<ORDER_ID>", industry_type = "INDUS_TYPE", txn_amount = "TXN_AMOUNT", checksum = "CHECKSUM", mobile = "MOBILE_NO", email = "EMAIL", channel_id = "WAP";
    String website = "APP_STAGING";
    String getuser_id, get_email, gte_phoe;
    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sessionManagement = new Session_management(this);
        getuser_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
        get_email = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
        gte_phoe = sessionManagement.getUserDetails().get(BaseURL.KEY_MOBILE);
        String total_rs = getIntent().getStringExtra("total");
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_amount = (EditText) findViewById(R.id.edt_amount);
        edt_amount.setText(total_rs);
        edt_mobile.setText(gte_phoe);
        edt_email.setText(get_email);
    }

    // This is to refresh the order id: Only for the Sample App’s purpose.
    @Override
    protected void onStart() {
        super.onStart();
        initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void initOrderId() {
        Random r = new Random(System.currentTimeMillis());
        orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
                + r.nextInt(10000);
        order_id_txt = (TextView) findViewById(R.id.order_id);
        order_id_txt.setText(orderId);

    }

    public void onStartTransaction(View view) throws InterruptedException, ExecutionException {


           PaytmPGService Service = PaytmPGService.getStagingService();
         //   PaytmPGService Service = PaytmPGService.getProductionService();

            Log.d("before request", "some");
            String edtemail = edt_email.getText().toString().trim();
            String edtmobile = edt_mobile.getText().toString().trim();
            String edtamount = edt_amount.getText().toString().trim();
            JSONObject postData = new JSONObject();

            HashMap<String, String> stringHashMap = new HashMap<>();
            stringHashMap.put("orderid", orderId);
            stringHashMap.put("email", edtemail);
            stringHashMap.put("mobile", edtmobile);
            stringHashMap.put("amount", edtamount);

            SendDeviceDetails sendDeviceDetails = null;
            try {
                sendDeviceDetails = new SendDeviceDetails(url, getPostDataString(stringHashMap), Service);
                sendDeviceDetails.execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

    }


    @SuppressLint("StaticFieldLeak")
    private class SendDeviceDetails extends AsyncTask<String, Void, String> {
        String url, data;
        PaytmPGService Service;

        public SendDeviceDetails(String url, String data, PaytmPGService Service) {
            this.url = url;
            this.data = data;
            this.Service = Service;
        }

        @Override
        protected String doInBackground(String... params) {

            String data1 = "";

            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes("PostData=" + params[1]);
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data1 += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data1;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            Log.e("TAG", result); // this is expecting a response code to be sent from your server upon receiving the POST data
            //String json = (String) myAsyncTask.execute(url).get();
            JSONObject mJsonObject = null;
            try {
                mJsonObject = new JSONObject(result);
                mid = mJsonObject.getString("MID");
                order_id = mJsonObject.getString("ORDER_ID");
                getuser_id = mJsonObject.getString("CUST_ID");
                callback = mJsonObject.getString("CALLBACK_URL");
                industry_type = mJsonObject.getString("INDUSTRY_TYPE_ID");
                channel_id = mJsonObject.getString("CHANNEL_ID");
                txn_amount = mJsonObject.getString("TXN_AMOUNT");
                checksum = mJsonObject.getString("CHECKSUMHASH");
                mobile = mJsonObject.getString("MOBILE_NO");
                email = mJsonObject.getString("EMAIL");
                website = mJsonObject.getString("WEBSITE");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Log.d("after request", "some");

            paramMap.put("MID", mid);
            paramMap.put("ORDER_ID", order_id);
            paramMap.put("CUST_ID", getuser_id);
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
            paramMap.put("CHANNEL_ID", channel_id);
            paramMap.put("TXN_AMOUNT", txn_amount);
            paramMap.put("WEBSITE", website);
            paramMap.put("EMAIL", email);
            paramMap.put("MOBILE_NO", mobile);
            paramMap.put("CALLBACK_URL", callback);


            PaytmOrder Order = new PaytmOrder(paramMap);

            PaytmMerchant Merchant = new PaytmMerchant(
                    BaseURL.BASE_URL+"paytm/generateChecksum.php",
                    BaseURL.BASE_URL+"paytm/verifyChecksum.php");

            Service.initialize(Order, Merchant, null);

            Service.startPaymentTransaction(Paytm.this, true, true,
                    new PaytmPaymentTransactionCallback() {

                        @Override
                        public void someUIErrorOccurred(String inErrorMessage) {
                            Toast.makeText(getApplicationContext(), "Payment Transaction response " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                        }


                        @Override
                        public void onTransactionSuccess(Bundle inResponse) {
                            Log.d("LOG", "Payment Transaction :" + inResponse);
                            Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                            order_res = (EditText) findViewById(R.id.order_res);
                            order_res.setText(inResponse.toString());

                            String response = inResponse.getString("RESPMSG");
                            if (response.equals("Txn Successful.")) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onTransactionFailure(String s, Bundle bundle) {

                        }

                        @Override
                        public void networkNotAvailable() {
                            Toast.makeText(getApplicationContext(), "Network", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void clientAuthenticationFailed(String inErrorMessage) {
                            Toast.makeText(getApplicationContext(), "clientAuthenticationFailed" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onErrorLoadingWebPage(int iniErrorCode,
                                                          String inErrorMessage, String inFailingUrl) {

                            Toast.makeText(getApplicationContext(), "onErrorLoadingWebPage" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();

                        }

                        // had to be added: NOTE
                        @Override
                        public void onBackPressedCancelTransaction() {
                            Toast.makeText(getApplicationContext(), "onBackPressedCancelTransaction", Toast.LENGTH_LONG).show();

// TODO Auto-generated method stub
                        }


                    });
        }
    }


    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }


}