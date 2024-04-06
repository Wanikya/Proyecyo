package com.example.proyecyo;

import java.util.*;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
public class Metodos {
    public static double x,y,z,f;
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
    public double Sigma(List<Integer> lista){
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

    public static void main(String[] args) {
        Metodos metodos= new Metodos();
        List<Double> lista = new ArrayList<>(Arrays.asList(
                0.86, 0.95, 0.19, 0.3, 0.48, 0.24, 0.97, 0.52, 0.93, 0.24,
                0.57, 0.59, 0.36, 0.54, 0.97, 0.38, 0.83, 0.01, 0.24, 0.55,
                0.44, 0.54, 0.99, 0.55, 0.6, 0.36, 0.19, 0.96, 0.87, 0.79,
                0.51, 0.8, 0.74, 0.27, 0.59, 0.31, 0.83, 0.45, 0.45, 0.72,
                0.59, 0.68, 0.47, 0.28, 0.51, 0.02, 0.17, 0.35, 0.15, 0.27,
                0.65, 0.56, 0.18, 0.76, 0.07, 0.06, 0.56, 0.77, 0.37, 0.71
        ));
        double a=0, h=0;
        double c = (double) ((2 * lista.size()) - 1) / 3;
        a = (double) ((16* lista.size())-29)/90;

        List<Integer> valores = listaS(lista);
        HashMap<Integer, Integer> map = corridas(valores);
        h=((map.size()-c)/(Math.sqrt(a)));

        System.out.println(lista);
        System.out.println(valores);
        System.out.println(map);
        System.out.println("Números de datos=" +lista.size());
        System.out.println("Corridas totales = "+map.size());
        System.out.println("Media de corridas es: "+c);
        System.out.println("Sigma cuadrada de corridas = "+a);
        System.out.println("Z es: "+h);
    }
}
