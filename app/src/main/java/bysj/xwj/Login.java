package bysj.xwj;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import bysj.xwj.DB.DBHelper;

public class Login extends AppCompatActivity {
    private Button btn1,btn2;//登陆，注册
    TextView Eusername,Epassword;//用户名，密码
    DBHelper dbHelper;//数据库资料
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn1=(Button) findViewById(R.id.btn1);
        btn2=(Button)findViewById(R.id.btn2);
        //用户登陆代码
        btn1=(Button)findViewById(R.id.btn1);//登陆
        btn2=(Button)findViewById(R.id.btn2);//注册
        Eusername=(TextView)findViewById(R.id.username);//用户名
        Epassword=(TextView)findViewById(R.id.password);//密码
        dbHelper=new DBHelper(this);//数据库，很重要，没写闪退
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=Eusername.getText().toString();
                String password=Epassword.getText().toString();
                SQLiteDatabase sdb=dbHelper.getReadableDatabase();
                String sql="select * from x where name=? and password=?";
                Cursor cursor=sdb.rawQuery(sql, new String[]{name,password});
                if(cursor.moveToFirst()==true){
                    Toast.makeText(Login.this, "登陆正确", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    Intent intent=new Intent(Login.this,MainActivity.class);
                    startActivity(intent);//启动
                }else{
                    Toast.makeText(Login.this, "密码错误,请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //btn1进主界面
        //btn2进正常注册界面
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Zhuce_login.class);
                startActivity(intent);
            }
        });




    }
}
