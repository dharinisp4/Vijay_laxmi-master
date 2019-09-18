package trolley.tcc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ThanksOrder extends AppCompatActivity {
    RelativeLayout Go_Home, Track_Order;
    TextView tv_info;
    @Override
    protected void attachBaseContext(Context newBase) {



        newBase = LocaleHelper.onAttach(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahnks_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.thanks));
        Go_Home = (RelativeLayout) findViewById(R.id.go_home);
        Track_Order = (RelativeLayout) findViewById(R.id.tack_order);


        String data = getIntent().getStringExtra("data");

        tv_info = (TextView) findViewById(R.id.tv_thank_info);
        tv_info.setText(Html.fromHtml(data));
        Go_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanksOrder.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Track_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanksOrder.this, My_Order_activity.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
