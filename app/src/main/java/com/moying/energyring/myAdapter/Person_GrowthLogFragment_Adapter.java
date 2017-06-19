package com.moying.energyring.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Person_GrowthLogFragment_Adapter extends RecyclerView.Adapter<Person_GrowthLogFragment_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public Person_GrowthLogFragment_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<EnergyList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person_growthlogfragment_adapter, null));
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

        String headStr = otherList.get(position).getProfilePicture();
        if (!headStr.isEmpty()) {
            if (!headStr.substring(0, 4).equals("http")) {
                headStr = "http://" + headStr;
            }
        }

        final EnergyList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getFilePath() != null){
            StaticData.ViewScale(holder.content_simple, 710, 440);
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.content_simple.setImageURI(contentUri);
        }
//        Uri headUri = Uri.parse(oneData.getProfilePicture());
//        holder.myhead_simple.setImageURI(headUri);
//        holder.name_Txt.setText(oneData.getNickName());
        holder.time_Txt.setText(StaticData.Datatypetwo(oneData.getCreateTime()));
        holder.content_Txt.setText(oneData.getPostContent());
        holder.talk_Txt.setText(oneData.getCommentNum()+"");
        holder.like_Txt.setText(oneData.getLikes()+"");

        if (oneData.getPostType() == POST_TYPE){//4英雄榜
            holder.hero_Lin.setVisibility(View.VISIBLE);
        }

        holder.remove_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                holder.remove_Txt.setEnabled(false);
                int postID = oneData.getPostID();
                deleData(context, saveFile.BaseUrl + saveFile.DelePost_Url,postID,position,holder.remove_Txt);
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
        private  LinearLayout hero_Lin;
        private  TextView time_Txt,content_Txt,talk_Txt,like_Txt,remove_Txt;
        private  ImageView energy_img;
        private  SimpleDraweeView myhead_simple,content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            hero_Lin = (LinearLayout) itemView.findViewById(R.id.hero_Lin);
//            myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
//            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            energy_img = (ImageView) itemView.findViewById(R.id.energy_img);
            ImageView energy_talk = (ImageView) itemView.findViewById(R.id.energy_talk);
            ImageView energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
             talk_Txt = (TextView) itemView.findViewById(R.id.talk_Txt);
            like_Txt = (TextView) itemView.findViewById(R.id.like_Txt);
            remove_Txt = (TextView) itemView.findViewById(R.id.remove_Txt);
            StaticData.ViewScale(mu_Rel, 710, 0);
//            StaticData.ViewScale(myhead_simple, 100, 100);

            StaticData.ViewScale(energy_img, 40, 40);
            StaticData.ViewScale(energy_talk, 40, 40);
            StaticData.ViewScale(energy_like, 40, 40);
            StaticData.ViewScale(remove_Txt, 80, 40);
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


    //删帖
    public void deleData(final Context context, String baseUrl, int postID, final int pos, final TextView mytext) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.addBodyParameter("PostID" , postID+"");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                    if (model.isIsSuccess()) {
                        mytext.setEnabled(true);
                        otherList.remove(pos);
                        notifyDataSetChanged();

                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                String errStr = throwable.getMessage();
                if (errStr.equals("Unauthorized")) {
                    Intent intent = new Intent(context, LoginRegister.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }





}
