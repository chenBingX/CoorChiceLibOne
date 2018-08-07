package com.chenbing.coorchicelibone.Views;

import java.util.List;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import com.chenbing.iceweather.R;

import static android.accessibilityservice.AccessibilityServiceInfo.FEEDBACK_BRAILLE;

public class AccessibilityActivity extends AppCompatActivity {

    private TextView tvGet, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);

        tvGet = (TextView)findViewById(R.id.tv_get);
        tvGet.setOnClickListener(v -> getAccessibilityListAndEnableList());
        tvContent = (TextView)findViewById(R.id.tv_content);
    }

    private void getAccessibilityListAndEnableList() {
        AccessibilityManager accessibilityManager = (AccessibilityManager)getSystemService(
            Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("isOpenAccessibility:").append(accessibilityManager.isEnabled()).append("\n");
            sb.append("isTouchExplorationEnabled:").append(accessibilityManager.isTouchExplorationEnabled()).append(
                "\n");
            List<AccessibilityServiceInfo> installedAccessibilityServiceList = accessibilityManager
                .getInstalledAccessibilityServiceList();
            sb.append("install accessibility list：\n");
            int count = 0;
            for (AccessibilityServiceInfo serviceInfo : installedAccessibilityServiceList) {
                count++;
                sb.append(count).append(". ").append(serviceInfo.getSettingsActivityName()).append("\n");
            }
            sb.append("\n");
            sb.append("enable accessibility list：\n");
            List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager
                .getEnabledAccessibilityServiceList(FEEDBACK_BRAILLE);
            //enabledAccessibilityServiceList.addAll(accessibilityManager
            //    .getEnabledAccessibilityServiceList(FEEDBACK_SPOKEN));
            //enabledAccessibilityServiceList.addAll(accessibilityManager
            //    .getEnabledAccessibilityServiceList(FEEDBACK_HAPTIC));
            count = 0;
            for (AccessibilityServiceInfo serviceInfo : enabledAccessibilityServiceList) {
                count++;
                sb.append(count).append(". ").append(serviceInfo.getSettingsActivityName()).append("\n");
            }

            tvContent.setText(sb.toString());
        }

    }
}
