package jlexdev.com.asynctaskexample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/** --- AsyncTask ---
 * Fuentes:
 * https://sekthdroid.wordpress.com/2012/12/01/guardar-imagen-en-memoria-interna-android/
 * http://jonsegador.com/2010/03/mostrarcargar-imagen-externa-en-una-aplicacion-android/
 */
public class MainActivity extends AppCompatActivity {

    // Sólo necesito implements OnClick (arriba) ** Falta corregir Dónde guardar mi imagen

    private TextView tvTitulo;
    private ImageView imgJobs;
//    private Button btnGuardar;

    // Contante
    public static final String URL =
            "http://www.kaizengroup.es/wp-content/uploads/2016/01/stevejobsbig.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitulo = (TextView)findViewById(R.id.tv_jobs);
        imgJobs = (ImageView)findViewById(R.id.img_jobs);
//        btnGuardar = (Button)findViewById(R.id.btn_guardar);

        // Carga de Imagen
        CargaImagen nuevaTarea = new CargaImagen();
        nuevaTarea.execute(URL);


//        btnGuardar.setOnClickListener(this);
    }


    /* ********** Método para Guardar al Clickar ;) ********** */
/**
    @Override
    public void onClick(View v) {

        Bitmap imagen = ((BitmapDrawable)imgJobs.getDrawable()).getBitmap();

        String ruta = guardarImagen(getApplicationContext(), imagen, imagen);

        Toast.makeText(getApplicationContext(), ruta, Toast.LENGTH_SHORT).show();
    }
*/

    /* ********** Método Guardar de "OnClick" ********** */
/**
    private String guardarImagen(Context applicationContext, Bitmap imagen, Bitmap imagen1) {

        ContextWrapper cw = new ContextWrapper(applicationContext);
        File dirImages = cw.getDir("ImagenesAsync", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, "_nombre" + ".png");

        FileOutputStream fos = null;

        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myPath.getAbsolutePath();
    }

*/



    /* ********** Clase Cargar Imagen ********** */

    public class CargaImagen extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando :)");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }


        @Override
        protected Bitmap doInBackground(String... params) {

            String url = params[0];
            Bitmap imagen = descargarImagen(url);
            return imagen;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            imgJobs.setImageBitmap(bitmap);  // Establece la imagen en el ImageView
            pDialog.dismiss();               // Termino el Progress Dialog
        }



        // Método para Descargar Imagen

        private Bitmap descargarImagen(String imageHttpAdress) {

            URL imageUrl = null;
            Bitmap imagen = null;

            try{
                imageUrl = new URL(imageHttpAdress);
                HttpURLConnection nexo = (HttpURLConnection)imageUrl.openConnection();
                nexo.connect();
                imagen = BitmapFactory.decodeStream(nexo.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return imagen;
        }
    }



}
