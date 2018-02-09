package com.moying.energyring.myAcativity.Person;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.moying.energyring.R;
import com.moying.energyring.StaticData.StaticData;
import com.moying.energyring.basePopup.popupThree;
import com.moying.energyring.database.ChildInfo;
import com.moying.energyring.database.Deaft_Adapter;
import com.moying.energyring.myAcativity.PostingActivity;
import com.moying.energyring.waylenBaseView.MyActivityManager;
import com.moying.energyring.xrecycle.XRecyclerView;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

public class PersonDeaft extends Activity {

    private XRecyclerView my_recycle;
    private List<ChildInfo> myArr;
    private Deaft_Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_deaft);
        MyActivityManager mam = MyActivityManager.getInstance();
        mam.pushOneActivity(this);//把当前activity压入了栈中

        initTitle();

        my_recycle = (XRecyclerView) findViewById(R.id.my_recycle);
        my_recycle.setPullRefreshEnabled(false);
        initDb();
    }

    private void initTitle() {
        View title_Include = (View) findViewById(R.id.title_Include);
        title_Include.setBackgroundColor(Color.parseColor("#ffffff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            title_Include.setElevation(2f);//阴影
        }
        Button return_Btn = (Button) title_Include.findViewById(R.id.return_Btn);
        return_Btn.setBackgroundResource(R.drawable.return_black);
        TextView cententtxt = (TextView) title_Include.findViewById(R.id.cententtxt);
        cententtxt.setTextColor(Color.parseColor("#909090"));
        cententtxt.setText("草稿箱");
        StaticData.ViewScale(return_Btn, 48, 48);
        StaticData.ViewScale(title_Include, 0, 88);

        return_Btn.setOnClickListener(new return_Btn());
//        right_Btn.setOnClickListener(new right_Btn());
    }

    public class return_Btn implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (db.findAll(ChildInfo.class) != null) {
                myArr = db.findAll(ChildInfo.class);
                initlist(myArr);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void initlist(List<ChildInfo> infoList) {
        //创建Manager
        LinearLayoutManager mMangaer = new LinearLayoutManager(this);
//         使用重写后的线性布局管理器
        my_recycle.setLayoutManager(mMangaer);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        my_recycle.setHasFixedSize(true);
        mAdapter = new Deaft_Adapter(PersonDeaft.this, infoList);
        my_recycle.setAdapter(mAdapter);
        mAdapter.setOnItemClickLitener(new Deaft_Adapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PersonDeaft.this, PostingActivity.class);
                intent.putExtra("dbId", myArr.get(position - 1).getId() + "");
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                new remove_Popup(PersonDeaft.this,my_recycle,position);
                showPopup(position);
            }
        });
    }

    protected DbManager db;

    protected void initDb() {
        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("energy.db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                    }
                })//设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //balabala...
                        //开启数据库支持多线程操作，提升性能
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下
        db = x.getDb(daoConfig);
    }

    public void dbDelete(int position) {
        try {
            List<ChildInfo> users = db.findAll(ChildInfo.class);
            if (users == null || users.size() == 0) {
                return;//先调用dbAdd()方法
            }
            //db.delete(users.get(0)); //删除第一个对象
            //db.delete(User.class);//删除表中所有的User对象【慎用】
            //db.delete(users); //删除users对象集合
            //users =  db.findAll(User.class);
            // showDbMessage("【dbDelete#delete】数据库中还有user数目:" + users.size());
            WhereBuilder whereBuilder = WhereBuilder.b();
            int deleId = users.get(position).getId();
            whereBuilder.and("id", "=", deleId);
            db.delete(ChildInfo.class, whereBuilder);
            users = db.findAll(ChildInfo.class);
            myArr.remove(position);
            mAdapter.notifyDataSetChanged();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void showPopup(final int position) {
        final popupThree removePopup = new popupThree(this, my_recycle, "草稿箱", "删除草稿");
        Button sure_btn = (Button) removePopup.getContentView().findViewById(R.id.sure_btn);
        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbDelete(position - 1);
                removePopup.dismiss();
            }
        });
    }

//    public class remove_Popup extends PopupWindow {
//        public remove_Popup(Context mContext, View parent, final int position) {
//            super(parent);
//            View view = View.inflate(mContext, R.layout.popup_remove, null);
//            setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
//            setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
//            setOutsideTouchable(true);
//            setFocusable(true);
//            setContentView(view);
//            showAtLocation(parent, Gravity.TOP, 0, 0);
//            update();
//            Button sure_Btn = (Button) view.findViewById(R.id.sure_Btn);
//            Button cancel_Btn = (Button) view.findViewById(R.id.cancel_Btn);
//            sure_Btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    dbDelete(position - 1);
//                    dismiss();
//                }
//            });
//            cancel_Btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    dismiss();
//                }
//            });
//        }
//    }


}
