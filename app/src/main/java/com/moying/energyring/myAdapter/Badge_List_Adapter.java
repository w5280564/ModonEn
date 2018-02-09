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
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.Model.BadgeList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class Badge_List_Adapter extends RecyclerView.Adapter<Badge_List_Adapter.MyViewHolder> {
    BadgeList_Model baseModel;
    Context context;
    private int myposition;

    public Badge_List_Adapter(Context context, BadgeList_Model baseModel) {
//        this.otherList = otherList;
        this.context = context;
        this.baseModel = baseModel;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.badge_list_adapter, null));
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
                    holder.itemView.setTag(holder);
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

        BadgeList_Model.DataBean.BadgeBean oneData = baseModel.getData().get_Badge().get(position);
        if (oneData.isIs_Have()) {
            Uri imgUri = Uri.parse(oneData.getFilePath());
            holder.my_simple.setImageURI(imgUri);
            if (oneData.getBadgeType() == 1) {
                holder.name_Txt.setTextColor(Color.parseColor("#F24D4D"));
            }else if (oneData.getBadgeType() == 3) {
                holder.name_Txt.setTextColor(Color.parseColor("#374C7E"));
            }else if (oneData.getBadgeType() == 2) {
                holder.name_Txt.setTextColor(Color.parseColor("#2E9887"));
            }
        } else {
            Uri imgUri = Uri.parse(oneData.getFilePath_Gray());
            holder.my_simple.setImageURI(imgUri);
        }
        holder.name_Txt.setText(oneData.getBadgeName());
//        if (baseModel.getData().get(position).getFilePath() != null) {
//            Uri imgUri = Uri.parse(baseModel.getData().get(position).getFilePath());
////            Uri imgUri = Uri.parse("http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0");
//            holder.my_simple.setImageURI(imgUri);
//        } else {
////            StaticData.lodingheadBg(holder.my_simple);
//        }
    }

    @Override
    public int getItemCount() {
        return (baseModel == null) ? 0 : baseModel.getData().get_Badge().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView name_Txt;
        private  RelativeLayout myRel;
        public SimpleDraweeView my_simple;

        public MyViewHolder(View itemView) {
            super(itemView);
            myRel = (RelativeLayout) itemView.findViewById(R.id.myRel);
            my_simple = (SimpleDraweeView) itemView.findViewById(R.id.my_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            StaticData.ViewScale(myRel, 228, 280);
            StaticData.ViewScale(my_simple, 180, 200);
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
