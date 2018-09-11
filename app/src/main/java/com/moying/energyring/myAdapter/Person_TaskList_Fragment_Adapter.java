package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.Person_ShopList_Model;
import com.moying.energyring.Model.Person_TaskList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_TaskList_Fragment_Adapter extends RecyclerView.Adapter<Person_TaskList_Fragment_Adapter.MyViewHolder> {
    private Person_TaskList_Model listModel;
    Context context;
    private int myposition;

    public Person_TaskList_Fragment_Adapter(Context context, Person_TaskList_Model listModel) {
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Person_ShopList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_tasklist_adapter, null));
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
        final Person_TaskList_Model.DataBean oneData = listModel.getData().get(position);

        holder.taskName_Txt.setText(oneData.getTaskName());
        holder.taskContent_Txt.setText(oneData.getSummary());
        holder.fen_Txt.setText("+"+oneData.getIntegral()+"积分");

        String[] taskArr = oneData.getBtnText().split(",");
        if (oneData.getFinishID() == 0){
            holder.task_Btn.setText(taskArr[0]);
            holder.task_Btn.setBackgroundResource(R.drawable.daypk_train_bg);
            holder.task_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    taskClickLitener.taskListener(view,position);
                }
            });
        }else {
            holder.task_Btn.setText(taskArr[1]);
            holder.task_Btn.setBackgroundResource(R.drawable.task_btn_gray);
        }

    }

    @Override
    public int getItemCount() {
//        return 10;
        return (listModel == null) ? 0 : listModel.getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private  Button task_Btn;
        private TextView  taskName_Txt, taskContent_Txt,fen_Txt;
        private RelativeLayout myRel;

        public MyViewHolder(View itemView) {
            super(itemView);
            myRel = (RelativeLayout) itemView.findViewById(R.id.myRel);
            taskName_Txt = (TextView) itemView.findViewById(R.id.taskName_Txt);
            taskContent_Txt = (TextView) itemView.findViewById(R.id.taskContent_Txt);
            fen_Txt = (TextView) itemView.findViewById(R.id.fen_Txt);
            task_Btn = (Button) itemView.findViewById(R.id.task_Btn);
            StaticData.ViewScale(myRel, 0, 160);
            StaticData.ViewScale(task_Btn, 160, 76);
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


    public interface TaskClickLitener{
        void taskListener(View view ,int position);
    }

    private TaskClickLitener taskClickLitener;

    public void setonTaskListener(TaskClickLitener litener){
        taskClickLitener = litener;
    }


}
