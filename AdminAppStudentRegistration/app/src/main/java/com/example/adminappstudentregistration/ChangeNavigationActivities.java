package com.example.adminappstudentregistration;

import android.content.Context;
import android.content.Intent;
import android.os.Build;


public class ChangeNavigationActivities {

    public void startHomeActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        if ((Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void startAvailableCoursesActivity(Context context) {
        Intent intent = new Intent(context, AddCourseActivity.class);
        if ((Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void startFilerActivity(Context context) {
        Intent intent = new Intent(context, FilterStudents.class);
        if ((Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }





}
