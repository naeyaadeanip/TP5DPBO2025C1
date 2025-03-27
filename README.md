# TP5DPBO2025C1
# Janji
Saya Naeya Adeani Putri dengan NIM 2304017 mengerjakan Tugas Praktikum Latihan 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program
A. Struktur Kelas

Program ini menggunakan beberapa kelas utama:

- `Menu.java` → Kelas utama yang mengelola tampilan GUI dan interaksi pengguna.
- `Mahasiswa.java` → Kelas model untuk menyimpan data mahasiswa.
- `Database.java` → Kelas untuk menghubungkan aplikasi dengan database MySQL.

B. Relasi Antar Kelas

- `Menu.java` berinteraksi langsung dengan `Database.java` untuk mengambil dan menyimpan data.
- Data mahasiswa ditampung dalam `ArrayList<Mahasiswa>` di `Menu.java` sebelum disinkronisasi ke tabel GUI.
- `Mahasiswa.java` hanya sebagai model penyimpanan data sebelum diproses ke database.

# Alur Program
A. Load Data Saat Program Dibuka

- Program dijalankan.
- `Menu` memanggil `setTable()` untuk mengambil data dari database menggunakan `Database.selectQuery()`.
- Data ditampilkan ke dalam tabel GUI (`JTable`).
- Jika database kosong, tabel tetap muncul tetapi tanpa isi.

B. Menambah Data Mahasiswa

1. Pengguna mengisi form (**NIM, Nama, Nilai, Alamat, Jenis Kelamin**).
2. Validasi NIM:
   - Jika NIM sudah ada di database (`isNimUnique()`), tampilkan error.
   - Jika valid, lanjut ke langkah berikutnya.
3. Data dimasukkan ke dalam database menggunakan fungsi `insertData()`: -
   - SQL: `INSERT INTO mahasiswa VALUES (...)`
   - Tambahkan data ke `listMhs`.
   - Refresh tabel GUI (`setTable()`).
   - Tampilkan pesan sukses.

C. Mengupdate Data Mahasiswa
  
1. Pengguna memilih baris data di tabel.
2. Form akan otomatis terisi dengan data mahasiswa yang dipilih.
3. Pengguna mengubah data lalu menekan tombol Update.
4. Data di listMhs diperbarui.
5. Tabel GUI direfresh (setTable()).
6. Tampilkan pesan sukses.

D. Menghapus Data Mahasiswa

1. Pengguna memilih baris data di tabel.
2. Program meminta konfirmasi penghapusan (`JOptionPane.showConfirmDialog`).
3. Jika pengguna memilih "Ya":
   - SQL: `DELETE FROM mahasiswa WHERE id=?`
   - Hapus data dari `listMhs`.
   - Refresh tabel GUI (`setTable()`).
   - Tampilkan pesan sukses.

# DOKUMENTASI
## Prompt saat mau menghapus data
![prompt saat mau menghapus data](https://github.com/user-attachments/assets/0078b25e-4a75-4451-a191-ba4d2e2a54fc)

## Prompt jika data sudah dihapus
![prompt jika data sudah dihapus](https://github.com/user-attachments/assets/05d4310e-0d33-475c-a746-f834ee0a5412)

## Homepage
![homepage](https://github.com/user-attachments/assets/a1e61e8b-3355-418c-91f7-e1695b2ec4e5)

## Hasil data setelah dihapus
![hasil data setlah dihapus](https://github.com/user-attachments/assets/b9a94adb-4c61-4384-bb00-944e662dd06e)

## Prompt error jika ada NIM yang sama saat insert
![error jika ada NIM yg sama saat insert](https://github.com/user-attachments/assets/8f928148-a786-483e-a8dc-b5273874af18)

## Database
![database](https://github.com/user-attachments/assets/78a8a29f-f155-493f-ac3e-42d6c7aa273b)

## Add
![add](https://github.com/user-attachments/assets/69e745d0-4aa1-4c42-ad69-248045356785)

## Prompt jika ada input kosong saat update
![ada input kosong saat update](https://github.com/user-attachments/assets/a69bbda2-d9c6-48c1-8cad-30e78c360ab5)

## Prompt jika ada input kosong saat insert
![ada input kosong saat insert](https://github.com/user-attachments/assets/ee0f9907-2f60-4053-bd34-98e822b227e1)
