package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
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
public class GrowthLogFragment_Adapter extends RecyclerView.Adapter<GrowthLogFragment_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;

    public GrowthLogFragment_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
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
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.growthlogfragment_adapter, null));
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

        String headStr = otherList.get(position).getProfilePicture();
        if (!headStr.isEmpty()) {
            if (!headStr.substring(0, 4).equals("http")) {
                headStr = "http://" + headStr;
            }
        }


        EnergyList_Model.DataBean oneData = otherList.get(position);

        if (oneData.getFilePath() != null){
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.content_simple.setImageURI(contentUri);
        }
        Uri headUri = Uri.parse(oneData.getProfilePicture());
        holder.myhead_simple.setImageURI(headUri);
        holder.name_Txt.setText(oneData.getNickName());
        holder.time_Txt.setText(StaticData.Datatypetwo(oneData.getCreateTime()));
        holder.content_Txt.setText(oneData.getPostContent());
        holder.talk_Txt.setText(oneData.getCommentNum()+"");
        holder.like_Txt.setText(oneData.getLikes()+"");
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
        private  TextView name_Txt,time_Txt,content_Txt,talk_Txt,like_Txt;
        private  ImageView energy_img;
        private  SimpleDraweeView myhead_simple,content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            energy_img = (ImageView) itemView.findViewById(R.id.energy_img);
            ImageView energy_talk = (ImageView) itemView.findViewById(R.id.energy_talk);
            ImageView energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
             talk_Txt = (TextView) itemView.findViewById(R.id.talk_Txt);
            like_Txt = (TextView) itemView.findViewById(R.id.like_Txt);
            StaticData.ViewScale(mu_Rel, 710, 0);
            StaticData.ViewScale(myhead_simple, 50, 50);
            StaticData.ViewScale(content_simple, 710, 440);
            StaticData.ViewScale(energy_img, 40, 40);
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
