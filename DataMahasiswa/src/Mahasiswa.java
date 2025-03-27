/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String nilai;
    private String alamat;
    private String jk;

    public Mahasiswa(String nim, String nama, String nilai, String alamat, String jk){
        this.nim = nim;
        this.nama = nama;
        this.nilai = nilai;
        this.alamat = alamat;
        this.jk = jk;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJk() {
        return this.jk;
    }

    public String getNilai() {
        return this.nilai;
    }

    public String getAlamat() {
        return this.alamat;
    }

}