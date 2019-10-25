package jaya.speechapp.cameraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btnCapture;
    private ImageView imgCapture;
    private static final int Image_Capture_Code=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCapture=findViewById(R.id.btnTakePicture);
        imgCapture=findViewById(R.id.capturedImage);
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cInt=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cInt,Image_Capture_Code);

            }
        });
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if (requestCode == Image_Capture_Code) {

            Bitmap bp = (Bitmap) data.getExtras().get("data");
            imgCapture.setImageBitmap(bp);
            saveImageToExternalStorage(bp);
            Toast.makeText(MainActivity.this,"Image Saved Successfully",Toast.LENGTH_LONG).show();

        }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this,"Cancelled",Toast.LENGTH_LONG).show();
            }
        }
    private void saveImageToExternalStorage(Bitmap finalBitmap) {
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/shameema");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // Tell the media scanner about the new file so that it is
        // immediately available to the user.


    }

}

