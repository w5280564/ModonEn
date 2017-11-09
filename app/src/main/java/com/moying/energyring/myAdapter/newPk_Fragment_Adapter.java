package com.moying.energyring.myAdapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.waylenBaseView.AutoScaleTextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class newPk_Fragment_Adapter extends RecyclerView.Adapter<newPk_Fragment_Adapter.MyViewHolder> {
    private newPk_Model listModel;
    Context context;
    private int myposition;

    public newPk_Fragment_Adapter(Context context, newPk_Model listModel) {
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<EnergyList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.newpkfragment_adapter, null));
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
        newPk_Model.DataBean oneData = listModel.getData().get(position);
        if (oneData.getReportID() == 0) { //0今天没有汇报项目
            setGray(holder);
            if (oneData.getFilePath() != null) {
                Uri contentUri = Uri.parse(String.valueOf(oneData.getGray_FilePath()));
                holder.project_Simple.setImageURI(contentUri);
            }
        } else {
            setYellow(holder);
            if (oneData.getFilePath() != null) {
                Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
                holder.project_Simple.setImageURI(contentUri);
            }
        }
        holder.today_Txt.setText(oneData.getRanking() + "");
        holder.project_Txt.setText(oneData.getProjectName());
//        holder.project_Txt.setText("坐姿收复举腿坐姿收复举腿");
        NumberFormat nf = new DecimalFormat("#.#");//# 0 不显示
        holder.count_Txt.setText(nf.format(oneData.getReportNum()));
        holder.unit_Txt.setText(oneData.getProjectUnit());
        holder.all_Txt.setText("本月累计" + nf.format(oneData.getReport_Num_Month()) + oneData.getProjectUnit());


    }


    @Override
    public int getItemCount() {
//        return 10;
        return (listModel == null) ? 0 : listModel.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AutoScaleTextView project_Txt, all_Txt;
        private TextView count_Txt, unit_Txt, today_Txt;
        private SimpleDraweeView project_Simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            project_Simple = (SimpleDraweeView) itemView.findViewById(R.id.project_Simple);
            project_Txt = (AutoScaleTextView) itemView.findViewById(R.id.project_Txt);
            all_Txt = (AutoScaleTextView) itemView.findViewById(R.id.all_Txt);
            count_Txt = (TextView) itemView.findViewById(R.id.count_Txt);
            unit_Txt = (TextView) itemView.findViewById(R.id.unit_Txt);
            today_Txt = (TextView) itemView.findViewById(R.id.today_Txt);

            StaticData.ViewScale(mu_Rel, 218, 336);
            StaticData.ViewScale(project_Simple, 190, 190);
            StaticData.ViewScale(all_Txt, 0, 40);
            StaticData.ViewScale(today_Txt, 104, 40);
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

    private void setYellow(MyViewHolder holder) {
        holder.today_Txt.setBackgroundResource(R.drawable.today_shap);
        holder.today_Txt.setTextColor(Color.parseColor("#262626"));
        holder.project_Txt.setTextColor(Color.parseColor("#262626"));
        holder.all_Txt.setBackgroundColor(Color.parseColor("#ffd91e"));
        holder.count_Txt.setTextColor(Color.parseColor("#262626"));
        holder.unit_Txt.setTextColor(Color.parseColor("#262626"));
    }

    private void setGray(MyViewHolder holder) {
        holder.today_Txt.setBackgroundResource(R.drawable.today_shap_gray);
        holder.today_Txt.setTextColor(Color.parseColor("#ffffff"));
        holder.project_Txt.setTextColor(Color.parseColor("#aaaaaa"));
        holder.all_Txt.setBackgroundColor(Color.parseColor("#ababab"));
        holder.count_Txt.setTextColor(Color.parseColor("#aaaaaa"));
        holder.unit_Txt.setTextColor(Color.parseColor("#aaaaaa"));

    }

}
