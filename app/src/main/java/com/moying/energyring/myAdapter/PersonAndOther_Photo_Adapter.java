package com.moying.energyring.myAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.Model.Person_Photo_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class PersonAndOther_Photo_Adapter extends RecyclerView.Adapter<PersonAndOther_Photo_Adapter.MyViewHolder> {
    //    List<AllPerson_Model.DataBean> otherList;
    List<Person_Photo_Model.DataBean> otherList;

    Context context;
    private int myposition;

    public PersonAndOther_Photo_Adapter(Context context, List<Person_Photo_Model.DataBean> otherList) {
//        this.otherList = otherList;
        this.context = context;
        this.otherList = otherList;
    }

    public void addMoreData(Person_Photo_Model otherList) {
        int lastIndex = this.otherList.size() + 1;//头部多几项刷新需要添加
        if (this.otherList.addAll(otherList.getData())) {
            notifyItemRangeInserted(lastIndex, otherList.getData().size());
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.personandother_photo_adapter, null));
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

        if (otherList.get(position).getFilePath() != null) {
            Uri imgUri = Uri.parse(otherList.get(position).getFilePath());
            holder.my_simple.setImageURI(imgUri);
        }else{
//            StaticData.lodingheadBg(holder.my_simple);
        }
//        holder.name_Txt.setText(baseModel.getData().get(position).getProjectName());

//        holder.choice_check.setChecked(mcheckflag.get(position));
    }

    @Override
    public int getItemCount() {
        return (otherList == null) ? 0 : otherList.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

   public class MyViewHolder extends RecyclerView.ViewHolder {
       public LinearLayout my_Lin;
        public SimpleDraweeView my_simple;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Lin = (LinearLayout) itemView.findViewById(R.id.my_Lin);
            my_simple = (SimpleDraweeView) itemView.findViewById(R.id.my_simple);
//            StaticData.ViewScale(my_Lin, 0, 300);
            StaticData.ViewScale(my_simple, 246, 246);
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
