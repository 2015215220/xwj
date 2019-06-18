package bysj.xwj;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bysj.xwj.DB.DBHelper;

public class Zhuce_login extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2;//注册和返回
    TextView Ename,Epassword,Erepassword;
    //数据库操作
    private DBHelper mHelper; //是一个类，主要是存放数据库的
    private SQLiteDatabase db;//
    private ContentValues values;
//      implements中使用方法监听
//      第一步：申明继承接口
//      第二步：alt+enter键 产生一个方法如下onClick
//      第三步：注册监听，如  名字.setOnClickListener(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce_login);
        btn1=(Button)findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        Ename=(TextView)findViewById(R.id.name);
        Epassword=(TextView)findViewById(R.id.password);
        Erepassword=(TextView)findViewById(R.id.repassword);
        mHelper=new DBHelper(this);//添加注册
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                //注册数据进数据库
                String name=Ename.getText().toString();
                String password=Epassword.getText().toString();
                String repassword=Erepassword.getText().toString();
                if((name.length()==0) || (password.length()==0) || (repassword.length()==0)){
                    Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
                }else if(password.equals(repassword)){
                    db = mHelper.getWritableDatabase();//获取可读写SQLiteDatabse对象
                    values = new ContentValues();       // 创建ContentValues对象
                    values.put("name", name);           // 将数据添加到ContentValues对象
                    values.put("password", password);
                    db.insert("x", null, values);
                    Toast.makeText(this, "信息已添加", Toast.LENGTH_SHORT).show();
                    db.close();
                    Intent intent=new Intent(Zhuce_login.this,Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "密码不对，请重新输入", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn2:
                Intent intent=new Intent(Zhuce_login.this,Login.class);
                startActivity(intent);
                break;
        }
    }
}
