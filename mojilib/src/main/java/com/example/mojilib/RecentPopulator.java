package com.example.mojilib;

import android.content.SharedPreferences;
import android.support.annotation.UiThread;
import android.util.ArrayMap;

import com.example.mojilib.model.MojiModel;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Scott Baar on 1/27/2016.
 */
public class RecentPopulator extends PagerPopulator<MojiModel> {


    private static List<MojiModel> recents;
    static SharedPreferences sp;
    private static int RECENT_SIZE = 150;
    private static List<MojiModel> getRecents(){
        if (recents!=null)return recents;
        sp = Moji.context.getSharedPreferences("_mm_recent",0);
        try{
            recents =MojiModel.fromJSONArray(new JSONArray(sp.getString("recent","[]")));
        }
        catch (Exception e){
            recents = new ArrayList<>();
        }
        return recents;

    }
    @UiThread
    public static void addRecent(MojiModel model){
        List<MojiModel> list = getRecents();
        if (!list.contains(model))list.add(0,model);
        else{//move to front
            int i = list.indexOf(model);
            list.remove(i);
            list.add(0,model);
        }
        while (list.size()>RECENT_SIZE)
            list.remove(list.size()-1);
        sp.edit().putString("recent",MojiModel.toJsonArray(list).toString()).apply();


    }
    @Override
    protected void setup(PopulatorObserver observer) {
        observer.onNewDataAvailable();
    }

    @Override
    List<MojiModel> populatePage(int count, int offset){
        if (getRecents().size()<offset)return new ArrayList<>();//return empty
        if (offset+count>getRecents().size())count = getRecents().size()-offset;
        return getRecents().subList(offset,offset+count);
    }
    int getTotalCount(){
        return getRecents().size();
    };

}
