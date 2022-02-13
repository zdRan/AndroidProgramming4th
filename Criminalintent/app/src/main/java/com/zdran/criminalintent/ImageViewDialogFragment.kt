package com.zdran.criminalintent

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import java.io.File

private const val FILE = "file"

class ImageViewDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val file = arguments?.getSerializable(FILE) as File
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.image_dialg_fragment, null)
        val imageView = view.findViewById<ImageView>(R.id.show_image)
        val bitmap = getScaledBitmap(file.path, requireActivity())
        imageView.setImageBitmap(bitmap)
        return AlertDialog.Builder(requireContext()).setView(view).create()
    }

    companion object {
        fun newInstance(file: File): ImageViewDialogFragment {
            val args = Bundle().apply {
                putSerializable(FILE, file)
            }
            return ImageViewDialogFragment().apply {
                arguments = args
            }
        }
    }
}