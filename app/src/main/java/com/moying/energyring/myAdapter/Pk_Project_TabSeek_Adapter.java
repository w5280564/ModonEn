package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.Model.Project_TabSeek_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class Pk_Project_TabSeek_Adapter extends RecyclerView.Adapter<Pk_Project_TabSeek_Adapter.MyViewHolder> {
    Project_TabSeek_Model baseModel;
    Context context;
    private int myposition;

    public Pk_Project_TabSeek_Adapter(Context context, Project_TabSeek_Model baseModel) {
        this.context = context;
        this.baseModel = baseModel;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tabseek_adapter, null));
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

        Project_TabSeek_Model.DataBean oneData = baseModel.getData().get(position);

        holder.name_Txt.setText("#" + oneData.getProjectName());

        if (oneData.getJoin_Num() != 0) {
            String contextStr = "已有%1$d人加入";
            holder.content_Txt.setText(String.format(contextStr, oneData.getJoin_Num()));
        }
    }

    @Override
    public int getItemCount() {
        return (baseModel == null) ? 0 : baseModel.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout my_Rel;
        private TextView name_Txt, content_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            StaticData.ViewScale(my_Rel, 0, 100);
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
