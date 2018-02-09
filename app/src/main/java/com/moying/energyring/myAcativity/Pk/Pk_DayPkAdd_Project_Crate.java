package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.BaseDataInt_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.LoginRegister;
import com.moying.energyring.myAcativity.MainActivity;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.waylenBaseView.BasePopupWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Pk_DayPkAdd_Project_Crate extends Activity {
    private LinearLayout my_Lin;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pkdayadd_project__crate);

        projectName = getIntent().getStringExtra("project");
        my_Lin = (LinearLayout) findViewById(R.id.my_Lin);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    private boolean popisShow = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!popisShow) {
            popisShow = true;
            showCrateName(this, my_Lin);
        }
    }

    private void showCrateName(final Context mContext, View view) {
        View contentView = View.inflate(mContext, R.layout.popup_daypk_crateproject, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        View projext_Rel = contentView.findViewById(R.id.projext_Rel);
        ImageView return_black = (ImageView) contentView.findViewById(R.id.return_black);
        final EditText project_Edit = (EditText) contentView.findViewById(R.id.project_Edit);
        Button next_Btn = (Button) contentView.findViewById(R.id.next_Btn);
        TextView hint_Name = (TextView) contentView.findViewById(R.id.hint_Name);
        StaticData.ViewScale(projext_Rel, 710, 500);
        StaticData.ViewScale(return_black, 80, 88);
        StaticData.ViewScale(next_Btn, 0, 120);
        hint_Name.setText("添加新习惯");
        project_Edit.setText(projectName);
        project_Edit.setSelection(projectName.length());

        project_Edit.setFocusable(true);
        project_Edit.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(project_Edit, 0);
                }
            }
        }, 500);

        return_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StaticData.isSpace(project_Edit.getText().toString())) {
                    Toast.makeText(mContext, "新习惯名字不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(project_Edit.getWindowToken(), 0);
                }
//                inputManager.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
                showCrateUnit(mContext, my_Lin, project_Edit.getText().toString());
            }
        });



    }

    private String projextUnit = "";

    private void showCrateUnit(final Context mContext, View view, final String projectName) {
        View contentView = View.inflate(mContext, R.layout.popup_daypk_crateproject_unit, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
//        popupWindow.showAsDropDown(view, 0, 0);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        View projext_Rel = contentView.findViewById(R.id.projext_Rel);
        ImageView return_black = (ImageView) contentView.findViewById(R.id.return_black);
        LinearLayout unit_Lin = (LinearLayout) contentView.findViewById(R.id.unit_Lin);
//        final EditText project_Edit = (EditText) contentView.findViewById(R.id.project_Edit);
        Button next_Btn = (Button) contentView.findViewById(R.id.next_Btn);
        TextView hint_Name = (TextView) contentView.findViewById(R.id.hint_Name);
        StaticData.ViewScale(projext_Rel, 710, 500);
        StaticData.ViewScale(return_black, 80, 88);
        StaticData.ViewScale(next_Btn, 0, 120);
//        project_Edit.setEnabled(false);
//        project_Edit.setText("单位");
        hint_Name.setText("添加单位");
        timeUnit(unit_Lin, mContext);
        return_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        next_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCrateSure(mContext, my_Lin, projectName, projextUnit);
            }
        });

    }

    private String[] timeArr = {"米", "秒", "分钟", "小时", "个"};
    private List<TextView> unitArr = new ArrayList<>();

    public void timeUnit(LinearLayout myLin, Context context) {
        if (myLin != null) {
            myLin.removeAllViews();
        }
        if (unitArr != null) {
            unitArr.clear();
        }
        int size = timeArr.length;
        for (int i = 0; i < size; i++) {
            View myview = LayoutInflater.from(this).inflate(R.layout.colock_textview, null);
            final TextView week_Txt = (TextView) myview.findViewById(R.id.time_Txt);
            StaticData.ViewScale(week_Txt, 100, 70);
            week_Txt.setText(timeArr[i]);
            if (i == 0) {
                week_Txt.setTextColor(Color.parseColor("#000000"));
                projextUnit = timeArr[i];
            } else {
                week_Txt.setTextColor(Color.parseColor("#e3e3e3"));
            }
            myview.setTag(i);
            myview.setEnabled(true);
            unitArr.add(week_Txt);
            myLin.addView(myview);
            myview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (Integer) v.getTag();
                    for (int j = 0; j < timeArr.length; j++) {
                        unitArr.get(j).setTextColor(Color.parseColor("#e3e3e3"));
                    }
                    unitArr.get(tag).setTextColor(Color.parseColor("#000000"));
                    projextUnit = timeArr[tag];
                }
            });

        }
    }

    private void showCrateSure(final Context mContext, View view, final String projectName, final String projectUnit) {
        View contentView = View.inflate(mContext, R.layout.popup_daypk_crateproject_delete, null);
        final BasePopupWindow popupWindow = new BasePopupWindow(mContext);
        popupWindow.setWidth(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(RadioGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setContentView(contentView);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
        View projext_Rel = contentView.findViewById(R.id.projext_Rel);
        ImageView return_black = (ImageView) contentView.findViewById(R.id.return_black);
        TextView project_sureTxt = (TextView) contentView.findViewById(R.id.project_sureTxt);
        TextView hint_Name = (TextView) contentView.findViewById(R.id.hint_Name);
        TextView cancel_btn = (TextView) contentView.findViewById(R.id.cancel_btn);
        TextView sure_btn = (TextView) contentView.findViewById(R.id.sure_btn);
        View cha_Lin = contentView.findViewById(R.id.cha_Lin);
        StaticData.ViewScale(projext_Rel, 710, 500);
        StaticData.ViewScale(return_black, 80, 88);
        StaticData.ViewScale(cha_Lin, 0, 120);
        project_sureTxt.setText("您即将创建“" + projectName + "”卡\n单位为“" + projectUnit + "”");
        hint_Name.setText("提示");
        return_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject_Data(mContext, saveFile.BaseUrl + saveFile.Project_Add_Url,projectName,projectUnit);
            }
        });
    }

    public void addProject_Data(final Context context, String baseUrl,String projectName,String projextUnit) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ProjectName", projectName);
            obj.put("ProjectUnit", projextUnit);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        params.setAsJsonContent(true);
        params.setBodyContent(obj.toString());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    BaseDataInt_Model baseModel = new Gson().fromJson(resultString, BaseDataInt_Model.class);
                    if (baseModel.isIsSuccess()) {

                        Intent i = new Intent(context, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

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
                    startActivity(intent);
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


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            return false;
//        }
//        return false;
//    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}

