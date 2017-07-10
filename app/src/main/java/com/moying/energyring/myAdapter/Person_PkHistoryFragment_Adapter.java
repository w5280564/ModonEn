package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Committed_Model;
import com.moying.energyring.Model.DayPkProject_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_PkHistoryFragment_Adapter extends RecyclerView.Adapter<Person_PkHistoryFragment_Adapter.MyViewHolder> {
    DayPkProject_Model otherList;
    Context context;
    private int myposition;

    public Person_PkHistoryFragment_Adapter(Context context, DayPkProject_Model otherList) {
        this.otherList = otherList;
        this.context = context;
    }

    public void addMoreData(List<Committed_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_pkhistoryfragment_adapter, null));
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

        DayPkProject_Model.DataBean oneData = otherList.getData().get(position);
        holder.time_Txt.setText(StaticData.Datatypetwo(oneData.getCreateTime()));

        if (oneData.getFilePath() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.content_simple.setImageURI(contentUri);
        }
        holder.name_Txt.setText(oneData.getProjectName());
        holder.hostory_Txt.setText(oneData.getReportFre()+"条记录");
    }

    @Override
    public int getItemCount() {
        return (otherList == null) ? 0 : otherList.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView time_Txt, name_Txt, hostory_Txt;
        private SimpleDraweeView content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            hostory_Txt = (TextView) itemView.findViewById(R.id.hostory_Txt);
            StaticData.ViewScale(mu_Rel, 710, 0);
            StaticData.ViewScale(content_simple, 190, 190);
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