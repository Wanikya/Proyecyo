package com.example.proyecyo;

import java.util.*;
import java.math.BigDecimal;

import javafx.scene.control.*;
import org.apache.commons.math3.distribution.NormalDistribution;
import javafx.fxml.FXML;

public class Metodos {
    double j;
    double p2;
    public static double x,y,z;
    @FXML private TextField datos;
    @FXML private TextField nc;
    @FXML private Label corrida;
    @FXML private Label longi;
    @FXML private Label nco;
    @FXML private Label ndatos;
    @FXML private Label media;
    @FXML private Label sigma;
    @FXML private Label zo;
    @FXML private Label zalfa;
    @FXML private Label ncor;
    @FXML private Label inv;
    @FXML private Label resu;

    public static List<Double> parseNumbers(String data) {
        List<Double> numbers = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            currentNumber.append(c);
            if (i + 1 < data.length() && data.charAt(i + 1) == '.' && Character.isDigit(c)) {
                try {
                    double number = Double.parseDouble(currentNumber.toString());
                    numbers.add(number);
                    currentNumber.setLength(0);
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear: " + currentNumber.toString());
                }
            }
        }
        return numbers;
    }
    public void Alto(){
        List<Double> lista = new ArrayList<>();
        lista=parseNumbers(this.datos.getText());
        if (nc.getText()!=""){
            j= Double.parseDouble(nc.getText());
            p2= (1 - j/100) /2;
            List<Integer> valores = listaS(lista);
            HashMap<Integer, Integer> map = corridas(valores);
            double c = Media(lista);
            double a = Sigma(lista);
            double h = Z(lista);
            double f = invNormEstand(1-p2);

            System.out.println("Los valores dados fueron:");
            System.out.print(lista);
            System.out.println("Las corridas son:");
            System.out.println(valores);
            corrida.setText(String.valueOf(valores));
            System.out.println(map);
            longi.setText(String.valueOf(map));
            System.out.println("Números de datos=" +lista.size());
            ndatos.setText(String.valueOf(lista.size()));
            System.out.println("Corridas totales = "+map.size());
            nco.setText(String.valueOf(map.size()));
            System.out.println("Media de corridas es: "+c);
            media.setText(String.valueOf(c));
            System.out.println("Sigma cuadrada de corridas = "+a);
            sigma.setText(String.valueOf(a));
            System.out.println("Z es: "+h);
            zo.setText(String.valueOf(h));
            System.out.println("El valor de confianza es: "+j);
            ncor.setText(String.valueOf(j));
            System.out.println("Zalfa/2 es: "+p2);
            zalfa.setText(String.valueOf(p2));
            System.out.println("Inversa nomral estandar: "+f);
            inv.setText(String.valueOf(f));
            if(p2>f){
                resu.setText("Ho se rechaza y los datos no son de una serie U(0, 1)");
            } else{
                resu.setText("Ho se acepta y los datos son de una serie U(0, 1)");
            }
        }
        else {
            System.out.println("Error");
        }

    }
    public static List<Integer> listaS(List<Double> listaDeDatos){
        List<Integer> listaS = new ArrayList<>();
        for(int i = 1; i<listaDeDatos.size();i++){
            if(listaDeDatos.get(i) > listaDeDatos.get(i-1)){
                listaS.add(1);
            }else
                listaS.add(0);
        }
        return listaS;
    }

    public static HashMap<Integer,Integer> corridas(List<Integer> lista){
        HashMap<Integer,Integer> corridas = new HashMap<>();
        int numCorrida = 1;
        int valorActual;
        int contador = 1;
        for (int i = 0; i<lista.size();){
            valorActual = lista.get(i);
            while (i + contador < lista.size() && lista.get(i + contador) == valorActual) {
                contador++;
            }
            corridas.put(numCorrida, contador);
            i = i+contador;
            contador=1;
            numCorrida++;
        }
        return corridas;
    }
    public double Media(List<Double> lista){
        x= (double) ((2 * lista.size()) - 1) / 3;
        return x;
    }
    public double Sigma(List<Double> lista){
        y = (double) ((16* lista.size())-29)/90;
        return y;
    }
    public double Z(List<Double> lista){
        List<Integer> valores = listaS(lista);
        HashMap<Integer, Integer> map = corridas(valores);
        z= (double) (map.size()-x/(Math.sqrt(y)));
        return z;
    }
    public static double invNormEstand(double probability) {
        if (probability <= 0.0 || probability >= 1.0) {
            throw new IllegalArgumentException("La probabilidad debe estar entre 0 y 1.");
        }

        NormalDistribution normalDistribution = new NormalDistribution();
        return normalDistribution.inverseCumulativeProbability(probability);
    }
}
