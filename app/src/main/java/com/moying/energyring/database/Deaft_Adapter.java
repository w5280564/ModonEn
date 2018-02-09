package com.moying.energyring.database;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.moying.energyring.R;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class Deaft_Adapter extends RecyclerView.Adapter<Deaft_Adapter.MyViewHolder> {
    List<ChildInfo> baselist;
    Context context;
    private int myposition;

    public Deaft_Adapter(Context context, List<ChildInfo> baselist) {
        this.baselist = baselist;
        this.context = context;
    }

    public void addMoreData(List<ChildInfo> datas) {
        this.baselist = datas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview;
        myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.energy_adapter, null));
        return myview;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

        holder.timeTxt.setText(baselist.get(position).getTime());
        String[] imgStr = baselist.get(position).getImgurl().split(",");
        Log.e("imgstr",imgStr.length+"");
//        List imgArr = Arrays.asList(imgStr);
        if (imgStr.length == 0 || !imgStr[0].equals("")){
            Uri imgUri = Uri.parse("file:// /"+imgStr[0]);
            holder.my_simple.setImageURI(imgUri);
//            ImageLoader.getInstance().displayImage("file://" + imgStr[0], holder.my_simple, ImageLoaderUtil.initImageLoaderOptions());
        }else{
            holder.my_simple.setVisibility(View.GONE);
        }
//        Uri imgUri = Uri.parse(baselist.get(position).getImgurl());
//        holder.my_simple.setImageURI(imgUri);
        holder.contentTxt.setText(baselist.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return (baselist == null) ? 0 : baselist.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView contentTxt;
        private SimpleDraweeView my_simple;
        private  TextView timeTxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeTxt = (TextView) itemView.findViewById(R.id.timeTxt);
            my_simple = (SimpleDraweeView) itemView.findViewById(R.id.my_simple);
            contentTxt = (TextView) itemView.findViewById(R.id.contentTxt);
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
