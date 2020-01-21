package com.infoicon.bonjob.recruiters.candidateProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomCheckedTextView;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse;

import java.util.List;

/**
 * Created by info on 29/5/18.
 */

public class newJobAdapter extends BaseAdapter {

    private Context context;
    private  String[]
     activeJobsBeanList;

    public newJobAdapter(Context context,  String[]
             activeJobsBeanList) {
        this.context = context;
        this.activeJobsBeanList = activeJobsBeanList;

    }

    @Override
    public int getCount() {
        return activeJobsBeanList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        newJobAdapter.ViewHolder viewHolder = null;
        View rowView = view;
        if (rowView == null) {
            viewHolder = new newJobAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.adapter_my_offers_list, viewGroup, false);
          viewHolder.tvJobTitle = (CustomCheckedTextView) rowView.findViewById(R.id.tvJobTitle);
           // viewHolder.tvJobLocation = (CustomsTextView) rowView.findViewById(R.id.tvJobLocation);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (newJobAdapter.ViewHolder) rowView.getTag();
        }

viewHolder.tvJobTitle.setText(activeJobsBeanList[position]);
      //  viewHolder.tvJobLocation.setText(activeJobsBeanList.get(position).getJob_location());

        return rowView;
    }

    private static class ViewHolder {
        CustomCheckedTextView tvJobTitle;
        CustomsTextView tvJobLocation;
    }
    }

