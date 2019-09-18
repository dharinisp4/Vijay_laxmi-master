package Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import trolley.tcc.MainActivity;
import trolley.tcc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Empty_wishlist_fragment extends Fragment {
    private static String TAG = Empty_wishlist_fragment.class.getSimpleName();

    RelativeLayout Shop_now;


    public Empty_wishlist_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate( R.layout.fragment_empty_wishlist, container, false );
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.wishlist));

        Shop_now = (RelativeLayout) view.findViewById(R.id.btn_shopnow);
        Shop_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.Fragment fm = new Home_fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
                        .addToBackStack(null).commit();
            }
        });
        return view ;
    }

}
