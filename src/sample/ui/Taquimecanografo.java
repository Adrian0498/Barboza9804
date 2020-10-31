package sample.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;

public class Taquimecanografo extends Stage{

    public static JFXTextArea txaEscritura;
    public static JFXTextArea txaTexto;
    public static VBox vPrincipal, vTeclado;
    public static HBox vBoton;
    public static HBox h1, h2, h3, h4, h5, h6;
    public static JFXButton btnIniciar;
    public static Button[] rowBtn1, rowBtn2, rowBtn3, rowBtn4, rowBtn5, rowBtn6;
    public static Label tempo;
    public static String arr="";
    public static int totalChar;
    public static char tecla_esperada;
    public static char tecla_escrita;
    public static boolean esIniciado = false;
    public static int pos_linea=0;
    public static Timeline timeline;
    public static int contPalabra=0;
    private static Button timer;
    private static boolean bandera = true;
    public static int mins = 0, secs = 0, millis = 0;
    public static String[] rowTeclas1 = {"Esc", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "ins"};
    public static String[] rowTeclas2 = {"|", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "?", "¿", "←"};
    public static String[] rowTeclas3 = {"⇄", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "´", "+", "↵"};
    public static String[] rowTeclas4 = {"bloq mayús", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Ñ", "{", "}"};
    public static String[] rowTeclas5 = {"↑", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "-", "↑", "fin", "↑"};
    public static String[] rowTeclas6 = {"ctrl", "fn", "windows", "alt", "  espacio  ", "AltGr", "≡", "Ctrl", "←", "↓", "→"};

    public Taquimecanografo(){
        this.setTitle("Tutor de Taquimecanografía");
        this.setScene(CrearUI(this));
        this.show();
    }

    private Scene CrearUI(Stage stage) {

        stage = stage;

        btnIniciar = new JFXButton("Cargar Archivo");

        timer = new Button("Iniciar");
        tempo = new Label();
        tempo.setText("00:00");
        timer.setVisible(false);

        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tempo();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        timer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(bandera) {
                    timeline.play();
                    bandera = false;
                    timer.setText("Prueba en proceso");
                }
            }
        });

        btnIniciar.setOnAction(event -> {
                iniciarTaqui();
        });

        txaEscritura = new JFXTextArea();
        txaEscritura.setPrefRowCount(5);
        txaTexto = new JFXTextArea();
        txaTexto.setEditable(false);
        txaTexto.setPrefRowCount(5);


        //Eventos de animacion Teclado
        txaEscritura.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                esPresionado(event);
            }
        });
        txaEscritura.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                esPresionado(event);
            }
        });

        initTeclado();

        vTeclado = new VBox();
        vBoton = new HBox();
        vBoton.setAlignment(Pos.CENTER);
        vBoton.setSpacing(10);
        vBoton.getChildren().addAll(btnIniciar, tempo, timer);
        vTeclado.getChildren().addAll(h1, h2, h3, h4, h5, h6);
        vPrincipal = new VBox();
        vPrincipal.setSpacing(5);
        vPrincipal.getChildren().addAll(vBoton, txaTexto, txaEscritura, vTeclado);
        return new Scene(vPrincipal, 900, 450);
    }

    public static void esPresionado(KeyEvent k) {

        Timeline presionado;

        if (k.getText().equals("¿")) {
            rowBtn2[12].setStyle("-fx-base: #3ecc27;");
            presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[12].setStyle("-fx-base: #f0f0f0;")));
            presionado.play();
        }

        if (k.getText().equals("|")) {
            rowBtn2[0].setStyle("-fx-base: #3ecc27;");
            presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[0].setStyle("-fx-base: #f0f0f0;")));
            presionado.play();
        }

        if (k.getText().equals("Ñ") || k.getText().equals("ñ")) {
            rowBtn4[10].setStyle("-fx-base: #3ecc27;");
            presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[10].setStyle("-fx-base: #f0f0f0;")));
            presionado.play();
        }

        switch (k.getCode()) {
            case ESCAPE:
                rowBtn1[0].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[0].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F1:
                rowBtn1[1].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[1].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F2:
                rowBtn1[2].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[2].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F3:
                rowBtn1[3].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[3].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F4:
                rowBtn1[4].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[4].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F5:
                rowBtn1[5].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[5].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F6:
                rowBtn1[6].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[6].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F7:
                rowBtn1[7].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[7].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F8:
                rowBtn1[8].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[8].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F9:
                rowBtn1[9].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F10:
                rowBtn1[10].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[10].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F11:
                rowBtn1[11].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[11].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F12:
                rowBtn1[12].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[12].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case INSERT:
                rowBtn1[13].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn1[13].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            //Segunda Fila Botones

            case DIGIT1:
                rowBtn2[1].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[1].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT2:
                rowBtn2[2].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[2].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT3:
                rowBtn2[3].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[3].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT4:
                rowBtn2[4].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[4].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT5:
                rowBtn2[5].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[5].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT6:
                rowBtn2[6].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[6].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT7:
                rowBtn2[7].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[7].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT8:
                rowBtn2[8].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[8].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT9:
                rowBtn2[9].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DIGIT0:
                rowBtn2[10].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[10].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case QUOTE:
                rowBtn2[11].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[11].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case BACK_SPACE:
                rowBtn2[13].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn2[13].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            //Tercera Fila Botones

            case TAB:
                rowBtn3[0].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[0].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case Q:
                rowBtn3[1].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[1].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case W:
                rowBtn3[2].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[2].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case E:
                rowBtn3[3].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[3].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case R:
                rowBtn3[4].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[4].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case T:
                rowBtn3[5].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[5].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case Y:
                rowBtn3[6].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[6].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case U:
                rowBtn3[7].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[7].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case I:
                rowBtn3[8].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[8].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case O:
                rowBtn3[9].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case P:
                rowBtn3[10].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[10].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DEAD_ACUTE:
                rowBtn3[11].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[11].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case PLUS:
                rowBtn3[12].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[12].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case ENTER:
                rowBtn3[13].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn3[13].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            //Cuarta Fila De Botones

            case CAPS:
                rowBtn4[0].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[0].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case A:
                rowBtn4[1].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[1].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;


            case S:
                rowBtn4[2].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[2].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case D:
                rowBtn4[3].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[3].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case F:
                rowBtn4[4].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[4].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case G:
                rowBtn4[5].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[5].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case H:
                rowBtn4[6].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[6].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case J:
                rowBtn4[7].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[7].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case K:
                rowBtn4[8].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[8].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case L:
                rowBtn4[9].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case BRACELEFT:
                rowBtn4[11].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[11].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case BRACERIGHT:
                rowBtn4[12].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn4[12].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            //Quinta fila de botones

            case SHIFT:
                rowBtn5[0].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[0].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case Z:
                rowBtn5[1].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[1].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case X:
                rowBtn5[2].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[2].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case C:
                rowBtn5[3].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[3].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case V:
                rowBtn5[4].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[4].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case B:
                rowBtn5[5].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[5].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case N:
                rowBtn5[6].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[6].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case M:
                rowBtn5[7].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[7].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case COMMA:
                rowBtn5[8].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[8].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case PERIOD:
                rowBtn5[9].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case MINUS:
                rowBtn5[10].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case END:
                rowBtn5[12].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[12].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case UP:
                rowBtn5[13].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn5[13].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            //Sexta fila de botones

            case CONTROL:
                rowBtn6[0].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[0].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();

                rowBtn6[7].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[7].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case WINDOWS:
                rowBtn6[2].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[2].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case ALT:
                rowBtn6[3].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[3].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case SPACE:
                rowBtn6[4].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[4].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case ALT_GRAPH:
                rowBtn6[5].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[5].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case CONTEXT_MENU:
                rowBtn6[6].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[6].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case RIGHT:
                rowBtn6[10].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[10].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case DOWN:
                rowBtn6[9].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[9].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;

            case LEFT:
                rowBtn6[8].setStyle("-fx-base: #3ecc27;");
                presionado = new Timeline(new KeyFrame(Duration.seconds(0.1), event1 -> rowBtn6[8].setStyle("-fx-base: #f0f0f0;")));
                presionado.play();
                break;
        }
    }

    public static void initTeclado(){

        h1 = new HBox();
        h1.setAlignment(Pos.CENTER);
        h1.setSpacing(1);
        rowBtn1 = new Button[14];
        for (int i = 0; i < rowBtn1.length; i++) {
            rowBtn1[i] = new Button(rowTeclas1[i]);
            rowBtn1[i].setPrefSize(200,50);
            h1.getChildren().add(rowBtn1[i]);
        }

        h2 = new HBox();
        h2.setAlignment(Pos.CENTER);
        h2.setSpacing(1);
        rowBtn2 = new Button[14];
        for (int i = 0; i < rowBtn2.length; i++) {
            rowBtn2[i] = new Button(rowTeclas2[i]);
            rowBtn2[i].setPrefSize(190,50);
            h2.getChildren().add(rowBtn2[i]);
        }

        h3 = new HBox();
        h3.setAlignment(Pos.CENTER);
        h3.setSpacing(1);
        rowBtn3 = new Button[14];
        for (int i = 0; i < rowBtn3.length; i++) {
            rowBtn3[i] = new Button(rowTeclas3[i]);
            rowBtn3[i].setPrefSize(190,50);
            h3.getChildren().add(rowBtn3[i]);
        }

        rowBtn3[13].setPrefSize(190,55);

        h4 = new HBox();
        h4.setAlignment(Pos.CENTER);
        h4.setSpacing(1);
        rowBtn4 = new Button[13];
        for (int i = 0; i < rowBtn4.length; i++) {
            rowBtn4[i] = new Button(rowTeclas4[i]);
            rowBtn4[i].setPrefSize(185,50);
            h4.getChildren().add(rowBtn4[i]);
        }

        rowBtn4[0].setPrefSize(200,50);

        h5 = new HBox();
        h5.setAlignment(Pos.CENTER);
        h5.setSpacing(1);
        rowBtn5 = new Button[14];
        for (int i = 0; i < rowBtn5.length; i++) {
            rowBtn5[i] = new Button(rowTeclas5[i]);
            rowBtn5[i].setPrefSize(190,50);
            h5.getChildren().add(rowBtn5[i]);
        }

        rowBtn5[0].setPrefSize(260,50);

        h6 = new HBox();
        h6.setAlignment(Pos.CENTER);
        h6.setSpacing(1);
        rowBtn6 = new Button[11];
        for (int i = 0; i < rowBtn6.length; i++) {
            rowBtn6[i] = new Button(rowTeclas6[i]);
            rowBtn6[i].setPrefSize(170,50);
            h6.getChildren().add(rowBtn6[i]);
        }

        rowBtn6[4].setPrefSize(290,50);

    }

    public void iniciarTaqui(){

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Abrir Archivo...");
            File file = fileChooser.showOpenDialog(this);
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            String linea;
            while((linea = reader.readLine()) != null){
                txaTexto.setText( txaTexto.getText() + linea);
                arr += linea;
            }
            reader.close();
            timer.setVisible(true);
            timer.fire();
            btnIniciar.setVisible(false);
            txaTexto.requestFocus();
            pos_linea=0; contPalabra=0; totalChar = arr.length();
            txaTexto.setStyle("-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
            txaTexto.selectRange(pos_linea, pos_linea+1);

            //Simula la escritura

            txaEscritura.setOnKeyTyped(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {

                    if(!esIniciado){
                        esIniciado = true;
                        timer.fire();
                    }

                    tecla_esperada = arr.charAt(pos_linea); //posicion en el texto original
                    tecla_escrita = event.getCharacter().charAt(0); //Con esto se puede guardar lo tecleado por usuario y comparar

                    if(pos_linea == arr.length()-1){
                        timeline.stop();
                        txaEscritura.setEditable(false);
                        obtResultado(txaTexto.getText(), txaEscritura.getText(), tempo.getText());
                    }else{
                        if(pos_linea < arr.length()){
                            if(tecla_escrita != tecla_esperada){
                                pos_linea++;
                                txaTexto.setStyle("-fx-background-color: #ff2320;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                                txaTexto.selectRange(pos_linea, pos_linea+1);
                            } else{
                                if(pos_linea == ' ')
                                    contPalabra++;
                                pos_linea++;
                                txaTexto.setStyle("-fx-background-color: white;-fx-highlight-fill: #bbdefb; -fx-highlight-text-fill: #2196f3;");
                                txaTexto.selectRange(pos_linea, pos_linea+1);
                            }
                        }
                    }
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void tempo(){
        if(millis == 1000) {
            secs++;
            millis = 0;
        }
        if(secs == 60) {
            mins++;
            secs = 0;
        }
        tempo.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs);
        millis++;
        if(tempo.getText().equals("03:00")){
            timer.fire();
        }
    }

    public static void obtResultado(String txtO, String txtC, String tiempo){

        char[] txtEChar, txtOChar;
        int contError=0, contAciertos=0;

        txtEChar = txtC.toCharArray();
        txtOChar = txtO.toCharArray();


        for (int i = 0; i < txtO.length()-1 ; i++) {
            if (txtOChar[i] == txtEChar[i]){
                contAciertos++;
            }else{
                contError++;
            }
        }

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Resultados");
        mensaje.setHeaderText(null);
        mensaje.setContentText("Tuviste: "+contAciertos + " Aciertos." + "\n\n" +
                "Con: " + contError + " Errores" + "\n\n" +
                "En un tiempo de: " + tiempo);
        mensaje.showAndWait();

        Alert salida = new Alert(Alert.AlertType.WARNING);
        salida.setTitle("Saliendo...");
        salida.setHeaderText(null);
        salida.setContentText("Gracias por usar el taquimecanografo");
        salida.showAndWait();
        System.exit(0);
    }


}
