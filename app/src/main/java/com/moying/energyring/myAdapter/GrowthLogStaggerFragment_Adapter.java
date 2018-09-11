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
public class GrowthLogStaggerFragment_Adapter extends RecyclerView.Adapter<GrowthLogStaggerFragment_Adapter.MyViewHolder> {
    List<EnergyList_Model.DataBean> otherList;
    private EnergyList_Model listModel;
    Context context;
    private int myposition;
    final int POST_TYPE = 4;

    public GrowthLogStaggerFragment_Adapter(Context context, List<EnergyList_Model.DataBean> otherList, EnergyList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(EnergyList_Model list) {
        int lastIndex = this.otherList.size() + 2;//头部多几项刷新需要添加
        if (this.otherList.addAll(list.getData())) {
            notifyItemRangeInserted(lastIndex, list.getData().size());
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.staggerfragment_adapter, null));
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

//        if (getShareData("role", context).equals("false")) {
//
//            holder.remove_Txt.setVisibility(View.INVISIBLE);
//        } else {
//            int role = Integer.parseInt(getShareData("role", context));
//            int UserId = Integer.parseInt(getShareData("userId", context));
//            if (otherList.get(position).getUserID() == UserId || role < 3) {
//                holder.remove_Txt.setVisibility(View.VISIBLE);
//            } else {
//                holder.remove_Txt.setVisibility(View.INVISIBLE);
//            }
//        }


        final EnergyList_Model.DataBean oneData = otherList.get(position);
        if (oneData.getProfilePicture() != null) {
//            StaticData.addPlaceRound(holder.myhead_simple, context);
            Uri headUri = Uri.parse(oneData.getProfilePicture());
            holder.myhead_simple.setImageURI(headUri);
        } else {
            StaticData.lodingheadBg(holder.myhead_simple);
        }
        holder.name_Txt.setText(oneData.getNickName());
        holder.content_Txt.setText(HtmlToText.delHTMLTag(oneData.getPostContent()));
        holder.like_Txt.setText(oneData.getLikes() + "");


        if (oneData.isIs_Like()) {
            holder.energy_like.setImageResource(R.drawable.like_red_icon);
        } else {
            holder.energy_like.setImageResource(R.drawable.energy_like);
        }

        if (oneData.getFilePath() != null) {
            setWidthHeight(holder.content_simple, oneData.getWidth(), oneData.getHeight());

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
        holder.stagger_bot_Img.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showMore(context, holder.stagger_bot_Img, position);
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
        private LinearLayout like_Lin;
        private TextView name_Txt, content_Txt, like_Txt;
        private ImageView energy_like, stagger_bot_Img;
        private SimpleDraweeView myhead_simple, content_simple;
        private RelativeLayout mu_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            mu_Rel = (RelativeLayout) itemView.findViewById(R.id.mu_Rel);
            content_simple = (SimpleDraweeView) itemView.findViewById(R.id.content_simple);
            content_Txt = (TextView) itemView.findViewById(R.id.content_Txt);
            stagger_bot_Img = (ImageView) itemView.findViewById(R.id.stagger_bot_Img);
            viewTouchDelegate.expandViewTouchDelegate(stagger_bot_Img, 100, 100, 100, 100);
            like_Lin = (LinearLayout) itemView.findViewById(R.id.like_Lin);
            viewTouchDelegate.expandViewTouchDelegate(like_Lin, 100, 100, 100, 100);
            energy_like = (ImageView) itemView.findViewById(R.id.energy_like);
            like_Txt = (TextView) itemView.findViewById(R.id.like_Txt);
            myhead_simple = (SimpleDraweeView) itemView.findViewById(R.id.myhead_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);

            StaticData.ViewScale(myhead_simple, 50, 50);
            StaticData.ViewScale(energy_like, 40, 40);
            StaticData.ViewScale(stagger_bot_Img, 22, 14);
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
                        notifyItemRemoved(pos + 2);//加1是有头部 +2 添加了关注列表
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

    //只剪切3:4  4：3  1:1 3种格式
    private void setWidthHeight(View view, int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }
        int newWidth = 0;
        int newHeight = 0;
        double scale = (double) width / height;
        double oneScale = (double) 3 / 4; // 0.75
        double twoScale = (double) 1 / 1; // 1
        double threeScale = (double) 4 / 3; // 1.333333...
        double newScale = 0;

        if (scale <= oneScale) {
            newScale = oneScale;
        } else if (oneScale < scale && scale < twoScale) {

            if (twoScale - scale > scale - oneScale) {
                newScale = twoScale;
            } else {
                newScale = oneScale;
            }
        } else if (scale == twoScale) {
            newScale = twoScale;
        } else if (twoScale < scale && scale < threeScale) {

            if (threeScale - scale > scale - twoScale) {
                newScale = threeScale;
            } else {
                newScale = twoScale;
            }

        } else if (scale >= threeScale) {
            newScale = threeScale;
        }

        newWidth = (750 - 30) / 2;
        if (newScale == oneScale) {
            newHeight = (int) (newWidth * ((double) 4 / 3));
        } else if (newScale == twoScale) {
            newHeight = newWidth;
        } else if (newScale == threeScale) {
            newHeight = (int) (newWidth * ((double) 3 / 4));
        }
        StaticData.ViewScale(view, newWidth, newHeight);
    }

    //更多
    private void showMore(final Context mContext, final View view, final int pos) {
        final View contentView = View.inflate(mContext, R.layout.popup_calner_more, null);
        final PopupWindow MorePopup = new BasePopupWindow(mContext);
        MorePopup.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        MorePopup.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        MorePopup.setTouchable(true);
        MorePopup.setContentView(contentView);
        MorePopup.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        TextView report_one_Txt = (TextView) contentView.findViewById(R.id.report_one_Txt);
        final TextView report_two_Txt = (TextView) contentView.findViewById(R.id.report_two_Txt);
        TextView report_three_Txt = (TextView) contentView.findViewById(R.id.report_three_Txt);
        final TextView report_four_Txt = (TextView) contentView.findViewById(R.id.report_four_Txt);
        report_one_Txt.setVisibility(View.GONE);

        report_two_Txt.setText("举报");


        final int postID = otherList.get(pos).getPostID();
        report_two_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showReport(context, view, postID + "", pos);
//                showMore(context, view, pos);
                MorePopup.dismiss();
            }
        });

        report_three_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                report_two_Txt.setEnabled(false);
                deleData(context, saveFile.BaseUrl + saveFile.DelePost_Url, postID, pos, report_two_Txt);
                MorePopup.dismiss();
            }
        });


        report_four_Txt.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {

                MorePopup.dismiss();
            }
        });

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorePopup.dismiss();
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
        String UserID = otherList.get(pos).getUserID() + "";

//        if (UserID.equals(dbUserId)) {
//            report_one_Txt.setVisibility(View.GONE);
//            report_two_Txt.setVisibility(View.GONE);
//            report_three_Txt.setVisibility(View.GONE);
//            report_four_Txt.setVisibility(View.GONE);
//            report_five_Txt.setVisibility(View.GONE);
//        } else {
            report_one_Txt.setVisibility(View.VISIBLE);
            report_two_Txt.setVisibility(View.VISIBLE);
            report_three_Txt.setVisibility(View.VISIBLE);
            report_four_Txt.setVisibility(View.VISIBLE);
            report_five_Txt.setVisibility(View.GONE);
//        }

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
//                deleData(context, saveFile.BaseUrl + saveFile.DelePost_Url, postID, pos);
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


}
