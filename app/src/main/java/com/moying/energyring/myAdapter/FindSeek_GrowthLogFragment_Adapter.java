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
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_Other;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.moying.energyring.StaticData.FrescoStatic.addGif;
import static com.moying.energyring.network.saveFile.getShareData;

/**
 * Created by Admin on 2016/3/29.
 */
public class FindSeek_GrowthLogFragment_Adapter extends RecyclerView.Adapter<FindSeek_GrowthLogFragment_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public FindSeek_GrowthLogFragment_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
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
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.growthlogfragment_adapter, null));
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

//        String headStr = otherList.get(position).getProfilePicture();
//        if (!headStr.isEmpty()) {
//            if (!headStr.substring(0, 4).equals("http")) {
//                headStr = "http://" + headStr;
//            }
//        }

        final EnergyList_Model.DataBean oneData = otherList.get(position);

//        if (oneData.getProfilePicture() != null) {
////            StaticData.addPlaceRound(holder.myhead_simple, context);
//            Uri headUri = Uri.parse(oneData.getProfilePicture());
//            holder.myhead_simple.setImageURI(headUri);
//
//        }

//        holder.name_Txt.setText(oneData.getNickName());
//        holder.time_Txt.setText(StaticData.getStandardDate(oneData.getCreateTime()));
//        holder.content_Txt.setText(HtmlToText.delHTMLTag(oneData.getPostContent()));
        if (oneData.getPostTitle() != null) {
            holder.name_Txt.setText(oneData.getPostTitle().toString());
        }
        holder.talk_Txt.setText(oneData.getCommentNum() + "");
        holder.like_Txt.setText(oneData.getLikes() + "");
        holder.yue_Txt.setText("阅读量："+oneData.getReadCount());
        if (oneData.isIs_Like()) {
            holder.energy_like.setImageResource(R.drawable.like_red_icon);
        } else {
            holder.energy_like.setImageResource(R.drawable.energy_like);
        }

        if (oneData.getPostType() == POST_TYPE) {//4英雄榜
            holder.hero_Lin.setVisibility(View.VISIBLE);
        } else if (oneData.getPostType() == 5) {
            if (oneData.getPostTitle() != null) {
                holder.content_Txt.setText(String.valueOf(oneData.getPostTitle()));
            } else {
                holder.content_Txt.setVisibility(View.INVISIBLE);
            }
        }
//        else if (oneData.getPostType() == 1 || oneData.getPostType() == 2) { //1是普通帖子 2是每日PK
//
//        }
        if (oneData.getFilePath() != null) {
//            StaticData.addPlace(holder.content_simple, context);
            StaticData.ViewScale(holder.content_simple, 710, 440);
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
//            holder.content_simple.setImageURI(contentUri);
            addGif(holder.content_simple, contentUri);
        }

        if (oneData.getTagName() != null) {//原type改为 tag标签
            holder.hero_Lin.setVisibility(View.VISIBLE);
            holder.hero_Txt.setText(oneData.getTagName()+"");
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
                zanData(context, saveFile.BaseUrl + saveFile.PostLike_Url + "?PostID=" + PostID, position);
            }
        });


        holder.remove_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                holder.remove_Txt.setEnabled(false);
                int postID = oneData.getPostID();
                deleData(context, saveFile.BaseUrl + saveFile.DelePost_Url, postID, position, holder.remove_Txt);
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
        private  TextView remove_Txt;
        private LinearLayout hero_Lin, like_Lin;
        private TextView name_Txt, time_Txt, content_Txt, talk_Txt, like_Txt,hero_Txt,yue_Txt;
        private ImageView energy_img, energy_like;
        private SimpleDraweeView myhead_simple, content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            hero_Lin = (LinearLayout) itemView.findViewById(R.id.hero_Lin);
            myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
            myhead_simple.setVisibility(View.GONE);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            name_Txt.setLines(1);
            time_Txt = (TextView) itemView.findViewById(R.id.time_Txt);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            content_Txt.setVisibility(View.GONE);
            energy_img = (ImageView) itemView.findViewById(R.id.energy_img);
            ImageView energy_talk = (ImageView) itemView.findViewById(R.id.energy_talk);
            energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
            talk_Txt = (TextView) itemView.findViewById(R.id.talk_Txt);
            like_Txt = (TextView) itemView.findViewById(R.id.like_Txt);
            like_Lin = (LinearLayout) itemView.findViewById(R.id.like_Lin);
            hero_Txt = (TextView) itemView.findViewById(R.id.hero_Txt);
            yue_Txt = (TextView) itemView.findViewById(R.id.yue_Txt);
            remove_Txt = (TextView) itemView.findViewById(R.id.remove_Txt);
            StaticData.ViewScale(mu_Rel, 710, 0);
//            StaticData.ViewScale(myhead_simple, 100, 100);

            StaticData.ViewScale(energy_img, 40, 40);
            StaticData.ViewScale(energy_talk, 40, 40);
            StaticData.ViewScale(energy_like, 40, 40);
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
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
        }
        params.addBodyParameter("PostID", postID + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model model = new Gson().fromJson(resultString, Base_Model.class);
                    if (model.isIsSuccess()) {
                        mytext.setEnabled(true);
                        otherList.remove(pos);
                        notifyItemRemoved(pos + 2);//加1是有头部
//                        notifyDataSetChanged();

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
                        if (oneData.isIs_Like()) {
                            oneData.setIs_Like(false);
                            if (oneData.getLikes() == 0) {
                                oneData.setLikes(0);
                            } else {
                                oneData.setLikes(oneData.getLikes() - 1);
                            }
                        } else {
                            oneData.setIs_Like(true);
                            oneData.setLikes(oneData.getLikes() + 1);
                        }
                        notifyItemChanged(pos + 2);//刷新一个item +2有一个刷新头部 与其他头部


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
