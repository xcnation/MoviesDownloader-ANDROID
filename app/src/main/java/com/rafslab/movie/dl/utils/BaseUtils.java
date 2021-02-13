package com.rafslab.movie.dl.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.common.collect.Iterables;
import com.rafslab.movie.dl.model.child.Cast;
import com.rafslab.movie.dl.model.child.ChildData;
import com.rafslab.movie.dl.ui.activity.HomeActivity;

import net.idik.lib.cipher.so.CipherClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by: Rais AlFani Lubis
 * Date: October 18, 2020
 */

public class BaseUtils {
    private BaseUtils() {
    }
    public static String getURLMaintenance(String body){
        return CipherClient.MAINTENANCE() + body + CipherClient.END();
    }
    public static int IntegerToPixel(Context context, int size){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, context.getResources().getDisplayMetrics());
    }
    public static void errorReporter(Context mContext){
        Thread.setDefaultUncaughtExceptionHandler(new CrashReporter(mContext));
    }
    public static void showMessage(Context context, String body, int duration){
        Toast.makeText(context, body, duration).show();
    }
    public static List<ChildData> filterAll(List<ChildData> models, String query){
        query = query.toLowerCase();
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData data : models){
            List<Cast> casts = data.getCastList();
            final String title = data.getTitle().toLowerCase();
            final String category  = data.getCategories().toLowerCase();
            final String status = data.getStatus();
            final String tag = data.getTags();
            for (Cast cast : casts){
                final String casting = cast.getReal_name().toLowerCase();
                if (casting.contains(query)) {
                    filteredList.add(data);
                }
            }
            if (title.contains(query)) {
                filteredList.add(data);
            }
            if (category.contains(query)) {
                filteredList.add(data);
            }
            if (query.contains("Complete")) {
                query = "1";
            } else if (query.contains("On going")) {
                query = "0";
            }
            if (status.contains(query)) {
                filteredList.add(data);
            }
            if (tag.contains(query)) {
                filteredList.add(data);
            }
        }
        return filteredList;
    }
//    public static List<ChildData> filterAllThings(List<ChildData> model, String categories, String tag, String status, double min, double max){
//        categories = categories.toLowerCase();
//        tag = tag.toLowerCase();
//        status = status.toLowerCase();
//        final List<ChildData> filteredList = new ArrayList<>();
//        for (ChildData data : model){
//            double rating = data.getRating();
//            String queryStatus = data.getStatus();
//            String queryTag = data.getTags();
//            String[] tagArray = tag.split(",");
//            String queryCategories = data.getCategories();
//            String[] categoriesArray = categories.split(",");
//            if (categories != null) {
//                for (String category : categoriesArray){
//                    if (queryCategories.contains(category)) {
//                        filteredList.add(data);
//                    }
//                }
//            }
//            if (tag != null) {
//                for (String tags : tagArray){
//                    if (queryTag.contains(tags)) {
//                        filteredList.add(data);
//                    }
//                }
//            }
//            if (status.contains(queryStatus)) {
//                filteredList.add(data);
//            }
//            if (min >= rating && max <= rating) {
//                filteredList.add(data);
//            }
//        }
//        return filteredList;
//    }
    public static double division(float value){
        return (double) value / 10;
    }
    @SuppressLint("DefaultLocale")
    public static String formatSeekBar(float value){
        return String.format("%.1f", value);
    }

    @SuppressLint("DefaultLocale")
    public static int formatSeekBar2(float value){
        return (int) value;
    }

    public static List<ChildData> filterCast(List<ChildData> models, String currentNameCast){
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            List<Cast> casts = eps.getCastList();
            for (Cast cast : casts){
                final String casting  = cast.getReal_name().toLowerCase();
                if (currentNameCast.contains(casting)) {
                    filteredList.add(eps);
                }
            }
        }
        return filteredList;
    }
    public static List<ChildData> filterOnlyCategories(List<ChildData> models, String filterText){
        filterText = filterText.toLowerCase();
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            String category  = eps.getCategories().toLowerCase();
            String[] array = filterText.split(",");
            for (String categories : array){
                if (category.contains(categories)) {
                    filteredList.add(eps);
                }
            }
        }
        return filteredList;
    }
    public static List<ChildData> filterOnlyRating(List<ChildData> models, double min, double max){
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            double rating = eps.getRating();
            if (rating >= min && rating <= max) {
                filteredList.add(eps);
            }
        }
        return filteredList;
    }
    public static List<ChildData> filterOnlyTags(List<ChildData> models, String tag){
        tag = tag.toLowerCase();
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            String getTag = eps.getTags().toLowerCase();
            String[] tagArray = tag.split(",");
            for (String tags : tagArray){
                if (getTag.contains(tags)) {
                    filteredList.add(eps);
                }
            }
        }
        return filteredList;
    }
    public static List<ChildData> filterTagsANDCategories(List<ChildData> models, String tag, String filterText){
        filterText = filterText.toLowerCase();
        tag = tag.toLowerCase();
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            String getTag = eps.getTags().toLowerCase();
            String[] tagArray = tag.split(",");
            String category  = eps.getCategories().toLowerCase();
            String[] catArray = filterText.split(",");
            List<String> catList = new ArrayList<>(Arrays.asList(catArray));
            List<String> tagList = new ArrayList<>(Arrays.asList(tagArray));
            for(String both : Iterables.concat(tagList, catList)){
                if (getTag.contains(both)) {
                    filteredList.add(eps);
                }
                if (category.contains(both)) {
                    filteredList.add(eps);
                }
                if (category.contains(both) && getTag.contains(both)) {
                    filteredList.add(eps);
                }
            }
        }
        return filteredList;
    }
    public static List<ChildData> filterOnlyStatus(List<ChildData> models, String status){
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            final String getStatus = eps.getStatus().toLowerCase();
            if (getStatus.contains(status)) {
                filteredList.add(eps);
            }
        }
        return filteredList;
    }

    public static List<ChildData> filterAll(List<ChildData> models, String filterText, double min, double max, String status){
        filterText = filterText.toLowerCase();
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            double rating = eps.getRating();
            String getStatus = eps.getStatus();
            if (rating >= min && rating <= max) {
                String category  = eps.getCategories().toLowerCase();
                String[] array = filterText.split(",");
                if (getStatus.equals(status)) {
                    for (String categories : array){
                        if (category.contains(categories)) {
                            filteredList.add(eps);
                        }
                    }
                }
            }
        }
        return filteredList;
    }
    public static List<ChildData> filterRatingANDCategories(List<ChildData> models, String filterText, double min, double max){
        filterText = filterText.toLowerCase();
        final List<ChildData> filteredList = new ArrayList<>();
        for (ChildData eps : models) {
            double rating = eps.getRating();
            if (rating >= min && rating <= max) {
                String category  = eps.getCategories().toLowerCase();
                String[] array = filterText.split(",");
                for (String categories : array){
                    if (category.contains(categories)) {
                        filteredList.add(eps);
                    }
                }
            }
        }
        return filteredList;
    }
    public static ActionBar getActionBar(Context context) {
        return ((HomeActivity) context).getSupportActionBar();
    }
    public static Window getStatusBar(Context context) {
        return ((HomeActivity) context).getWindow();
    }
    public static FragmentTransaction refreshHomeFragment(Context context, Fragment fragment){
        return ((HomeActivity) context).getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment);
    }
    public static Date getYesterday(){
        return new Date(System.currentTimeMillis()-24*60*60*1000);
    }
    public static Date getLastWeek(){
        return new Date(System.currentTimeMillis()-7*24*60*60*1000);
    }
    public static void setThemeAtRuntime(Context context){

    }
    public static void setUniversalLog(String message){
        Log.d("BaseUtils", message);
    }

}
