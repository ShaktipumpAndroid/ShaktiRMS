package com.shaktipumps.shakti_rms.GlobalClass;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Pattern;

public class UtilMethod {

    ///////////////This is for Device Height and Width
    public static int getDeviceHeightWidth(Context context, boolean isWidth) {

        if (isWidth)
            return context.getResources().getDisplayMetrics().widthPixels;
        else
            return context.getResources().getDisplayMetrics().heightPixels;

    }

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    public static ProgressDialog showLoading(ProgressDialog progress, Context context) {
        try {

            progress.setMessage("Please Wait...");
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progress;
    }

    public static void ShowAlertDialog(Context context, String msg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

	/*public static void showServerError(Context ctx){
		try{
			if(ctx!=null){
				String message = ctx.getResources().getString(R.string.server_error_message);
			    Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void showNetworkError(Context ctx){
		try{
			if(ctx!=null){
				String message = ctx.getResources().getString(R.string.network_error_message);
			    Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
				final AlertDialog adialog=new AlertDialog.Builder(ctx).create();
				adialog.setTitle("Message");
				adialog.setMessage("Internet Error!");
				adialog.setButton("OK",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						adialog.dismiss();
					}
				});
				adialog.show();

			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}*/


    public static void hideLoading(ProgressDialog progress) {
        try {
            if (progress != null) {
                if (progress.isShowing()) {
                    progress.dismiss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showDownloading(ProgressBar progress) {
        if (progress != null) {

            progress.setVisibility(View.VISIBLE);

        }
    }

    public static void hideDownloading(ProgressBar progress) {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    public static final boolean isInternetOn(Context ctx) {

        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.w("INTERNET:", String.valueOf(i));
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.w("INTERNET:", "connected!");
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public static boolean isStringNullOrBlank(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("null") || str.equals("")) {
            return true;
        }
        return false;
    }

    public static void showToast(String message, Context ctx) {
        try {
            Toast toast = null;
            if (ctx != null)
                toast = Toast.makeText(ctx, Html.fromHtml(message), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 0);
            toast.show();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    //method to close keyboard
    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
    }




}
