package xyz.vicnetto.amio_project.mail;

import android.net.Uri;
import android.content.Intent;
import android.util.Log;

public class MailTool {
    public MailTool(){

    }
    public Intent sendMailIntent(String addressDest, String addressCC){
        Log.d("Mail", "Send mail...");

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, addressDest);
        emailIntent.putExtra(Intent.EXTRA_CC, addressCC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        return emailIntent;
    }
}
