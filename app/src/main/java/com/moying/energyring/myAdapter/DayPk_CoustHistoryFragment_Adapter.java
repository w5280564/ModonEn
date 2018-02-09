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
import com.moying.energyring.Model.ReportHistory_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class DayPk_CoustHistoryFragment_Adapter extends RecyclerView.Adapter<DayPk_CoustHistoryFragment_Adapter.MyViewHolder> {
    List<ReportHistory_Model.DataBean> otherList;
    private ReportHistory_Model listModel;
    Context context;
    private int myposition;

    public DayPk_CoustHistoryFragment_Adapter(Context context, List<ReportHistory_Model.DataBean> otherList, ReportHistory_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<ReportHistory_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.daypk_history_adapter, null));
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
        final ReportHistory_Model.DataBean oneData = otherList.get(position);

        StringBuffer sbf = new StringBuffer();
        sbf.append(oneData.getCreateDate().substring(5, 16));
        sbf.insert(6, "\n");

        holder.time_Txt.setText(sbf.toString());
        if (oneData.getFilePath() != null) {
            Uri uri = Uri.parse(oneData.getFilePath());
            holder.project_simple.setImageURI(uri);
        }
        holder.project_Name.setText("#"+oneData.getProjectName());

        if (oneData.getLimit() == 1) {
            holder.count_Txt.setText("第" + oneData.getReportFre() + "次");
        }else{
            NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
            holder.count_Txt.setText(nf.format(oneData.getReportNum()) + oneData.getProjectUnit());
        }
//        if (oneData.getProjectName().equals("早起")){
//            holder.rank_Txt.setVisibility(View.INVISIBLE);
//        }else{
//            holder.rank_Txt.setText("第"+oneData.getRanking()+"名");
//        }

    }

    @Override
    public int getItemCount() {
//        return 20;
        return (otherList == null) ? 0 : otherList.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  SimpleDraweeView project_simple;
        private  TextView time_Txt,project_Name,count_Txt,rank_Txt;
        private RelativeLayout my_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            project_simple = (SimpleDraweeView) itemView.findViewById(R.id.project_simple);
            project_Name = (TextView) itemView.findViewById(R.id.project_Name);
            count_Txt = (TextView) itemView.findViewById(R.id.count_Txt);
            rank_Txt = (TextView) itemView.findViewById(R.id.rank_Txt);
            rank_Txt.setVisibility(View.GONE);
            StaticData.ViewScale(my_Rel, 0, 120);
            StaticData.ViewScale(project_simple, 80, 80);
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
