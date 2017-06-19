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
import com.moying.energyring.Model.CheckList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class pk_CheckIn_Adapter extends RecyclerView.Adapter<pk_CheckIn_Adapter.MyViewHolder> {
    List<CheckList_Model.DataBean> otherList;
    private CheckList_Model listModel;
    Context context;
    private int myposition;

    public pk_CheckIn_Adapter(Context context, List<CheckList_Model.DataBean> otherList, CheckList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<CheckList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.pk_zanranking_adapter, null));
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
//        position = position -1;

        CheckList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null) {
            Uri uri = Uri.parse(oneData.getProfilePicture());
            holder.head_simple.setImageURI(uri);
        }else {
            StaticData.lodingheadBg(holder.head_simple);
        }
        holder.rank_Txt.setText(oneData.getRanking() + ".");
        holder.name_Txt.setText(oneData.getNickName());
        holder.all_Txt.setText(oneData.getEarlyDays() + "天");
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
        private SimpleDraweeView head_simple;
        private TextView rank_Txt, name_Txt, all_Txt;
        private RelativeLayout zan_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            zan_Rel = (RelativeLayout) itemView.findViewById(R.id.zan_Rel);
            rank_Txt = (TextView) itemView.findViewById(R.id.rank_Txt);
            head_simple = (SimpleDraweeView) itemView.findViewById(R.id.head_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            all_Txt = (TextView) itemView.findViewById(R.id.all_Txt);
            StaticData.ViewScale(zan_Rel, 710, 120);
            StaticData.ViewScale(head_simple, 72, 72);
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
