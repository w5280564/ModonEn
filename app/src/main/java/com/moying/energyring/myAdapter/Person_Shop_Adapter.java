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
import com.moying.energyring.Model.Person_ShopList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_Shop_Adapter extends RecyclerView.Adapter<Person_Shop_Adapter.MyViewHolder> {
    List<Person_ShopList_Model.DataBean> otherList;
    private Person_ShopList_Model listModel;
    Context context;
    private int myposition;

    public Person_Shop_Adapter(Context context, List<Person_ShopList_Model.DataBean> otherList, Person_ShopList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Person_ShopList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_shop_adapter, null));
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

        Person_ShopList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getFilePath() != null) {
            Uri uri = Uri.parse(oneData.getFilePath());
            holder.content_simple.setImageURI(uri);
        }else {
//            StaticData.lodingheadBg(holder.head_simple);
        }
        holder.name_Txt.setText(oneData.getProductName());
        holder.price_Txt.setText("参考价："+ String.valueOf(oneData.getRefPrice())+"¥");
        holder.fen_Txt.setText(String.valueOf(oneData.getIntegral())+"积分");
//        holder.rank_Txt.setText(oneData.getRanking() + ".");
//        holder.all_Txt.setText(oneData.getLikes() + "天");
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
        private  SimpleDraweeView content_simple;
        private TextView price_Txt, name_Txt, fen_Txt;
        private RelativeLayout shop_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            shop_Rel = (RelativeLayout) itemView.findViewById(R.id.shop_Rel);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            price_Txt = (TextView) itemView.findViewById(R.id.price_Txt);
            fen_Txt = (TextView) itemView.findViewById(R.id.fen_Txt);
            StaticData.ViewScale(shop_Rel, 355, 480);
            StaticData.ViewScale(content_simple, 290, 290);
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
