package dam.romsanbryan.hilos;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    private EditText entrada;
    private TextView salida, contador;
    public static boolean stop = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        entrada = (EditText) findViewById(R.id.entrada);
        salida = (TextView) findViewById(R.id.salida);
        contador = (TextView) findViewById(R.id.contador);
    }

    public void parar(View v){
        stop = true;
        int n = 0;
        MiTarea tarea = new MiTarea();
        tarea.execute(n);
    }

    public void iniciar(View v){
        stop = false;
        int n = 0;
        MiTarea tarea = new MiTarea();
        tarea.execute(n);
    }

    public void calcularOperacion(View view) {
        int n = Integer.parseInt(entrada.getText().toString());
        salida.append(n + "! = ");
        // Utilizando Threads
        //MiThread thread = new MiThread(n);
        //thread.start();
        // Fin de Threads
        // Utuilizando AsyncTask
        MiTarea tarea = new MiTarea();
        tarea.execute(n);
        // Fin de AsyncTask
    }


    public int factorial(int n) {
        int res=1;
        for (int i=1; i<=n; i++){
            res*=i;
            SystemClock.sleep(1000);
        }
        return res;
    }


    class MiThread extends Thread {
        private int n, res;

        public MiThread(int n) {
            this.n = n;
        }

        @Override public void run() {
            res = factorial(n);
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    salida.append(res + "\n");
                }
            });
        }
    }

    class MiTarea extends AsyncTask {
        private int num = 0;

        @Override
        protected Object doInBackground(Object[] n) {
        //    return factorial(Integer.valueOf(n[0].toString())); // Ejemplo de factorial

        // Contador
            while (!MainActivity.stop) {
                num++;
                publishProgress(new Integer[]{num});
            }
            return num;
        // Fin contador
        }

        @Override
        protected void onPostExecute(Object res) {
            //salida.append(res.toString() + "\n"); // Ejemplo de factorial
            contador.setText("Tiempo parado"); // Contador
        }

        /**
         * Metodo utilizado para el contador
         * @param values
         */
        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
            contador.setText(values[0].toString());
        }
    }
}



