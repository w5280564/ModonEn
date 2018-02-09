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
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class AllPersonHostory_Adapter extends RecyclerView.Adapter<AllPersonHostory_Adapter.MyViewHolder> {
    List<AllPerson_Model.DataBean> otherList;
    AllPerson_Model personModel;
    Context context;
    private int myposition;

    public AllPersonHostory_Adapter(Context context, List<AllPerson_Model.DataBean> otherList, AllPerson_Model personModel) {
        this.otherList = otherList;
        this.context = context;
        this.personModel = personModel;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.allpersonhostory_adapter, null));
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

        if (otherList.get(position).getFinishDays() == otherList.get(position).getTotalDays()){
            Uri imgUri = Uri.parse("res:// /" + R.drawable.hostory_finsh);
            holder.show_icon.setImageURI(imgUri);
        }else{
            Uri imgUri = Uri.parse("res:// /" + R.drawable.hostory_unfinsh);
            holder.show_icon.setImageURI(imgUri);
        }
        holder.name_Txt.setText(otherList.get(position).getProjectName());
        String timeTxt = StaticData.Datatypetwo(otherList.get(position).getStartDate())+"-"+StaticData.Datatypetwo(otherList.get(position).getEndDate());
        holder.time_Txt.setText(timeTxt);
        holder.all_Txt.setText(otherList.get(position).getTotalDays()+"");
        holder.oneday_Txt.setText(otherList.get(position).getReportNum()+otherList.get(position).getProjectUnit());
        if (otherList.get(position).getFilePath() != null){
            Uri imgUri = Uri.parse(otherList.get(position).getFilePath());
            holder.my_simpleview.setImageURI(imgUri);
        }

    }

    @Override
    public int getItemCount() {
        return (otherList == null) ? 0 : otherList.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mu_Rel;
        private TextView name_Txt,time_Txt,all_Txt,oneday_Txt;
        private SimpleDraweeView my_simpleview,show_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            StaticData.ViewScale(mu_Rel,714,210);
            my_simpleview = (SimpleDraweeView) itemView.findViewById(R.id.my_simpleview);
            StaticData.ViewScale(my_simpleview,120,120);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            all_Txt = (TextView) itemView.findViewById(R.id.all_Txt);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            oneday_Txt = (TextView) itemView.findViewById(R.id.oneday_Txt);
            show_icon = (SimpleDraweeView) itemView.findViewById(R.id.show_icon);
            StaticData.ViewScale(show_icon,160,160);
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
