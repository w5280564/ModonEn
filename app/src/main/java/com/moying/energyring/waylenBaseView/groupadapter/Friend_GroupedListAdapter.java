package com.moying.energyring.waylenBaseView.groupadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.Model.Friend_InviteAll_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.NoDoubleClickListener;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAcativity.Person.PersonMyCenter_And_Other;
import com.moying.energyring.network.saveFile;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 这是普通的分组Adapter 每一个组都有头部、尾部和子项。
 */
public class Friend_GroupedListAdapter extends GroupedRecyclerViewAdapter {

    private Friend_InviteAll_Model mGroups;
    Context context;

    public Friend_GroupedListAdapter(Context context, Friend_InviteAll_Model groups) {
        super(context);
        mGroups = groups;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return mGroups == null ? 0 : mGroups.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Friend_InviteAll_Model.DataBean.ProjectListBean> children = mGroups.getData().get(groupPosition).get_Project_List();
//        ArrayList<ChildEntity> children = mGroups.get(groupPosition).getChildren();

        return children == null ? 0 : children.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return true;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
//        if (groupPosition == 0) {
        return false;
//        } else {
//            return true;
//        }
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return R.layout.friend_adapter_header;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return R.layout.adapter_footer;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.friend_adapter_child;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {
//        GroupEntity entity = mGroups.get(groupPosition);
//        holder.setText(R.id.tv_header, entity.getHeader());
//        StaticData.ViewScale(holder.get(R.id.my_Lin), 0, 126);
        StaticData.ViewScale(holder.get(R.id.tv_header), 0, 88);
//        Friend_Invite_Model.DataBean.InSysBean oneData = mGroups.getData().getInSys().get(groupPosition);
        Friend_InviteAll_Model.DataBean oneData = mGroups.getData().get(groupPosition);
        holder.setText(R.id.tv_header, oneData.getProjectTypeName());
    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {
//        GroupEntity entity = mGroups.get(groupPosition);
//        holder.setText(R.id.tv_footer, entity.getFooter());
        Friend_InviteAll_Model.DataBean oneData = mGroups.getData().get(groupPosition);
        holder.setText(R.id.tv_footer, oneData.getProjectTypeName());
    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, final int childPosition) {
//        ChildEntity entity = mGroups.get(groupPosition).getChildren().get(childPosition);
//        StaticData.ViewScale(holder.get(R.id.tv_child),0,144);
//        holder.setText(R.id.tv_child, oneData.getProjectName());


//

        RelativeLayout my_Rel = holder.get(R.id.my_Rel);
        RelativeLayout join_Rel = holder.get(R.id.join_Rel);
        SimpleDraweeView my_Head = holder.get(R.id.my_Head);
        TextView name_Txt = holder.get(R.id.name_Txt);
        TextView content_Txt = holder.get(R.id.content_Txt);
        TextView isfocus_Txt = holder.get(R.id.isfocus_Txt);
        ImageView grade_Img = holder.get(R.id.grade_Img);
        StaticData.ViewScale(my_Rel, 0, 120);
        StaticData.ViewScale(my_Head, 80, 80);
        StaticData.ViewScale(isfocus_Txt, 110, 44);
        StaticData.ViewScale(grade_Img, 40, 20);

        RelativeLayout notJoin_Rel = holder.get(R.id.notJoin_Rel);
        SimpleDraweeView project_simple = holder.get(R.id.project_simple);
        TextView head_Txt = holder.get(R.id.head_Txt);
        TextView headname_Txt = holder.get(R.id.headname_Txt);
        TextView invite_Txt = holder.get(R.id.invite_Txt);
        StaticData.ViewScale(project_simple, 80, 80);
        StaticData.ViewScale(invite_Txt, 110, 44);

        final Friend_InviteAll_Model.DataBean.ProjectListBean oneData = mGroups.getData().get(groupPosition).get_Project_List().get(childPosition);

        if (!StaticData.isSpace(mGroups.getData().get(groupPosition).getProjectTypeName())) {


            if (mGroups.getData().get(groupPosition).getProjectTypeName().equals("已加入能量圈")) {
                join_Rel.setVisibility(View.VISIBLE);
                notJoin_Rel.setVisibility(View.GONE);
                if (oneData.getProfilePicture() != null) {
                    Uri contentUri = Uri.parse(String.valueOf(oneData.getProfilePicture()));
                    my_Head.setImageURI(contentUri);
                } else {
                    StaticData.lodingheadBg(my_Head);
                }
                name_Txt.setText(oneData.getNickName().toString());
//            if (oneData.getBrief() != null) {
//                content_Txt.setText(oneData.getBrief().toString());
//            }

                String contentStr = "坚持#" + oneData.getProjectName() + " " + oneData.getReport_Days() + "天";
                content_Txt.setText(contentStr);
                if (oneData.getIntegralLevel() != null) {
                    StaticData.setGarde(grade_Img, Integer.parseInt(oneData.getIntegralLevel().toString()));

                }

                if (oneData.isIs_Attention_Me() && oneData.isIs_Attention()) {
                    isfocus_Txt.setBackgroundResource(R.drawable.friend_focus_already);
                } else {
                    if (oneData.isIs_Attention()) {
                        isfocus_Txt.setBackgroundResource(R.drawable.friend_focus_select);
                    } else {
                        isfocus_Txt.setBackgroundResource(R.drawable.friend_focus_icon);
                    }
                }

                isfocus_Txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int FriendUserID = oneData.getUserID();
                        zanData(context, saveFile.BaseUrl + saveFile.Friend_Add_User_Url + "?FriendUserID=" + FriendUserID, childPosition, oneData);
                    }
                });

                my_Head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PersonMyCenter_And_Other.class);
                        intent.putExtra("UserID", oneData.getUserID() + "");
                        context.startActivity(intent);
                    }
                });

            } else {
                join_Rel.setVisibility(View.GONE);
                notJoin_Rel.setVisibility(View.VISIBLE);
                if (oneData.getNickName() != null) {
//                my_Head.setText(oneData.getNickName().toString());
//                holder.setText(R.id.name_Txt, oneData.getNickName().toString());
                    head_Txt.setText(oneData.getNickName().toString());
                    headname_Txt.setText(oneData.getNickName().toString());

                    invite_Txt.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        protected void onNoDoubleClick(View v) {
                            doSendSMSTo(oneData.getPhoneNum(), "");
                        }
                    });

                }

            }

        }

    }

    /**
     * 调起系统发短信功能
     *
     * @param phoneNumber
     * @param message
     */
    public void doSendSMSTo(String phoneNumber, String message) {
        if (PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
//            intent.putExtra("sms_body", message);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "手机号是否正常", Toast.LENGTH_SHORT).show();
        }
    }

    public void zanData(final Context context, String baseUrl, final int pos, final Friend_InviteAll_Model.DataBean.ProjectListBean oneData) {
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
//                        Recommend_Model.DataBean oneData = otherList.get(pos);
                        if (oneData.isIs_Attention()) {
                            oneData.setIs_Attention(false);
                        } else {
                            oneData.setIs_Attention(true);
                        }
//                        notifyDataSetChanged();
                        notifyItemChanged(pos + 1);//刷新一个item +1有一个刷新头部 +2 添加了关注列表

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
