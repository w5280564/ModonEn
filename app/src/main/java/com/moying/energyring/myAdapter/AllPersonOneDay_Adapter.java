package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.AllPerson_CalModel;
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class AllPersonOneDay_Adapter extends RecyclerView.Adapter<AllPersonOneDay_Adapter.MyViewHolder> {
    AllPerson_CalModel baseModel;
    Context context;
    private int myposition;

    public AllPersonOneDay_Adapter(Context context, AllPerson_CalModel baseModel) {
        this.baseModel = baseModel;
        this.context = context;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.allpersonoenday_adapter, null));
        return myview;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
        if (baseModel.getData().getTarget_List().get(position) != null) {
            AllPerson_CalModel.DataBean.TargetListBean model = baseModel.getData().getTarget_List().get(position);
            if (model.isIs_Finish()) {
                holder.round_Txt.setBackgroundResource(R.drawable.calendar_item_red);
                holder.finsh_Txt.setText("完成");
            } else {
                holder.round_Txt.setBackgroundResource(R.drawable.calendar_item_gray);
                holder.finsh_Txt.setText("未完成");
            }

            String contentStr = "每日目标  " + model.getProjectName() + model.getReportNum() + model.getProjectUnit();
            holder.content_Txt.setText(contentStr);
        }
    }

    @Override
    public int getItemCount() {
        return (baseModel.getData().getTarget_List() == null) ? 0 : baseModel.getData().getTarget_List().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView round_Txt, content_Txt, finsh_Txt;
        private RelativeLayout my_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            round_Txt = (TextView) itemView.findViewById(R.id.round_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            finsh_Txt = (TextView) itemView.findViewById(R.id.finsh_Txt);
            StaticData.ViewScale(my_Rel, 0, 70);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener listener) {
        this.mOnItemClickLitener = listener;
    }


}
