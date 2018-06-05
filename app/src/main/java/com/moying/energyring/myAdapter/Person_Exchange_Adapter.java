package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Person_Exchange_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_Exchange_Adapter extends RecyclerView.Adapter<Person_Exchange_Adapter.MyViewHolder> {
    List<Person_Exchange_Model.DataBean> otherList;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_Exchange_Adapter(Context context, List<Person_Exchange_Model.DataBean> otherList) {
        this.otherList = otherList;
        this.context = context;
    }

    public void addMoreData(Person_Exchange_Model list) {
//        notifyDataSetChanged();
        int lastIndex = this.otherList.size();
        if (this.otherList.addAll(list.getData())) {
            notifyItemRangeInserted(lastIndex + 1, list.getData().size());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_exchange_adapter, null));
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



        final Person_Exchange_Model.DataBean oneData = otherList.get(position);

        if (oneData.getFilePath() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.my_simple.setImageURI(contentUri);
        }

        holder.Name_Txt.setText(oneData.getProductName());
        holder.Time_Txt.setText(oneData.getCreateTime());
        if (oneData.getOrderState() == 1){
            holder.Status_Txt.setText("待发货");
        }else if (oneData.getOrderState() == 2){
            holder.Status_Txt.setText("已发货");
        }else if (oneData.getOrderState() == 3){
            holder.Status_Txt.setText("交易完成");
        }

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
        private  SimpleDraweeView my_simple;
        private LinearLayout my_Lin;
        private TextView Name_Txt, Time_Txt, Status_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Lin = (LinearLayout) itemView.findViewById(R.id.my_Lin);
            my_simple = (SimpleDraweeView) itemView.findViewById(R.id.my_simple);
            Name_Txt = (TextView) itemView.findViewById(R.id.Name_Txt);
            Time_Txt = (TextView) itemView.findViewById(R.id.Time_Txt);
            Status_Txt = (TextView) itemView.findViewById(R.id.Status_Txt);
            StaticData.ViewScale(my_Lin, 750, 200);
            StaticData.ViewScale(my_simple, 160, 160);
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
