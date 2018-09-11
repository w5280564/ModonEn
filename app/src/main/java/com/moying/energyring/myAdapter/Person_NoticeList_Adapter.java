package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moying.energyring.Model.Notice_NoticeList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_NoticeList_Adapter extends RecyclerView.Adapter<Person_NoticeList_Adapter.MyViewHolder> {
    List<Notice_NoticeList_Model.DataBean> otherList;
    private Notice_NoticeList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_NoticeList_Adapter(Context context, List<Notice_NoticeList_Model.DataBean> otherList, Notice_NoticeList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Notice_NoticeList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_noticelist_adapter, null));
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

        Notice_NoticeList_Model.DataBean oneData = otherList.get(position);
        holder.name_Txt.setText(oneData.getNoticeEvent());
        holder.ceontent_Txt.setText(oneData.getNoticeContent());

        holder.time_Txt.setText(StaticData.getDate(oneData.getCreateTime()));
    }



    @Override
    public int getItemCount() {
//        return 10;
        return (otherList == null) ? 0 : otherList.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView name_Txt,ceontent_Txt,time_Txt;
        private  LinearLayout notice_Lin;

        public MyViewHolder(View itemView) {
            super(itemView);
            notice_Lin = (LinearLayout) itemView.findViewById(R.id.notice_Lin);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            ceontent_Txt = (TextView) itemView.findViewById(R.id.ceontent_Txt);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);

            StaticData.ViewScale(notice_Lin, 710, 0);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener listener) {
        mOnItemClickLitener = listener;
    }



}
