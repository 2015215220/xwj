package bysj.xwj;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends BaseActivity {
    private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        performCodeWithPermission("申请所有权限",new PermissionCallback(){
                    @Override
                    public void hasPermission(){
                    }
                    @Override
                    public void noPermission(){
                    }
                }, Manifest.permission.CALL_PHONE,Manifest.permission.RECORD_AUDIO,
                Manifest.permission.SEND_SMS,Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE,Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_SETTINGS,Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.BLUETOOTH,
                Manifest.permission.VIBRATE,Manifest.permission.EXPAND_STATUS_BAR,
                Manifest.permission.INTERNET,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                Manifest.permission.CHANGE_NETWORK_STATE,Manifest.permission.WAKE_LOCK,Manifest.permission.CAMERA);


        //用户登陆代码
        btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this,Choice.class);
                startActivity(intent);
            }
        });
    }
}
