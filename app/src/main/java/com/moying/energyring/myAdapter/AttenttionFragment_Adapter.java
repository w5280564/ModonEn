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
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.HtmlToText;
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
public class AttenttionFragment_Adapter extends RecyclerView.Adapter<AttenttionFragment_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 3;//3是公众承诺

    public AttenttionFragment_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(EnergyList_Model list) {
        int lastIndex = this.otherList.size();
        if (this.otherList.addAll(list.getData())) {
            notifyItemRangeInserted(lastIndex+1, list.getData().size());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview;
        if (otherList.get(myposition).getPostType() == POST_TYPE) {
            myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.commitedfragment_adapter, null));
        } else {
            myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.growthlogfragment_adapter, null));
        }
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

        final EnergyList_Model.DataBean oneData = otherList.get(myposition);


        if (otherList.get(myposition).getPostType() == POST_TYPE) {//3是公众承诺

            if (oneData.getProfilePicture() != null) {
//                StaticData.addPlaceRound(holder.myhead_simple, context);
                Uri headUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
                holder.myhead_simple.setImageURI(headUri);
            } else {
                StaticData.lodingheadBg(holder.myhead_simple);
            }

            holder.name_Txt.setText(oneData.getNickName());
            holder.time_Txt.setText(StaticData.getStandardDate(oneData.getCreateTime()));


            if (oneData.getFilePath() != null) {
//                StaticData.addPlace(holder.content_simple, context);
                Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
                holder.content_simple.setImageURI(contentUri);
            }
            holder.content_Txt.setText(HtmlToText.delHTMLTag(oneData.getPostContent()));
            holder.talk_Txt.setText(oneData.getCommentNum() + "");
            holder.like_Txt.setText(oneData.getLikes() + "");

        } else {

            if (oneData.getFilePath() != null) {
//                StaticData.addPlace(holder.content_simple, context);
                StaticData.ViewScale(holder.content_simple, 710, 440);
                Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
                holder.content_simple.setImageURI(contentUri);
            }
            if (oneData.getProfilePicture() != null){
            Uri headUri = Uri.parse(oneData.getProfilePicture());
                holder.myhead_simple.setImageURI(headUri);
            }
            holder.name_Txt.setText(oneData.getNickName());
            holder.time_Txt.setText(StaticData.getStandardDate(oneData.getCreateTime()));
            holder.content_Txt.setText(oneData.getPostContent());
            holder.talk_Txt.setText(oneData.getCommentNum() + "");
            holder.like_Txt.setText(oneData.getLikes() + "");

            if (oneData.getPostType() == POST_TYPE) {//4英雄榜
                holder.hero_Lin.setVisibility(View.VISIBLE);
            } else if (oneData.getPostType() == 5) {
                if (oneData.getPostTitle() != null) {
                    holder.content_Txt.setText(String.valueOf(oneData.getPostTitle()));
                } else {
                    holder.content_Txt.setVisibility(View.INVISIBLE);
                }
            }

        }

        if (oneData.isIs_Like()) {
            holder.energy_like.setImageResource(R.drawable.like_red_icon);
        } else {
            holder.energy_like.setImageResource(R.drawable.energy_like);
        }


        holder.myhead_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonMyCenter_Other.class);
                intent.putExtra("UserID", oneData.getUserID() + "");
                context.startActivity(intent);
            }
        });

        holder.like_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int PostID = oneData.getPostID();
                zanData(context, saveFile.BaseUrl + saveFile.PostLike_Url + "?PostID=" + PostID,position);
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
        private  LinearLayout like_Lin;
        private ImageView energy_like;
        private LinearLayout hero_Lin;
        private TextView name_Txt, time_Txt, content_Txt, talk_Txt, like_Txt;
        private ImageView energy_img;
        private SimpleDraweeView myhead_simple, content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (otherList.get(myposition).getPostType() == POST_TYPE) {
                mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
                myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
                content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
                name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
                time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
                content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
                talk_Txt = (TextView) itemView.findViewById(R.id.talk_Txt);
                like_Txt = (TextView) itemView.findViewById(R.id.like_Txt);
                ImageView energy_talk = (ImageView) itemView.findViewById(R.id.energy_talk);
                energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
                like_Lin = (LinearLayout) itemView.findViewById(R.id.like_Lin);
                StaticData.ViewScale(mu_Rel, 710, 0);
                StaticData.ViewScale(myhead_simple, 100, 100);
                StaticData.ViewScale(content_simple, 190, 190);
                StaticData.ViewScale(energy_talk, 40, 40);
                StaticData.ViewScale(energy_like, 40, 40);
            } else {
                mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
                hero_Lin = (LinearLayout) itemView.findViewById(R.id.hero_Lin);
                myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
                content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
                name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
                time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
                content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
                energy_img = (ImageView) itemView.findViewById(R.id.energy_img);
                ImageView energy_talk = (ImageView) itemView.findViewById(R.id.energy_talk);
                energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
                talk_Txt = (TextView) itemView.findViewById(R.id.talk_Txt);
                like_Txt = (TextView) itemView.findViewById(R.id.like_Txt);
                like_Lin = (LinearLayout) itemView.findViewById(R.id.like_Lin);
                StaticData.ViewScale(mu_Rel, 710, 0);
                StaticData.ViewScale(myhead_simple, 100, 100);
                StaticData.ViewScale(energy_img, 40, 40);
                StaticData.ViewScale(energy_talk, 40, 40);
                StaticData.ViewScale(energy_like, 40, 40);

            }
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
                        EnergyList_Model.DataBean oneData = otherList.get(pos);
                        if (oneData.isIs_Like()){
                            oneData.setIs_Like(false);
                            if (oneData.getLikes() == 0) {
                                oneData.setLikes(0);
                            } else {
                                oneData.setLikes(oneData.getLikes() - 1);
                            }
                        }else{
                            oneData.setIs_Like(true);
                            oneData.setLikes(oneData.getLikes() + 1);
                        }
                        notifyItemChanged(pos +1 );//刷新一个item +1有一个刷新头部


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
