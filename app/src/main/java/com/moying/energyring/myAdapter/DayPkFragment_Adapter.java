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
import com.moying.energyring.Model.DayPkList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.StaticData.viewTouchDelegate;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Admin on 2016/3/29.
 */
public class DayPkFragment_Adapter extends RecyclerView.Adapter<DayPkFragment_Adapter.MyViewHolder> {
    List<DayPkList_Model.DataBean> otherList;
    private DayPkList_Model listModel;
    Context context;
    private int myposition;

    public DayPkFragment_Adapter(Context context, List<DayPkList_Model.DataBean> otherList, DayPkList_Model listModel) {
        this.otherList = otherList;
        this.context = context;
        this.listModel = listModel;
    }

    public void addMoreData(List<DayPkList_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.daypk_adapter, null));
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
        if (position == 0) {
            holder.my_Rel.setBackgroundResource(R.drawable.ban_round_top);
        } else if (position == otherList.size() - 1) {
//            holder.my_Rel.setBackgroundResource(R.drawable.ban_round_below);
//            holder.line.setVisibility(View.GONE);
        }else{

        }

        final DayPkList_Model.DataBean oneData = otherList.get(position);

        holder.rank_Txt.setText(oneData.getRanking() + ".");
        if (oneData.getProfilePicture() != null) {
            Uri headUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
            holder.head_simple.setImageURI(headUri);
        } else {
            StaticData.lodingheadBg(holder.head_simple);
        }
        if (oneData.getNickName() != null) {
            holder.name_Txt.setText(oneData.getNickName() + "");
        }

//        if (oneData.getLimit() >= oneData.getReportNum()) {
//            holder.all_Txt.setText(oneData.getReportNum() + "+");
//        } else {
//            holder.all_Txt.setText(oneData.getReportNum() + "个");
//        }
        NumberFormat nf = new DecimalFormat("#.#");//# 0不显示
        if (oneData.getLimit() == 1){
            holder.all_Txt.setText(oneData.getReport_Days() + oneData.getProjectUnit());
        }else{
            if (oneData.getReportNum() >= oneData.getLimit()){
                holder.all_Txt.setText(nf.format(oneData.getLimit()) + "+");
            }else{
                holder.all_Txt.setText(nf.format(oneData.getReportNum()) + oneData.getProjectUnit());
            }
        }


//        if (oneData.getReportNum() >= oneData.getLimit()) {
//            if (oneData.getReportNum() == 1) {
//                holder.all_Txt.setText(nf.format(oneData.getLimit()));
//            } else {
//                holder.all_Txt.setText(nf.format(oneData.getLimit()) + "+");
//            }
//        } else {
//            holder.all_Txt.setText(nf.format(oneData.getReportNum()) + oneData.getProjectUnit());
//        }
//        holder.zan_Txt.setText(oneData.getLikes() + "");


//        if (saveFile.getShareData("userId", context).equals(oneData.getUserID() + "")) {
//            holder.zan_Lin.setVisibility(View.INVISIBLE);
////            holder.zan_img.setVisibility(View.INVISIBLE);
//        } else {
            if (oneData.isIs_Like()) {
                holder.zan_img.setImageResource(R.drawable.like_red_icon);
            } else {
                holder.zan_img.setImageResource(R.drawable.energy_like);
            }
//        }

        holder.zan_Lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ReportID = oneData.getReportID();
                zanData(context, saveFile.BaseUrl + saveFile.like_Url + "?ReportID=" + ReportID, position);
            }
        });

    }

    @Override
    public int getItemCount() {
//        return 20;
        return (otherList == null) ? 0 : otherList.size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout zan_Lin;
        private View line;
        private ImageView zan_img;
        private SimpleDraweeView head_simple;
        private TextView rank_Txt, name_Txt, all_Txt, zan_Txt;
        private RelativeLayout my_Rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            my_Rel = (RelativeLayout) itemView.findViewById(R.id.my_Rel);
            rank_Txt = (TextView) itemView.findViewById(R.id.rank_Txt);
            head_simple = (SimpleDraweeView) itemView.findViewById(R.id.head_simple);
            name_Txt = (TextView) itemView.findViewById(R.id.name_Txt);
            all_Txt = (TextView) itemView.findViewById(R.id.all_Txt);
            zan_Txt = (TextView) itemView.findViewById(R.id.zan_Txt);
            zan_img = (ImageView) itemView.findViewById(R.id.zan_img);
            zan_Lin = (LinearLayout) itemView.findViewById(R.id.zan_Lin);
            viewTouchDelegate.expandViewTouchDelegate(zan_Lin, 100, 100, 200, 200);
            line = (View) itemView.findViewById(R.id.line);
            StaticData.ViewScale(my_Rel, 710, 120);
            StaticData.ViewScale(head_simple, 80, 80);
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
                        DayPkList_Model.DataBean oneData = otherList.get(pos);
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
//                        notifyDataSetChanged();
                        notifyItemChanged(pos + 2);//刷新一个item +2有一个刷新头部 跟头部个人资料


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
