package com.infoicon.bonjob.setting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomsTextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sumit on 31/1/17.
 */

public class FAQExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private List<String> _listDataChild;
    private int preIndex;

    public FAQExpandableListAdapter(Context context, List<String> listDataHeader,
                                    List<String> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        preIndex = 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_list_item, null);
        }

        CustomsTextView textViewAns = (CustomsTextView) convertView
                .findViewById(R.id.textViewAns);

        textViewAns.setText(childText);
        preIndex = groupPosition;



       /* RelativeLayout relativeLayoutMain= (RelativeLayout) parent.findViewById(R.id.mainLayout);
        if(isChildSelectable(groupPosition,childPosition)){
            relativeLayoutMain.setBackgroundResource(R.drawable.three_side_bottom_less_bluebg);
        }else {
            relativeLayoutMain.setBackgroundResource(R.drawable.round_button_blue);
        }*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exp_group_item, null);
        }

        CustomsTextView textViewQues = (CustomsTextView) convertView.findViewById(R.id.textViewQues);
        CustomsTextView tvIndicator = (CustomsTextView) convertView.findViewById(R.id.tvIndicator);

        textViewQues.setText(headerTitle);


        RelativeLayout relativeLayoutMain = (RelativeLayout) convertView.findViewById(R.id.mainLayout);
        if (isExpanded) {
            tvIndicator.setText("-");
            relativeLayoutMain.setBackgroundResource(R.drawable.three_side_bottom_less_bluebg);
        } else {
            tvIndicator.setText("+");
            relativeLayoutMain.setBackgroundResource(R.drawable.round_button_blue2);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
