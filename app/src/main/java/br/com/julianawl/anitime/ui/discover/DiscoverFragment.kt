package br.com.julianawl.anitime.ui.discover

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.SearchManager
import android.content.Context
import android.content.Context.SEARCH_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.PROFILE_PATH
import br.com.julianawl.anitime.ui.QUERY_LENGTH
import br.com.julianawl.anitime.ui.SEARCH_HINT
import br.com.julianawl.anitime.ui.activity.MainActivity
import br.com.julianawl.anitime.ui.adapter.AnimesAdapter
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.custom_loading_dialog.*
import kotlinx.android.synthetic.main.fragment_discover.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

@Suppress("DEPRECATION")
class DiscoverFragment : Fragment() {

    private val viewModel: DiscoverViewModel by viewModels {
        DiscoverViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            AnimesAdapter(context = it)
        }
    }

    private val controller by lazy {
        findNavController()
    }

    private lateinit var userImage: ShapeableImageView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawer: DrawerLayout
    private var discoverPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAnimesResponse()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_discover,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configuraAppBar(view)
        configuraDiscover()
    }

    //configura a toolbar com as ações e o drawer
    private fun configuraAppBar(view: View) {
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarDisc)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_discover))
        val navHostFragment = NavHostFragment.findNavController(this)

        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        configuraNavigationDrawer(view)
        configuraUserImage()
    }

    private fun configuraNavigationDrawer(view: View){
        drawer = view.findViewById(R.id.fragment_discover_container)
        toggle = ActionBarDrawerToggle(
            activity,
            drawer,
            R.string.open,
            R.string.close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    //configura imagem do usuário internamente
    private fun configuraUserImage(){
        val header = nav_drawer.getHeaderView(0)
        userImage = header.findViewById(R.id.user_image)
        userImage.setOnClickListener {
            chooseProfilePic()
        }
        loadImageFromStorage(PROFILE_PATH)
    }

    private fun chooseProfilePic() {
        val items = arrayOf("Camera", "Gallery")
        MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Theme)
            .setTitle(resources.getString(R.string.title_dialog))
            .setItems(items) { dialog, which ->
                if (items[which] == items[0]) {
                    if (checkAndRequestPermissions()) {
                        takePictureFromCamera()
                    }
                } else {
                    takePictureFromGallery()
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun takePictureFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun takePictureFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity((activity as MainActivity).packageManager) != null) {
            startActivityForResult(intent, 2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {//get picture from gallery
                val selectedImage: Uri? = data?.data
                val bitmap = MediaStore.Images.Media
                    .getBitmap(activity?.contentResolver, selectedImage)
                userImage.setImageBitmap(bitmap)
                saveToInternalStorage(bitmap)
            }
            2 -> if (resultCode == RESULT_OK) {//take picture from camera
                val bundle = data?.extras
                val bitmap = bundle?.get("data") as Bitmap
                userImage.setImageBitmap(bitmap)
                saveToInternalStorage(bitmap)
            }
        }
    }

    private fun saveToInternalStorage(bitmap: Bitmap): String {
        val cw = ContextWrapper(activity?.applicationContext)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val path = File(directory, "profile.jpg")

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(path)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return directory.absolutePath
        }
    }

    private fun loadImageFromStorage(path: String) {
        try {
            val f = File(path, "profile.jpg")
            val b = BitmapFactory.decodeStream(object : FileInputStream(f) {})
            userImage.setImageBitmap(b)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //configura a função de pesquisa na appbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_discover, menu)
        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.search_anime).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.search_anime)

        configuraSearchView(searchManager, searchView, searchMenuItem)
    }

    private fun configuraSearchView(
        searchManager: SearchManager,
        searchView: SearchView,
        searchMenuItem: MenuItem
    ){
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = SEARCH_HINT
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                search(newText)
                return true
            }
        })
        searchMenuItem.icon.setVisible(false, false)
    }

    private fun search(query: String?) {
        if (query?.length!! >= QUERY_LENGTH) {
            viewModel.getSearch(query)
            viewModel.mSearchResponse.observe(this@DiscoverFragment, {
                if (it.isSuccessful) {
                    it.body()?.let { result ->
                        discover_list.scrollToPosition(0)
                        adapter?.appendSearch(result.results)
                    }
                }
            })
        }
    }

    //define o adapter e a ação quando clica no anime
    private fun configuraDiscover() {
        adapter?.onItemClickListener = {
            goToDetails(it)
        }
        discover_list.adapter = adapter
        layoutManager = LinearLayoutManager(context)
        discover_list.layoutManager = layoutManager
    }

    private fun setScrollListener() {
        discover_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        discoverPages++
                        discover_list.removeOnScrollListener(this)
                        getAnimes()
                    }
                }
            }
        })
    }

    //chama os animes da lista discover
    private fun getAnimes() {
        viewModel.getAnimes(discoverPages)
    }

    private fun getAnimesResponse(){
        getAnimes()
        viewModel.mResponse.observe(this, {
            if (it.isSuccessful) {
                it.body()?.let { animes ->
                    discover_list.post {
                        adapter?.append(animes.animes)
                        adapter?.notifyDataSetChanged()
                    }
                    setScrollListener()
                }
            } else {
                Toast.makeText(requireContext(), "The end", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //vai para os detalhes quando clica em um anime
    private fun goToDetails(anime: AnimeItem) {
        val direction = DiscoverFragmentDirections
            .actionNavigationDiscoverToDetails(anime)
        controller.navigate(direction)
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
            val cameraPermission = ActivityCompat
                .checkSelfPermission(
                    activity as MainActivity, Manifest.permission.CAMERA
                )
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                    activity as MainActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    20
                )
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}