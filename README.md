# TP5DPBO2025C1
# Janji
Saya Naeya Adeani Putri dengan NIM 2304017 mengerjakan Tugas Praktikum Latihan 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program
  A. Struktur Kelas
      Menggunakan beberapa kelas utama:
      Menu.java → Kelas utama yang mengelola tampilan GUI dan interaksi pengguna. 
      Mahasiswa.java → Kelas model untuk menyimpan data mahasiswa. 
      Database.java → Kelas untuk menghubungkan aplikasi dengan database MySQL.

  B. Relasi Antar Kelas
      Menu.java berinteraksi langsung dengan Database.java untuk mengambil dan menyimpan data. 
      Data mahasiswa ditampung dalam ArrayList<Mahasiswa> di Menu.java sebelum disinkronisasi ke tabel GUI. 
      Mahasiswa.java hanya sebagai model penyimpanan data sebelum diproses ke database.
