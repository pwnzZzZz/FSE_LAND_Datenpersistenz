package domain;

import java.sql.Date;

public class Student extends BaseEntity {
    private String vn, nn;
    private Date geb;


    public Student(Long id, String vn, String nn, Date geb) {
        super(id);
        setVorname(vn);
        setNachname(nn);
        setGeburtsdatum(geb);

    }

    public Student(String vn, String nn, Date geb) {
        super(null);
        setVorname(vn);
        setNachname(nn);
        setGeburtsdatum(geb);

    }

    public String getVorname() {
        return vn;
    }

    public void setVorname(String vorname) {
        if (vorname != null && vorname.length() > 1) {
            this.vn = vorname;
        } else {
            throw new InvalidValueException("Vorname des Studenten darf nicht leer sein!");
        }
    }

    public String getNachname() {
        return nn;
    }

    public void setNachname(String nachname) {
        if (nachname != null && nachname.length() > 1) {
            this.nn = nachname;
        } else {
            throw new InvalidValueException("Nachname des Studenten darf nicht leer sein!");
        }
    }

    public Date getGeburtsdatum() {
        return geb;
    }

    public void setGeburtsdatum(Date geb) {
        if(geb != null){
            this.geb = geb;
        }else {
            throw new InvalidValueException("Geburtsdatum darf nicht leer sein!");
        }
    }

    @Override
    public String toString() {
        return "Student{id=" + this.getId() + ", " +
                "vn='" + vn + '\'' +
                ", nn='" + nn + '\'' +
                ", geb=" + geb +
                '}';
    }
}
