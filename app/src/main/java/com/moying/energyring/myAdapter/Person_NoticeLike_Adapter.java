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
import com.moying.energyring.Model.Notice_Nomm_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_Other;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_NoticeLike_Adapter extends RecyclerView.Adapter<Person_NoticeLike_Adapter.MyViewHolder> {
    List<Notice_Nomm_Model.DataBean> otherList;
    private Notice_Nomm_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_NoticeLike_Adapter(Context context, List<Notice_Nomm_Model.DataBean> otherList, Notice_Nomm_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Notice_Nomm_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_noticenomm_adapter, null));
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

        final Notice_Nomm_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
            holder.my_Head.setImageURI(contentUri);
        } else {
//            StaticData.lodingheadBg(holder.my_Head);
        }
        holder.name_Txt.setText(oneData.getNickName());
//        holder.content_Txt.setText(oneData.getPostContent());
        holder.time_Txt.setText(oneData.getCreateTime());

        if (oneData.getFilePath() != null){
            Uri uri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.content_simple.setVisibility(View.VISIBLE);
            holder.content_simple.setImageURI(uri);
        }else {
            holder.content_simple.setVisibility(View.GONE);
            holder.isfocus_Txt.setVisibility(View.VISIBLE);
            holder.isfocus_Txt.setText(oneData.getPostContent());
        }

        holder.my_Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonMyCenter_Other.class);
                intent.putExtra("UserID",oneData.getUserID()+"");
                context.startActivity(intent);
            }
        });

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
        private ImageView line_img;
        private RelativeLayout my_Rel;
        private SimpleDraweeView my_Head,content_simple;
        private TextView name_Txt, content_Txt,time_Txt;
        private TextView isfocus_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            my_Head = (SimpleDraweeView) itemView.findViewById(R.id.my_Head);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            isfocus_Txt = (TextView) itemView.findViewById(R.id.isfocus_Txt);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            line_img = (ImageView) itemView.findViewById(R.id.line_img);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);

            content_Txt.setBackgroundResource(R.drawable.like_red_icon);

            StaticData.ViewScale(my_Rel, 710, 150);
            StaticData.ViewScale(my_Head, 108, 108);
            StaticData.ViewScale(content_simple, 100, 100);
            StaticData.ViewScale(content_Txt, 40, 40);
            StaticData.ViewScale(isfocus_Txt, 100, 100);
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
