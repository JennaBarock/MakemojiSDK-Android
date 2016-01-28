package com.example.mojilib;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.UiThread;
import android.support.v4.content.SharedPreferencesCompat;

import com.example.mojilib.model.MojiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Baar on 1/24/2016.
 */
public abstract class PagerPopulator<T> {
    protected List<T> mojiModels = new ArrayList<>();

    public interface PopulatorObserver{
       @UiThread void onNewDataAvailable();
    }
    protected abstract void setup(PopulatorObserver observer);//once done, call the next two
    List<T> populatePage(int count, int offset){
        if (mojiModels.size()<offset)return new ArrayList<>();//return empty
        if (offset+count>mojiModels.size())count = mojiModels.size()-offset;
        return mojiModels.subList(offset,offset+count);
    }
    int getTotalCount(){
        return mojiModels.size();
    };
}
