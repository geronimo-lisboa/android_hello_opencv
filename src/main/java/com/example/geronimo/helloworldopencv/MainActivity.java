package com.example.geronimo.helloworldopencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    private Bitmap image = null;
    private ImageView imageView = null;
    ////É aqui que acontece a macumba do opencv
    private Bitmap increaseBrightness(Bitmap bitmap, int value){
        Mat src = new Mat(bitmap.getHeight(), bitmap.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(bitmap, src);
        src.convertTo(src,-1,1,value);//é aqui que o brilho é aplicado
        Bitmap result = Bitmap.createBitmap(src.cols(),src.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(src,result);
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);
        //Testa se o OpenCV tá funcionando.
        if(!OpenCVLoader.initDebug()){
            TextView txt = (TextView)findViewById(R.id.IsOpenCVOk);
            txt.setText("fail opencv");
        }
        else {
            TextView txt = (TextView)findViewById(R.id.IsOpenCVOk);
            txt.setText("success opencv");
        }
        ////Pega algumas coisas necessárias.
        image = BitmapFactory.decodeResource(getResources(), R.drawable.imagem);
        imageView = (ImageView)findViewById(R.id.iv_image);
        SeekBar seekBar = (SeekBar)findViewById(R.id.sb_Brightness);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Bitmap edited = increaseBrightness(image,progress);
                imageView.setImageBitmap(edited);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
