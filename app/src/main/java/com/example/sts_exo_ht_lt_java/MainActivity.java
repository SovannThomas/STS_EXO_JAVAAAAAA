package com.example.sts_exo_ht_lt_java;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Ici on définit les valeurs qu'on voudra

    private EditText textNumber;
    private ListView listView;
    private TextView calculatedNumber;

    private TextView calculatedTime;

    private long startTime;
    private long endTime;

    private List<Integer> ListNombrePremier;
    private ArrayAdapter<Integer> adapter;

    //Ici on initialise l'interface utilisateur

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrouve les éléments du layout
        textNumber = findViewById(R.id.text_number);
        listView = findViewById(R.id.listMatView);
        calculatedNumber = findViewById(R.id.calculatedNumber);
        calculatedTime = findViewById(R.id.calculatedTime);


        ListNombrePremier = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListNombrePremier);
        listView.setAdapter(adapter);


        //Voit si on met quelque chose dans le champ
        //On fait ca parce que c'est chiant de faire un bouton et que j'ai trouvé un tuto simple

        textNumber.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void afterTextChanged(Editable editable) {
                calculatePrimeNumbers();
            }
        });
    }

    // DoInBackground et onPostExecute marche et sont utilisés dans le cours
    //(On s'en fout si cees methodes ne sont plus à jour)
    private class PrimeNumbersTask extends AsyncTask<Integer, Void, List<Integer>> {

        //S'occupe du calcul en arriere plan
        protected List<Integer> doInBackground(Integer... params) {
            startTime = System.currentTimeMillis();
            return calculatePrimes(params[0]);
        }

        //s'occupe de l'affichage en temps réel des nombres premiers
        protected void onPostExecute(List<Integer> NombrePremierCalcule) {
            endTime = System.currentTimeMillis();

            // Calcul du temps écoulé en millisecondes
            long totalTime = endTime - startTime;

            // Affichage du temps de calcul en millisecondes
            calculatedTime.setText("Temps de calcul : " + totalTime + "ms");

            ListNombrePremier.clear();
            ListNombrePremier.addAll(NombrePremierCalcule);
            adapter.notifyDataSetChanged();
            calculatedNumber.setText("nombre de premier total : " + NombrePremierCalcule.size());
        }
    }

    //Fonction qui calcul les nombres premiers
    private void calculatePrimeNumbers() {
        String Nombre = textNumber.getText().toString();

        if (!Nombre.isEmpty()) {
            int maxNumber = Integer.parseInt(Nombre);
            new PrimeNumbersTask().execute(maxNumber);
        } else {
            //Clear sinon en mettant rien ca ne remet pas à 0
            ListNombrePremier.clear();
            adapter.notifyDataSetChanged();
            calculatedNumber.setText("Nombre de nombres premiers calculés : 0");
        }
    }

    //Methode qui verifie si un nombre est premier.
    //explication: on prend n qui est le nombre max voulu et on essaye toutes les divisions
    //Possible de 2 jusqu'à n. Si l'une des divisions ne retourne aucun reste(donc est vrai)
    //Alors on renvoie false. à la fin de toutes les divisions, ce nombre qui n'a pas pu se
    //Faire diviser est alors prime. Retourne true. On a juste besoin de calculer jusq'a la
    //moitié
    private boolean isPrime(int n) {
        for (int i = 2; i < n/2; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    //Réalise l'appel de la methode créée juste avant et s'occupe d'ajouter de le ListView
    //Les nombres premiers. On commence à 2 car on sait que les nombres premiers
    //Commence après 2
    private List<Integer> calculatePrimes(int maxNumber) {
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= maxNumber; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }


}
