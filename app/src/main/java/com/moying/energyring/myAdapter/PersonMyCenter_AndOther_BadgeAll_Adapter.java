package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Person_BadgeAll_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

/**
 * Created by Admin on 2016/3/29.
 */
public class PersonMyCenter_AndOther_BadgeAll_Adapter extends RecyclerView.Adapter<PersonMyCenter_AndOther_BadgeAll_Adapter.MyViewHolder> {
    Person_BadgeAll_Model otherList;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public PersonMyCenter_AndOther_BadgeAll_Adapter(Context context, Person_BadgeAll_Model otherList) {
        this.otherList = otherList;
        this.context = context;
    }

    public void addMoreData(Person_BadgeAll_Model list) {
//        notifyDataSetChanged();
//        int lastIndex = this.otherList.size();
//        if (this.otherList.addAll(list.getData().get())) {
//            notifyItemRangeInserted(lastIndex + 1, list.getData().get());
//        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.personandother_badge, null));
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


        final Person_BadgeAll_Model.DataBean oneData = otherList.getData().get(position);

        holder.badgeName_Txt.setText(oneData.getName());

        String CountStr = "（已获得%1$d/%2$d个）";
        String Count = String.format(CountStr, oneData.getFinishBadgeCount(), oneData.getBadgeCount());
//        holder.badgeCount_Txt.setText(Count);
        int index = (oneData.getFinishBadgeCount() + "").toString().length();
        badgeCountTextsColor(index, Count, holder.badgeCount_Txt);

        hasBadgeInit(holder.badge_Lin, otherList, position, context);
    }


    @Override
    public int getItemCount() {
//        return 10;
        return (otherList == null) ? 0 : otherList.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView badgeName_Txt, badgeCount_Txt;
        private final LinearLayout badge_Lin;

        public MyViewHolder(View itemView) {
            super(itemView);
            RelativeLayout badge_Rel = (RelativeLayout) itemView.findViewById(R.id.badge_Rel);
            RelativeLayout badgenei_Rel = (RelativeLayout) itemView.findViewById(R.id.badgenei_Rel);
            badgeName_Txt = (TextView) itemView.findViewById(R.id.badgeName_Txt);
            badgeCount_Txt = (TextView) itemView.findViewById(R.id.badgeCount_Txt);
            HorizontalScrollView badge_Scro = (HorizontalScrollView) itemView.findViewById(R.id.badge_Scro);
            badge_Lin = (LinearLayout) itemView.findViewById(R.id.badge_Lin);
            StaticData.ViewScale(badge_Rel, 0, 440);
            StaticData.ViewScale(badgenei_Rel, 0, 96);
            View badge_arrow = itemView.findViewById(R.id.badge_arrow);
            StaticData.ViewScale(badge_arrow, 60, 60);
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

    public void badgeCountTextsColor(int indexsize, String alltext, TextView myTxt) {
        SpannableStringBuilder styledText = new SpannableStringBuilder(alltext);
        styledText.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorFourRed)), 4, indexsize + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//修改颜色
        myTxt.setText(styledText);
    }

    public void hasBadgeInit(LinearLayout myLin, final Person_BadgeAll_Model badge_Model, int pos, final Context context) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        int size = badge_Model.getData().get(pos).getInsignia_List().size();
        for (int i = 0; i < size; i++) {
            LinearLayout myview = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.personandother_adapeter_badgelist, null);

            View badge_Lin = (View) myview.findViewById(R.id.badge_Lin);
            View pross_Rel = (View) myview.findViewById(R.id.pross_Rel);
            SimpleDraweeView badge_Sim = (SimpleDraweeView) myview.findViewById(R.id.badge_Sim);
            TextView badgeName_Txt = (TextView) myview.findViewById(R.id.badgeName_Txt);
            TextView badgeContent_Txt = (TextView) myview.findViewById(R.id.badgeContent_Txt);
            ProgressBar mypross = (ProgressBar) myview.findViewById(R.id.mypross);
            TextView mypross_Txt = (TextView) myview.findViewById(R.id.mypross_Txt);
            StaticData.ViewScale(badge_Lin, 180, 0);
            StaticData.ViewScale(badge_Sim, 140, 140);
            StaticData.ViewScale(mypross, 140, 12);

            Person_BadgeAll_Model.DataBean.InsigniaListBean oneData = badge_Model.getData().get(pos).getInsignia_List().get(i);
            String ImgPath = null;
            if (oneData.isIs_Have()) {
                ImgPath = oneData.getFilePath();
            } else {
                ImgPath = oneData.getBadge_Gray();
            }
            if (ImgPath != null) {
                Uri imgUri = Uri.parse(ImgPath);
                badge_Sim.setImageURI(imgUri);
            }
            badgeName_Txt.setText(oneData.getBadgeName());

            int TypeID = badge_Model.getData().get(pos).getTypeID();

            String ContentStr = "";
            if (TypeID == 4) { //训练徽章
                badgeContent_Txt.setVisibility(View.VISIBLE);
                if (oneData.getCreatDate() != null) {
                    ContentStr = oneData.getCreatDate().substring(0, 8).replace("/", ".") + "获得";
                }
            } else if (TypeID == 5) {//成就徽章
                ContentStr = oneData.getCaption();
            } else {
                badgeContent_Txt.setVisibility(View.INVISIBLE);
            }
            badgeContent_Txt.setText(ContentStr);


            if (oneData.getProgress() == 0) {
                pross_Rel.setVisibility(View.INVISIBLE);

            } else {
                pross_Rel.setVisibility(View.VISIBLE);
                mypross.setMax(100);
                int progress = (int) (oneData.getProgress() * 100);
                mypross.setProgress(progress);
                mypross_Txt.setText(progress + "%");
            }

            myview.setTag(i);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();

//                    Intent intent1 = new Intent(context, TrainingTodaySet.class);
//                    intent1.putExtra("ProjectID", train_Model.getData().getTarin_List().get(tag).getProjectID() + "");
//                    context.startActivity(intent1);

                }
            });
        }
    }


}
