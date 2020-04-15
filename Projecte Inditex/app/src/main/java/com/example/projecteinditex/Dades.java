package com.example.projecteinditex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dades extends AppCompatActivity {

    Button btnconsultar,btnreset;
    EditText etId, etDesc;
    EditText etTallaS_Botiga,etTallaM_Botiga,etTallaL_Botiga,etTallaXL_Botiga,etTallaXXL_Botiga;
    EditText etTallaS_Magatzem,etTallaM_Magatzem,etTallaL_Magatzem,etTallaXL_Magatzem,etTallaXXL_Magatzem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades);


        btnconsultar = (Button)findViewById(R.id.btnConsultar);
        btnreset = (Button)findViewById(R.id.btnReset);
        etId = (EditText)findViewById(R.id.etId);
        etDesc = (EditText)findViewById(R.id.etDesc);
        etTallaS_Botiga = (EditText)findViewById(R.id.etTallaS);
        etTallaM_Botiga = (EditText)findViewById(R.id.etTallaM);
        etTallaL_Botiga = (EditText)findViewById(R.id.etTallaL);
        etTallaXL_Botiga = (EditText)findViewById(R.id.etTallaXL);
        etTallaXXL_Botiga = (EditText)findViewById(R.id.etTallaXXL);

        etTallaS_Magatzem = (EditText)findViewById(R.id.etTallaS2);
        etTallaM_Magatzem = (EditText)findViewById(R.id.etTallaM2);
        etTallaL_Magatzem = (EditText)findViewById(R.id.etTallaL2);
        etTallaXL_Magatzem = (EditText)findViewById(R.id.etTallaXL2);
        etTallaXXL_Magatzem = (EditText)findViewById(R.id.etTallaXXL2);




        etDesc.setEnabled(false);
        etTallaS_Botiga.setEnabled(false);
        etTallaM_Botiga.setEnabled(false);
        etTallaL_Botiga.setEnabled(false);
        etTallaXL_Botiga.setEnabled(false);
        etTallaXXL_Botiga.setEnabled(false);

        etTallaS_Magatzem.setEnabled(false);
        etTallaM_Magatzem.setEnabled(false);
        etTallaL_Magatzem.setEnabled(false);
        etTallaXL_Magatzem.setEnabled(false);
        etTallaXXL_Magatzem.setEnabled(false);






        btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etId.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Indrodueix el codi de l'article", Toast.LENGTH_LONG).show();
                    etId.setText("");

                }else {

                    new ConsultarDatos().execute("https://unsectarian-stack.000webhostapp.com/Android/consulta.php?id="+etId.getText().toString());
                    //new ConsultarDatos().execute("http://192.168.0.14/CursoAndroid/consulta.php?id="+etId.getText().toString());
                }
            }
        });


        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                etId.setText("");
                etDesc.setText("");
                etTallaS_Botiga.setText("");
                etTallaM_Botiga.setText("");
                etTallaL_Botiga.setText("");
                etTallaXL_Botiga.setText("");
                etTallaXXL_Botiga.setText("");

                etTallaS_Magatzem.setText("");
                etTallaM_Magatzem.setText("");
                etTallaL_Magatzem.setText("");
                etTallaXL_Magatzem.setText("");
                etTallaXXL_Magatzem.setText("");



            }
        });

    }

    private class CargarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }


    private class ConsultarDatos extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray ja = null;
            try {
                ja = new JSONArray(result);
                etDesc.setText(ja.getString(1));

                etTallaS_Botiga.setText("S "+ja.getString(2));
                etTallaS_Magatzem.setText("S "+ja.getString(3));

                etTallaM_Botiga.setText("M "+ja.getString(4));
                etTallaM_Magatzem.setText("M "+ja.getString(5));

                etTallaL_Botiga.setText("L "+ja.getString(6));
                etTallaL_Magatzem.setText("L "+ja.getString(7));

                etTallaXL_Botiga.setText("XL "+ja.getString(8));
                etTallaXL_Magatzem.setText("XL "+ja.getString(9));

                etTallaXXL_Botiga.setText("XXL "+ja.getString(10));
                etTallaXXL_Magatzem.setText("XXL "+ja.getString(11));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }


    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}
