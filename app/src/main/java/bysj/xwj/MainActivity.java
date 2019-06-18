package bysj.xwj;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.acker.simplezxing.activity.CaptureActivity;
import java.sql.Date;
import java.text.SimpleDateFormat;
import bysj.xwj.DB.DBHelper;
public class MainActivity extends AppCompatActivity {
    ImageButton car,unlock,scan;
    private DBHelper keyHelper; //是一个类，主要是存放数据库的
    private SQLiteDatabase db;//
    private ContentValues values;
    int i=0;
    String state="false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyHelper=new DBHelper(this);//添加
        car=(ImageButton)findViewById(R.id.car);
        unlock=(ImageButton)findViewById(R.id.unlock);
        scan=(ImageButton)findViewById(R.id.scan);
        unlock.setOnClickListener(new View.OnClickListener() {//主要
            @Override
            public void onClick(View v) {
                if(i==1) {
                    showNormalDialog1();
                    state="false";
                }else{
                    Toast.makeText(MainActivity.this,"当前没有需要还的车",Toast.LENGTH_LONG).show();
                }
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {//主要
            @Override
            public void onClick(View v) {
                if("true".equals(state)){
                    Toast.makeText(MainActivity.this,"您正在租车中...", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(intent,CaptureActivity.REQ_CODE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CaptureActivity.REQ_CODE){
            switch (resultCode){
                case Activity.RESULT_OK:
                    String info = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                    if(TextUtils.isEmpty(info)){
                        Toast.makeText(MainActivity.this,"未扫描到任何信息，请重试", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainActivity.this,"租车成功！", Toast.LENGTH_LONG).show();
                        state="true";
                        if(i==0) {
                            showNormalDialog();//显示对话框
                        }
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(MainActivity.this,"请检查相机并重试。", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.car);
        normalDialog.setTitle("Android的共享汽车");
        normalDialog.setMessage("请问是否借车");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name="宝马5系";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                        //获取当前时间
                        Date date = new Date(System.currentTimeMillis());
                        db = keyHelper.getWritableDatabase();//获取可读写SQLiteDatabse对象
                        values = new ContentValues();// 创建CoentValues对象
                        values.put("name",name);
                        values.put("borrow_time", simpleDateFormat.format(date));
                        db.insert("x_borrowtime", null, values);
                        Toast.makeText(MainActivity.this,name+simpleDateFormat.format(date),Toast.LENGTH_SHORT).show();
                        db.close();
                        i=1;//此时的值变了
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void showNormalDialog1() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MainActivity.this);
        normalDialog.setIcon(R.mipmap.car);
        normalDialog.setTitle("Android的共享汽车");
        normalDialog.setMessage("请问是否还车");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name="宝马5系";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
                        //获取当前时间
                        Date date = new Date(System.currentTimeMillis());
                        db = keyHelper.getWritableDatabase();//获取可读写SQLiteDatabse对象
                        values = new ContentValues();// 创建CoentValues对象
                        values.put("name",name);
                        values.put("return_time", simpleDateFormat.format(date));

                        db.insert("x_returntime", null, values);
                        Toast.makeText(MainActivity.this,name+simpleDateFormat.format(date),Toast.LENGTH_SHORT).show();
                        db.close();
                        i=0;//此时车搞定了
                        //计算钱的数目
                        money();

                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void money() {//计费处理
        String time,retime;
        String hour,fz,ms;
        String rehour,refz,rems;
        db = keyHelper.getReadableDatabase();
        String sql = "select * from x_borrowtime";
        Cursor cursor = db.rawQuery(sql, null);
        //使用数据进行数据存放
        if(cursor.moveToLast() == true){
                time=cursor.getString(cursor.getColumnIndex("borrow_time"));//最后一次时间
        }
        time=cursor.getString(cursor.getColumnIndex("borrow_time"));//最后一次时间
        hour=time.substring(12,14);//小时
        fz=time.substring(15,17);//分钟
        ms=time.substring(18,20);//秒数

        String sql1 = "select * from x_returntime";
        Cursor cursor1 = db.rawQuery(sql1, null);
        //使用数据进行数据存放
        if(cursor1.moveToLast() == true){
            retime=cursor1.getString(cursor1.getColumnIndex("return_time"));//最后一次时间
        }

        retime=cursor1.getString(cursor1.getColumnIndex("return_time"));//最后一次时间
        rehour=retime.substring(12,14);//小时
        refz=retime.substring(15,17);//分钟
        rems=retime.substring(18,20);//秒数

        //小时和分钟进行计算，别的就不计算了
        if(Integer.parseInt(rehour)<Integer.parseInt(hour)){//显示第一天和第二天的视角差例如11点0点还车
            int fen=(Integer.parseInt(rehour)+12-Integer.parseInt(hour))*60;
            if(Integer.parseInt(refz)<Integer.parseInt(fz)){//11分借的，下一个小时12分还的，应该是59分钟
                int fen1=fen+Integer.parseInt(refz)-Integer.parseInt(fz);//主要是分钟小于的问题，需要进行借位
                if(Integer.parseInt(rems)<Integer.parseInt(ms)){//秒数小于，属于时间正常情况
                    //发送时间fen1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }else{
                    fen1=fen1+1;//说明11秒借的12秒还的  说明开始新的1秒，要加1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }
            }else{
                int fen1=fen+Integer.parseInt(refz)-Integer.parseInt(fz);//如果是大于也是这个值，说明在60分中以上而已
                if(Integer.parseInt(rems)<Integer.parseInt(ms)){//秒数小于，属于时间正常情况
                    //发送时间fen1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }else{
                    fen1=fen1+1;//说明11秒借的12秒还的  说明开始新的1秒，要加1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }
            }
        }else{
            int fen=(Integer.parseInt(rehour)-Integer.parseInt(hour))*60;//正常情况下上午10点中午11点还的
            if(Integer.parseInt(refz)<Integer.parseInt(fz)){//11分借的，下一个小时12分还的，应该是59分钟
                int fen1=fen+Integer.parseInt(refz)-Integer.parseInt(fz);//主要是分钟小于的问题，需要进行借位
                if(Integer.parseInt(rems)<Integer.parseInt(ms)){//秒数小于，属于时间正常情况
                    //发送时间fen1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }else{
                    fen1=fen1+1;//说明11秒借的12秒还的  说明开始新的1秒，要加1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }
            }else{
                int fen1=fen+Integer.parseInt(refz)-Integer.parseInt(fz);//如果是大于也是这个值，说明在60分中以上而已
                if(Integer.parseInt(rems)<Integer.parseInt(ms)){//秒数小于，属于时间正常情况
                    //发送时间fen1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }else{
                    fen1=fen1+1;//说明11秒借的12秒还的  说明开始新的1秒，要加1
                    Toast.makeText(MainActivity.this,"一共花费了"+String.valueOf(fen1)+"分钟"+",共计"+String.valueOf( fen1)+"元",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
