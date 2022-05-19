package com.zdran.photogallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zdran.photogallery.api.GalleryItem
import com.zdran.photogallery.bingApi.BingFetchr
import com.zdran.photogallery.bingApi.BingGalleryItem

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoGalleryViewModel = ViewModelProviders.of(this)[PhotoGalleryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, 3)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoGalleryViewModel.galleryItemLiveData.observe(
            viewLifecycleOwner,
            { galleryItems ->
                photoRecyclerView.adapter = PhotoAdapter(galleryItems)

            })
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }

    private class PhotoHolder(private val itemTextView: TextView) :
        RecyclerView.ViewHolder(itemTextView) {
        fun bindText(text: String) {
            itemTextView.text = text
        }
    }

    private class PhotoAdapter(private val galleryItems: List<BingGalleryItem>) :
        RecyclerView.Adapter<PhotoHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            return PhotoHolder(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            holder.bindText(galleryItems[position].title)
        }

        override fun getItemCount(): Int {
            return galleryItems.size
        }

    }
}