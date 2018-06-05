package com.moying.energyring.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Recommend_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_Other;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class HeadBefo_Adapter extends RecyclerView.Adapter<HeadBefo_Adapter.MyViewHolder> {
    List<Recommend_Model.DataBean> otherList;
    private Recommend_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public HeadBefo_Adapter(Context context, List<Recommend_Model.DataBean> otherList, Recommend_Model listModel) {
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
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.headbefo_adapter, null));
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
//
        final Recommend_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null){
            Uri contentUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
            holder.my_Head.setImageURI(contentUri);
        }else{
            StaticData.lodingheadBg(holder.my_Head);
        }
        holder.name_Txt.setText(oneData.getNickName());
        if (oneData.isIs_Attention()){
            holder.isfocus_Txt.setBackgroundResource(R.drawable.focus_icon_select);
            holder.isfocus_Txt.setTextColor(Color.parseColor("#b9b9b9"));
            holder.isfocus_Txt.setText("已关注");
        }else{
            holder.isfocus_Txt.setBackgroundResource(R.drawable.focus_icon);
            holder.isfocus_Txt.setTextColor(Color.parseColor("#e05954"));
            holder.isfocus_Txt.setText("关注");
        }

        holder.isfocus_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int FriendUserID = oneData.getUserID();
                zanData(context, saveFile.BaseUrl + saveFile.Friend_Add_User_Url + "?FriendUserID=" + FriendUserID,position);
            }
        });

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

        private  LinearLayout my_Lin;
        private  SimpleDraweeView my_Head;
        private  TextView name_Txt;
        private  TextView isfocus_Txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Lin = (LinearLayout) itemView.findViewById(R.id.my_Lin);
            my_Head = (SimpleDraweeView) itemView.findViewById(R.id.my_Head);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            isfocus_Txt = (TextView) itemView.findViewById(R.id.isfocus_Txt);

            StaticData.ViewScale(my_Lin, 200, 280);
            StaticData.ViewScale(my_Head, 100, 100);
            StaticData.ViewScale(isfocus_Txt, 143, 52);
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

    public void zanData(final Context context, String baseUrl, final int pos) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model model = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (model.isIsSuccess()) {
                        Recommend_Model.DataBean oneData = otherList.get(pos);
                        if (oneData.isIs_Attention()){
                            oneData.setIs_Attention(false);
                        }else{
                            oneData.setIs_Attention(true);
                        }
//                        notifyDataSetChanged();
                        notifyItemChanged(pos);
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
                    Intent intent = new Intent(context, MainLogin.class);
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
