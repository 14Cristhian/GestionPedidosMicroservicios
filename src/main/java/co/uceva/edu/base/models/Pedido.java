package co.uceva.edu.base.models;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pedido {


    private  Long UID_Pedido;
    private Long id_tienda;
    private double precio_total;
    private String timeStamp;


    public String getTimeStampFormat(){
        return this.timeStamp.split("\\.")[0];
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Long getUID_Pedido() {
        return UID_Pedido;
    }

    public void setUID_Pedido(Long UID_Pedido) {
        this.UID_Pedido = UID_Pedido;
    }

    public Long getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(Long id_tienda) {
        this.id_tienda = id_tienda;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }


}
