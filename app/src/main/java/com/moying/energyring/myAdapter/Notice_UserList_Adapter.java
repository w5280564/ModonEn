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
import com.moying.energyring.Model.Notice_UserList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.HtmlToText;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Notice_UserList_Adapter extends RecyclerView.Adapter<Notice_UserList_Adapter.MyViewHolder> {
    List<Notice_UserList_Model.DataBean> otherList;
    private Notice_UserList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Notice_UserList_Adapter(Context context, List<Notice_UserList_Model.DataBean> otherList, Notice_UserList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Notice_UserList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.notice_userlist_adapter, null));
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

        Notice_UserList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
            holder.my_Head.setImageURI(contentUri);
        } else {
            StaticData.lodingheadBg(holder.my_Head);
        }

        holder.name_Txt.setText(oneData.getNickName());
        holder.content_Txt.setText(HtmlToText.delHTMLTag(oneData.getMessageContent()));
        holder.time_Txt.setText(oneData.getCreateTime());
        if (oneData.getNotRead_Num() > 0){
            holder.unrend_Txt.setVisibility(View.VISIBLE);
            holder.unrend_Txt.setText(oneData.getNotRead_Num() +"");
        }else{
            holder.unrend_Txt.setVisibility(View.INVISIBLE);
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
        private final TextView unrend_Txt,name_Txt,content_Txt,time_Txt;
        private  RelativeLayout my_Rel;
        private  SimpleDraweeView my_Head;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            my_Head = (SimpleDraweeView) itemView.findViewById(R.id.my_Head);
            unrend_Txt = (TextView) itemView.findViewById(R.id.unrend_Txt);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);

            StaticData.ViewScale(my_Rel, 710, 148);
            StaticData.ViewScale(my_Head, 108, 108);
            StaticData.ViewScale(unrend_Txt, 36, 36);
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
