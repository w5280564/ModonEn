package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Person_BadgeAll_Model;
import com.moying.energyring.Model.Person_OtherBadge_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;


/**
 * Created by Admin on 2016/3/29.
 */
public class PersonAndOther_OtherBadge_Adapter extends RecyclerView.Adapter<PersonAndOther_OtherBadge_Adapter.MyViewHolder> {
    Person_OtherBadge_Model baseModel;
    Context context;
    private int myposition;

    public PersonAndOther_OtherBadge_Adapter(Context context, Person_OtherBadge_Model baseModel) {
//        this.otherList = otherList;
        this.context = context;
        this.baseModel = baseModel;
    }

    public void addMoreData(Person_BadgeAll_Model otherList) {
//        this.otherList = otherList;
//        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.personandother_badgedetail_adapter, null));
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
                    holder.itemView.setTag(holder);
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

        Person_OtherBadge_Model.DataBean oneData = baseModel.getData().get(position);

        String ImgPath = null;
        if (oneData.isIs_Have()) {
            ImgPath = oneData.getFilePath();
        } else {
            ImgPath = oneData.getBadge_Gray().toString();
        }
        if (ImgPath != null) {
            Uri imgUri = Uri.parse(ImgPath);
            holder.badge_Sim.setImageURI(imgUri);
        }


        holder.badgeName_Txt.setText(oneData.getBadgeName());

//        int TypeID = baseModel.getTypeID();

//        String ContentStr = "";
//        if (TypeID == 4) { //训练徽章
//            holder.badgeContent_Txt.setVisibility(View.VISIBLE);
//            if (oneData.getCreatDate() != null) {
        String ContentStr = oneData.getCreatDate().substring(0, 8).replace("/", ".") + "获得";
//            }
//        } else if (TypeID == 5) {//成就徽章
//            ContentStr = oneData.getCaption();
//        } else {
//            holder.badgeContent_Txt.setVisibility(View.INVISIBLE);
//        }
        holder.badgeContent_Txt.setText(ContentStr);
//        holder.mypross.setVisibility(View.GONE);

        if (oneData.getProgress() == 0) {
            holder.mypross_Rel.setVisibility(View.INVISIBLE);

        } else {
            holder.mypross_Rel.setVisibility(View.VISIBLE);
            holder.mypross.setMax(100);
            int progress = (int) (oneData.getProgress() * 100);
            holder.mypross.setProgress(progress);
            holder.mypross_Txt.setText(progress + "%");
        }

    }

    @Override
    public int getItemCount() {

        return (baseModel == null) ? 0 : baseModel.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView badge_Sim;
        private final TextView badgeName_Txt, badgeContent_Txt,mypross_Txt;
        private final ProgressBar mypross;
        private final RelativeLayout mypross_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            View badge_Lin = (View) itemView.findViewById(R.id.badge_Lin);
            badge_Sim = (SimpleDraweeView) itemView.findViewById(R.id.badge_Sim);
            badgeName_Txt = (TextView) itemView.findViewById(R.id.badgeName_Txt);
            badgeContent_Txt = (TextView) itemView.findViewById(R.id.badgeContent_Txt);
            mypross = (ProgressBar) itemView.findViewById(R.id.mypross);
             mypross_Rel = (RelativeLayout) itemView.findViewById(R.id.mypross_Rel);
             mypross_Txt = (TextView) itemView.findViewById(R.id.mypross_Txt);
            StaticData.ViewScale(badge_Lin, 250, 264);
            StaticData.ViewScale(badge_Sim, 140, 140);
            StaticData.ViewScale(mypross, 140, 12);

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
