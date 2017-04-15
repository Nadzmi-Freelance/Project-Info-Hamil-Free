package com.onepage.infohamilfree.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.onepage.infohamilfree.R;


public class ListViewMenuAdapter extends BaseAdapter {
    private Activity context;
    private String[] titles;

    public ListViewMenuAdapter(Activity context, String[] titles) {
        this.context = context;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater;

            viewHolder = new ViewHolder();
            inflater = context.getLayoutInflater();

            convertView = inflater.inflate(R.layout.listview_custom_menu, parent, false);

            viewHolder.tvListIndex = (TextView) convertView.findViewById(R.id.tvListIndex);
            viewHolder.tvListTitle = (TextView) convertView.findViewById(R.id.tvListTitle);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        if(titles[position] != null) {
            viewHolder.tvListIndex.setText(String.valueOf(position + 1));
            viewHolder.tvListTitle.setText(titles[position]);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView tvListIndex, tvListTitle;
    }
}
