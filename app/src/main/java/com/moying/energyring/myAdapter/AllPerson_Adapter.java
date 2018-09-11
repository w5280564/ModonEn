package com.moying.energyring.myAdapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class AllPerson_Adapter extends RecyclerView.Adapter<AllPerson_Adapter.MyViewHolder> {
    List<AllPerson_Model.DataBean> otherList;
    AllPerson_Model personModel;
    Context context;
    private int myposition;

    public AllPerson_Adapter(Context context, List<AllPerson_Model.DataBean> otherList, AllPerson_Model personModel) {
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
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.allperson_otheradapter, null));
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
        holder.title_Txt.setText(otherList.get(position).getProjectName());
        holder.day_Txt.setText((int) otherList.get(position).getTotalDays() +"天");
        holder.count_Txt.setText(otherList.get(position).getReportNum()+otherList.get(position).getProjectUnit());
        if (saveFile.getBig(otherList.get(position).getStartDate())){//判断承诺是否开始 false是目标没开始
            String TxtString = "完成目标第" + otherList.get(position).getFinishDays() +"/"+otherList.get(position).getTotalDays()+"天";
            int allSize = 7 + String.valueOf(otherList.get(position).getFinishDays()).length()+ String.valueOf(otherList.get(position).getTotalDays()).length();
            TextsColor(5,allSize-1,allSize,TxtString,holder.start_Txt);
        }else{
            String TxtString = "目标将于" + saveFile.getUserDate(otherList.get(position).getStartDate())+"开始";
            int allSize = 6 + saveFile.getUserDate(otherList.get(position).getStartDate()).length();
            TextsColor(4,allSize-2,allSize,TxtString,holder.start_Txt);
        }

        Integer finshTxt =(int) ( (double)otherList.get(position).getFinishDays() / (double) otherList.get(position).getTotalDays() * 100 );
        holder.finsh_Txt.setText(finshTxt+"%");
        holder.mypross.setMax(otherList.get(position).getTotalDays());
        holder.mypross.setProgress(otherList.get(position).getFinishDays());

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
        private ProgressBar mypross;
        private RelativeLayout mu_Rel;
        private TextView title_Txt,day_Txt,count_Txt,start_Txt,finsh_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            title_Txt = (TextView) itemView.findViewById(R.id.title_Txt);
            day_Txt = (TextView) itemView.findViewById(R.id.day_Txt);
            count_Txt = (TextView) itemView.findViewById(R.id.count_Txt);
            start_Txt = (TextView) itemView.findViewById(R.id.start_Txt);
            finsh_Txt = (TextView) itemView.findViewById(R.id.finsh_Txt);
            mypross = (ProgressBar) itemView.findViewById(R.id.mypross);
            StaticData.ViewScale(mu_Rel, 714, 380);
            StaticData.ViewScale(mypross,650,14);
//                StaticData.ViewScale(mu_Btn, 399, 90);
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

    public void TextsColor(int start, int end, int allSize, String allText, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(allText);
        styledText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.colorSecondWhite)), 0, allSize , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.colorFristBlack)),start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        myTxt.setText(styledText);
    }


}
