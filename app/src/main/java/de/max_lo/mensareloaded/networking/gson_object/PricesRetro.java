package de.max_lo.mensareloaded.networking.gson_object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PricesRetro {

    @SerializedName("students")
    @Expose
    private float students;
    @SerializedName("employees")
    @Expose
    private Object employees;
    @SerializedName("pupils")
    @Expose
    private Object pupils;
    @SerializedName("others")
    @Expose
    private Object others;

public float getStudents() {
        return students;
    }

    public void setStudents(float students) {
        this.students = students;
    }

    public Object getEmployees() {
        return employees;
    }

    public void setEmployees(Object employees) {
        this.employees = employees;
    }

    public Object getPupils() {
        return pupils;
    }

    public void setPupils(Object pupils) {
        this.pupils = pupils;
    }

    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
        this.others = others;
    }

}