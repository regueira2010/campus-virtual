package com.campusvirtual;

public class NotificadorStub implements Notificador {
    private String ultimoDestinatario;
    private String ultimoMensaje;
    private boolean fueLlamado = false;

    @Override
    public void enviar(String destinatario, String mensaje) {
        this.ultimoDestinatario = destinatario;
        this.ultimoMensaje = mensaje;
        this.fueLlamado = true;
    }

    public boolean fueLlamado() {
        return fueLlamado;
    }

    public String getUltimoDestinatario() {
        return ultimoDestinatario;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}