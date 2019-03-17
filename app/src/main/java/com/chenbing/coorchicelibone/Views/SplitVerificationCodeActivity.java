package com.chenbing.coorchicelibone.Views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chenbing.coorchicelibone.Utils.BitmapUtils;
import com.chenbing.coorchicelibone.Utils.DisplayUtils;
import com.chenbing.iceweather.R;

public class SplitVerificationCodeActivity extends AppCompatActivity {

    private ImageView ivSrc, ivGray, ivBinary, ivProjectOntoX;
    private LinearLayout llSplitBmpContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_verification_code);

        ivSrc = (ImageView) findViewById(R.id.iv_src);
        ivGray = (ImageView) findViewById(R.id.iv_gray);
        ivBinary = (ImageView) findViewById(R.id.iv_binary);
        ivProjectOntoX = (ImageView) findViewById(R.id.iv_project_onto_x);

        llSplitBmpContainer = (LinearLayout) findViewById(R.id.ll_split_bmp_container);

        Bitmap srcBmp = readBitmap(R.drawable.vercode2);
        ivSrc.setImageBitmap(srcBmp);
        handleBitmap(srcBmp);

    }

    private Bitmap readBitmap(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    private void handleBitmap(Bitmap bmp) {
        Bitmap grayBmp = BitmapUtils.bitmap2Gray(bmp);
        ivGray.setImageBitmap(grayBmp);
        Bitmap binaryBmp = BitmapUtils.bitmap2Binary(bmp);
        ivBinary.setImageBitmap(binaryBmp);
        Bitmap projectOntoXBmp = BitmapUtils.projectionOntoX(binaryBmp);
        ivProjectOntoX.setImageBitmap(projectOntoXBmp);

        llSplitBmpContainer.removeAllViews();
        Bitmap[] splitBmps = BitmapUtils.splitVerCodeBmp(bmp);
        for (Bitmap temp : splitBmps) {
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, DisplayUtils.dipToPx(60));
            lp.weight = 1;
            img.setScaleType(ImageView.ScaleType.CENTER);
            img.setImageBitmap(temp);
            llSplitBmpContainer.addView(img, lp);
        }
    }

}
