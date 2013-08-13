package com.bizbump.view.fragment.share;

import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bizbump.R;
import com.bizbump.utils.QRCodeUtiils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Shane on 8/12/13.
 */
public class ShareByQR extends Fragment {

    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout v =  (LinearLayout) inflater.inflate(R.layout.share_by_qr, container, false);

        imageView = (ImageView) v.findViewById(R.id.share_qr_code_image);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String contents = "Shane Is Cool";
        imageView.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int width = 512;
        int height = width;
        Log.d("Width", width + "");
        BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        try {
            BitMatrix matrix = barcodeWriter.encode(contents, barcodeFormat, width, height);
            imageView.setImageBitmap(QRCodeUtiils.toBitmap(matrix));
        } catch (WriterException e) {
            //Handle the error
            e.printStackTrace();
        }
    }
}
