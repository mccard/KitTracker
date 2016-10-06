package edu.uco.mcamposcardoso.kittracker.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.LinearLayout;

import com.welcu.android.zxingfragmentlib.BarCodeScannerFragment;

import edu.uco.mcamposcardoso.kittracker.R;

public class ScannerActivity extends FragmentActivity {

    LinearLayout layoutContent;
    BarCodeScannerFragment mScannerFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        FragmentManager fm = getSupportFragmentManager();
        mScannerFragment = (BarCodeScannerFragment) fm.findFragmentById(R.id.scanner_fragment);

/*    layoutContent = (LinearLayout) findViewById(R.id.layout_content);

    final ViewTreeObserver observer = layoutContent.getViewTreeObserver();

    observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        // We're assuming that the other layout is under the scanner
        int activityWidth = layoutContent.getWidth();
        int activityHeight = findViewById(R.id.scanner_fragment).getHeight();

        int usableWidth = layoutContent.getWidth();
        int usableHeight = activityHeight - layoutContent.getHeight();

        int desiredHeight = (int) (usableHeight * 0.8);
        int desiredWidth = (int) (usableWidth * 0.75);

        Rect framingRect = new Rect(
            (usableWidth - desiredWidth) / 2, // left
            (usableHeight - desiredHeight) / 2, // top
            (usableWidth - desiredWidth) / 2 + desiredWidth, // right
            (usableHeight - desiredHeight) / 2 + desiredHeight// bottom
        );
        Log.v("RECT", "left: " + framingRect.left + " top: " + framingRect.top + " right: " + framingRect.right + " bottom: " + framingRect.bottom + " activityHeight: " + activityHeight + " activitiWidth: " + activityWidth);
//        mScannerFragment.setFramingRect(framingRect);
      }
    });*/

//    mToggleButton = (Button) findViewById(R.id.button_flash);
//    mToggleButton.setOnClickListener(createToggleFlashListener());

    }
}