public class Producto {
    private Integer id;
    private String tipo;

    public Producto(Integer id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }
}
