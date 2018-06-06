package com.dxs.stc.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dxs.stc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by hl at 2018/6/5
 * PopupAdapter
 */
public class PopupAdapter extends BaseAdapter {
    private Context myContext;
    private LayoutInflater inflater;
    private List<String> myItems;
    private final Object mLock = new Object();

    public PopupAdapter(Context context, List<String> items) {
        this.myContext = context;
        this.myItems = items;
        inflater = LayoutInflater.from(myContext);

    }

    @Override
    public int getCount() {
        return myItems.size();
    }

    @Override
    public String getItem(int position) {
        return myItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PopupHolder holder = null;
        if (convertView == null) {
            holder = new PopupHolder();
            convertView = inflater.inflate(R.layout.top_popup_item, null);
            holder.itemNameTv = (TextView) convertView
                    .findViewById(R.id.popup_tv);
            convertView.setTag(holder);
        } else {
            holder = (PopupHolder) convertView.getTag();
        }
        String itemName = getItem(position);
        holder.itemNameTv.setText(itemName);
        return convertView;
    }

    private class PopupHolder {
        TextView itemNameTv;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param newItem The object to add at the end of the array.
     */
    public void addAll(List<String> newItem) {
        synchronized (mLock) {
            if (myItems == null) {
                myItems = new ArrayList<>();
            }
            myItems.clear();
            myItems.addAll(newItem);
        }
        notifyDataSetChanged();
    }


}  
