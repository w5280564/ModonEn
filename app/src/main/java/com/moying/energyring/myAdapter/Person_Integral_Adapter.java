package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.Person_Intrgral_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_Integral_Adapter extends RecyclerView.Adapter<Person_Integral_Adapter.MyViewHolder> {
    List<Person_Intrgral_Model.DataBean> otherList;
    private Person_Intrgral_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_Integral_Adapter(Context context, List<Person_Intrgral_Model.DataBean> otherList, Person_Intrgral_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Person_Intrgral_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_integral_adapter, null));
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


        Person_Intrgral_Model.DataBean oneData = otherList.get(position);

        holder.name_Txt.setText(oneData.getIntegralEvent());

        String contentStr = "";
        if (oneData.getIntegral() > 0) {
            contentStr = "+" + oneData.getIntegral();
        } else {
            contentStr = "" + oneData.getIntegral();
        }
        holder.content_Txt.setText(contentStr);

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
        private RelativeLayout my_Rel;
        private TextView name_Txt, content_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);

            StaticData.ViewScale(my_Rel, 710, 120);
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
