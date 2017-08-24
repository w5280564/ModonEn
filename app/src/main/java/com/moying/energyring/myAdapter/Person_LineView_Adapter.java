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
import com.moying.energyring.Model.DayPkProject_Model;
import com.moying.energyring.Model.PkHistoryLineList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_LineView_Adapter extends RecyclerView.Adapter<Person_LineView_Adapter.MyViewHolder> {
    private DayPkProject_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_LineView_Adapter(Context context, DayPkProject_Model listModel) {
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<PkHistoryLineList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_pkhistoryline_adapter, null));
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

        final DayPkProject_Model.DataBean oneData = listModel.getData().get(position);
        if (position == 0){
            holder.content_Txt.setTextColor(Color.parseColor("#f24d4d"));
        }else{
            holder.content_Txt.setTextColor(Color.parseColor("#4d4d4d"));
        }
        if (oneData.getFilePath() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.my_Head.setImageURI(contentUri);
        } else {
            StaticData.lodingheadBg(holder.my_Head);
        }
        holder.name_Txt.setText(oneData.getProjectName());
        holder.content_Txt.setText(oneData.getReportFre() + "条记录");

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
        private RelativeLayout my_Rel;
        private SimpleDraweeView my_Head;
        private TextView name_Txt, content_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            my_Head = (SimpleDraweeView) itemView.findViewById(R.id.my_Head);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
//            line_img = (View) itemView.findViewById(R.id.line_img);

            StaticData.ViewScale(my_Rel, 710, 122);
            StaticData.ViewScale(my_Head, 108, 108);
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