package sample.ui;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.events.EventosMemorama;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class  Memorama extends Stage implements EventHandler {

    private String[] arImagenes = {"Avestruz.jpg", "Buho.jpg", "Cebra.jpg", "Gorila.jpg", "Leon.jpg", "loro.jpg", "Mariposa.jpg", "Pav_Real.jpg", "Serpiente.jpg", "Tigre.jpg", "Tucan.jpg", "elefante.jpg", "abeja.jpg"};

    private Label lblTarjetas;
    private TextField txtNoTarjetas;
    private Button btnAceptar;
    private HBox hBox;
    private VBox vBox;
    private GridPane gdpMesa;
    private Label msgAciertos;
    private Label lblAciertos;
    private Button[][] arTarjetas;
    private String[][] arAsignacion; //para la logica
    int contAv, contPavo, contBuh, contCeb, contElefante, contAbeja, contGo, contLe, contLo, contMa, contSe, contTi, contTu, posImg, pares;
    private int noCartas, aciertos, ganar, intentos;
    static int posx, posy;
    private ArrayList<String> auxPosiciones = new ArrayList<>();
    List<Integer> auxPos = new ArrayList<>();
    private Scene escena;


    public Memorama() {

        CrearUI();
        this.setTitle("Memorama :)");
        this.setScene(escena);
        this.setMaximized(true);
        this.show();
    }

    private void CrearUI() {

        lblTarjetas = new Label("Número de Pares:"); //num de tarjetas
        txtNoTarjetas = new TextField();
        msgAciertos = new Label("Intentos: ");
        lblAciertos = new Label();
        btnAceptar = new Button("Jugar!");
        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, this);


        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas, txtNoTarjetas, msgAciertos, lblAciertos, btnAceptar);
        hBox.setSpacing(10);

        gdpMesa = new GridPane();
        vBox = new VBox();
        vBox.getChildren().addAll(hBox, gdpMesa);

        escena = new Scene(vBox, 1280, 720);

    }

    @Override
    public void handle(Event event) {//Evento para validar que se ponga el numero de cartas con el que se desea jugar

        if (txtNoTarjetas.getText().equals("")) {
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Error!");
            alert3.setHeaderText("Te falto algo");
            alert3.setContentText("Ingresa numero de cartas");
            alert3.showAndWait();
        }


        try {
            noCartas = Integer.parseInt(txtNoTarjetas.getText()); //Tomamos total de cartas a jugar

        } catch (NumberFormatException e) {

        }
        try {
            if (noCartas >= 12) {
                Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                alert4.setTitle("Atencion");
                alert4.setHeaderText("!!!!!!!!!");
                alert4.setContentText("Numero de cartas disponibles 11");
                alert4.showAndWait();
            } else {
                if (noCartas == 3) {
                    ganar = 3;
                } else if (noCartas == 4) {
                    ganar = 4;
                } else if (noCartas == 5) {
                    ganar = 5;
                } else if (noCartas == 6) {
                    ganar = 6;
                } else if (noCartas == 7) {
                    ganar = 7;
                } else if (noCartas == 8) {
                    ganar = 8;
                } else if (noCartas == 9) {
                    ganar = 9;
                } else if (noCartas == 10) {
                    ganar = 10;
                } else if (noCartas == 11) {
                    ganar = 11;
                }
                initJuego();
            }

        } catch (NullPointerException e) {

        }


    }

    private void initJuego() { //se inicia el juego

        btnAceptar.setVisible(false);
        vBox.getChildren().remove(gdpMesa);

        arAsignacion = new String[2][noCartas];
        revolver();
        arTarjetas = new Button[2][noCartas];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < noCartas; j++) {
                Image img = new Image("sample/assets/carta_base.jpg");
                ImageView imv = new ImageView(img);
                imv.setFitHeight(80);
                imv.setPreserveRatio(true);

                arTarjetas[i][j] = new Button();

                int finalI = i;//variables auxiliares de posiciones del boton para enviar a metodo ver tarjeta
                int finalJ = j;

                arTarjetas[i][j].setOnAction(event -> { //se le da una accion a cada posicion del arreglo de tarjeta
                    verTarjeta(finalI, finalJ);
                });
                arTarjetas[i][j].setGraphic(imv);
                arTarjetas[i][j].setPrefSize(30, 30);
                gdpMesa.add(arTarjetas[i][j], j, i);
            }
        }
        vBox.getChildren().add(gdpMesa);//se le agrega el gridpane
    }

    private void checarPares(ArrayList<String> pos) {//metodo para verificar si la posicion del arreglo son pares

        //Llamamos al arraylist de nombres
        if (pos.get(0).equals(pos.get(1))) {
            aciertos++;
            if (aciertos == ganar) {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Memorama Completado!!!");
                    alert.setHeaderText("Haz acertado a todos los pares!");
                    alert.setContentText("Tu puntuacion: " + aciertos + " de " + ganar);
                    alert.showAndWait();
                    Thread.sleep(2000);//hilo para pausar el programa
                    System.exit(0);//cierra el programa
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            //Resetiamos Arraylist De Control
            auxPos.removeAll(auxPos);
            pos.removeAll(pos);
            pares = 0;

        } else {

            Image restore1 = new Image("sample/assets/carta_base.jpg");
            ImageView imvrestore = new ImageView(restore1);
            imvrestore.setFitHeight(80);
            imvrestore.setPreserveRatio(true);
            arTarjetas[auxPos.get(0)][auxPos.get(1)].setPrefSize(30, 30);//para volver a poner la carta de base que no se le atino
            arTarjetas[auxPos.get(0)][auxPos.get(1)].setGraphic(imvrestore);

            Image restore2 = new Image("sample/assets/carta_base.jpg");
            ImageView imvrestore2 = new ImageView(restore2);
            imvrestore2.setFitHeight(80);
            imvrestore2.setPreserveRatio(true);
            arTarjetas[auxPos.get(2)][auxPos.get(3)].setPrefSize(30, 30);
            arTarjetas[auxPos.get(2)][auxPos.get(3)].setGraphic(imvrestore2);

            intentos++;

            lblAciertos.setText(String.valueOf(intentos));

            if (intentos == ganar) {
                try {
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Perdiste!");
                    alert2.setHeaderText("Ups no fuiste tan acertado!");
                    alert2.setContentText("Tu puntuacion: " + aciertos + " de " + ganar);
                    alert2.showAndWait();
                    Thread.sleep(2000);
                    System.exit(0);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }

            //Resetiamos Arraylist De Control
            auxPos.removeAll(auxPos);
            pos.removeAll(pos);
            pares = 0;
        }
    }

    private void verTarjeta(int finalI, int finalJ) {

        auxPosiciones.add(arAsignacion[finalI][finalJ]); // Almacena Nombres De Tarjeta en esa Posicion
        Image img = new Image("sample/assets/" + arAsignacion[finalI][finalJ]);
        ImageView imv = new ImageView(img);
        imv.setFitHeight(80);
        imv.setPreserveRatio(true);
        arTarjetas[finalI][finalJ].setGraphic(imv);

        if (pares < 2) {//lleva el control de los pares de las tarjetas
            //auxPos - Arraylist Que almacena posicion entera (x,y)
            auxPos.add(finalI);
            auxPos.add(finalJ);
            pares++;
            if (pares == 2) {
                checarPares(auxPosiciones);
            }
        }

    }


    private void revolver() {

        for (int i = 0; i < 2; i++)
            for (int j = 0; j < noCartas; j++) {
                arAsignacion[i][j] = "";
            }

        posImg = (int) (Math.random() * arImagenes.length);//genera numero random para la posicion de la imagen
        for (int i = 0; i < noCartas; ) {
            posx = (int) (Math.random() * 2) + 0;
            posy = (int) (Math.random() * noCartas) + 0;
            if (arAsignacion[posx][posy].equals("")) {

                switch (arImagenes[posImg]) {
                    case "Avestruz.jpg":
                        contAv++;//contador para la tarjeta
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contAv == 2) {
                            i++;
                            arImagenes[posImg] = "";//se borra el nombre del arreglo para que no se trabe
                            posImg = (int) (Math.random() * arImagenes.length);//se vuelve a calcular una posicion de imagen aleatoria
                        }
                        break;
                    case "Buho.jpg":
                        contBuh++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contBuh == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Cebra.jpg":
                        contCeb++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contCeb == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Gorila.jpg":
                        contGo++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contGo == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);

                        }
                        break;
                    case "Leon.jpg":
                        contLe++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contLe == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "loro.jpg":
                        contLo++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contLo == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Mariposa.jpg":
                        contMa++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contMa == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Serpiente.jpg":
                        contSe++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contSe == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Tigre.jpg":
                        contTi++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contTi == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Tucan.jpg":
                        contTu++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contTu == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;
                    case "Pav_Real.jpg":
                        contPavo++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contPavo == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;

                    case "elefante.jpg":
                        contElefante++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contElefante == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;

                    case "abeja.jpg":
                        contAbeja++;
                        arAsignacion[posx][posy] = arImagenes[posImg];
                        if (contAbeja == 2) {
                            i++;
                            arImagenes[posImg] = "";
                            posImg = (int) (Math.random() * arImagenes.length);
                        }
                        break;

                    default://cuando no es ninguno de los casos anteriores calcula otra posicion aleatoria
                        posImg = (int) (Math.random() * arImagenes.length);
                        break;
                }
            }

        }
    }


}