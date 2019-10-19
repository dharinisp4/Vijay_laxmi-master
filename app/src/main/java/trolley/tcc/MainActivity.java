package trolley.tcc;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
//import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Fragment.*;
import Adapter.ExpandableListAdapter;
import Config.BaseURL;
import Config.SharedPref;
import Fonts.CustomTypefaceSpan;
import Fragment.About_us_fragment;
import Fragment.Contact_Us_fragment;
import Fragment.Empty_cart_fragment;
import Fragment.Home_fragment;
import Fragment.Cart_fragment;
import Fragment.Edit_profile_fragment;
import Fragment.Shop_Now_fragment;
import Fragment.Terms_and_Condition_fragment;
import Fragment.Wallet_fragment;
import Model.MenuModel;
import trolley.tcc.networkconnectivity.NetworkError;
import util.CartHandler;
import util.ConnectivityReceiver;
import util.DatabaseCartHandler;
import util.DatabaseHandler;
import util.DatabaseHandlerWishList;
import util.Session_management;
import util.WishlistHandler;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView totalBudgetCount, totalBudgetCount2, totalBudgetCount3, tv_name, powerd_text;
    private ImageView iv_profile;
    private DatabaseHandler dbcart;
    private DatabaseCartHandler db_cart;
    private WishlistHandler db_wish ;
    private Session_management sessionManagement;
    private Menu nav_menu;
    ImageView imageView;
    TextView mTitle;
    LinearLayout viewpa;
    Toolbar toolbar;
    String language = "";
    LinearLayout My_Order, My_Wishlist, My_Walllet, My_Cart;
    int padding = 0;
    LinearLayout linearLayout_login;
    ImageView open , close;
    private TextView txtRegId;
    NavigationView navigationView;
    LinearLayout Change_Store;
    String Store_Count;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    @Override
    protected void attachBaseContext(Context newBase) {


        newBase = LocaleHelper.onAttach(newBase);

        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        String token;
        //token = FirebaseInstanceId.getInstance().getToken();
        //Log.d("MYTAG", "This is your Firebase token" + token);
        sharedPreferences = getSharedPreferences("lan", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString("language", "english");
        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);

                if (key.equals("MainActivity") && value.equals("True")) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                    finish();
                }

            }
            subscribeToPushService();
        }


        Store_Count = SharedPref.getString(MainActivity.this, BaseURL.KEY_STORE_COUNT);


//

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPadding(padding, toolbar.getPaddingTop(), padding, toolbar.getPaddingBottom());


        setSupportActionBar(toolbar);
        for (int i = 0; i < toolbar.getChildCount(); i++) {

            View view = toolbar.getChildAt(i);

            if (view instanceof TextView) {
                TextView textView = (TextView) view;

                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "Font/Bold.ttf");
                textView.setTypeface(myCustomFont);
            }


        }
        getSupportActionBar().setTitle(getResources().getString(R.string.name));


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }


        dbcart = new DatabaseHandler(this);
        db_cart=new DatabaseCartHandler(this);
        db_wish = new WishlistHandler( this );

        checkConnection();

        sessionManagement = new Session_management(MainActivity.this);

       // expandableListView = findViewById(R.id.expandableListView);
       // prepareMenuData();
       // populateExpandableList();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        View headerView = navigationView.getHeaderView(0);
//        navigationView.getBackground().setColorFilter(0x80000000, PorterDuff.Mode.MULTIPLY);
        navigationView.setNavigationItemSelectedListener(this);
        nav_menu = navigationView.getMenu();
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        Change_Store = (LinearLayout) header.findViewById(R.id.change_store_btn);
        viewpa = (LinearLayout) header.findViewById(R.id.viewpa);
        if (sessionManagement.isLoggedIn()) {
            viewpa.setVisibility(View.VISIBLE);
        }


        if (Store_Count.equals("1")) {
            Change_Store.setVisibility(View.INVISIBLE);
        } else if (Store_Count.equals("2")) {
            Change_Store.setVisibility(View.VISIBLE);
            Change_Store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SelectStore.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
        }
        iv_profile = (ImageView) header.findViewById(R.id.iv_header_img);
        tv_name = (TextView) header.findViewById(R.id.tv_header_name);
        My_Order = (LinearLayout) header.findViewById(R.id.my_orders);
        My_Wishlist = (LinearLayout) header.findViewById(R.id.my_wishlist);
        My_Walllet = (LinearLayout) header.findViewById(R.id.my_wallet);
        My_Cart = (LinearLayout) header.findViewById(R.id.my_cart);
        open=header.findViewById( R.id.Save );
        close=header.findViewById( R.id.close );


        linearLayout_login=(LinearLayout)header.findViewById(R.id.linearLayout_login);


        My_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, My_Order_activity.class);
                startActivity(intent);

            }
        });
        My_Wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fm = new Wishlist();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            }
        });
        My_Walllet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fm = new Wallet_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            }
        });

        My_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db_cart.getCartCount() > 0) {
                    Fragment fm = new Cart_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();

                } else {
                    Fragment fm = new Empty_cart_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();


                    //  Toast.makeText(MainActivity.this, "No Item in Cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sessionManagement.isLoggedIn()) {
                    Fragment fm = new Edit_profile_fragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                            .addToBackStack(null).commit();

                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(0, 0);
                }
            }
        });


        updateHeader();

        sideMenu();


        if (savedInstanceState == null) {
            Fragment fm = new Home_fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.contentPanel, fm, "Home_fragment")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        getFragmentManager().

                addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        try {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            Fragment fr = getFragmentManager().findFragmentById(R.id.contentPanel);

                            final String fm_name = fr.getClass().getSimpleName();
                            Log.e("backstack: ", ": " + fm_name);
                            if (fm_name.contentEquals("Home_fragment")) {
                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                                toggle.setDrawerIndicatorEnabled(true);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                                toggle.syncState();

                            } else if (fm_name.contentEquals("My_order_fragment") ||
                                    fm_name.contentEquals("Thanks_fragment")) {
                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                                toggle.setDrawerIndicatorEnabled(false);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                toggle.syncState();

                                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Fragment fm = new Home_fragment();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                                                .addToBackStack(null).commit();
                                    }
                                });
                            } else {

                                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                                toggle.setDrawerIndicatorEnabled(false);
                                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                                toggle.syncState();

                                toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        onBackPressed();
                                    }
                                });
                            }

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });


//        if (sessionManagement.getUserDetails().
//                get(BaseURL.KEY_ID) != null && !sessionManagement.getUserDetails().
//                get(BaseURL.KEY_ID).equalsIgnoreCase())
//
//        {
//            MyFirebaseRegister fireReg = new MyFirebaseRegister(this);
//            fireReg.RegisterUser(sessionManagement.getUserDetails().get(BaseURL.KEY_ID));
//        }

    }


    public void updateHeader() {
        if (sessionManagement.isLoggedIn()) {
            viewpa.setVisibility(View.VISIBLE);
            String getname = sessionManagement.getUserDetails().get(BaseURL.KEY_NAME);
            String getimage = sessionManagement.getUserDetails().get(BaseURL.KEY_IMAGE);
            String getemail = sessionManagement.getUserDetails().get(BaseURL.KEY_EMAIL);
            SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
            String previouslyEncodedImage = shre.getString("image_data", "");
            if (!previouslyEncodedImage.equalsIgnoreCase("")) {
                byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                iv_profile.setImageBitmap(bitmap);
            }
//            Glide.with(this)
//                    .load(BaseURL.IMG_PROFILE_URL + getimage)
//                    .placeholder(R.drawable.icon)
//                    .crossFade()
//                    .into(iv_profile);
            tv_name.setText(getname);

        }
        else
        {
           //linearLayout_login.setVisibility(View.GONE);
        }
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "Font/Bold.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    public void sideMenu() {

        if (sessionManagement.isLoggedIn()) {
            //  tv_number.setVisibility(View.VISIBLE);
            nav_menu.findItem(R.id.nav_logout).setVisible(true);
//            nav_menu.findItem(R.id.nav_powerd).setVisible(true);

//            nav_menu.findItem(R.id.nav_user).setVisible(true);
        } else {

            //tv_number.setVisibility(View.GONE);
            tv_name.setText(getResources().getString(R.string.btn_login));
            tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            });
            nav_menu.findItem(R.id.nav_logout).setVisible(false);

            //            nav_menu.findItem(R.id.nav_user).setVisible(false);
        }
    }

    public void setFinish() {
        finish();
    }

    public void setCartCounter(String totalitem) {
        try {
            totalBudgetCount.setText(totalitem);
        } catch (Exception e) {

        }
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);



        final MenuItem cart = menu.findItem(R.id.action_cart);
        cart.setVisible(true);
//        final MenuItem notify = menu.findItem(R.id.action_notify);
//        notify.setVisible(true);
        final MenuItem search = menu.findItem(R.id.action_search);
        search.setVisible(true);
        final MenuItem wish = menu.findItem(R.id.action_wish);
        wish.setVisible(true);

        View cartcount = cart.getActionView();
        cartcount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                menu.performIdentifierAction(cart.getItemId(), 0);
            }
        });
        View wishcount = wish.getActionView();
        wishcount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                menu.performIdentifierAction(wish.getItemId(), 0);
            }
        });
//        View notifycount = notify.getActionView();
//        notifycount.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                menu.performIdentifierAction(notify.getItemId(), 0);
//            }
//        });
        totalBudgetCount = (TextView) cartcount.findViewById( R.id.actionbar_notifcation );
        totalBudgetCount.setText( "" + db_cart.getCartCount() );
        if (db_cart.getCartCount()<=0) {
            totalBudgetCount.setVisibility( View.GONE );

        }
        totalBudgetCount2 = (TextView) wishcount.findViewById( R.id.actionbar_notifcation );
        totalBudgetCount2.setText( "" + db_wish.getWishtableCount() );
        if(db_wish.getWishtableCount()<=0) {
            totalBudgetCount2.setVisibility( View.GONE );

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       /* if (id == R.id.action_language) {
            openLanguageDialog();
        }*/

        if (id == R.id.action_cart) {
            if (db_cart.getCartCount() > 0) {
                Fragment fm = new Cart_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            } else {
                Fragment fm = new Empty_cart_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            }
            return true;
        }
//        else if (id == R.id.action_notify)
//        {
//            Fragment fm = new Notification_fragment();
//            FragmentManager fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                    .addToBackStack(null).commit();
//        }
        else if (id==R.id.action_wish)
        {
          // db_wish.clearWishtable();
            if (db_wish.getWishtableCount() > 0) {

           //     Toast.makeText(MainActivity.this,""+db_wish.getWishtableCount(),Toast.LENGTH_LONG).show();
                Fragment fm = new Wishlist();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            } else {
               // Toast.makeText(MainActivity.this,""+db_wish.getWishtableCount(),Toast.LENGTH_LONG).show();
                Fragment fm = new Empty_wishlist_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            }
        }
        else if (id== R.id.action_search)
        {
            Fragment fm = new Search_fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }


        return super.onOptionsItemSelected(item);
    }

    private void openLanguageDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_language, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        TextView lEnglish = v.findViewById(R.id.l_english);
        TextView lSpanish = v.findViewById(R.id.l_arabic);
        final AlertDialog dialog = builder.create();

        lEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleHelper.setLocale(getApplication(), "en");
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                editor.putString("language", "english");
                editor.apply();


                recreate();
                dialog.dismiss();
            }
        });
        lSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleHelper.setLocale(getApplication(), "ar");
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("language", "spanish");
                editor.apply();


                recreate();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("ResourceType")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fm = null;
        Bundle args = new Bundle();
        if (id == R.id.nav_shop_now) {
            fm = new Shop_Now_fragment();
        } else if (id == R.id.nav_my_profile) {
            if (sessionManagement.isLoggedIn()) {
                 fm = new Edit_profile_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();

            } else {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        }
        /*else if(id == R.id.nav_my_wishlist)


        {
            fm =new Wishlist();
        }*/
//        else if (id == R.id.nav_support) {
//            String smsNumber = "919584267640";
//            Intent sendIntent = new Intent("android.intent.action.MAIN");
//            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
//            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
//            startActivity(sendIntent);
//
//        }
        else if (id == R.id.nav_aboutus) {
            toolbar.setTitle("About");
            fm = new About_us_fragment();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();

        } else if (id == R.id.nav_policy) {
            fm = new Terms_and_Condition_fragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
//            args.putString("url", BaseURL.GET_TERMS_URL);
//            args.putString("title", getResources().getString(R.string.nav_terms));
//            fm.setArguments(args);
        }  else if (id == R.id.nav_contact) {
            fm = new Contact_Us_fragment();
            args.putString("url", BaseURL.GET_SUPPORT_URL);
            args.putString("title", getResources().getString(R.string.nav_terms));
            fm.setArguments(args);
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_logout) {
            sessionManagement.logoutSession();
            viewpa.setVisibility(View.GONE);
            finish();

        } /*else if (id == R.id.nav_powerd) {
            // stripUnderlines(textView);
            String url = "http://sameciti.com";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            finish();
        }*/

        if (fm != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                    .addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        try {
            super.startActivityForResult(intent, requestCode);
        } catch (Exception ignored) {
        }

    }

    private void stripUnderlines(TextView textView) {
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);
            s.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            s.setSpan(span, start, end, 0);
        }
        textView.setText(s);
    }

    public void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi friends i am using ." + " http://play.google.com/store/apps/details?id=" + getPackageName() + " APP");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }


    public void reviewOnApp() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;

        if (!isConnected) {
            Intent intent = new Intent(MainActivity.this, NetworkError.class);
            startActivity(intent);
        }
    }


    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(BaseURL.PREFS_NAME, 0);
        String regId = pref.getString("regId", null);
        Log.e(TAG, "Firebase reg id: " + regId);
        if (!TextUtils.isEmpty(regId)) {
            // txtRegId.setText("Firebase Reg Id: " + regId);
        } else {
            // txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Log.d("Tecmanic", "Subscribed");
//        Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

       // String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
       // Log.d("Tecmanic", token);
        //      Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        // register connection status listener
//        AppController.getInstance().setConnectivityListener(this);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(BaseURL.REGISTRATION_COMPLETE));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(BaseURL.PUSH_NOTIFICATION));
//        NotificationUtils.clearNotifications(getApplicationContext());
//    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        // register reciver

    }
   /* private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Java Tutorials", true, true, ""); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Core Java Tutorial", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Python Tutorials", true, true, ""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, "");
        childModelsList.add(childModel);

        childModel = new MenuModel("Python Fractions", false, false, "");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }*/

   /* private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter( this, headerList, childList );
        expandableListView.setAdapter( expandableListAdapter );

        expandableListView.setOnGroupClickListener( new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get( groupPosition ).isGroup) {
                    if (!headerList.get( groupPosition ).hasChildren) {
                        WebView webView = findViewById( R.id.webView );
                        webView.loadUrl( headerList.get( groupPosition ).url );
                        onBackPressed();
                    }
                }

                return false;
            }
        } );

        expandableListView.setOnChildClickListener( new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get( headerList.get( groupPosition ) ) != null) {
                    MenuModel model = childList.get( headerList.get( groupPosition ) ).get( childPosition );
                    if (model.url.length() > 0) {
                        WebView webView = findViewById( R.id.webView );
                        webView.loadUrl( model.url );
                        onBackPressed();
                    }
                }

                return false;
            }
        } );
        }*/
    }


