package com.example.my_aplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class CatatanAdapter(
    private var catatanList: List<Catatan>,
    private val onItemClick: (Catatan) -> Unit,
    private val onEditClick: (Catatan) -> Unit,
    private val onDeleteClick: (Catatan) -> Unit
) : RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {

    inner class CatatanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val kartuCatatan: ConstraintLayout = view.findViewById(R.id.kartucatatan)
        val judulTextView: TextView = view.findViewById(R.id.catatan_judul)
        val deskripsiTextView: TextView = view.findViewById(R.id.catatan_deskripsi)
        val tanggalTextView: TextView = view.findViewById(R.id.tglcatatan)
        val editIcon: ImageView = view.findViewById(R.id.ic_edit_catatan)
        val deleteIcon: ImageView = view.findViewById(R.id.ic_hapus_catatan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catatan, parent, false)
        return CatatanViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val catatan = catatanList[position]

        holder.judulTextView.text = catatan.judul

        // Tampilkan deskripsi dengan bullet point
        holder.deskripsiTextView.text = if (catatan.deskripsi.isNotEmpty()) {
            "• ${catatan.deskripsi}"
        } else {
            "• Tidak ada deskripsi"
        }

        // Tampilkan tanggal jika ada
        holder.tanggalTextView.text = if (catatan.tanggal != null) {
            catatan.tanggal
        } else {
            "Tanpa tanggal"
        }

        // Handle item click (klik seluruh card)
        holder.kartuCatatan.setOnClickListener {
            onItemClick(catatan)
        }

        // Handle edit click
        holder.editIcon.setOnClickListener {
            onEditClick(catatan)
        }

        // Handle delete click
        holder.deleteIcon.setOnClickListener {
            onDeleteClick(catatan)
        }
    }

    override fun getItemCount(): Int = catatanList.size

    // Update data adapter
    fun updateData(newList: List<Catatan>) {
        catatanList = newList
        notifyDataSetChanged()
    }
}