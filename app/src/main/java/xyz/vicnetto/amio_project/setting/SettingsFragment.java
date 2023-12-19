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

    /**
     * Each time we change the preferences, we will store them in the sharedpreferences
     * @param sharedPreferences The {@link SharedPreferences} that received the change.
     * @param key The key of the preference that was changed, added, or removed. Apps targeting
     *            {@link android.os.Build.VERSION_CODES#R} on devices running OS versions
     *            {@link android.os.Build.VERSION_CODES#R Android R} or later, will receive
     *            a {@code null} value when preferences are cleared.
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        storePreference();
    }


    /**
     * To store the preferences in our sharedpreferences
     */
    public void storePreference(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("addressMail", getMailAddressDst());
        editor.putString("message", getMailMessage());
        editor.putString("start_time_s", getStartTimeWorkdays());
        editor.putString("end_time_s", getEndTimeWorkdays());
        editor.putString("start_time_w", getStartTimeWeekend());
        editor.putString("end_time_w", getEndTimeWeekend());
        editor.putBoolean("startup", getStartupOnBoot());

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

    public Boolean getStartupOnBoot(){
        return this.getBoolean("startup",false);
    }

    private  String getSetting(String key, String dfValue){
        String value = PreferenceManager.getDefaultSharedPreferences(requireContext()).getString(key, dfValue);
        return value;
    }

    private Boolean getBoolean(String key, Boolean dfValue) {
        return PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(key, dfValue);

    }



}