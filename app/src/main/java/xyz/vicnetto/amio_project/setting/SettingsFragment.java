package xyz.vicnetto.amio_project.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import xyz.vicnetto.amio_project.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        // Set up a listener for preference changes
        SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // React to changes in preferences
        // broadcast to mail service TODO
    }


    public void storePreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("addressMail", getMailAddressDst());
        Log.i("fragment_sw", getStartTimeWeekend());
        editor.putString("message", getMailMessage());
        editor.putString("start_time_s", getStartTimeWorkdays());
        editor.putString("end_time_s", getEndTimeWorkdays());
        editor.putString("start_time_w", getStartTimeWeekend());
        editor.putString("end_time_w", getEndTimeWeekend());

        editor.apply();

    }

    private String getMailAddressDst(){
        this.getSetting("addressMail","");
        String addressMail = this.getSetting("addressMail","wenjia.tang@telecomnancy.net");
        return addressMail;
    }

    public String getMailMessage(){
        String mailMessage = this.getSetting("message","Attention, une nouvelle lumière allumée!");
        return mailMessage;
    }

    public String getStartTimeWorkdays(){
        String startTimeS = this.getSetting("start_time_s","23");
        return startTimeS;
    }

    public String getEndTimeWorkdays(){
        String endTimeS = this.getSetting("end_time_s","6");
        return endTimeS;
    }

    public String getStartTimeWeekend(){
        String startTimeW = this.getSetting("start_time_w","19");
        return startTimeW;

    }

    public String getEndTimeWeekend(){
        String endTimeW = this.getSetting("end_time_w","23");
        return endTimeW;
    }

    private  String getSetting(String key, String dfValue){
        String value = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString(key, dfValue);
        return value;

    }



}