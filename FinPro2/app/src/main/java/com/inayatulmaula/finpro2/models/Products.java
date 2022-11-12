package com.inayatulmaula.finpro2.models;

public class Products {
    int id;
    private String docId, gender, name, stok, harga, deskripsi, images, kategori, filter;

    public Products(int id, String name, String stok, String harga, String images, String kategori, String filter, String gender, String deskripsi) {
        this.id = id;
        this.name = name;
        this.stok = stok;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.images = images;
        this.kategori = kategori;
        this.filter = filter;
        this.gender = gender;
    }

    public Products(){

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
