package Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import trolley.tcc.R;

public class SelectStoreListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> item=new ArrayList<>();
    LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;

    public SelectStoreListViewAdapter(Context context, ArrayList<String> item) {
        this.context = context;
        mSelectedItemsIds = new SparseBooleanArray();
        this.item = item;
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.storelist, null);
        TextView textView = (TextView) view.findViewById(R.id.text_store);
        textView.setText(StringUtils.capitalize(item.get(position).toLowerCase().trim()));
        return view;
    }
}


