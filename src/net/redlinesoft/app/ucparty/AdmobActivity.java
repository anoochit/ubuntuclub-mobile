package net.redlinesoft.app.ucparty;

import net.redlinesoft.app.ucparty.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.*;

public class AdmobActivity extends Activity {
	private AdView adView;
	private WebView myWebView;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Create the adView
		adView = new AdView(this, AdSize.BANNER, "a14fdd13075ac95");
		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
		// Add the adView to it
		layout.addView(adView);
		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());
		
		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new WebViewClient());
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		if (this.chkStatus()) {
			myWebView.loadUrl("http://ubuntuclub.com");
		} else {
			Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG);
			myWebView.loadUrl("file:///android_asset/www/notconnect.html");
		}

	}
 
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //if (Uri.parse(url).getHost().equals("www.example.com")) {
        // This is my web site, so do not override; let my WebView load the page
        //    return false;
        //}
        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
        return true;
    }
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    // Check if the key event was the Back button and if there's history
	    
		if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
	        myWebView.goBack();
	        return true;
	    }
	    // If it wasn't the Back key or there's no web page history, bubble up to the default
	    // system behavior (probably exit the activity)
	    return super.onKeyDown(keyCode, event);
	}
	
	public boolean chkStatus() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
		.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
	
	
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			// Handle the error
			myWebView.loadUrl("file:///android_asset/www/notconnect.html");
			Toast.makeText(getApplicationContext(), description,Toast.LENGTH_LONG).show();
		}
	
	
}

