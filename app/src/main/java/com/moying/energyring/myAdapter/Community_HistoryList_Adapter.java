package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Community_HistoryList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.network.saveFile;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Community_HistoryList_Adapter extends RecyclerView.Adapter<Community_HistoryList_Adapter.MyViewHolder> {
    Community_HistoryList_Model baseModel;
    List<Community_HistoryList_Model.DataBean> otherList;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Community_HistoryList_Adapter(Context context, Community_HistoryList_Model baseModel, List<Community_HistoryList_Model.DataBean> otherList) {
        this.otherList = otherList;
        this.context = context;
        this.baseModel = baseModel;
    }

    public void addMoreData(Community_HistoryList_Model list) {
//        notifyDataSetChanged();
//        int lastIndex = this.otherList.size();
//        if (this.otherList.addAll(list.getData().get())) {
//            notifyItemRangeInserted(lastIndex + 1, list.getData().get());
//        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.community_history_adapter, null));
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


        final Community_HistoryList_Model.DataBean oneData = otherList.get(position);

        holder.time_Txt.setText(StaticData.getDate(oneData.getCreateTime()));
        holder.content_Txt.setText(oneData.getPostContent());


        if (otherList.get(position).getImg_List().isEmpty()) {
            holder.badge_Scro.setVisibility(View.GONE);
        }else {
            holder.badge_Scro.setVisibility(View.VISIBLE);
            hasBadgeInit(holder.img_Lin, baseModel, position, context);
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
        private final TextView time_Txt, content_Txt;
        private final LinearLayout img_Lin;
        private final HorizontalScrollView badge_Scro;

        public MyViewHolder(View itemView) {
            super(itemView);
//            RelativeLayout my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);

             badge_Scro = (HorizontalScrollView) itemView.findViewById(R.id.badge_Scro);
            img_Lin = (LinearLayout) itemView.findViewById(R.id.img_Lin);
            View badge_arrow = itemView.findViewById(R.id.badge_arrow);
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


    public void hasBadgeInit(LinearLayout myLin, final Community_HistoryList_Model badge_Model, int pos, final Context context) {
        if (myLin != null) {
            myLin.removeAllViews();
        }

//        new SimpleDraweeView()
        int size = badge_Model.getData().get(pos).getImg_List().size();
        for (int i = 0; i < size; i++) {

            Community_HistoryList_Model.DataBean oneData = badge_Model.getData().get(pos);

            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(itemParams, 176, 176);
            int pad = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 10);
            itemParams.setMargins(0, 0, pad, 0);
            final SimpleDraweeView mySimple = new SimpleDraweeView(context);
            mySimple.setLayoutParams(itemParams);

            if (oneData.getImg_List().get(i) != null) {
                Uri imgUri = Uri.parse(oneData.getImg_List().get(i));
                mySimple.setImageURI(imgUri);
            }

            mySimple.setTag(i);
            myLin.addView(mySimple);
            mySimple.setOnClickListener(new View.OnClickListener() {
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
