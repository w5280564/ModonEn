package com.moying.energyring.myAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.Model.newPk_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

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
//            if (oneData.getFilePath() != null) {
//                Uri contentUri = Uri.parse(String.valueOf(oneData.getGray_FilePath()));
//                holder.project_Simple.setImageURI(contentUri);
//            }
            holder.rank_Txt.setVisibility(View.INVISIBLE);
        } else {
            setYellow(holder);
            holder.rank_Txt.setVisibility(View.VISIBLE);
//            if (oneData.getFilePath() != null) {
//                Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
//                holder.project_Simple.setImageURI(contentUri);
//            }
        }
//        holder.today_Txt.setText(oneData.getRanking() + "");
//        holder.project_Txt.setText(oneData.getProjectName());
////        holder.project_Txt.setText("坐姿收复举腿坐姿收复举腿");
//        holder.count_Txt.setText(nf.format(oneData.getReportNum()));
//        holder.unit_Txt.setText(oneData.getProjectUnit());
//        holder.all_Txt.setText("本月累计" + nf.format(oneData.getReport_Num_Month()) + oneData.getProjectUnit());

        NumberFormat nf = new DecimalFormat("#.#");//# 0 不显示
        if (oneData.getLimit() == 1) {
            holder.count_Txt.setText(oneData.getReport_Days() + oneData.getProjectUnit());
        } else {
            holder.count_Txt.setText(nf.format(oneData.getReportNum()) + oneData.getProjectUnit());
        }
        holder.project_Txt.setText(oneData.getProjectName());
        holder.all_Txt.setText("本月累计" + nf.format(oneData.getReport_Num_Month()) + oneData.getProjectUnit());
        if (oneData.getRanking() == 1) {
            holder.rank_Txt.setBackgroundResource(R.drawable.rank_one);
        } else if (oneData.getRanking() == 2) {
            holder.rank_Txt.setBackgroundResource(R.drawable.rank_two);
        } else if (oneData.getRanking() == 2) {
            holder.rank_Txt.setBackgroundResource(R.drawable.rank_three);
        } else {
            holder.rank_Txt.setText(oneData.getRanking() + "");
//            holder.rank_Txt.setVisibility(View.INVISIBLE);
        }

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
        private LinearLayout my_Lin;
        private TextView count_Txt, project_Txt, all_Txt, rank_Txt;
//        private AutoScaleTextView project_Txt, all_Txt;
//        private TextView count_Txt, unit_Txt, today_Txt;
//        private SimpleDraweeView project_Simple;
//        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Lin = (LinearLayout) itemView.findViewById(R.id.my_Lin);
            count_Txt = (TextView) itemView.findViewById(R.id.count_Txt);
            project_Txt = (TextView) itemView.findViewById(R.id.project_Txt);
            all_Txt = (TextView) itemView.findViewById(R.id.all_Txt);
            ImageView line_Img = (ImageView) itemView.findViewById(R.id.line_Img);
            rank_Txt = (TextView) itemView.findViewById(R.id.rank_Txt);
            StaticData.ViewScale(my_Lin, 348, 222);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int magTop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 18);
            int magBottom = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 20);
            params.setMargins(0, magTop, 0, magBottom);
            StaticData.layoutParamsScale(params, 208, 2);
            line_Img.setLayoutParams(params);

            RelativeLayout.LayoutParams paramsRank = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsRank.addRule(RelativeLayout.ALIGN_TOP, R.id.my_Lin);
            paramsRank.addRule(RelativeLayout.ALIGN_RIGHT, R.id.my_Lin);
            int magRight = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 26);
            paramsRank.setMargins(0, 0, magRight, 0);
            StaticData.layoutParamsScale(paramsRank, 36, 48);
            rank_Txt.setLayoutParams(paramsRank);


//            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
//            project_Simple = (SimpleDraweeView) itemView.findViewById(R.id.project_Simple);
//            project_Txt = (AutoScaleTextView) itemView.findViewById(R.id.project_Txt);
//            all_Txt = (AutoScaleTextView) itemView.findViewById(R.id.all_Txt);
//            count_Txt = (TextView) itemView.findViewById(R.id.count_Txt);
//            unit_Txt = (TextView) itemView.findViewById(R.id.unit_Txt);
//            today_Txt = (TextView) itemView.findViewById(R.id.today_Txt);
//
//            StaticData.ViewScale(mu_Rel, 218, 336);
//            StaticData.ViewScale(project_Simple, 190, 190);
//            StaticData.ViewScale(all_Txt, 0, 40);
//            StaticData.ViewScale(today_Txt, 104, 40);
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
        holder.count_Txt.setTextColor(Color.parseColor("#ffffff"));
        holder.project_Txt.setTextColor(Color.parseColor("#989797"));
//        holder.today_Txt.setBackgroundResource(R.drawable.today_shap);
//        holder.today_Txt.setTextColor(Color.parseColor("#262626"));
//        holder.project_Txt.setTextColor(Color.parseColor("#262626"));
//        holder.all_Txt.setBackgroundColor(Color.parseColor("#ffd91e"));
//        holder.count_Txt.setTextColor(Color.parseColor("#262626"));
//        holder.unit_Txt.setTextColor(Color.parseColor("#262626"));
    }

    private void setGray(MyViewHolder holder) {
        holder.count_Txt.setTextColor(Color.parseColor("#4d4a4a"));
        holder.project_Txt.setTextColor(Color.parseColor("#4d4a4a"));
//        holder.today_Txt.setBackgroundResource(R.drawable.today_shap_gray);
//        holder.today_Txt.setTextColor(Color.parseColor("#ffffff"));
//        holder.project_Txt.setTextColor(Color.parseColor("#aaaaaa"));
//        holder.all_Txt.setBackgroundColor(Color.parseColor("#ababab"));
//        holder.count_Txt.setTextColor(Color.parseColor("#aaaaaa"));
//        holder.unit_Txt.setTextColor(Color.parseColor("#aaaaaa"));

    }

}
