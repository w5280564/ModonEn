package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class CommHead_Adapter extends RecyclerView.Adapter<CommHead_Adapter.MyViewHolder> {
    //    List<AllPerson_Model.DataBean> otherList;
    AllPerson_Model baseModel;
    Context context;
    private int myposition;

    public CommHead_Adapter(Context context, AllPerson_Model baseModel) {
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
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.commhead_adapter, null));
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

        AllPerson_Model.DataBean oneData = baseModel.getData().get(position);
        if (oneData.getFilePath() != null) {
            Uri imgUri = Uri.parse(oneData.getFilePath());
            holder.nohead_simple.setImageURI(imgUri);
        } else {
            StaticData.lodingheadBg(holder.nohead_simple);
        }

        holder.nocontent_Txt.setText(oneData.getProjectName());
        holder.nocount_Txt.setText(oneData.getReportNum() + "");
    }

    @Override
    public int getItemCount() {
//        return 1;
        return (baseModel == null) ? 0 : baseModel.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nocontent_Txt, nocount_Txt;
        private SimpleDraweeView nohead_simple;

        public MyViewHolder(View itemView) {
            super(itemView);
//            RelativeLayout no_Rel = (RelativeLayout) itemView.findViewById(R.id.no_Rel);
            nohead_simple = (SimpleDraweeView) itemView.findViewById(R.id.nohead_simple);
            nocontent_Txt = (TextView) itemView.findViewById(R.id.nocontent_Txt);
            nocount_Txt = (TextView) itemView.findViewById(R.id.nocount_Txt);
//            StaticData.ViewScale(no_Rel, 200, 50);
            StaticData.ViewScale(nohead_simple, 100, 100);
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
