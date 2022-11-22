package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private WebView self;
    public static String tmp;
    public static boolean isRunning = false;
    private Button bottone;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottone = findViewById(R.id.button);
        PyscriptLoader temp = new PyscriptLoader(this);
        temp.loadPython();
        bottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRunning){

                    // to return a value from python you should use the variable here (y) assignment as return value,
                    // use this variable just for this
                    // like
                    //     return "val rom python"
                    // should be replaced into
                    //     var = "val from python"
                    // like down here
                    //     y = "this comes from python :" + str(5+1)
                    // in this way the result can be extracted and placed in this tmp variable
                    // this code needs to be fully revised,its uncompleted,like tmp should be rewritten as array/map
                    // instead of a single value that i used for testing).

                    temp.executePython("y = \"this comes from python :\" + str(5+1)","y");
                    bottone.setEnabled(false);
                    while(tmp == null){}
                    bottone.setText(tmp);
                    bottone.setEnabled(true);

                }
            }
        });

    }
    public class PyscriptLoader {

        @SuppressLint("JavascriptInterface")
        public PyscriptLoader(Context c){
            self = (WebView) new WebView(c);
            WebSettings webSettings = self.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);
            self.loadUrl("file:///android_asset/pyscript.html");
            self.addJavascriptInterface(this, "android");
        }

        public void executePython(String pycode,String val){
            final String loader = "eval(pyscript.runtime.interpreter.runPython(`global " + val + "\n" + pycode + "`));\n" +
                    "javascript:android.GetPython(pyscript.runtime.globals.get('"+val+"'));\n";
            Log.e("errstr",loader);
            self.evaluateJavascript(loader, s -> {});
            }

        public void loadPython(){
            final String loader = "document.addEventListener(\'pyscript-running\', () => {pyscript.runtime.interpreter.runPython(`global x\nx=\"python is running\"`);\njavascript:android.LoadPyhton(pyscript.runtime.globals.get('x'))})";

            self.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    self.evaluateJavascript(loader, s -> {
                    });
                }
            });
        }
        @JavascriptInterface
        public void GetPython(String value) {
            tmp = value;
            Log.e("Python return",tmp);
        }
        @JavascriptInterface
        public void LoadPyhton(String value) {

            isRunning = true;
            Log.e("Python loaded","python is loaded");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bottone.setText("python is ready");
                }
            });
        }
    }
}