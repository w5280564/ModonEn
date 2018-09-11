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
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Communit_Rank_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class Communit_RankFragment_Adapter extends RecyclerView.Adapter<Communit_RankFragment_Adapter.MyViewHolder> {
    List<Communit_Rank_Model.DataBean.CommRankingBean> otherList;
    private Communit_Rank_Model listModel;
    Context context;
    private int myposition;
    String ProjectID;

    public Communit_RankFragment_Adapter(Context context, List<Communit_Rank_Model.DataBean.CommRankingBean> otherList, Communit_Rank_Model listModel, String ProjectID) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
        this.ProjectID = ProjectID;
    }

    public void addMoreData(List<Communit_Rank_Model.DataBean.CommRankingBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.communit_rankfragment, null));
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
//        position = position -1;

        final Communit_Rank_Model.DataBean.CommRankingBean oneData = otherList.get(position);

        if (oneData.getRanking() == 1) {
            holder.rank_Txt.setBackgroundResource(R.drawable.rank_one);
        } else if (oneData.getRanking() == 2) {
            holder.rank_Txt.setBackgroundResource(R.drawable.rank_two);
        } else if (oneData.getRanking() == 3) {
            holder.rank_Txt.setBackgroundResource(R.drawable.rank_three);
        } else {
            holder.rank_Txt.setText(oneData.getRanking() + "");
        }

//        if (oneData.getCreateDate() != null) {
//            StringBuffer sbf = new StringBuffer();
//            sbf.append(oneData.getCreateDate().toString().substring(5, 16));
//            holder.time_Txt.setText(sbf.toString());
//        }
        holder.time_Txt.setText("已坚持" + oneData.getReport_Days() + "天");
        if (oneData.getProfilePicture() != null) {
            Uri uri = Uri.parse(oneData.getProfilePicture());
            holder.project_simple.setImageURI(uri);
        } else {
            StaticData.lodingheadBg(holder.project_simple);
        }

        holder.project_Name.setText(oneData.getNickName());


        holder.count_Txt.setText(oneData.getLikes() + "");
        if (oneData.getIs_Like() == 1) {
            holder.zan_img.setImageResource(R.drawable.like_red_icon);
        } else {
            holder.zan_img.setImageResource(R.drawable.energy_like);
        }

        holder.zan_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int PostID = oneData.getPostID();
                int ToUserID = oneData.getUserID();
                zanData(context, saveFile.BaseUrl + saveFile.Community_Likes_Add_Url + "?ProjectID=" + ProjectID + "&ToUserID=" + ToUserID, position);
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
        private final ImageView zan_img;
        private SimpleDraweeView project_simple;
        private TextView time_Txt, project_Name, count_Txt, rank_Txt;
        private RelativeLayout my_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            project_simple = (SimpleDraweeView) itemView.findViewById(R.id.project_simple);
            project_Name = (TextView) itemView.findViewById(R.id.project_Name);
            count_Txt = (TextView) itemView.findViewById(R.id.count_Txt);
            rank_Txt = (TextView) itemView.findViewById(R.id.rank_Txt);
            zan_img = (ImageView) itemView.findViewById(R.id.zan_img);
            viewTouchDelegate.expandViewTouchDelegate(zan_img, 100, 100, 100, 100);
            StaticData.ViewScale(my_Rel, 0, 120);
            StaticData.ViewScale(project_simple, 80, 80);
            StaticData.ViewScale(rank_Txt, 36, 48);
            StaticData.ViewScale(zan_img, 40, 40);
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
                        Communit_Rank_Model.DataBean.CommRankingBean oneData = otherList.get(pos);
                        if (oneData.getIs_Like() == 1) {
                            oneData.setIs_Like(0);
                            if (oneData.getLikes() == 0) {
                                oneData.setLikes(0);
                            } else {
                                oneData.setLikes(oneData.getLikes() - 1);
                            }
                        } else {
                            oneData.setIs_Like(1);
                            oneData.setLikes(oneData.getLikes() + 1);
                        }
//                        notifyDataSetChanged();
                        notifyItemChanged(pos + 1);//刷新一个item +2有一个刷新头部 跟头部个人资料


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
