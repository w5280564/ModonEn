package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.Model.Goal_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Admin on 2016/3/29.
 */
public class DayPk_AddTab_Adapter extends RecyclerView.Adapter<DayPk_AddTab_Adapter.MyViewHolder> {
    //    List<AllPerson_Model.DataBean> otherList;
    Goal_Model baseModel;
    Context context;
    private int myposition;
    public Map<Integer, Boolean> mcheckflag = new HashMap<>();

    public DayPk_AddTab_Adapter(Context context, Goal_Model baseModel, Map<Integer, Boolean> mcheckflag) {
//        this.otherList = otherList;
        this.context = context;
        this.baseModel = baseModel;
        this.mcheckflag = mcheckflag;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.addtab_adapter, null));
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


        Goal_Model.DataBean oneData = baseModel.getData().get(position);
        if (oneData.getFilePath() != null) {
            Uri imgUri = Uri.parse(oneData.getFilePath());
//            Uri imgUri = Uri.parse("http://wx.qlogo.cn/mmopen/PiajxSqBRaEJeZY41PdItuibhfu1W86xuqlkrwjJz49KWKrsfQ2ljEoommY8abzibqdZNLiaNqfa1u5v595Iian20pg/0");
            holder.my_simple.setImageURI(imgUri);
        } else {
//            StaticData.lodingheadBg(holder.my_simple);
        }
        if (oneData.getProjectTypeID() == -1) {

            holder.name_Txt.setText(oneData.getProjectName()+"（自定义）");
        }else {
            holder.name_Txt.setText(oneData.getProjectName());
        }

        if (mcheckflag.get(position)) {
            holder.my_Lin.setBackgroundResource(R.drawable.addtab_adapter_select);
        } else {
            holder.my_Lin.setBackgroundResource(R.drawable.addtab_adapter);
        }
        holder.choice_check.setChecked(mcheckflag.get(position));
        if (oneData.isIs_Train()){
            holder.istrain_Img.setVisibility(View.VISIBLE);
        }else {
            holder.istrain_Img.setVisibility(View.GONE);
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
        private  ImageView istrain_Img;
        public CheckBox choice_check;
        public LinearLayout my_Lin;
        private RelativeLayout mu_Rel;
        private TextView name_Txt;
        public SimpleDraweeView my_simple;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Lin = (LinearLayout) itemView.findViewById(R.id.my_Lin);
            my_simple = (SimpleDraweeView) itemView.findViewById(R.id.my_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            choice_check = (CheckBox) itemView.findViewById(R.id.choice_check);
            istrain_Img = (ImageView) itemView.findViewById(R.id.istrain_Img);
            StaticData.ViewScale(my_Lin, 210, 240);
            StaticData.ViewScale(my_simple, 160, 160);
            StaticData.ViewScale(choice_check, 40, 40);
            StaticData.ViewScale(istrain_Img, 60, 60);
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
