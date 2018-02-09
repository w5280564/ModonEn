package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class Leran_Goal_Add_TabSeek_Adapter extends RecyclerView.Adapter<Leran_Goal_Add_TabSeek_Adapter.MyViewHolder> {
    //    List<AllPerson_Model.DataBean> otherList;
    Goal_Model baseModel;
    Context context;
    private int myposition;

    public Leran_Goal_Add_TabSeek_Adapter(Context context, Goal_Model baseModel) {
//        this.otherList = otherList;
        this.context = context;
        this.baseModel = baseModel;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_child, null));
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

        if (baseModel.getData().get(position).getFilePath() != null) {
            Uri imgUri = Uri.parse(baseModel.getData().get(position).getFilePath());
//            Uri imgUri = Uri.parse("http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0");
            holder.my_simple.setImageURI(imgUri);
        } else {
//            StaticData.lodingheadBg(holder.my_simple);
        }
        holder.name_Txt.setText(baseModel.getData().get(position).getProjectName());

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
        public RelativeLayout my_Rel;
        public CheckBox choice_check;
        private TextView name_Txt;
        public SimpleDraweeView my_simple;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            my_simple = (SimpleDraweeView) itemView.findViewById(R.id.my_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            choice_check = (CheckBox) itemView.findViewById(R.id.choice_check);
            choice_check.setVisibility(View.INVISIBLE);
            StaticData.ViewScale(my_Rel, 0, 144);
            StaticData.ViewScale(my_simple, 100, 100);
//            StaticData.ViewScale(choice_check, 40, 40);
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
