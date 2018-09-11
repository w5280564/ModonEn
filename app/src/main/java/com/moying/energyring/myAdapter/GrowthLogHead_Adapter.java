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
import com.moying.energyring.Model.AllPerson_Model;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.headListModel;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


/**
 * Created by Admin on 2016/3/29.
 */
public class GrowthLogHead_Adapter extends RecyclerView.Adapter<GrowthLogHead_Adapter.MyViewHolder> {
    headListModel baseModel;
    Context context;
    private int myposition;

    public GrowthLogHead_Adapter(Context context, headListModel baseModel) {
        this.context = context;
        this.baseModel = baseModel;
    }

    public void addMoreData(List<AllPerson_Model.DataBean> otherList) {
//        this.otherList = otherList;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myview = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.growthloghead_adapter, null));
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

        final headListModel.DataBean oneData = baseModel.getData().get(position);

        if (oneData.getProfilePicture() != null) {
//            StaticData.addPlace(holder.nohead_simple, context);
            Uri imgUri = Uri.parse(oneData.getProfilePicture());
            holder.head_simple.setImageURI(imgUri);
        } else {
            StaticData.lodingheadBg(holder.head_simple);
        }

        holder.headcontent_Txt.setText(oneData.getNickName());
        if (oneData.isIs_Attention()){
            holder.tui_foucuse.setVisibility(View.GONE);
        }else{
            holder.tui_foucuse.setVisibility(View.VISIBLE);
            holder.tui_foucuse.setImageResource(R.drawable.tui_focuse);
        }


        StaticData.setGarde(holder.grade_Img,oneData.getIntegralLevel());

        holder.tui_foucuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int FriendUserID = oneData.getUserID();
                zanData(context, saveFile.BaseUrl + saveFile.Friend_Add_User_Url + "?FriendUserID=" + FriendUserID, position);
            }
        });

    }

    @Override
    public int getItemCount() {
//        return 1;
        return (baseModel == null) ? 0 : baseModel.getData().size();//数据加一项
    }

    @Override
    public int getItemViewType(int position) {
        return myposition = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView tui_foucuse,grade_Img;
        private TextView headcontent_Txt;
        private SimpleDraweeView head_simple;

        public MyViewHolder(View itemView) {
            super(itemView);
            RelativeLayout head_Rel = (RelativeLayout) itemView.findViewById(R.id.head_Rel);
            head_simple = (SimpleDraweeView) itemView.findViewById(R.id.head_simple);
            headcontent_Txt = (TextView) itemView.findViewById(R.id.headcontent_Txt);
            tui_foucuse = (ImageView) itemView.findViewById(R.id.tui_foucuse);
            grade_Img = (ImageView) itemView.findViewById(R.id.grade_Img);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            int mag = (int)(Float.parseFloat(saveFile.getShareData("scale",context)) *68);
            params.setMargins(mag,mag,0,0);
            tui_foucuse.setLayoutParams(params);

            RelativeLayout.LayoutParams gradeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            gradeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,R.id.head_Rel);
            StaticData.layoutParamsScale(gradeParams, 40, 20);
            int magTop = (int)(Float.parseFloat(saveFile.getShareData("scale",context)) *8);
            gradeParams.setMargins(0,magTop,0,0);
            grade_Img.setLayoutParams(gradeParams);

            StaticData.ViewScale(head_Rel, 114, 150);
            StaticData.ViewScale(head_simple, 100, 100);
            StaticData.ViewScale(tui_foucuse, 36, 36);
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
                        headListModel.DataBean oneData = baseModel.getData().get(pos);
                        if (oneData.isIs_Attention()) {
                            oneData.setIs_Attention(false);
                        } else {
                            oneData.setIs_Attention(true);
                        }
                        notifyItemChanged(pos);
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
