package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.PersonAndOther_Train_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class PersonMyCenter_AndOther_Train_Adapter extends RecyclerView.Adapter<PersonMyCenter_AndOther_Train_Adapter.MyViewHolder> {
    List<PersonAndOther_Train_Model.DataBean.TarinListBean> otherList;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public PersonMyCenter_AndOther_Train_Adapter(Context context, List<PersonAndOther_Train_Model.DataBean.TarinListBean> otherList) {
        this.otherList = otherList;
        this.context = context;
    }

    public void addMoreData(PersonAndOther_Train_Model list) {
//        notifyDataSetChanged();
        int lastIndex = this.otherList.size();
        if (this.otherList.addAll(list.getData().getTarin_List())) {
            notifyItemRangeInserted(lastIndex + 1, list.getData().getTarin_List().size());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.personandother_train, null));
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



        final PersonAndOther_Train_Model.DataBean.TarinListBean oneData = otherList.get(position);


//        PersonAndOther_Train_Model.DataBean.TarinListBean oneData = train_Model.getData().getTarin_List().get(i);
        holder.trainName_Txt.setText(oneData.getProjectName());
        holder.trainCount_Txt.setText("完成" + oneData.getFinishCount() + "次");

        int Duration = oneData.getDuration() / 60;
        if (Duration < 1) {
            Duration = 1;
        }
        String time = Duration + "分钟";
        holder.trainTime_Txt.setText(time);


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
        private final TextView trainName_Txt,trainCount_Txt,trainTime_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            RelativeLayout train_Rel = (RelativeLayout) itemView.findViewById(R.id.train_Rel);
            StaticData.ViewScale(train_Rel, 0, 130);
             trainName_Txt = (TextView) itemView.findViewById(R.id.trainName_Txt);
             trainCount_Txt = (TextView) itemView.findViewById(R.id.trainCount_Txt);
             trainTime_Txt = (TextView) itemView.findViewById(R.id.trainTime_Txt);
            View train_arrow = itemView.findViewById(R.id.train_arrow);
            StaticData.ViewScale(train_arrow, 60, 60);
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
