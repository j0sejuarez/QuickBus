package com.example.quickbus;

public class Bus {
    private String ruta;
    private int num_camion;
    private String tiempo_est;
    private String sig_parada;
    private String estado;

    public Bus(String ruta,int num_camion, String tiempo_est, String sig_parada, String estado) {
        this.ruta = ruta;
        this.num_camion = num_camion;
        this.tiempo_est = tiempo_est;
        this.sig_parada = sig_parada;
        this.estado = estado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getNum_camion() {
        return num_camion;
    }

    public void setNum_camion(int num_camion) {
        this.num_camion = num_camion;
    }

    public String getTiempo_est() {
        return tiempo_est;
    }

    public void setTiempo_est(String tiempo_est) {
        this.tiempo_est = tiempo_est;
    }

    public String getSig_parada() {
        return sig_parada;
    }

    public void setSig_parada(String sig_parada) {
        this.sig_parada = sig_parada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
