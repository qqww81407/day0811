package tw.com.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity2 extends AppCompatActivity {

    private Button inwbtn,inrbtn,inlistbtn,getpathbtn,getoutpath,outwbtn,tempbtn,writeout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        checkPermission(); //確認權限
        findView();

    }

    //Android 6.0 版本之後,權限要檢查
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Log.i("Lcc","OK");
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void findView(){
        inwbtn = findViewById(R.id.inwbtn);
        inrbtn = findViewById(R.id.inrbtn);
        inlistbtn = findViewById(R.id.inlistbtn);
        getpathbtn = findViewById(R.id.getpathbtn);
        getoutpath = findViewById(R.id.getoutpath);
        outwbtn = findViewById(R.id.outwbtn);
        tempbtn = findViewById(R.id.tempbtn);
        writeout = findViewById(R.id.writeout);

        writeout.setOnClickListener(v -> {
            try {
                writePublicPic();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //寫入內部
        inwbtn.setOnClickListener(v -> {

            writeInFile();

        });
        //讀取內部
        inrbtn.setOnClickListener(v -> {

            readInFile();

        });
        //內部列表
        inlistbtn.setOnClickListener(v -> {

        });
        //顯示內部路徑
        getpathbtn.setOnClickListener(v -> {
            showInPath();

        });
        //顯示外部路徑
        getoutpath.setOnClickListener(v -> {
            showOutPath();

        });
        //外部寫檔
        outwbtn.setOnClickListener(v -> {
            writeOutFile();

        });
        //暫存寫檔
        tempbtn.setOnClickListener(v -> {
            tempWriteFile();

        });

    }

    private void writePublicPic() throws IOException {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        InputStream is = null;
        OutputStream os = null;
        try {
            if (! file.exists()){
                if (!file.mkdirs()){
                    Toast.makeText(MainActivity2.this,"mkdir ERROR",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            is = getAssets().open("證件照.jpg");
            File dir = new File(file,"證件照.jpg");
            os = new FileOutputStream(dir);
            byte[] bytes = new byte[is.available()];
            while(is.read(bytes) != -1) {
                os.write(bytes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                is.close();
                os.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //寫暫存檔
    private void tempWriteFile(){
        String temppath = getCacheDir().getPath();
        String path = temppath + "/tempHello.txt";
        File file = getExternalFilesDir(null);
        try {
            file.mkdir();
            if(! file.exists()){
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(path,true);
            output.write("Hello Lcc".getBytes());
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //寫外部檔案
    private void writeOutFile(){
        String outpath = getExternalFilesDir(null).getAbsolutePath()+"/lcc.txt";
        File file = getExternalFilesDir(null);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream output = new FileOutputStream(outpath,true);
            output.write("Hello LCC".getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //寫內部檔案
    private void writeInFile(){
        try {
            FileOutputStream output = openFileOutput("Hello.txt",MODE_PRIVATE);
            output.write("LCC GOOD".getBytes());
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //讀內部檔案
    private void readInFile(){
        try {
            FileInputStream input = openFileInput("Hello.txt");
            byte[] bytes = new byte[1024];
            StringBuffer sb = new StringBuffer();
            int length = 0;
            while((length = input.read(bytes)) != -1){
                sb.append(new String(bytes,0,length));
            }
            input.close();
            Log.i("Lcc",sb.toString());

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showInPath(){
        //抓取內部files目錄路徑
        Log.i("Lcc",getFilesDir().getPath());
        //抓取內部暫存目錄路徑
        Log.i("Lcc",getCacheDir().getPath());
        //Android 7.0之後才有 根目錄
        Log.i("Lcc",getDataDir().getPath());
    }
    private void showOutPath(){
        //公共目錄下的路徑 (DCIM)
        Log.i("Lcc", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath());

        Log.i("Lcc",getExternalFilesDir(null).getPath());

    }
}