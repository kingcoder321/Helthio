package com.example.helthio.viewmodel

import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.load
import coil.request.ImageRequest
import com.example.helthio.R
import com.example.helthio.api.ApiClient
import com.example.helthio.model.FigmaFileResponse
import com.example.helthio.model.FigmaImagesResponse
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import retrofit2.Response

class FigmaViewModel : ViewModel() {

    private val _figmaNodeResponse = MutableLiveData<FigmaFileResponse?>()
    private val _figmaImageResponse = MutableLiveData<FigmaImagesResponse?>()

    val figmaNodeResponse: LiveData<FigmaFileResponse?> = _figmaNodeResponse
    val figmaImageResponse: LiveData<FigmaImagesResponse?> = _figmaImageResponse

    private val figmaToken = "figd_mE1Q00fjp5yHrgqcWOmj7iLNWSkfhmZQdfBzblzJ"
    private val figmaFileKey = "yFlCzft7wpwgWpdAaJl4HH"

    fun fetchNodeData(nodeIds: String) {
        viewModelScope.launch {
            try {
                // Call the API
                val response: Response<FigmaFileResponse> = ApiClient.figmaApi.getNodeData(
                    fileKey = figmaFileKey,
                    nodeIds = nodeIds,
                    token = figmaToken
                )
                if (response.isSuccessful) {
                    val nodeData = response.body()
                    // Handle the node data (e.g., update UI)
                    println("Node data: $nodeData")
                    _figmaNodeResponse.postValue(nodeData)
                } else {
                    println("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                println("API call failed: ${e.message}")
            }
        }
    }

    fun fetchImageData(nodeIds: String) {
        viewModelScope.launch {
            try {
                val response: Response<FigmaImagesResponse> = ApiClient.figmaApi.getImages(
                    fileKey = figmaFileKey,
                    nodeIds = nodeIds,
                    token = figmaToken
                )
                if (response.isSuccessful) {
                    // Retrofit auto-parses the JSON response
                    _figmaImageResponse.postValue(response.body())
                }
            } catch (e: Exception) {
                println("API call failed: ${e.message}")
                _figmaImageResponse.postValue(null)
            }
        }
    }
}

// Function to observe and bind the image URL to the ImageView
fun bindImageData(
    viewModel: FigmaViewModel,
    context: LifecycleOwner,
    logoImage: ImageView,
    nodeId: String
) {
    viewModel.figmaImageResponse.observe(context) { response ->
        if (response != null) {
            Log.d("FigmaAPI", "Images: ${response.images}")
            // Get the image URL for the logo
            val imageUrl = response.images[nodeId]
            if (imageUrl != null) {
                // Use Coil to load the image into the ImageView
                logoImage.load(imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.helthio_logo)  // Add a placeholder image if needed
                }
            }
        } else {
            Log.d("FigmaAPI", "Failed to get image data from Figma API.")
        }
    }
}

fun bindNodeTextData(
    viewModel: FigmaViewModel,
    context: LifecycleOwner,
    textView: TextView,
    nodeId: String
) {
    viewModel.figmaNodeResponse.observe(context) { response ->
        if (response != null && response.nodes.containsKey(nodeId)) {
            Log.d("FigmaAPI", "Node data: ${response.nodes[nodeId]?.document?.characters}")
            // Fetch the text content from the node and set it to the TextView
            val textContent = response.nodes[nodeId]?.document?.characters ?: "Default Text"
            textView.text = textContent
        } else {
            Log.d("FigmaAPI", "Failed to get node data from Figma API or node not found.")
        }
    }
}

fun bindButtonData(
    viewModel: FigmaViewModel,
    context: LifecycleOwner,
    button: Button,
    nodeId: String,
    imageUrlNodeId: String? = null,
    onClickAction: () -> Unit,
) {
    viewModel.figmaNodeResponse.observe(context) { response ->
        Log.d("FigmaAPI", "Received response for nodeId: $nodeId")
        if (response != null) {
            if (response.nodes.containsKey(nodeId)) {
                val nodeData = response.nodes[nodeId]?.document
                Log.d("FigmaAPI", "Node data for $nodeId: $nodeData")
                val buttonText = nodeData?.characters ?: "Default Button"
                button.text = buttonText
                button.setOnClickListener {
                    onClickAction()
                }
                Log.d("FigmaAPI", "Button text set to: $buttonText")

                imageUrlNodeId?.let { id ->
                    Log.d("FigmaAPI", "Button text set to: $id")
                    viewModel.figmaImageResponse.observe(context) { imageResponse ->
                        Log.d("FigmaAPI", "Button text set to: $imageResponse")
                        val imageUrl = imageResponse?.images?.get(id)
                        Log.d("FigmaAPI", "Image URL for $id: $imageUrl")
                        imageUrl?.let {
                            loadImageForButton(button, it)
                        }
                    }
                }
            } else {
                Log.w("FigmaAPI", "Node $nodeId not found in response")
            }
        } else {
            Log.e("FigmaAPI", "Received null response from Figma API")
        }
    }
}

fun bindNodeEditTextData(
    viewModel: FigmaViewModel,
    context: LifecycleOwner,
    textInputLayout: TextInputLayout,
    nodeId: String,
    imageUrlNodeId: String? = null
) {
    // Observe text data from the API
    viewModel.figmaNodeResponse.observe(context) { response ->
        if (response != null && response.nodes.containsKey(nodeId)) {
            val textContent = response.nodes[nodeId]?.document?.characters ?: "Default Text"
            Log.d("FigmaAPI", "Failed to get node data from  $textContent.")
            textInputLayout.hint = textContent
        } else {
            Log.d("FigmaAPI", "Failed to get node data from Figma API.")
        }
    }
    Log.d("FigmaAPI", "Failed to get node data from  $imageUrlNodeId.")
    // Observe image data from the API (if provided)
    imageUrlNodeId?.let { id ->
        viewModel.figmaImageResponse.observe(context) { imageResponse ->
            Log.d("FigmaAPI", "Failed to get node data from  $id.")
            val imageUrl = imageResponse?.images?.get(id)
            Log.d("FigmaAPI", "Failed to get node data from  $imageUrl.")
            imageUrl?.let {
                loadImageForTextInputLayout(textInputLayout, it)
            }
        }
    }
}

fun loadImageForTextInputLayout(textInputLayout: TextInputLayout, imageUrl: String) {
    val context = textInputLayout.context
    val size = context.resources.getDimensionPixelSize(R.dimen.editTextDrawableSize)
    Log.d("FigmaAPI", "Failed to get node data from  $imageUrl.")
    // Load the image using Coil
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .size(size)
        .target { drawable ->
            // Set the loaded image as the start icon for the TextInputLayout
            textInputLayout.startIconDrawable = drawable
        }
        .build()
    Coil.imageLoader(context).enqueue(request)
}

fun loadImageForButton(button: Button, imageUrl: String) {
    val context = button.context
    val size = context.resources.getDimensionPixelSize(R.dimen.buttonIconSize)
    Log.d("FigmaAPI", "Failed to get node data from button $imageUrl.")
    // Load the image using Coil
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .size(size)
        .target { drawable ->
            button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)
        }
        .build()
    Coil.imageLoader(context).enqueue(request)
}

