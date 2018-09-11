package com.moying.energyring.myAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Base_Model;
import com.moying.energyring.Model.EnergyList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.HtmlToText;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_And_Other;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static com.moying.energyring.network.saveFile.getShareData;

/**
 * Created by Admin on 2016/3/29.
 */
public class PersonAndOther_GrowthLog_Adapter extends RecyclerView.Adapter<PersonAndOther_GrowthLog_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public PersonAndOther_GrowthLog_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(EnergyList_Model list) {
        int lastIndex = this.otherList.size() + 1;//头部多几项刷新需要添加
        if (this.otherList.addAll(list.getData())) {
            notifyItemRangeInserted(lastIndex, list.getData().size());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.personandother_growthlog_adapter, null));
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

        if (getShareData("role", context).equals("false")) {

            holder.report_Img.setVisibility(View.INVISIBLE);
        } else {
            int role = Integer.parseInt(getShareData("role", context));
            int UserId = Integer.parseInt(getShareData("userId", context));
            if (otherList.get(position).getUserID() == UserId || role < 3) {
                holder.report_Img.setVisibility(View.VISIBLE);
            } else {
                holder.report_Img.setVisibility(View.INVISIBLE);
            }
        }


        final EnergyList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null) {
//            StaticData.addPlaceRound(holder.myhead_simple, context);
            Uri headUri = Uri.parse(oneData.getProfilePicture());
            holder.myhead_simple.setImageURI(headUri);
        } else {
            StaticData.lodingheadBg(holder.myhead_simple);
        }
        holder.name_Txt.setText(oneData.getNickName());
        holder.time_Txt.setText(StaticData.getDate(oneData.getCreateTime()));
//        holder.time_Txt.setText(StaticData.Datatypetwo(oneData.getCreateTime()));
//        holder.time_Txt.setText(StaticData.getStandardDate(oneData.getCreateTime()));
        holder.content_Txt.setText(HtmlToText.delHTMLTag(oneData.getPostContent()));
        holder.talk_Txt.setText(oneData.getCommentNum() + "");
        holder.like_Txt.setText(oneData.getLikes() + "");
        StaticData.setGarde(holder.grade_Img, oneData.getIntegralLevel());


        if (oneData.isIs_Like()) {
            holder.energy_like.setImageResource(R.drawable.like_red_icon);
        } else {
            holder.energy_like.setImageResource(R.drawable.energy_like);
        }

        if (oneData.getTagName() != null) {//原type改为 tag标签
            holder.hero_Lin.setVisibility(View.VISIBLE);
            holder.hero_Txt.setText(oneData.getTagName() + "");
        }

        if (oneData.getPostType() == POST_TYPE) {//4英雄榜
            holder.hero_Lin.setVisibility(View.VISIBLE);
        } else if (oneData.getPostType() == 5) {//微信文章
            if (oneData.getPostTitle() != null) {
                holder.content_Txt.setText(String.valueOf(oneData.getPostTitle()));
            } else {
                holder.content_Txt.setVisibility(View.INVISIBLE);
            }
        } else if (oneData.getPostType() == 6) { //6是每日汇总
            holder.hui_Img.setVisibility(View.VISIBLE);
            holder.hero_Lin.setVisibility(View.GONE);
        }
//        else if (oneData.getPostType() == 1 || oneData.getPostType() == 2) { //1是普通帖子 2是每日PK
//
//        }
        if (oneData.getFilePath() != null) {
//            StaticData.addPlace(holder.content_simple, context);
            StaticData.ViewScale(holder.content_simple, 710, 710);
            Uri contentUri = Uri.parse(String.valueOf(oneData.getFilePath()));
            holder.content_simple.setImageURI(contentUri);
        }


        holder.myhead_simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PersonMyCenter_And_Other.class);
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

//        holder.remove_Txt.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//            protected void onNoDoubleClick(View v) {
//                holder.remove_Txt.setEnabled(false);
//                int postID = oneData.getPostID();
//                deleData(context, saveFile.BaseUrl + saveFile.DelePost_Url, postID, position, holder.remove_Txt);
//            }
//        });

        holder.report_Img.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showReport(context, holder.report_Img, oneData.getPostID() + "", position);
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
        private ImageView grade_Img, report_Img;
        private LinearLayout hero_Lin, like_Lin;
        private TextView name_Txt, time_Txt, content_Txt, talk_Txt, like_Txt, hero_Txt;
        private ImageView energy_img, energy_like, hui_Img;
        private SimpleDraweeView myhead_simple, content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
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
            viewTouchDelegate.expandViewTouchDelegate(like_Lin, 100, 100, 100, 100);
            hero_Txt = (TextView) itemView.findViewById(R.id.hero_Txt);
            grade_Img = (ImageView) itemView.findViewById(R.id.grade_Img);
            report_Img = (ImageView) itemView.findViewById(R.id.report_Img);
            viewTouchDelegate.expandViewTouchDelegate(report_Img, 100, 100, 100, 100);
//            remove_Txt = (TextView) itemView.findViewById(R.id.remove_Txt);
//            viewTouchDelegate.expandViewTouchDelegate(remove_Txt, 100, 100, 100, 100);
            StaticData.ViewScale(myhead_simple, 100, 100);
            StaticData.ViewScale(grade_Img, 56, 28);

            StaticData.ViewScale(energy_img, 40, 40);
            StaticData.ViewScale(energy_talk, 40, 40);
            StaticData.ViewScale(energy_like, 40, 40);
            StaticData.ViewScale(report_Img, 44, 40);

            hui_Img = (ImageView) itemView.findViewById(R.id.hui_Img);
            RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            StaticData.layoutParamsScale(Params, 100, 76);
            int padtop = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * -8);
            int padright = (int) (Float.parseFloat(saveFile.getShareData("scale", context)) * 50);
            Params.setMargins(0, padtop, padright, 0);
            Params.addRule(RelativeLayout.ALIGN_TOP, R.id.mu_Rel);
            Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            hui_Img.setLayoutParams(Params);
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
        if (getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", getShareData("JSESSIONID", context));
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
                        notifyItemChanged(pos + 2);//刷新一个item +1有一个刷新头部 +2 添加了关注列表


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

    //举报
    private void showReport(final Context mContext, final View view, final String SourceID, final int pos) {
        View contentView = View.inflate(mContext, R.layout.popup_report, null);
        final PopupWindow ReportPopup = new BasePopupWindow(mContext);
        ReportPopup.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        ReportPopup.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        ReportPopup.setTouchable(true);
        ReportPopup.setContentView(contentView);
        ReportPopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        TextView report_one_Txt = (TextView) contentView.findViewById(R.id.report_one_Txt);
        TextView report_two_Txt = (TextView) contentView.findViewById(R.id.report_two_Txt);
        TextView report_three_Txt = (TextView) contentView.findViewById(R.id.report_three_Txt);
        TextView report_four_Txt = (TextView) contentView.findViewById(R.id.report_four_Txt);
        TextView report_five_Txt = (TextView) contentView.findViewById(R.id.report_five_Txt);


        report_one_Txt.setText("数据虚假");
        report_two_Txt.setText("广告");
        report_three_Txt.setText("不友善行为");
        report_four_Txt.setText("其他");
        report_five_Txt.setText("删除");


        final String dbUserId = saveFile.getShareData("userId", mContext);
        final String SourceName = "User";
        String UserID = otherList.get(pos).getUserID()+"";

        if (UserID.equals(dbUserId)) {
            report_one_Txt.setVisibility(View.GONE);
            report_two_Txt.setVisibility(View.GONE);
            report_three_Txt.setVisibility(View.GONE);
            report_four_Txt.setVisibility(View.GONE);
            report_five_Txt.setVisibility(View.VISIBLE);
        }else {
            report_one_Txt.setVisibility(View.VISIBLE);
            report_two_Txt.setVisibility(View.VISIBLE);
            report_three_Txt.setVisibility(View.VISIBLE);
            report_four_Txt.setVisibility(View.VISIBLE);
            report_five_Txt.setVisibility(View.VISIBLE);
        }

        report_one_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String ReportType = "1";
                String baseUrl = saveFile.BaseUrl + saveFile.Report_Add_Url;
                ReportMethod(mContext, baseUrl, dbUserId, SourceID, SourceName, ReportType);
                ReportPopup.dismiss();

            }
        });
        report_two_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String ReportType = "2";
                String baseUrl = saveFile.BaseUrl + saveFile.Report_Add_Url;
                ReportMethod(mContext, baseUrl, dbUserId, SourceID, SourceName, ReportType);
                ReportPopup.dismiss();

            }
        });

        report_three_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String ReportType = "3";
                String baseUrl = saveFile.BaseUrl + saveFile.Report_Add_Url;
                ReportMethod(mContext, baseUrl, dbUserId, SourceID, SourceName, ReportType);
                ReportPopup.dismiss();

            }
        });
        report_four_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                String ReportType = "4";
                String baseUrl = saveFile.BaseUrl + saveFile.Report_Add_Url;
                ReportMethod(mContext, baseUrl, dbUserId, SourceID, SourceName, ReportType);
                ReportPopup.dismiss();
            }
        });
        report_five_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

                int postID = otherList.get(pos).getPostID();
                deleData(context, saveFile.BaseUrl + saveFile.DelePost_Url, postID, pos);
                ReportPopup.dismiss();
            }
        });

//        contentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ReportPopup.dismiss();
//            }
//        });
    }


    public void ReportMethod(final Context context, final String baseUrl, String myuserId, String SourceID, String SourceName, String ReportType) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("UserID", myuserId);
            obj.put("SourceID", SourceID);
            obj.put("SourceName", SourceName);
            obj.put("ReportType", ReportType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Base_Model baseModel = new Gson().fromJson(resultString, Base_Model.class);
                    if (baseModel.isIsSuccess()) {
                        Toast.makeText(context, "举报成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "数据获取失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {

            }
        });
    }


    //删帖
    public void deleData(final Context context, String baseUrl, int postID, final int pos) {
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
                        otherList.remove(pos);
                        notifyItemRemoved(pos + 1);//加1是有头部 +2 添加了关注列表
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
