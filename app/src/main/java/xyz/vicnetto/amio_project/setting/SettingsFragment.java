package xyz.vicnetto.amio_project.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;


import xyz.vicnetto.amio_project.R;

public class SettingsFragment extends PreferenceFragmentCompat {



    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

    }

    public void storePreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("addressMail", getMailAddressDst());
        editor.putString("message", getMailMessage());
        editor.commit();

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

    public int getStartTimeWorkdays(){
        String startTimeS = this.getSetting("start_time_s","19");
        return Integer.parseInt(startTimeS);
    }

    public int getEndTimeWorkdays(){
        String endTimeS = this.getSetting("end_time_s","23");
        return Integer.parseInt(endTimeS);
    }

    public int getStartTimeWeekend(){
        String startTimeW = this.getSetting("start_time_w","19");
        return Integer.parseInt(startTimeW);

    }

    public int getEndTimeWeekend(){
        String endTimeW = this.getSetting("end_time_w","19");
        return Integer.parseInt(endTimeW);
    }

    private  String getSetting(String key, String dfValue){
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, dfValue);
        return value;

    }






}