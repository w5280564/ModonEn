package com.moying.energyring.waylenBaseView.groupadapter;

import android.content.Context;

import com.moying.energyring.Model.pk_Project_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 */
public class GroupedListAdapter extends GroupedRecyclerViewAdapter {

    private pk_Project_Model mGroups;

    public GroupedListAdapter(Context context, pk_Project_Model groups) {
        super(context);
        mGroups = groups;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<pk_Project_Model.DataBean.ProjectListBean> children = mGroups.getData().get(groupPosition).get_Project_List();
//        ArrayList<ChildEntity> children = mGroups.get(groupPosition).getChildren();
        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
//        if (groupPosition == 0) {
            return false;
//        } else {
//            return true;
//        }
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.adapter_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.adapter_footer;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.group_adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
//        GroupEntity entity = mGroups.get(groupPosition);
//        holder.setText(R.id.tv_header, entity.getHeader());
        StaticData.ViewScale(holder.get(R.id.my_Lin), 0, 126);
        StaticData.ViewScale(holder.get(R.id.line_View), 4, 24);
        pk_Project_Model.DataBean oneData = mGroups.getData().get(groupPosition);
        holder.setText(R.id.tv_header, oneData.getProjectTypeName());
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
//        GroupEntity entity = mGroups.get(groupPosition);
//        holder.setText(R.id.tv_footer, entity.getFooter());
        pk_Project_Model.DataBean oneData = mGroups.getData().get(groupPosition);
        holder.setText(R.id.tv_footer, oneData.getProjectTypeName());
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
//        ChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
//        StaticData.ViewScale(holder.get(R.id.tv_child),0,144);
//        holder.setText(R.id.tv_child, oneData.getProjectName());
        StaticData.ViewScale(holder.get(R.id.name_Txt),0,126);
        pk_Project_Model.DataBean.ProjectListBean oneData = mGroups.getData().get(groupPosition).get_Project_List().get(childPosition);
        holder.setText(R.id.name_Txt, oneData.getProjectName());
    }
}
