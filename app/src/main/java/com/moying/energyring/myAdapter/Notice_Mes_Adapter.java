package com.moying.energyring.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Notice_UserList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.HtmlToText;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_Other;
import com.moying.energyring.network.saveFile;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Notice_Mes_Adapter extends RecyclerView.Adapter<Notice_Mes_Adapter.MyViewHolder> {
    List<Notice_UserList_Model.DataBean> otherList;
    private Notice_UserList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Notice_Mes_Adapter(Context context, List<Notice_UserList_Model.DataBean> otherList, Notice_UserList_Model listModel) {
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
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.notice_mes_adapter, null));
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

        final Notice_UserList_Model.DataBean oneData = otherList.get(position);

        String userId = saveFile.getShareData("userId", context);
        if (oneData.getUserID() == Integer.parseInt(userId)) {
            holder.mesright_Rel.setVisibility(View.VISIBLE);
            holder.mesleft_Rel.setVisibility(View.GONE);
            if (oneData.getProfilePicture() != null) {
                Uri imgUri = Uri.parse(oneData.getProfilePicture());
                holder.mesright_simple.setImageURI(imgUri);
            } else {
                StaticData.lodingheadBg(holder.mesright_simple);
            }
            holder.mesright_simple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonMyCenter_Other.class);
                    intent.putExtra("UserID",oneData.getUserID()+"");
                    context.startActivity(intent);
                }
            });
            holder.mesright_Txt.setText(HtmlToText.delHTMLTag(oneData.getMessageContent()));
        }else {
            holder.mesright_Rel.setVisibility(View.GONE);
            holder.mesleft_Rel.setVisibility(View.VISIBLE);
            if (oneData.getProfilePicture() != null) {
                Uri imgUri = Uri.parse(oneData.getProfilePicture());
                holder.mesleft_simple.setImageURI(imgUri);
            } else {
                StaticData.lodingheadBg(holder.mesleft_simple);
            }
            holder.mesleft_Txt.setText(HtmlToText.delHTMLTag(oneData.getMessageContent()));
            holder.mesleft_simple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonMyCenter_Other.class);
                    intent.putExtra("UserID",oneData.getUserID()+"");
                    context.startActivity(intent);
                }
            });
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
        private TextView mesleft_Txt, mesright_Txt;
        private ImageView mes_left_img, mes_right_img;
        private TextView time_Txt;
        private SimpleDraweeView mesleft_simple, mesright_simple;
        private RelativeLayout mesleft_Rel, mesright_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            mesleft_Rel = (RelativeLayout) itemView.findViewById(R.id.mesleft_Rel);
            mesleft_simple = (SimpleDraweeView) itemView.findViewById(R.id.mesleft_simple);
            mes_left_img = (ImageView) itemView.findViewById(R.id.mes_left_img);
            mesleft_Txt = (TextView) itemView.findViewById(R.id.mesleft_Txt);
            mesright_Rel = (RelativeLayout) itemView.findViewById(R.id.mesright_Rel);
            mesright_simple = (SimpleDraweeView) itemView.findViewById(R.id.mesright_simple);
            mes_right_img = (ImageView) itemView.findViewById(R.id.mes_right_img);
            mesright_Txt = (TextView) itemView.findViewById(R.id.mesright_Txt);

            StaticData.ViewScale(time_Txt, 240, 36);
            StaticData.ViewScale(mesleft_simple, 80, 80);
            StaticData.ViewScale(mesright_simple, 80, 80);
            StaticData.ViewScale(mes_left_img, 25, 56);
            StaticData.ViewScale(mes_right_img, 25, 56);
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
