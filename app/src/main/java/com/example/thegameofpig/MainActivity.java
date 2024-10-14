package com.example.thegameofpig;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imageViewDado;
    private Button buttonLanzarDado;
    private Button buttonMantener;
    private Button buttonNuevaPartida;
    private TextView textViewActual1, textViewActual2, textViewNumMantener1, textViewNumMantener2;
    private View viewPlayer1, viewPlayer2;
    private Random random;
    private int puntajeJugador1 = 0;
    private int puntajeJugador2 = 0;
    private int puntosRondaJugador1 = 0;
    private int puntosRondaJugador2 = 0;
    private boolean esTurnoJugador1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageViewDado = findViewById(R.id.imageViewDado);
        buttonLanzarDado = findViewById(R.id.buttonLanzarDado);
        buttonMantener = findViewById(R.id.buttonMantener);
        buttonNuevaPartida = findViewById(R.id.buttonNuevaPartida);
        textViewActual1 = findViewById(R.id.textViewActual1);
        textViewActual2 = findViewById(R.id.textViewActual2);
        textViewNumMantener1 = findViewById(R.id.textViewNumMantener1);
        textViewNumMantener2 = findViewById(R.id.textViewNumMantener2);

        viewPlayer1 = findViewById(R.id.viewPlayer1);
        viewPlayer2 = findViewById(R.id.viewPlayer2);

        random = new Random();

        buttonLanzarDado.setOnClickListener(v -> lanzarDado());
        buttonMantener.setOnClickListener(v -> mantenerPuntos());
        buttonNuevaPartida.setOnClickListener(v -> nuevaPartida());

        nuevaPartida();
    }

    private void mantenerPuntos() {
        if (esTurnoJugador1) {
            puntajeJugador1 += puntosRondaJugador1;
            puntosRondaJugador1 = 0;
            textViewNumMantener1.setText(String.valueOf(puntajeJugador1));
            textViewActual1.setText("0");
        } else {
            puntajeJugador2 += puntosRondaJugador2;
            puntosRondaJugador2 = 0;
            textViewNumMantener2.setText(String.valueOf(puntajeJugador2));
            textViewActual2.setText("0");
        }

        cambiarTurno();
    }

    private void lanzarDado() {
        int valorDado = random.nextInt(6) + 1;

        switch (valorDado) {
            case 1:
                imageViewDado.setImageResource(R.drawable.dice_one);
                break;
            case 2:
                imageViewDado.setImageResource(R.drawable.dice_two);
                break;
            case 3:
                imageViewDado.setImageResource(R.drawable.dice_three);
                break;
            case 4:
                imageViewDado.setImageResource(R.drawable.dice_four);
                break;
            case 5:
                imageViewDado.setImageResource(R.drawable.dice_five);
                break;
            case 6:
                imageViewDado.setImageResource(R.drawable.dice_six);
                break;
        }

        if (valorDado == 1) {
            manejarPuntosPerdidos();
        } else {
            sumarPuntos(valorDado);
        }
    }

    private void manejarPuntosPerdidos() {
        if (esTurnoJugador1) {
            puntosRondaJugador1 = 0;
            textViewActual1.setText("0");
        } else {
            puntosRondaJugador2 = 0;
            textViewActual2.setText("0");
        }

        cambiarTurno();
    }

    private void sumarPuntos(int valorDado) {
        if (esTurnoJugador1) {
            puntosRondaJugador1 += valorDado;
            textViewActual1.setText(String.valueOf(puntosRondaJugador1));
        } else {
            puntosRondaJugador2 += valorDado;
            textViewActual2.setText(String.valueOf(puntosRondaJugador2));
        }
    }

    private void cambiarTurno() {
        esTurnoJugador1 = !esTurnoJugador1;

        if (esTurnoJugador1) {
            viewPlayer1.setBackgroundColor(0xFFFFFFFF);
            viewPlayer2.setBackgroundColor(0xABABABFF);
            Toast.makeText(this, "Jugador 1", Toast.LENGTH_SHORT).show();
        } else {
            viewPlayer1.setBackgroundColor(0xABABABFF);
            viewPlayer2.setBackgroundColor(0xFFFFFFFF);
            Toast.makeText(this, "Ahora Jugador 2", Toast.LENGTH_SHORT).show();
        }

        verificarGanador();
    }

    private void verificarGanador() {
        if (puntajeJugador1 >= 100) {
            Toast.makeText(this, "¡Jugador 1 gana!", Toast.LENGTH_SHORT).show();
            nuevaPartida();
        } else if (puntajeJugador2 >= 100) {
            Toast.makeText(this, "¡Jugador 2 gana!", Toast.LENGTH_SHORT).show();
            nuevaPartida();
        }
    }

    private void nuevaPartida() {
        puntajeJugador1 = 0;
        puntajeJugador2 = 0;
        puntosRondaJugador1 = 0;
        puntosRondaJugador2 = 0;
        esTurnoJugador1 = true;

        textViewActual1.setText("0");
        textViewActual2.setText("0");
        textViewNumMantener1.setText("0");
        textViewNumMantener2.setText("0");
        imageViewDado.setImageResource(R.drawable.dice_random);

        viewPlayer1.setBackgroundColor(0xFFFFFFFF);
        viewPlayer2.setBackgroundColor(0xABABABFF);

        Toast.makeText(this, "Nueva partida iniciada", Toast.LENGTH_SHORT).show();
    }
}
