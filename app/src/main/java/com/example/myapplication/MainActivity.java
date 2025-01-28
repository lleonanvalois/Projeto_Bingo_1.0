package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonGenerate;
    private Button buttonReset;
    private TextView textViewNumbers;
    private GridLayout gridLayoutNumbers;
    private List<TextView> numberViews;
    private List<Integer> allNumbers;
    private int currentIndex = 0;
    private boolean gameEnded = false;  // Flag para verificar se o jogo acabou

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializando os componentes da interface
        buttonGenerate = findViewById(R.id.button_generate);
        buttonReset = findViewById(R.id.button_reset);
        textViewNumbers = findViewById(R.id.textView_numbers);
        gridLayoutNumbers = findViewById(R.id.gridLayout_numbers);

        // Lista com todos os números de 1 a 75
        allNumbers = new ArrayList<>();
        for (int i = 1; i <= 75; i++) {
            allNumbers.add(i);
        }

        // Embaralha os números uma vez ao iniciar o jogo
        Collections.shuffle(allNumbers);

        // Criando uma lista de TextViews para todos os números possíveis
        numberViews = new ArrayList<>();

        // Preenche o GridLayout com os números de 1 a 75
        for (int i = 1; i <= 75; i++) {
            TextView numberView = new TextView(this);
            String formattedNumber = String.format("%02d", i);  // Formata números com 2 dígitos
            numberView.setText(formattedNumber);
            numberView.setTextSize(10);
            numberView.setTextColor(Color.BLACK);
            numberView.setPadding(16, 16, 16, 16);
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(Color.WHITE);
            numberView.setBackground(drawable);
            numberView.setTag(i);  // Armazena o número como tag

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(60, 8, 60, 8);
            numberView.setLayoutParams(params);

            // Adiciona o número à lista de views
            numberViews.add(numberView);
            gridLayoutNumbers.addView(numberView);
        }

        // Definindo o evento de clique do botão
        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameEnded) {
                    generateRandomNumber();
                }
            }
        });

        // Definindo o evento de clique do botão "Resetar"
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    // Método para gerar números aleatórios
    private void generateRandomNumber() {
        // Se ainda há números restantes para serem chamados
        if (currentIndex < allNumbers.size()) {
            int randomNumber = allNumbers.get(currentIndex);

            // Exibir o número chamado no TextView
            String formattedNumber = String.format("%02d", randomNumber);
            textViewNumbers.setText("Número atual: " + formattedNumber);

            // Marca o número no GridLayout
            TextView numberView = numberViews.get(randomNumber - 1); // -1 para corresponder ao índice
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(Color.GREEN);
            numberView.setBackground(drawable);

            currentIndex++;
        }

        // Se todos os números já foram chamados, mostrar que o sorteio acabou
        if (currentIndex == allNumbers.size()) {
            textViewNumbers.append("\nSorteio encerrado! Todos os 75 números foram chamados.");
            gameEnded = true;  // Marcar que o jogo acabou
            buttonGenerate.setEnabled(false);  // Desabilitar o botão de gerar número
        }

        // Ajuste de layout para acomodar a interface corretamente em dispositivos com barras de sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Método para resetar o jogo
    private void resetGame() {
        // Limpar o TextView
        textViewNumbers.setText("Números chamados aparecerão aqui");

        // Restaurar a aparência dos números no GridLayout
        for (TextView numberView : numberViews) {
            ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
            drawable.getPaint().setColor(Color.WHITE);
            numberView.setBackground(drawable);
        }

        // Restaurar os números e reiniciar a indexação
        currentIndex = 0;
        gameEnded = false;  // Resetar o status do jogo

        // Habilitar novamente o botão de gerar números
        buttonGenerate.setEnabled(true);

        // Embaralhar os números novamente
        Collections.shuffle(allNumbers);
    }
}
