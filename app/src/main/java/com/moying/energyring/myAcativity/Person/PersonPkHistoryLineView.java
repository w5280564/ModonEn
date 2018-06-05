package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moying.energyring.Model.Line_Model;
import com.moying.energyring.Model.PkHistoryLineList_Model;
import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.myAcativity.MainLogin;
import com.moying.energyring.myAdapter.Person_PkHistoryLine_Adapter;
import com.moying.energyring.network.saveFile;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class PersonPkHistoryLineView extends Activity implements XRecyclerView.LoadingListener {

    private static final String TAG = "PersonPkHistoryLineView";
    private LineChartView chart;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private Axis axisX;
    private Axis axisY;
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private boolean isBiss;

    private float mFirstX, mLastX;
    private static final float chartWidth = -6.5f;
    private final int initAddScro = 0;
    private final int leftScro = 1;
    private final int rightScro = 2;
    String ProjectID,userId;
    Calendar lineCalLeft = Calendar.getInstance();//最左日期
    Calendar lineCalRight = Calendar.getInstance();//最右日期
    private XRecyclerView my_recycle;
    private int PageIndex;
    private int pageSize;
    private  Person_PkHistoryLine_Adapter  mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_pk_history_line_view);
        chart = (LineChartView) findViewById(R.id.chart);
        StaticData.ViewScale(chart,0,370);
        chart.setZoomEnabled(false);//设置是否支持缩放
        chart.setVisibility(View.INVISIBLE);
        chart.setValueSelectionEnabled(true);//点击选中

        my_recycle = (XRecyclerView) findViewById(R.id.my_recycle);
//        my_recycle.setPullRefreshEnabled(false);
        my_recycle.setLoadingListener(this);
        my_recycle.getItemAnimator().setChangeDuration(0);//动画执行时间为0 刷新不会闪烁

        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        userId = intent.getStringExtra("userId");

        initTitle();
        initData(initAddScro);
        initListData();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#2b2a2a"));
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#ffffff"));
        cententtxt.setText("");
        StaticData.ViewScale(return_Btn, 80, 88);
        StaticData.ViewScale(title_Include, 0, 88);
        return_Btn.setOnClickListener(new return_Btn());
    }



    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    private void initListData() {
        PageIndex = 1;
        pageSize = 10;
        listData(PersonPkHistoryLineView.this, saveFile.BaseUrl + saveFile.HistoryPk_List_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize+"&ProjectID="+ProjectID+"&UserID="+userId);
    }

    @Override
    public void onRefresh() {
        initListData();
    }

    @Override
    public void onLoadMore() {
        PageIndex += 1;
        pageSize = 10;
        listData(PersonPkHistoryLineView.this, saveFile.BaseUrl + saveFile.HistoryPk_List_Url + "?PageIndex=" + PageIndex + "&PageSize=" + pageSize+"&ProjectID="+ProjectID+"&UserID="+userId);
    }

    private void initData(int direction) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        Date nowDate = new Date();
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(nowDate);
        lineCalLeft.setTime(nowDate);
        lineCalLeft.add(Calendar.DATE, -29);//30天以前

        lineCalRight.setTime(nowDate);

        String StartDate = format.format(lineCalLeft.getTime());
        String EndDate = format.format(calNow.getTime());
        lineData(PersonPkHistoryLineView.this, saveFile.BaseUrl + saveFile.HistoryPk_Url + "?ProjectID=" + ProjectID + "&StartDate=" + StartDate + "&EndDate=" + EndDate+"&UserID="+userId, direction, calNow);
    }

    private void initLeftData(int direction) {
        lineCalLeft.add(Calendar.DATE, -1);//上一次开始日期减一天 最左日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        Calendar calNow = Calendar.getInstance();
        calNow.setTime(lineCalLeft.getTime());
        lineCalLeft.add(Calendar.DATE, -29);//30天以前


        String StartDate = format.format(lineCalLeft.getTime());
        String EndDate = format.format(calNow.getTime());
        lineData(PersonPkHistoryLineView.this, saveFile.BaseUrl + saveFile.HistoryPk_Url + "?ProjectID=" + ProjectID + "&StartDate=" + StartDate + "&EndDate=" + EndDate+"&UserID="+userId, direction, calNow);
    }

//    private void initRightData(int direction) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
//        Calendar calNow = Calendar.getInstance();
//        calNow.setTime(lineCalRight.getTime());
//
//        lineCalRight.add(Calendar.DATE, +29);//30天以前
//
//        String StartDate = format.format(calNow.getTime());
//        String EndDate = format.format(lineCalRight.getTime());
//        lineData(PersonPkHistoryLineView.this, saveFile.BaseUrl + saveFile.HistoryPk_Url + "?ProjectID=" + ProjectID + "&StartDate=" + StartDate + "&EndDate=" + EndDate, direction, calNow);
//    }
    public void lineData(final Context context, String baseUrl, final int direction, final Calendar calendar) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", context) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", context));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    Line_Model Model = new Gson().fromJson(resultString, Line_Model.class);
                    if (Model.isIsSuccess() && !Model.getData().equals("[]")) {

                        Line_Model.DataBean oneData = Model.getData();
                        int size = Model.getData().getReport_List().size();
                        Double max = Model.getData().getMaxReportNum();
                        if (direction == 0) {
                            generateData(size, max, oneData, calendar);
                        } else if (direction == 1) {
                            leftAddDataPoint(size - 1, max, oneData, calendar);
                            isBiss = false;
                        } else if (direction == 2) {
                            rightAddDataPoint(30);
                        }

                        if (size == 30){ //数据少于30条滑动到底部会加载更多
                            isBiss = false;
                        }else {
                            isBiss = true;
                        }
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


    /**
     * 设置视图
     *
     * @param left
     * @param right
     * @return
     */
    private Viewport initViewPort(float left, float right, Double max) {
        // 设置viewport
        Viewport port = new Viewport();
        port.top = max.floatValue();//Y轴上限，固定(不固定上下限的话，Y轴坐标值可自适应变化)
        port.bottom = 0;//Y轴下限，固定
        port.left = left;//X轴左边界，变化
        port.right = right;//X轴右边界，变化
        return port;
    }

    /**
     * 初始化图表
     *
     * @param numberOfPoints 初始数据
     */
    private void generateData(int numberOfPoints, Double max, Line_Model.DataBean oneData, Calendar calendar) {
        chart.setVisibility(View.VISIBLE);
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendar.getTime());
        SimpleDateFormat format = new SimpleDateFormat("M/d");
        final List<Line> lines = new ArrayList<>();
        int numberOfLines = 1;
        List<PointValue> values = null;
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < numberOfLines; ++i) {
            values = new ArrayList<>();
            for (int j = 0; j < numberOfPoints; j++) {
                int newIndex = j * -1;
                Log.i(TAG, "generateData: newIndex=" + newIndex);
//                values.add(new PointValue(newIndex, (float) Math.random() * 100));//坐标轴数据
                int dataj = numberOfPoints - j - 1;//颠倒循环
                int num = oneData.getReport_List().get(dataj).getReportNum();
                values.add(new PointValue(newIndex, num));//坐标轴数据

                String str;
                if (j == 0) {
                    str = "今天";
                } else {
                    str = format.format(cal.getTime());
                }
                AxisValue xValue = new AxisValue(newIndex);//X軸添加數據
                xValue.setLabel(str);
                mAxisXValues.add(xValue);
                cal.add(Calendar.DATE, -1);
            }
            Line line = new Line(values);

            line.setColor(Color.parseColor("#f24d4d"));
            line.setShape(ValueShape.CIRCLE);
            line.setStrokeWidth(1);
            line.setCubic(false);//曲线是否平滑，即是曲线还是折线
//            line.setPointColor(Color.parseColor("#000000"));
            line.setHasLabels(false);//节点上的数据是否显示
            line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
            line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示

            line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
            line.setPointRadius(4);
//            line.setAreaTransparency(2);//这里是设置填充颜色的透明度 取值0-255；
            line.setHasGradientToTransparent(true);//填充曲线渐变效果
            line.setFilled(true);//是否填充曲线的面积
            lines.add(line);
        }



        final LineChartData data = new LineChartData(lines);

        data.setValueLabelBackgroundEnabled(true);
        data.setValueLabelBackgroundAuto(true );
        data.setValueLabelBackgroundColor(Color.parseColor("#00ffffff"));
        data.setValueLabelsTextColor(Color.parseColor("#3e3d3d"));//节点字颜色
        if (hasAxes) {
            axisX = new Axis();
            axisX.setHasLines(true); //x 轴分割线
            axisX.setHasSeparationLine(false);//设置标签跟图表之间的轴线
            axisX.setLineColor(Color.parseColor("#3e3d3d"));//虚线颜色
            axisX.setValues(mAxisXValues);  //X軸加入數據列表
            axisY = new Axis().setHasLines(true);
            if (hasAxesNames) {
//                axisX.setName("Axis X");
//                axisY.setName("Axis Y");
            }
            data.setAxisXBottom(axisX);
        } else {
            data.setAxisXBottom(null);
            data.setAxisYLeft(null);
        }
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart.setLineChartData(data);
//        List<Float> maxList = new ArrayList<>();
//        for (int k = 0; k < numberOfPoints; k++) {
//            maxList.add(values.get(k).getY());
//        }
//        chart.setMax(Collections.max(maxList));

        final Viewport v = new Viewport(chart.getMaximumViewport());
        v.bottom = 0;
        v.top = max.floatValue();
        chart.setMaximumViewport(v);//固定Y轴的范围,如果没有这个,Y轴的范围会根据数据的最大值和最小值决定
        v.left = chartWidth;
        v.right = 0;
        chart.setCurrentViewport(v);

        final float firstXValue = values.get(values.size() - 1).getX();
        final float lastXValue = values.get(0).getX();
        chart.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                Log.i(TAG, "onViewportChanged: " + viewport.toString());
                int portLeft = (int) viewport.left;
                int portRight = (int) viewport.right;
                if (!isBiss && portLeft == firstXValue) {

                    isBiss = true;
//                    loadData();
                    initLeftData(leftScro);
                }
//                else if (!isBiss && portRight == lastXValue) {
//                    Log.i(TAG, "onViewportChanged: " + viewport.toString());
//
////                    isBiss = true;
////                    initRightData(rightScro);
////                    isBiss = false;
////                    rightLoadData();
////                    rightAddDataPoint(30);
//                }
            }
        });



        final Rect rect = chart.getChartComputator().getContentRectMinusAllMargins();
        final List<PointValue> finalValues = values;
        chart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float fx = chart.getChartComputator().computeRawDistanceX(5);
                float fy = chart.getChartComputator().computeRawDistanceY(100);
                Rect r = chart.getChartComputator().getContentRectMinusAllMargins();
                float x = motionEvent.getX() / (r.width() / v.width());
                float y = motionEvent.getY() / (r.height() / v.height());
                chart.moveTo(x, y);

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstX = motionEvent.getX();
                        mLastX = motionEvent.getX();
//                        Log.e(TAG, "onTouch: "+mFirstX );
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float totalDeltaX = motionEvent.getX() - mLastX;
                        mLastX = motionEvent.getX();
                        float realX = v.width() * totalDeltaX / rect.width();
                        Viewport vTemp = new Viewport(v);
                        vTemp.left += -realX;
                        vTemp.right = vTemp.left - chartWidth;
//                        if(vTemp.left<0){
//                            vTemp.left=0;
//                            vTemp.right=chartWidth;
//                        }
                        if (vTemp.left < finalValues.get(finalValues.size() - 1).getX()){//滑动超出最左边 加载到最左
                            vTemp.left = finalValues.get(finalValues.size() - 1).getX();
                            vTemp.right = finalValues.get(finalValues.size() - 1).getX() - chartWidth;
                        }

                        if (vTemp.right > finalValues.get(0).getX()) {//滑动超出最右边 加载到最右
                            vTemp.right = finalValues.get(0).getX();
                            vTemp.left = vTemp.right + chartWidth;
                        }
//                        if(vTemp.right-vTemp.left!=chartWidth){
//                            break;
//                        }
                        v.set(vTemp);
                        chart.setMaximumViewport(v);
                        chart.setCurrentViewport(v);
                        Log.e(TAG, "onTouch:after... " + v.left + "#" + v.top + "$" + v.right + "$" + v.bottom);
                        break;
                    case MotionEvent.ACTION_UP:
                    default:
                        break;
                }
                return false;
            }
        });


        chart.getSelectedValue().set(0, 0, SelectedValue.SelectedValueType.LINE);//默认第一个选中

    }


    public class order implements Comparator<Line_Model.DataBean.ReportListBean> {
        public int compare(Line_Model.DataBean.ReportListBean reportListBean, Line_Model.DataBean.ReportListBean t1) {
            return reportListBean.getReportNum() - t1.getReportNum(); //降序写法
        }
    }
    /**
     * 往左动态添加点到图表
     *
     * @param count
     */
    private void leftAddDataPoint(int count, Double max, Line_Model.DataBean oneData, Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("M/d");
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendar.getTime());
        Collections.sort(oneData.getReport_List(),new order());

        Line line = chart.getLineChartData().getLines().get(0);
        List<PointValue> values = line.getValues();
        int startIndex = (int) values.get(values.size() - 1).getX();
        for (int i = 0; i < count; i++) {
            int newIndex = startIndex - i -1;
//            int countDex = count -i; //颠倒循环
            int num = oneData.getReport_List().get(i).getReportNum();
            values.add(new PointValue(newIndex, num));

            String str = format.format(cal.getTime());
            AxisValue xValue = new AxisValue(newIndex);//X軸添加數據
            xValue.setLabel(str);
            mAxisXValues.add(xValue);
            cal.add(Calendar.DATE, -1);
        }

        line.setValues(values);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData lineData = new LineChartData(lines);
        lineData.setAxisXBottom(axisX);
//        lineData.setAxisYLeft(axisY); //y轴位置
        chart.setLineChartData(lineData);
        //根据点的横坐标实时变换X坐标轴的视图范围
        Viewport port = initViewPort(startIndex - 6, startIndex, max);
//        chart.setMaximumViewport(port);
//        Viewport port = initViewPort(-startIndex+1,-startIndex+10);
        chart.setCurrentViewport(port);
//        viewScro();//滑动刷新

//        Line line = chart.getLineChartData().getLines().get(0);
//        List<PointValue> values = line.getValues();
        final float firstXValue = values.get(values.size() - 1).getX();
        final float lastXValue = values.get(0).getX();
        chart.setViewportChangeListener(new ViewportChangeListener() {
            @Override
            public void onViewportChanged(Viewport viewport) {
                Log.i(TAG, "onViewportChanged: " + viewport.toString());
                int portLeft = (int) viewport.left;
                int portRight = (int) viewport.right;
                if (!isBiss && portLeft == firstXValue) {

                    isBiss = true;
//                    loadData();
                    initLeftData(leftScro);

                }
//                else if (!isBiss && portRight == lastXValue) {
//                    Log.i(TAG, "onViewportChanged: " + viewport.toString());
//
////                    isBiss = true;
////                    initRightData(rightScro);
////                    isBiss = false;
////                    rightLoadData();
////                    rightAddDataPoint(30);
//                }
            }
        });

    }


    /**
     * 往右动态添加点到图表
     *
     * @param count
     */
    private void rightAddDataPoint(int count) {
        Line line = chart.getLineChartData().getLines().get(0);
        List<PointValue> values = line.getValues();
        int startIndex = (int) values.get(0).getX();
        for (int i = 1; i <= count; i++) {
            int newIndex = i + startIndex;
//            Log.i(TAG, "addDataPoint: newIndex=" + newIndex);
            values.add(0, new PointValue(newIndex, (float) Math.random() * 100f));
//            values.add(new PointValue(newIndex, (float) Math.random() * 100f));
        }
        line.setValues(values);
        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData lineData = new LineChartData(lines);
        lineData.setAxisXBottom(axisX);
//        lineData.setAxisYRight(axisY);//y轴位置
        chart.setLineChartData(lineData);
        Viewport port = initViewPort(startIndex, startIndex + 6, 100.00);
//        chart.setMaximumViewport(port);
        chart.setCurrentViewport(port);
//        viewScro();//滑动刷新
    }

    private List<PkHistoryLineList_Model.DataBean> lineListModel;
    PkHistoryLineList_Model lineModel;

    public void listData(final Context context, String baseUrl) {
        RequestParams params = new RequestParams(baseUrl);
        if (saveFile.getShareData("JSESSIONID", this) != null) {
            params.setHeader("Cookie", saveFile.getShareData("JSESSIONID", this));
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String resultString) {
                if (resultString != null) {
                    if (PageIndex == 1) {
                        if (lineListModel != null) {
                            lineListModel.clear();
                        }
                        lineListModel = new ArrayList<>();
                    }
                    lineModel = new Gson().fromJson(resultString, PkHistoryLineList_Model.class);
                    if (lineModel.isIsSuccess() && !lineModel.getData().equals("[]")) {
                        lineListModel.addAll(lineModel.getData());
                        if (PageIndex == 1) {
                            my_recycle.refreshComplete();
                            initlist(context);
                        } else {
                            my_recycle.loadMoreComplete();
                            mAdapter.addMoreData(lineListModel);
                        }

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


    public void initlist(final Context context) {
//        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        itemParams.gravity = Gravity.CENTER_HORIZONTAL;
//        All_XRecy.setLayoutParams(itemParams);
        LinearLayoutManager mMangaer = new LinearLayoutManager(context);
        mMangaer.setOrientation(LinearLayoutManager.VERTICAL);
        my_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        my_recycle.setHasFixedSize(true);
        mAdapter = new Person_PkHistoryLine_Adapter(context, lineListModel, lineModel);
        my_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Person_PkHistoryLine_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }



}



