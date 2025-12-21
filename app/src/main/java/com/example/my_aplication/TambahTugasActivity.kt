package com.example.my_aplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class TambahTugasActivity : AppCompatActivity() {

    private lateinit var etJudulTugas: EditText
    private lateinit var etDeskripsiTugas: EditText
    private lateinit var layoutPilihTanggal: LinearLayout
    private lateinit var tvTanggalTugas: TextView
    private lateinit var layoutPilihWaktu: LinearLayout
    private lateinit var tvWaktuTugas: TextView
    private lateinit var btnSimpanTugas: Button

    private var tanggalDipilih: String? = null
    private var waktuDipilih: String? = null

    private lateinit var preferensiTugas: TugasPreferences
    private var modeEdit = false
    private var idTugasEdit: String? = null
    private var statusSelesaiEdit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_tugas)

        // Inisialisasi view
        etJudulTugas = findViewById(R.id.et_judul_tugas)
        etDeskripsiTugas = findViewById(R.id.et_isi_tugas)
        layoutPilihTanggal = findViewById(R.id.layout_tanggal_tugas)
        tvTanggalTugas = findViewById(R.id.tv_tanggal_tugas)
        layoutPilihWaktu = findViewById(R.id.layout_waktu_tugas)
        tvWaktuTugas = findViewById(R.id.tv_waktu_tugas)
        btnSimpanTugas = findViewById(R.id.btn_simpan)

        hideSystemUI()
        preferensiTugas = TugasPreferences(this)
        cekModeEdit()
        setupPickerTanggal()
        setupPickerWaktu()
        setupTombolSimpan()
    }

    private fun cekModeEdit() {
        val id = intent.getStringExtra("id")
        val judul = intent.getStringExtra("judul")
        val deskripsi = intent.getStringExtra("deskripsi")
        val tanggal = intent.getStringExtra("tanggal")
        val isSelesai = intent.getBooleanExtra("isSelesai", false)

        if (id != null) {
            modeEdit = true
            idTugasEdit = id
            statusSelesaiEdit = isSelesai

            etJudulTugas.setText(judul)
            etDeskripsiTugas.setText(deskripsi)

            if (tanggal != null) {
                val bagian = tanggal.split(" ")
                if (bagian.isNotEmpty()) {
                    tanggalDipilih = bagian[0]
                    tvTanggalTugas.text = tanggalDipilih
                    if (bagian.size > 1) {
                        waktuDipilih = bagian[1]
                        tvWaktuTugas.text = waktuDipilih
                    }
                }
            }
            btnSimpanTugas.text = "Update"
        } else {
            modeEdit = false
            btnSimpanTugas.text = "Simpan"
        }
    }

    private fun setupPickerTanggal() {
        layoutPilihTanggal.setOnClickListener {
            val kalender = Calendar.getInstance()
            val tahun = kalender.get(Calendar.YEAR)
            val bulan = kalender.get(Calendar.MONTH)
            val hari = kalender.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, y, m, d ->
                tanggalDipilih = String.format("%02d/%02d/%04d", d, m + 1, y)
                tvTanggalTugas.text = tanggalDipilih
            }, tahun, bulan, hari).show()
        }
    }

    private fun setupPickerWaktu() {
        layoutPilihWaktu.setOnClickListener {
            val kalender = Calendar.getInstance()
            val jam = kalender.get(Calendar.HOUR_OF_DAY)
            val menit = kalender.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, h, m ->
                waktuDipilih = String.format("%02d:%02d", h, m)
                tvWaktuTugas.text = waktuDipilih
            }, jam, menit, true).show()
        }
    }

    private fun setupTombolSimpan() {
        btnSimpanTugas.setOnClickListener {
            val judul = etJudulTugas.text.toString().trim()
            val deskripsi = etDeskripsiTugas.text.toString().trim()
            if (judul.isEmpty()) {
                Toast.makeText(this, "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (deskripsi.isEmpty()) {
                Toast.makeText(this, "Isi tugas tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (tanggalDipilih.isNullOrEmpty() || waktuDipilih.isNullOrEmpty()) {
                Toast.makeText(this, "Tanggal dan waktu harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val tanggalWaktuGabungan = "$tanggalDipilih $waktuDipilih"

            if (modeEdit && idTugasEdit != null) {
                val tugasUpdate = Tugas(
                    id = idTugasEdit!!,
                    judul = judul,
                    deskripsi = deskripsi,
                    tanggal = tanggalWaktuGabungan,
                    waktu = waktuDipilih!!,
                    isSelesai = statusSelesaiEdit,
                    timestamp = System.currentTimeMillis()
                )
                preferensiTugas.updateTugas(tugasUpdate)
                Toast.makeText(this, "Tugas berhasil diupdate", Toast.LENGTH_SHORT).show()
            } else {
                val tugasBaru = Tugas(
                    judul = judul,
                    deskripsi = deskripsi,
                    tanggal = tanggalWaktuGabungan,
                    waktu = waktuDipilih?:"00:00",
                    isSelesai = false,
                    timestamp = System.currentTimeMillis()
                )
                preferensiTugas.addTugas(tugasBaru)
                Toast.makeText(this, "Tugas berhasil disimpan", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }
}
