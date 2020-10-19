package cursos.com.petagramendpoint.pojo;

/**
 * Created by
 */
public class Mascota {

    private String idMascota;
    private String imagen;
    private String nombre;
    private int likes;

    public Mascota() {
    }

    public Mascota(String idMascota, String imagen, String nombre, int likes) {
        this.idMascota = idMascota;
        this.imagen = imagen;
        this.nombre = nombre;
        this.likes = likes;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
