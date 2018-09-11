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
import com.moying.energyring.Model.Recommend_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_And_Other;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class CommunityFriend_Seek_Adapter extends RecyclerView.Adapter<CommunityFriend_Seek_Adapter.MyViewHolder> {
    List<Recommend_Model.DataBean> otherList;
    private Recommend_Model listModel;
    Context context;
    private int myposition;

    public CommunityFriend_Seek_Adapter(Context context, List<Recommend_Model.DataBean> otherList, Recommend_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<Recommend_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.communityfriend_seek_adapter, null));
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

        if (position + 1 == otherList.size()) {
            holder.line_img.setVisibility(View.GONE);
        }
        final Recommend_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null) {
            Uri contentUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
            holder.my_Head.setImageURI(contentUri);
        } else {
            StaticData.lodingheadBg(holder.my_Head);
        }
        holder.name_Txt.setText(oneData.getNickName());

        holder.my_Head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonMyCenter_And_Other.class);
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
        private SimpleDraweeView my_Head;
        private TextView name_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            my_Head = (SimpleDraweeView) itemView.findViewById(R.id.my_Head);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            line_img = (ImageView) itemView.findViewById(R.id.line_img);

            StaticData.ViewScale(my_Rel, 0, 100);
            StaticData.ViewScale(my_Head, 80, 80);
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