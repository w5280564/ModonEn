package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class CommittedFragment_Adapter extends RecyclerView.Adapter<CommittedFragment_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;

    public CommittedFragment_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<EnergyList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.commitedfragment_adapter, null));
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
//        holder.title_Txt.setText(otherList.get(position).getProjectName());
//        holder.day_Txt.setText((int) otherList.get(position).getAllDays() +"天");
//        holder.count_Txt.setText(otherList.get(position).getReportNum()+otherList.get(position).getP_UNIT());
//        if (saveFile.getBig(otherList.get(position).getStartDate())){//判断承诺是否开始 false是目标没开始
//            String TxtString = "完成目标第" + otherList.get(position).getFinishDays() +"/"+otherList.get(position).getAllDays()+"天";
//            int allSize = 7 + String.valueOf(otherList.get(position).getFinishDays()).length()+ String.valueOf(otherList.get(position).getAllDays()).length();
//            TextsColor(5,allSize-1,allSize,TxtString,holder.start_Txt);
//        }else{
//            String TxtString = "目标将于" + saveFile.getUserDate(otherList.get(position).getStartDate())+"开始";
//            int allSize = 6 + saveFile.getUserDate(otherList.get(position).getStartDate()).length();
//            TextsColor(4,allSize-2,allSize,TxtString,holder.start_Txt);
//        }
//
//        Integer finshTxt =(int) ( (double)otherList.get(position).getFinishDays() / (double) otherList.get(position).getAllDays() * 100 );
//        holder.finsh_Txt.setText(finshTxt+"%");
//        holder.mypross.setMax(otherList.get(position).getAllDays());
//        holder.mypross.setProgress(otherList.get(position).getFinishDays());
//        Uri headUri = Uri.parse(otherList.get(position))
    }

    @Override
    public int getItemCount() {
        return 5;
//        return (otherList == null) ? 0 : otherList.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView name_Txt;
        private  SimpleDraweeView myhead_simple,content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            ImageView energy_talk = (ImageView) itemView.findViewById(R.id.energy_talk);
            ImageView energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
            StaticData.ViewScale(mu_Rel, 710, 0);
            StaticData.ViewScale(myhead_simple, 50, 50);
            StaticData.ViewScale(content_simple, 95, 95);
            StaticData.ViewScale(energy_talk, 40, 40);
            StaticData.ViewScale(energy_like, 40, 40);
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
