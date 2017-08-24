package com.moying.energyring.myAdapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.PkHistoryLineList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_PkHistoryLine_Adapter extends RecyclerView.Adapter<Person_PkHistoryLine_Adapter.MyViewHolder> {
    List<PkHistoryLineList_Model.DataBean> otherList;
    private PkHistoryLineList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_PkHistoryLine_Adapter(Context context, List<PkHistoryLineList_Model.DataBean> otherList, PkHistoryLineList_Model listModel) {
        this.otherList = otherList;
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

        final PkHistoryLineList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getFilePath() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.my_Head.setImageURI(contentUri);
        } else {
            StaticData.lodingheadBg(holder.my_Head);
        }
        holder.name_Txt.setText(oneData.getProjectName());
//        holder.content_Txt.setText(oneData.getReportNum() + "");
        rankCountTextsColor(20, 48, oneData.getReportNum() + oneData.getProjectUnit(), holder.content_Txt);

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

    public void rankCountTextsColor(int size, int endsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        int padSize = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * size);
        styledText.setSpan(new AbsoluteSizeSpan(padSize), 0, alltext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //初始字体大小
        int padEndSize = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * endsize);
        styledText.setSpan(new AbsoluteSizeSpan(padEndSize), 0, alltext.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //修改字体大小
        styledText.setSpan(new ForegroundColorSpan(Color.parseColor("#f27b7b")),0,alltext.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }



}
