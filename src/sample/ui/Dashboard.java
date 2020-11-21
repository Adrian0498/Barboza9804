package sample.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Dashboard extends Stage {

    Button btnAdminPlatillo, btnAdminTipoDePlatillo;
    private VBox vBox;
    private Scene escena;

    public Dashboard(){

        CrearUI();
        this.setTitle("Panel de control del Restaurante el Antojito");
        this.setScene(escena);
        this.show();


    }

    private void CrearUI() {
        btnAdminPlatillo = new Button("Administracion de platillo");
        btnAdminTipoDePlatillo = new Button("Administracion de tipo de platillo");
        vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(btnAdminPlatillo, btnAdminTipoDePlatillo);
        escena = new Scene(vBox,250,250);
        btnAdminPlatillo.setOnAction(event -> {
            new PlatilloCRUD();
        });

        btnAdminTipoDePlatillo.setOnAction(event -> {
            new TipoPlatilloCRUD();
        });
    }

}

//Java (Clase) -> MySql(Tabla)
//Java (Objeto) -> MySql(Registro/Renglon)
//Empleados                                   (OK)
//Clientes (Domicilio)                        (OK)
//Platillos                                   (OK)
//Tipo de Platillo                            (OK)
//Pedidos                                     (OK)
//Mesas                                       (OK)

//Empleado (1) - (N) Pedidos
//Pedidos (N) - (1) Clientes
//Pedidos (N) - (N) Platillos----DetallePedido (Cantidad,Precio,Descuento)
//Mesas (1) - (N) Pedidos
//Platillos (N) - (1) TipoPlatillo

//DAO (Object Access Data)
